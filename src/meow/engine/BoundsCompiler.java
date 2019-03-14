package meow.engine;

import kodkod.ast.Node;
import kodkod.ast.Relation;

import kodkod.instance.Bounds;
import kodkod.instance.Tuple;
import kodkod.instance.TupleSet;

import java.util.*;

public class BoundsCompiler {
    private final NameFactory factory;
    private final Bounds bounds;
    private final RelationCompiler relCompiler;
    private final HashMap<TupleSet, Assignment> tupleAssignments;
    private final HashMap<Relation, Assignment> boundAssignments;

    private Assignment universeAssignment;
    private ArrayList<String> tupleDefs;
    private ArrayList<String> boundDefs;
    private Assignment totalBoundsAssignment;

    public BoundsCompiler(Bounds bounds, RelationCompiler rc) {
        this.bounds = bounds;
        this.factory = new NameFactory();
        this.relCompiler = rc;
        this.tupleAssignments = new HashMap<>();
        this.boundAssignments = new HashMap<>();

        this.tupleDefs = new ArrayList<>();
        this.boundDefs = new ArrayList<>();
    }

    public void compile() {
        compileUniverse();
        compileBounds();
        compileDefs(tupleAssignments.values(), tupleDefs);
        compileDefs(boundAssignments.values(), boundDefs);
        compileTotalBounds();
    }

    public Assignment getUniverseAssignment() {
        return universeAssignment;
    }

    public Assignment getTotalBoundsAssignment() {
        return totalBoundsAssignment;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (String td : tupleDefs)
            builder.append(td);
        for (String bd : boundDefs)
            builder.append(bd);
        builder.append("(define " + totalBoundsAssignment.id + " " + totalBoundsAssignment.sExpr + ")\n");

        return builder.toString();
    }

    private void compileUniverse() {
        String sExpr = "(universe (list";
        for (Object atom : bounds.universe()) {
            sExpr += " \"" + atom.toString() + "\"";
        }
        sExpr += "))";

        String id = factory.withPrefix("universe$");
        universeAssignment = new Assignment(id, sExpr, new HashSet<>());
    }

    private void compileTotalBounds() {
        String sExpr = "(bounds " + universeAssignment.id + " (list";
        for (Assignment as : boundAssignments.values()) {
            sExpr += " " + as.id;
        }
        sExpr += "))";

        String id = factory.withPrefix("bounds$");
        totalBoundsAssignment = new Assignment(id, sExpr, new HashSet<>());
    }

    private void compileDefs(Collection<Assignment> assignments, List<String> target) {
        for (Assignment as : assignments) {
            target.add("(define " + as.id + " " + as.sExpr + ")\n");
        }
    }

    private void compileBounds() {
        Map<Relation, TupleSet> lowers = bounds.lowerBounds();
        Map<Relation, TupleSet> uppers = bounds.upperBounds();

        for (Relation rel : bounds.relations()) {
            String sExpr = "(bound " + relCompiler.compile(rel) + " "
                         + visitTupleSet(lowers.get(rel)) + " "
                         + visitTupleSet(uppers.get(rel)) + ")";

            String id = factory.withPrefix("bd$");
            boundAssignments.put(rel, new Assignment(id, sExpr, new HashSet<>()));
        }
    }

    private String visitTupleSet(TupleSet ts) {
        if (!tupleAssignments.containsKey(ts)) {
            /*String sExpr = "(make-tupleset " + ts.arity() + " (list";
            for (Tuple t : ts) {
                sExpr += " " + t.index();
            }
            sExpr += "))";*/
            String sExpr = "(list";
            for (Tuple t : ts) {
                sExpr += " " + makeTuple(t);
            }
            sExpr += ")";

            String id = factory.withPrefix("ts$");
            tupleAssignments.put(ts, new Assignment(id, sExpr, new HashSet<>()));
        }

        return tupleAssignments.get(ts).id;
    }

    private String makeTuple(Tuple t) {
        StringBuilder builder = new StringBuilder();
        builder.append("(list");
        for (int i = 0; i < t.arity(); ++i) {
            builder.append(" \"" + t.atom(i).toString() + "\"");
        }
        builder.append(")");
        return builder.toString();
    }
}
