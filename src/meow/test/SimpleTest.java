package meow.test;

import kodkod.ast.Decl;
import kodkod.ast.Expression;
import kodkod.ast.Node;
import meow.engine.MeowVisitor;
import meow.engine.Assignment;

import kodkod.ast.Variable;

public class SimpleTest {
    public static void main(String[] args) {
        MeowVisitor mv = new MeowVisitor();
        Variable myvariable = Variable.nary("hello", 1);
        Expression myexpr = Expression.UNIV;
        Decl mydecl = myvariable.oneOf(myexpr);

        mydecl.accept(mv);

        for (Assignment a : mv.getAssignments().values()) {
            if (a.isGlobal()) continue;

            System.out.println(a.id + " := " + a.expr);
            for (Node child : a.deps) {
                if (mv.getAssignments().get(child).isGlobal()) continue;

                String childId = mv.getAssignments().get(child).id;
                System.out.println("\trequires " + childId);
            }
        }
    }
}
