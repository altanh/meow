package meow.engine;

import kodkod.ast.*;
import kodkod.ast.operator.FormulaOperator;
import kodkod.ast.visitor.ReturnVisitor;

import java.util.HashMap;
import java.util.HashSet;

public class MeowVisitor implements ReturnVisitor<String, String, String, String> {
    private final HashMap<Node, Assignment> assignments;
    private final NameFactory factory;

    public MeowVisitor() {
        this.assignments = new HashMap<>();
        this.factory = new NameFactory();

        // Colocolo globals
        assignments.putAll(Assignment.GLOBALS);
    }

    public HashMap<Node, Assignment> getAssignments() {
        return assignments;
    }

    /**
     * Maps decls -> (list decl1 ...)
     * @param decls the declarations
     * @return the corresponding s-expression
     * @apiNote multiplicity information is lost
     */
    public String visit(Decls decls) {
        if (!assignments.containsKey(decls)) {
            HashSet<Node> deps = new HashSet<>();

            String sExpr = "(list";
            for (Decl d : decls) {
                deps.add(d);
                sExpr += " " + d.accept(this);
            }
            sExpr += ")";

            String id = factory.withPrefix("decls$");
            assignments.put(decls, new Assignment(id, sExpr, deps));
        }

        return assignments.get(decls).id;
    }

    /**
     * Maps decl -> (cons var sExpr)
     * @param decl the declaration
     * @return the corresponding s-expression
     * @apiNote multiplicity information is lost
     */
    public String visit(Decl decl) {
        if (!assignments.containsKey(decl)) {
            HashSet<Node> deps = new HashSet<>();
            deps.add(decl.variable());
            deps.add(decl.expression());

            String sExpr = "(cons " + decl.variable().accept(this) + " "
                         + decl.expression().accept(this) + ")";

            String id = factory.withPrefix("decl$");
            assignments.put(decl, new Assignment(id, sExpr, deps));
        }

        return assignments.get(decl).id;
    }

    public String visit(Relation relation) {
        if (!assignments.containsKey(relation)) {
            String sExpr = "(declare-relation " + relation.arity() + " \"" + relation.name() + "\")";

            String id = factory.withPrefix("rel$");
            assignments.put(relation, new Assignment(id, sExpr, new HashSet<>()));
        }

        return assignments.get(relation).id;
    }

    public String visit(Variable variable) {
        if (!assignments.containsKey(variable)) {
            // Ocelot seems to only ever declare 'variables' of arity 1...
            String sExpr = "(declare-relation " + variable.arity() + " \"" + variable.name() + "\")";

            String id = factory.withPrefix("var$");
            assignments.put(variable, new Assignment(id, sExpr, new HashSet<>()));
        }

        return assignments.get(variable).id;
    }

    public String visit(ConstantExpression constExpr) {
        if (!assignments.containsKey(constExpr)) {
            throw new IllegalArgumentException("Illegal constant expression: " + constExpr.name());
        }

        return assignments.get(constExpr).id;
    }

    public String visit(UnaryExpression unaryExpr) {
        if (!assignments.containsKey(unaryExpr)) {
            HashSet<Node> deps = new HashSet<>();
            deps.add(unaryExpr.expression());

            String sExpr = "(" + unaryExpr.op().toString() + " " + unaryExpr.expression().accept(this) + ")";

            String id = factory.withPrefix("u-ex$");
            assignments.put(unaryExpr, new Assignment(id, sExpr, deps));
        }

        return assignments.get(unaryExpr).id;
    }

    public String visit(BinaryExpression binExpr) {
        if (!assignments.containsKey(binExpr)) {
            HashSet<Node> deps = new HashSet<>();
            deps.add(binExpr.left());
            deps.add(binExpr.right());

            String sExpr = "(" + binExpr.op().toString() + " " + binExpr.left().accept(this) + " "
                         + binExpr.right().accept(this) + ")";

            String id = factory.withPrefix("b-ex$");
            assignments.put(binExpr, new Assignment(id, sExpr, deps));
        }

        return assignments.get(binExpr).id;
    }

    public String visit(NaryExpression expr) {
        if (!assignments.containsKey(expr)) {
            HashSet<Node> deps = new HashSet<>();

            String sExpr = "(" + expr.op().toString();
            for (Expression child : expr) {
                deps.add(child);
                sExpr += " " + child.accept(this);
            }
            sExpr += ")";

            String id = factory.withPrefix("n-ex$");
            assignments.put(expr, new Assignment(id, sExpr, deps));
        }

        return assignments.get(expr).id;
    }

    public String visit(Comprehension comprehension) {
        if (!assignments.containsKey(comprehension)) {
            HashSet<Node> deps = new HashSet<>();
            deps.add(comprehension.decls());
            deps.add(comprehension.formula());

            String sExpr = "(comprehension " + comprehension.decls().accept(this) + " "
                         + comprehension.formula().accept(this) + ")";

            String id = factory.withPrefix("cmp$");
            assignments.put(comprehension, new Assignment(id, sExpr, deps));
        }

        return assignments.get(comprehension).id;
    }

