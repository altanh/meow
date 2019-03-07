package meow;

import kodkod.ast.Formula;
import kodkod.instance.Bounds;
import meow.engine.GraphCompiler;
import meow.engine.BoundsCompiler;

public class Meow {
    private final Formula formula;
    private final Bounds bounds;
    private GraphCompiler graphCompiler;
    private BoundsCompiler boundsCompiler;

    private String universe;
    private String interp;
    private String satFormula;
    private String solverCall;

    public Meow(Formula formula, Bounds bounds) {
        this.formula = formula;
        this.bounds = bounds;
    }

    public void compile() {
        graphCompiler = new GraphCompiler(formula);
        graphCompiler.compile();

        boundsCompiler = new BoundsCompiler(bounds, graphCompiler.getAssignments());
        boundsCompiler.compile();

        universe = "(define " + boundsCompiler.getUniverseAssignment().id + " "
                 + boundsCompiler.getUniverseAssignment().sExpr + ")\n";
        interp = "(define interp (instantiate-bounds " + boundsCompiler.getTotalBoundsAssignment().id + "))\n";
        satFormula = "(define F* (interpret* " + graphCompiler.getRootAssignment().id + " interp))\n";
        solverCall = "(define sol (solve (assert F*)))\n";
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        String[] includes = {
                "ocelot", "lang/ast", "engine/interpretation", "lang/bounds"
        };

        builder.append("#lang rosette\n");
        builder.append("(require");
        for (String include : includes) {
            builder.append(" \"../../../" + include + ".rkt\"");
        }
        builder.append(")\n");
        builder.append(universe);
        builder.append(graphCompiler.toString());
        builder.append(boundsCompiler.toString());
        builder.append(interp);
        builder.append(satFormula);
        builder.append(solverCall);

        return builder.toString();
    }
}
