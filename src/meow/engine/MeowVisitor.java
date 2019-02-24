package meow.engine;

import kodkod.ast.*;
import kodkod.ast.visitor.VoidVisitor;

import java.util.HashMap;

public class MeowVisitor implements VoidVisitor {
    private final HashMap<Node, CacheEntry> nodeCache;
    private final StringBuilder tokens;
    private final NameFactory factory;

    public MeowVisitor() {
        this.nodeCache = new HashMap<>();
        this.tokens = new StringBuilder();
        this.factory = new NameFactory();
    }

    public HashMap<Node, CacheEntry> getCache() {
        return nodeCache;
    }

    public String toString() {
        return tokens.toString();
    }

    public void visit(Decls decls) {
        if (nodeCache.containsKey(decls)) {
            tokens.append(nodeCache.get(decls).id);
            return;
        }


    }

    public void visit(Decl decl) {

    }

    public void visit(Relation relation) {
        if (!nodeCache.containsKey(relation)) {
            String id = factory.withPrefix("r$");
            String expr = "(declare-relation " + relation.arity() + " \"" + id + "\")";

            nodeCache.put(relation, new CacheEntry(id, expr));
        }

        tokens.append(nodeCache.get(relation).id);
    }

    public void visit(Variable variable) {
        if (!nodeCache.containsKey(variable)) {
            // Ocelot seems to only ever declare 'variables' of arity 1...
            String id = factory.withPrefix("v$");
            String expr = "(declare-relation " + variable.arity() + " \"" + id + "\")";

            nodeCache.put(variable, new CacheEntry(id, expr));
        }

        tokens.append(nodeCache.get(variable).id);
    }

    public void visit(ConstantExpression constExpr) {

    }

    public void visit(UnaryExpression unaryExpr) {

    }

    public void visit(BinaryExpression binExpr) {

    }

    public void visit(NaryExpression expr) {

    }

    public void visit(Comprehension comprehension) {

    }

    public void visit(IfExpression ifExpr) {

    }

    public void visit(ProjectExpression project) {

    }

    public void visit(IntToExprCast castExpr) {

    }

    public void visit(IntConstant intConst) {

    }

    public void visit(IfIntExpression intExpr) {

    }

    public void visit(ExprToIntCast intExpr) {

    }

    public void visit(NaryIntExpression intExpr) {

    }

    public void visit(BinaryIntExpression intExpr) {

    }

    public void visit(UnaryIntExpression intExpr) {

    }

    public void visit(SumExpression intExpr) {

    }

    public void visit(IntComparisonFormula intComp) {

    }

    public void visit(QuantifiedFormula quantFormula) {

    }

    public void visit(NaryFormula formula) {

    }

    public void visit(BinaryFormula formula) {

    }

    public void visit(NotFormula formula) {

    }

    public void visit(ConstantFormula constant) {

    }

    public void visit(ComparisonFormula compFormula) {

    }

    public void visit(MultiplicityFormula multFormula) {

    }

    public void visit(RelationPredicate predicate) {

    }
}
