package meow.engine;

import kodkod.ast.Node;
import kodkod.ast.Relation;
import kodkod.ast.Variable;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class RelationCompiler {
    private final NameFactory factory;
    private final HashMap<Node, Assignment> assignments;

    public RelationCompiler() {
        this.factory = new NameFactory();
        this.assignments = new HashMap<>();
    }

    /**
     * Compiles the given relation if not already, and returns the compiled id
     * @param rel the relation to be compiled
     */
    public String compile(Relation rel) {
        if (!assignments.containsKey(rel)) {
            String sExpr = "(declare-relation " + rel.arity() + " \"" + rel.name() + "\")";
            String id = factory.withPrefix("r$");
            assignments.put(rel, new Assignment(id, sExpr, new HashSet<>()));
        }

        return assignments.get(rel).id;
    }

    /**
     * Compiles the given variable if not already, and returns the compiled id
     * @param var the relation to be compiled
     */
    public String compile(Variable var) {
        if (!assignments.containsKey(var)) {
            String sExpr = "(declare-relation " + var.arity() + " \"" + var.name() + "\")";
            String id = factory.withPrefix("v$");
            assignments.put(var, new Assignment(id, sExpr, new HashSet<>()));
        }

        return assignments.get(var).id;
    }

    public Collection<Assignment> getAssignments() {
        return assignments.values();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Assignment as : getAssignments()) {
            builder.append("(define " + as.id + " " + as.sExpr + ")\n");
        }

        return builder.toString();
    }
}
