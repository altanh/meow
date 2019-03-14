package meow.engine;

import kodkod.ast.Node;
import kodkod.ast.Relation;
import kodkod.ast.Variable;
import kodkod.instance.Bounds;

import java.util.*;
import java.util.stream.Collectors;

public class GraphCompiler {
    private MeowVisitor visitor;
    private ArrayList<String> nodeDefs;
    private Node root;
    private RelationCompiler relCompiler;

    public GraphCompiler(Node root, RelationCompiler rc) {
        this.visitor = new MeowVisitor(rc);
        this.nodeDefs = new ArrayList<>();
        this.root = root;
        this.relCompiler = rc;
    }

    public void compile() {
        this.root.accept(visitor);
        buildNodes();
    }

    public HashMap<Node, Assignment> getAssignments() {
        return visitor.getAssignments();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (String def : nodeDefs) {
            builder.append(def);
        }

        return builder.toString();
    }

    public Assignment getRootAssignment() {
        return visitor.getAssignments().get(root);
    }

    private void fixDependencies(Node n) {
        Assignment as = visitor.getAssignments().get(n);
        as.deps.removeIf(node -> (node instanceof Relation || node instanceof Variable));
    }

    private void buildNodes() {
        HashMap<Node, Assignment> assignments = visitor.getAssignments();
        HashSet<Node> undefined = new HashSet<>();
        for (Map.Entry<Node, Assignment> entry : assignments.entrySet()) {
            if (!entry.getValue().isGlobal())
                undefined.add(entry.getKey());
        }

        for (Node n : undefined) {
            fixDependencies(n);
        }

        while (!undefined.isEmpty()) {
            List<Node> leaves = undefined.stream().filter(n -> assignments.get(n).isLeaf()).collect(Collectors.toList());
            if (leaves.isEmpty()) {
                System.err.println("no leaf nodes but there are still undefined nodes");
                System.err.println("undefined: " + undefined.toString());
                throw new IllegalStateException("cycle detected in graph");
            }

            for (Node n : leaves) {
                addDefinition(n);
                removeDependency(n, undefined);
                undefined.remove(n);
            }
        }
    }

    private void addDefinition(Node n) {
        Assignment as = visitor.getAssignments().get(n);
        String def = "(define " + as.id + " " + as.sExpr + ")\n";

        nodeDefs.add(def);
    }

    private void removeDependency(Node n, HashSet<Node> undefined) {
        for (Node un : undefined) {
            Assignment as = visitor.getAssignments().get(un);
            if (as.deps.contains(n)) {
                as.deps.remove(n);
            }
        }
    }
}
