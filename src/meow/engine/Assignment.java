package meow.engine;

import kodkod.ast.Node;

import java.util.ArrayList;

public final class Assignment {
    public final String id;
    public final String expr;
    public ArrayList<Node> deps;

    /**
     * Constructs a new assignment with the given parameters.
     * @param id the identifier of this assignment
     * @param expr the s-expression of this assignment, empty String if it already exists in Colocolo
     * @param deps the dependences of this assignment, i.e. other nodes that appear in expr
     * @implNote deps will be modified
     */
    public Assignment(String id, String expr, ArrayList<Node> deps) {
        this.id = id;
        this.expr = expr;
        this.deps = deps;
    }

    public boolean isLeaf() {
        return deps.size() == 0;
    }

    public boolean isGlobal() {
        return expr.isEmpty();
    }
}
