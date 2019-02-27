package meow.test;

import kodkod.ast.*;

import meow.engine.GraphCompiler;

public class GraphCompilerTest {
    public static void main(String[] args) {
        Relation rel = Relation.unary("haha");
        Relation ral = Relation.unary("hoho");
        Variable x = Variable.unary("x");
        Variable y = Variable.unary("y");
        Decls somexrel = x.oneOf(rel).and(y.oneOf(ral));
        Formula fo = x.in(ral).or(y.in(rel));
        Expression myset = fo.comprehension(somexrel);

        GraphCompiler comp = new GraphCompiler(myset);

        long time = System.currentTimeMillis();
        comp.compile();
        time = System.currentTimeMillis() - time;

        System.out.println("took " + time + "ms:");
        System.out.println(comp.toString());
    }
}
