package meow.test;

import kodkod.ast.*;

import meow.engine.Compiler;

public class CompilerTest {
    public static void main(String[] args) {
        Relation rel = Relation.unary("haha");
        Relation ral = Relation.unary("hoho");
        Variable x = Variable.unary("x");
        Variable y = Variable.unary("y");
        Decls somexrel = x.oneOf(rel).and(y.oneOf(ral));
        Formula fo = x.in(ral).or(y.in(rel));
        Expression myset = fo.comprehension(somexrel);

        Compiler comp = new Compiler();
        comp.compileGraph(myset);

        System.out.println("compiled:");
        System.out.println(comp.toString());
    }
}
