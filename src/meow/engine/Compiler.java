package meow.engine;

import kodkod.ast.Node;

import java.util.*;
import java.util.stream.Collectors;

public class Compiler {
    private MeowVisitor visitor;
    private ArrayList<String> defs;
    private Node root;

    public Compiler() {
        visitor = new MeowVisitor();
        defs = new ArrayList<>();
        root = null;
    }

    public void compileGraph(Node root) {
        visitor = new MeowVisitor();
        defs = new ArrayList<>();
        this.root = root;

        this.root.accept(visitor);
        buildDefinitions();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (String def : defs) {
            builder.append(def);
            builder.append("\n");
        }

        builder.append(visitor.getAssignments().get(root).id);
        builder.append("\n");

        return builder.toString();
    }

    private void buildDefinitions() {
        HashMap<Node, Assignment> assignments = visitor.getAssignments();
        HashSet<Node> undefined = new HashSet<>();
        for (Map.Entry<Node, Assignment> entry : assignments.entrySet()) {
            if (!entry.getValue().isGlobal())
                undefined.add(entry.getKey());
        }

        while (!undefined.isEmpty()) {
            List<Node> leaves = undefined.stream().filter(n -> assignments.get(n).isLeaf()).collect(Collectors.toList());
            if (leaves.isEmpty()) {
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
        String def = "(define " + as.id + " " + as.sExpr + ")";

        defs.add(def);
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
