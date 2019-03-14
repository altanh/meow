package meow;

import kodkod.ast.Formula;
import kodkod.instance.Bounds;
import meow.engine.GraphCompiler;
import meow.engine.BoundsCompiler;
import meow.engine.RelationCompiler;

import java.io.*;

public class Meow {
    private final Formula formula;
    private final Bounds bounds;
    private GraphCompiler graphCompiler;
    private BoundsCompiler boundsCompiler;
    private RelationCompiler relCompiler;

    private String universe;

    private int skolemDepth;
    private boolean useSAT;
    private int symmetry;

    public Meow(Formula formula, Bounds bounds) {
        this.formula = formula;
        this.bounds = bounds;
        this.skolemDepth = 0;
        this.useSAT = false;
        this.symmetry = 0;
    }

    public void compile() {
        relCompiler = new RelationCompiler();
        graphCompiler = new GraphCompiler(formula, relCompiler);
        graphCompiler.compile();

        boundsCompiler = new BoundsCompiler(bounds, relCompiler);
        boundsCompiler.compile();

        universe = "(define " + boundsCompiler.getUniverseAssignment().id + " "
                 + boundsCompiler.getUniverseAssignment().sExpr + ")\n";
    }

    public void setSkolemDepth(int val) { skolemDepth = val; }
    public void useSAT(boolean val) { useSAT = val; }
    public void setSymmetry(int val) { symmetry = val; }

    public void writeToFile(String filename) {
        try {
            FileWriter out = new FileWriter(filename);
            out.write(this.toString());
            out.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void writeSuite(String prefix) {
        this.skolemDepth = 0;
        this.useSAT = false;
        this.symmetry = 0;
        writeToFile(prefix + "-plain.rkt");
        this.skolemDepth = 3;
        writeToFile(prefix + "-skolem.rkt");
        this.skolemDepth = 0;
        this.symmetry = 20;
        writeToFile(prefix + "-symmetry.rkt");
        this.skolemDepth = 3;
        writeToFile(prefix + "-skolem-symmetry.rkt");

        this.skolemDepth = 0;
        this.useSAT = true;
        this.symmetry = 0;
        writeToFile(prefix + "-plain-sat.rkt");
        this.skolemDepth = 3;
        writeToFile(prefix + "-skolem-sat.rkt");
        this.skolemDepth = 0;
        this.symmetry = 20;
        writeToFile(prefix + "-symmetry-sat.rkt");
        this.skolemDepth = 3;
        writeToFile(prefix + "-skolem-symmetry-sat.rkt");
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        String[] includes = {
                "ocelot", "lang/ast", "engine/interpretation", "lang/bounds", "engine/sat/solver",
                "lib/skolemize-solve", "engine/symmetry"
        };

        builder.append("#lang rosette\n");
        builder.append("(require");
        for (String include : includes) {
            builder.append(" \"../../" + include + ".rkt\"");
        }
        builder.append(")\n");;
        builder.append(universe);
        builder.append(relCompiler.toString());
        builder.append(graphCompiler.toString());
        builder.append(boundsCompiler.toString());
        if (skolemDepth > 0) {
            builder.append("(displayln \"-- skolemizing...\")\n");
            builder.append("(match-define (cons F bnds) (time (skolemize-merge "
                          + boundsCompiler.getTotalBoundsAssignment().id + " "
                          + graphCompiler.getRootAssignment().id + " "
                          + skolemDepth + ")))\n");
        } else {
            builder.append("(define F " + graphCompiler.getRootAssignment().id + ")\n");
            builder.append("(define bnds " + boundsCompiler.getTotalBoundsAssignment().id + ")\n");
        }
        builder.append("(displayln \"-- instantiating bounds...\")\n");
        builder.append("(define interp (time (instantiate-bounds bnds)))\n");
        if (symmetry > 0) {
            builder.append("(displayln \"-- breaking symmetry...\")\n");
            builder.append("(define sbp (time (generate-sbp interp bnds)))\n");
        }
        builder.append("(displayln \"-- making boolean interpretation...\")\n");
        builder.append("(define F* (time (interpret* F interp)))\n");
        if (useSAT) {
            builder.append("(define SS (make-SAT))\n");
            builder.append("(displayln \"-- making optimized SAT call...\")\n");
            if (symmetry > 0) {
                builder.append("(define sol (time (SAT-solve SS (list (&& F* sbp)))))\n");
            } else {
                builder.append("(define sol (time (SAT-solve SS (list F*))))\n");
            }
        } else {
            builder.append("(displayln \"-- making Rosette solver call...\")\n");
            if (symmetry > 0) {
                builder.append("(define sol (time (solve (assert (&& F* sbp)))))\n");
            } else {
                builder.append("(define sol (time (solve (assert F*))))\n");
            }
        }

        return builder.toString();
    }
}