    public String visit(IfExpression ifExpr) {
        if (!assignments.containsKey(ifExpr)) {
            HashSet<Node> deps = new HashSet<>();
            deps.add(ifExpr.condition());
            deps.add(ifExpr.thenExpr());
            deps.add(ifExpr.elseExpr());

            String sExpr = "(ite " + ifExpr.condition().accept(this) + " "
                         + ifExpr.thenExpr().accept(this) + " "
                         + ifExpr.elseExpr().accept(this) + ")";

            String id = factory.withPrefix("ite$");
            assignments.put(ifExpr, new Assignment(id, sExpr, deps));
        }

        return assignments.get(ifExpr).id;
    }

    public String visit(ProjectExpression project) {
        throw new UnsupportedOperationException();
    }

    public String visit(IntToExprCast castExpr) {
        throw new UnsupportedOperationException();
    }

    public String visit(IntConstant intConst) {
        throw new UnsupportedOperationException();
    }

    public String visit(IfIntExpression intExpr) {
        throw new UnsupportedOperationException();
    }

    public String visit(ExprToIntCast intExpr) {
        throw new UnsupportedOperationException();
    }

    public String visit(NaryIntExpression intExpr) {
        throw new UnsupportedOperationException();
    }

    public String visit(BinaryIntExpression intExpr) {
        throw new UnsupportedOperationException();
    }

    public String visit(UnaryIntExpression intExpr) {
        throw new UnsupportedOperationException();
    }

    public String visit(SumExpression intExpr) {
        throw new UnsupportedOperationException();
    }

    public String visit(IntComparisonFormula intComp) {
        throw new UnsupportedOperationException();
    }

    public String visit(QuantifiedFormula quantFormula) {
        if (!assignments.containsKey(quantFormula)) {
            HashSet<Node> deps = new HashSet<>();
            deps.add(quantFormula.decls());
            deps.add(quantFormula.formula());

            String sExpr = "(quantified-formula \'" + quantFormula.quantifier().toString() + " "
                         + quantFormula.decls().accept(this) + " "
                         + quantFormula.formula().accept(this) + ")";

            String id = factory.withPrefix("q-f$");
            assignments.put(quantFormula, new Assignment(id, sExpr, deps));
        }

        return assignments.get(quantFormula).id;
    }

    public String visit(NaryFormula formula) {
        if (!assignments.containsKey(formula)) {
            HashSet<Node> deps = new HashSet<>();

            String sExpr = "(" + formula.op().toString();
            for (Formula child : formula) {
                deps.add(child);
                sExpr += " " + child.accept(this);
            }
            sExpr += ")";

            String id = factory.withPrefix("n-f$");
            assignments.put(formula, new Assignment(id, sExpr, deps));
        }

        return assignments.get(formula).id;
    }

    public String visit(BinaryFormula formula) {
        if (!assignments.containsKey(formula)) {
            HashSet<Node> deps = new HashSet<>();
            deps.add(formula.left());
            deps.add(formula.right());

            String leftSExpr = formula.left().accept(this);
            String rightSExpr = formula.right().accept(this);
            String op = formula.op().toString();

            String sExpr = formula.op() == FormulaOperator.IFF ?
                    "(&& (=> " + leftSExpr + " " + rightSExpr + ") " + "(=> " + rightSExpr + " " + leftSExpr + "))" :
                    "(" + op + " " + leftSExpr + " " + rightSExpr + ")";

            String id = factory.withPrefix("b-f$");
            assignments.put(formula, new Assignment(id, sExpr, deps));
        }

        return assignments.get(formula).id;
    }

    public String visit(NotFormula formula) {
        if (!assignments.containsKey(formula)) {
            HashSet<Node> deps = new HashSet<>();
            deps.add(formula.formula());

            String sExpr = "(! " + formula.formula().accept(this) + ")";

            String id = factory.withPrefix("!-f$");
            assignments.put(formula, new Assignment(id, sExpr, deps));
        }

        return assignments.get(formula).id;
    }

    public String visit(ConstantFormula constant) {
        // Ocelot doesn't have any?
        throw new UnsupportedOperationException();
    }

    public String visit(ComparisonFormula compFormula) {
        if (!assignments.containsKey(compFormula)) {
            HashSet<Node> deps = new HashSet<>();
            deps.add(compFormula.left());
            deps.add(compFormula.right());

            String sExpr = "(" + compFormula.op().toString() + " "
                         + compFormula.left().accept(this) + " "
                         + compFormula.right().accept(this) + ")";

            String id = factory.withPrefix("cmp-f$");
            assignments.put(compFormula, new Assignment(id, sExpr, deps));
        }

        return assignments.get(compFormula).id;
    }

    public String visit(MultiplicityFormula multFormula) {
        if (!assignments.containsKey(multFormula)) {
            HashSet<Node> deps = new HashSet<>();
            deps.add(multFormula.expression());

            String sExpr = "(multiplicity-formula \'" + multFormula.multiplicity().toString() + " "
                         + multFormula.expression().accept(this) + ")";

            String id = factory.withPrefix("mul-f$");
            assignments.put(multFormula, new Assignment(id, sExpr, deps));
        }

        return assignments.get(multFormula).id;
    }

    public String visit(RelationPredicate predicate) {
        throw new UnsupportedOperationException();
    }
}
