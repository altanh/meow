package meow.engine;

import kodkod.ast.Node;
import kodkod.instance.Bounds;

import java.util.*;
import java.util.stream.Collectors;

public class GraphCompiler {
    private MeowVisitor visitor;
    private ArrayList<String> relDefs;
    private Node root;

    public GraphCompiler(Node root) {
        this.visitor = new MeowVisitor();
        this.relDefs = new ArrayList<>();
        this.root = root;
    }

    public void compile() {
        this.root.accept(visitor);
        buildRelations();
    }

    public HashMap<Node, Assignment> getAssignments() {
        return visitor.getAssignments();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (String def : relDefs) {
            builder.append(def);
        }

        return builder.toString();
    }

    public Assignment getRootAssignment() {
        return visitor.getAssignments().get(root);
    }

    private void buildRelations() {
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
        String def = "(define " + as.id + " " + as.sExpr + ")\n";

        relDefs.add(def);
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
