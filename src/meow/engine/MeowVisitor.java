package meow.engine;

import kodkod.ast.*;
import kodkod.ast.operator.Multiplicity;
import kodkod.ast.visitor.ReturnVisitor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MeowVisitor implements ReturnVisitor<String, String, String, String> {
    private final HashMap<Node, Assignment> assignments;
    private final NameFactory factory;

    public MeowVisitor() {
        this.assignments = new HashMap<>();
        this.factory = new NameFactory();

        // Colocolo globals
        assignments.put(Expression.UNIV, new Assignment("univ", "", new ArrayList<>()));
        assignments.put(Expression.IDEN, new Assignment("iden", "", new ArrayList<>()));
        assignments.put(Expression.NONE, new Assignment("none", "", new ArrayList<>()));
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
            ArrayList<Node> deps = new ArrayList<>();

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
            ArrayList<Node> deps = new ArrayList<>();
            deps.add(decl.variable());
            deps.add(decl.expression());

            String sExpr = "(cons ";
            String varSExpr = decl.variable().accept(this);
            String exprSExpr = decl.expression().accept(this);
            sExpr += varSExpr + " " + exprSExpr + ")";

            String id = factory.withPrefix("decl$");
            assignments.put(decl, new Assignment(id, sExpr, deps));
        }

        return assignments.get(decl).id;
    }

    public String visit(Relation relation) {
        if (!assignments.containsKey(relation)) {
            String id = factory.withPrefix("rel$");
            String sExpr = "(declare-relation " + relation.arity() + " \"" + id + "\")";

            assignments.put(relation, new Assignment(id, sExpr, new ArrayList<>()));
        }

        return assignments.get(relation).id;
    }

    public String visit(Variable variable) {
        if (!assignments.containsKey(variable)) {
            // Ocelot seems to only ever declare 'variables' of arity 1...
            String id = factory.withPrefix("var$");
            String sExpr = "(declare-relation " + variable.arity() + " \"" + id + "\")";

            assignments.put(variable, new Assignment(id, sExpr, new ArrayList<>()));
        }

        return assignments.get(variable).id;
    }

    public String visit(ConstantExpression constExpr) {
        if (!assignments.containsKey(constExpr)) {
            // no support for INTS in Ocelot as far as I can tell
            throw new IllegalArgumentException("Illegal constant expression: " + constExpr.name());
        }

        return assignments.get(constExpr).id;
    }

    public String visit(UnaryExpression unaryExpr) {
        if (!assignments.containsKey(unaryExpr)) {
            String childExpr = unaryExpr.expression().accept(this);
            String op = unaryExpr.op().toString();
            String sExpr = "(" + op + " " + childExpr + ")";
            String id = factory.withPrefix("u-sExpr$");

            ArrayList<Node> deps = new ArrayList<>();
            deps.add(unaryExpr.expression());

            assignments.put(unaryExpr, new Assignment(id, sExpr, deps));
        }

        return assignments.get(unaryExpr).id;
    }

    public String visit(BinaryExpression binExpr) {
        if (!assignments.containsKey(binExpr)) {
            String leftExpr = binExpr.left().accept(this);
            String rightExpr = binExpr.right().accept(this);
            String op = binExpr.op().toString();
            String sExpr = "(" + op + " " + leftExpr + " " + rightExpr + ")";
            String id = factory.withPrefix("b-sExpr$");

            ArrayList<Node> deps = new ArrayList<>();
            deps.add(binExpr.left());
            deps.add(binExpr.right());

            assignments.put(binExpr, new Assignment(id, sExpr, deps));
        }

        return assignments.get(binExpr).id;
    }

    public String visit(NaryExpression expr) {
        if (!assignments.containsKey(expr)) {
            ArrayList<Node> deps = new ArrayList<>();

            String sExpr = "(" + expr.op().toString();
            for (Expression child : expr) {
                deps.add(child);
                sExpr += " " + child.accept(this);
            }
            sExpr += ")";

            String id = factory.withPrefix("n-sExpr$");
            assignments.put(expr, new Assignment(id, sExpr, deps));
        }

        return assignments.get(expr).id;
    }

    public String visit(Comprehension comprehension) {
        if (!assignments.containsKey(comprehension)) {
            ArrayList<Node> deps = new ArrayList<>();
            deps.add(comprehension.decls());
            deps.add(comprehension.formula());

            String declsSExpr = comprehension.decls().accept(this);
            String formulaSExpr = comprehension.formula().accept(this);
            String sExpr = "(comprehension " + declsSExpr + " " + formulaSExpr + ")";

            String id = factory.withPrefix("cmp$");
            assignments.put(comprehension, new Assignment(id, sExpr, deps));
        }

        return assignments.get(comprehension).id;
    }

    public String visit(IfExpression ifExpr) {
        throw new UnsupportedOperationException();
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
            ArrayList<Node> deps = new ArrayList<>();
            deps.add(quantFormula.decls());
            deps.add(quantFormula.formula());

            String sExpr = "(quantified-formula \'" + quantFormula.quantifier().toString() + " "
                         + quantFormula.decls().accept(this) + " "
                         + quantFormula.formula().accept(this) + ")";
            String id = factory.withPrefix("q-form$");
            assignments.put(quantFormula, new Assignment(id, sExpr, deps));
        }

        return assignments.get(quantFormula).id;
    }

    public String visit(NaryFormula formula) {
        if (!assignments.containsKey(formula)) {
            ArrayList<Node> deps = new ArrayList<>();

            String sExpr = "(" + formula.op().toString();
            for (Formula child : formula) {
                deps.add(child);
                sExpr += " " + child.accept(this);
            }
            sExpr += ")";

            String id = factory.withPrefix("n-form$");
            assignments.put(formula, new Assignment(id, sExpr, deps));
        }

        return assignments.get(formula).id;
    }

    public String visit(BinaryFormula formula) {
        if (!assignments.containsKey(formula)) {
            ArrayList<Node> deps = new ArrayList<>();
            deps.add(formula.left());
            deps.add(formula.right());

            String sExpr = "(" + formula.op().toString() + " "
                         + formula.left().accept(this) + " "
                         + formula.right().accept(this) + ")";

            String id = factory.withPrefix("b-form$");
            assignments.put(formula, new Assignment(id, sExpr, deps));
        }

        return assignments.get(formula).id;
    }

    public String visit(NotFormula formula) {
        if (!assignments.containsKey(formula)) {
            ArrayList<Node> deps = new ArrayList<>();
            deps.add(formula.formula());

            String sExpr = "(! " + formula.formula().accept(this) + ")";
            String id = factory.withPrefix("!-form$");
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
            ArrayList<Node> deps = new ArrayList<>();
            deps.add(compFormula.left());
            deps.add(compFormula.right());

            String sExpr = "(" + compFormula.op().toString() + " "
                         + compFormula.left().accept(this) + " "
                         + compFormula.right().accept(this) + ")";
            String id = factory.withPrefix("cmp-form$");
            assignments.put(compFormula, new Assignment(id, sExpr, deps));
        }

        return assignments.get(compFormula).id;
    }

    public String visit(MultiplicityFormula multFormula) {
        if (!assignments.containsKey(multFormula)) {
            ArrayList<Node> deps = new ArrayList<>();
            deps.add(multFormula.expression());

            String sExpr = "(multiplicity-formula \'" + multFormula.multiplicity().toString() + " "
                         + multFormula.expression().accept(this) + ")";
            String id = factory.withPrefix("mul-form$");
            assignments.put(multFormula, new Assignment(id, sExpr, deps));
        }

        return assignments.get(multFormula).id;
    }

    public String visit(RelationPredicate predicate) {
        throw new UnsupportedOperationException();
    }
}
