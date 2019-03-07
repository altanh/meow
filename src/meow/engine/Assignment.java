package meow.engine;

import kodkod.ast.Expression;
import kodkod.ast.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public final class Assignment {
    public final String id;
    public final String sExpr;
    public HashSet<Node> deps;

    public static final HashMap<Node, Assignment> GLOBALS = new HashMap<Node, Assignment>() {{
        put(Expression.UNIV, new Assignment("univ", "", new HashSet<>()));
        put(Expression.IDEN, new Assignment("iden", "", new HashSet<>()));
        put(Expression.NONE, new Assignment("none", "", new HashSet<>()));
        put(Expression.INTS, new Assignment("none", "", new HashSet<>()));
    }};

    /**
     * Constructs a new assignment with the given parameters.
     * @param id the identifier of this assignment
     * @param sExpr the s-expression of this assignment, empty String if it already exists in Colocolo
     * @param deps the dependences of this assignment, i.e. other nodes that appear in sExpr
     * @implNote deps will be modified
     */
    public Assignment(String id, String sExpr, HashSet<Node> deps) {
        this.id = id;
        this.sExpr = sExpr;
        this.deps = deps;
    }

    public boolean isLeaf() {
        return deps.isEmpty() || deps.stream().allMatch(n -> GLOBALS.containsKey(n));
    }

    public boolean isGlobal() {
        return sExpr.isEmpty();
    }
}
