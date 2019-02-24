package meow.test;

import kodkod.ast.Node;
import meow.engine.MeowVisitor;
import meow.engine.CacheEntry;

import kodkod.ast.Variable;

import java.util.Map;

public class SimpleTest {
    public static void main(String[] args) {
        MeowVisitor mv = new MeowVisitor();
        Variable myvariable = Variable.nary("hello", 3);

        mv.visit(myvariable);

        System.out.println("s-expr: " + mv.toString());
        System.out.println("defs:");

        for (CacheEntry entry : mv.getCache().values()) {
            System.out.println("\t" + entry.id + ": " + entry.expr);
        }
    }
}
