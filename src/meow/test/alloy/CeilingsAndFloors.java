package meow.test.alloy;

import java.util.Arrays;
import java.util.List;

import kodkod.ast.*;
import kodkod.ast.operator.*;
import kodkod.instance.*;
import kodkod.engine.*;
import kodkod.engine.satlab.SATFactory;
import kodkod.engine.config.Options;
import meow.Meow;

/*
  ==================================================
    kodkod formula:
  ==================================================
    (all BelowToo_this: this/Man |
      one (BelowToo_this . this/Man.ceiling) &&
      (BelowToo_this . this/Man.ceiling) in this/Platform) &&
    (this/Man.ceiling . univ) in this/Man &&
    (all BelowToo_this: this/Man |
      one (BelowToo_this . this/Man.floor) &&
      (BelowToo_this . this/Man.floor) in this/Platform) &&
    (this/Man.floor . univ) in this/Man &&
    (all BelowToo_m: this/Man |
      some BelowToo_n: this/Man |
       (BelowToo_n . this/Man.floor) = (BelowToo_m . this/Man.ceiling)) &&
    !(all BelowToo_m: this/Man |
       some BelowToo_n: this/Man |
        (BelowToo_m . this/Man.floor) = (BelowToo_n . this/Man.ceiling)) &&
    Int/min = Int/min &&
    Int/zero = Int/zero &&
    Int/max = Int/max &&
    Int/next = Int/next &&
    seq/Int = seq/Int &&
    String = String &&
    this/Platform = this/Platform &&
    this/Man = this/Man &&
    this/Man.ceiling = this/Man.ceiling &&
    this/Man.floor = this/Man.floor
  ==================================================
*/
public final class CeilingsAndFloors {

    public static void main(String[] args) throws Exception {

        Relation x0 = Relation.unary("Int/min");
        Relation x1 = Relation.unary("Int/zero");
        Relation x2 = Relation.unary("Int/max");
        Relation x3 = Relation.nary("Int/next", 2);
        Relation x4 = Relation.unary("seq/Int");
        Relation x5 = Relation.unary("String");
        Relation x6 = Relation.unary("this/Platform");
        Relation x7 = Relation.unary("this/Man");
        Relation x8 = Relation.nary("this/Man.ceiling", 2);
        Relation x9 = Relation.nary("this/Man.floor", 2);

        List<String> atomlist = Arrays.asList(
                "-1", "-2", "-3", "-4", "-5",
                "-6", "-7", "-8", "0", "1", "2",
                "3", "4", "5", "6", "7", "Man$0",
                "Man$1", "Man$2", "Man$3", "Man$4", "Man$5", "Man$6",
                "Man$7", "Man$8", "Man$9", "Platform$0", "Platform$1", "Platform$2",
                "Platform$3", "Platform$4", "Platform$5", "Platform$6", "Platform$7", "Platform$8",
                "Platform$9"
        );

        Universe universe = new Universe(atomlist);
        TupleFactory factory = universe.factory();
        Bounds bounds = new Bounds(universe);

        TupleSet x0_upper = factory.noneOf(1);
        x0_upper.add(factory.tuple("-8"));
        bounds.boundExactly(x0, x0_upper);

        TupleSet x1_upper = factory.noneOf(1);
        x1_upper.add(factory.tuple("0"));
        bounds.boundExactly(x1, x1_upper);

        TupleSet x2_upper = factory.noneOf(1);
        x2_upper.add(factory.tuple("7"));
        bounds.boundExactly(x2, x2_upper);

        TupleSet x3_upper = factory.noneOf(2);
        x3_upper.add(factory.tuple("-8").product(factory.tuple("-7")));
        x3_upper.add(factory.tuple("-7").product(factory.tuple("-6")));
        x3_upper.add(factory.tuple("-6").product(factory.tuple("-5")));
        x3_upper.add(factory.tuple("-5").product(factory.tuple("-4")));
        x3_upper.add(factory.tuple("-4").product(factory.tuple("-3")));
        x3_upper.add(factory.tuple("-3").product(factory.tuple("-2")));
        x3_upper.add(factory.tuple("-2").product(factory.tuple("-1")));
        x3_upper.add(factory.tuple("-1").product(factory.tuple("0")));
        x3_upper.add(factory.tuple("0").product(factory.tuple("1")));
        x3_upper.add(factory.tuple("1").product(factory.tuple("2")));
        x3_upper.add(factory.tuple("2").product(factory.tuple("3")));
        x3_upper.add(factory.tuple("3").product(factory.tuple("4")));
        x3_upper.add(factory.tuple("4").product(factory.tuple("5")));
        x3_upper.add(factory.tuple("5").product(factory.tuple("6")));
        x3_upper.add(factory.tuple("6").product(factory.tuple("7")));
        bounds.boundExactly(x3, x3_upper);

        TupleSet x4_upper = factory.noneOf(1);
        x4_upper.add(factory.tuple("0"));
        x4_upper.add(factory.tuple("1"));
        x4_upper.add(factory.tuple("2"));
        x4_upper.add(factory.tuple("3"));
        x4_upper.add(factory.tuple("4"));
        x4_upper.add(factory.tuple("5"));
        x4_upper.add(factory.tuple("6"));
        bounds.boundExactly(x4, x4_upper);

        TupleSet x5_upper = factory.noneOf(1);
        bounds.boundExactly(x5, x5_upper);

        TupleSet x6_upper = factory.noneOf(1);
        x6_upper.add(factory.tuple("Platform$0"));
        x6_upper.add(factory.tuple("Platform$1"));
        x6_upper.add(factory.tuple("Platform$2"));
        x6_upper.add(factory.tuple("Platform$3"));
        x6_upper.add(factory.tuple("Platform$4"));
        x6_upper.add(factory.tuple("Platform$5"));
        x6_upper.add(factory.tuple("Platform$6"));
        x6_upper.add(factory.tuple("Platform$7"));
        x6_upper.add(factory.tuple("Platform$8"));
        x6_upper.add(factory.tuple("Platform$9"));
        bounds.bound(x6, x6_upper);

        TupleSet x7_upper = factory.noneOf(1);
        x7_upper.add(factory.tuple("Man$0"));
        x7_upper.add(factory.tuple("Man$1"));
        x7_upper.add(factory.tuple("Man$2"));
        x7_upper.add(factory.tuple("Man$3"));
        x7_upper.add(factory.tuple("Man$4"));
        x7_upper.add(factory.tuple("Man$5"));
        x7_upper.add(factory.tuple("Man$6"));
        x7_upper.add(factory.tuple("Man$7"));
        x7_upper.add(factory.tuple("Man$8"));
        x7_upper.add(factory.tuple("Man$9"));
        bounds.bound(x7, x7_upper);

        TupleSet x8_upper = factory.noneOf(2);
        x8_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$0")));
        x8_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$1")));
        x8_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$2")));
        x8_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$3")));
        x8_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$4")));
        x8_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$5")));
        x8_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$6")));
        x8_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$7")));
        x8_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$8")));
        x8_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$9")));
        x8_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$0")));
        x8_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$1")));
        x8_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$2")));
        x8_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$3")));
        x8_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$4")));
        x8_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$5")));
        x8_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$6")));
        x8_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$7")));
        x8_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$8")));
        x8_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$9")));
        x8_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$0")));
        x8_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$1")));
        x8_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$2")));
        x8_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$3")));
        x8_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$4")));
        x8_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$5")));
        x8_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$6")));
        x8_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$7")));
        x8_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$8")));
        x8_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$9")));
        x8_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$0")));
        x8_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$1")));
        x8_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$2")));
        x8_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$3")));
        x8_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$4")));
        x8_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$5")));
        x8_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$6")));
        x8_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$7")));
        x8_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$8")));
        x8_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$9")));
        x8_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$0")));
        x8_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$1")));
        x8_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$2")));
        x8_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$3")));
        x8_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$4")));
        x8_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$5")));
        x8_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$6")));
        x8_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$7")));
        x8_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$8")));
        x8_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$9")));
        x8_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$0")));
        x8_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$1")));
        x8_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$2")));
        x8_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$3")));
        x8_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$4")));
        x8_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$5")));
        x8_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$6")));
        x8_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$7")));
        x8_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$8")));
        x8_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$9")));
        x8_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$0")));
        x8_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$1")));
        x8_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$2")));
        x8_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$3")));
        x8_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$4")));
        x8_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$5")));
        x8_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$6")));
        x8_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$7")));
        x8_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$8")));
        x8_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$9")));
        x8_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$0")));
        x8_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$1")));
        x8_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$2")));
        x8_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$3")));
        x8_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$4")));
        x8_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$5")));
        x8_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$6")));
        x8_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$7")));
        x8_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$8")));
        x8_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$9")));
        x8_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$0")));
        x8_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$1")));
        x8_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$2")));
        x8_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$3")));
        x8_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$4")));
        x8_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$5")));
        x8_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$6")));
        x8_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$7")));
        x8_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$8")));
        x8_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$9")));
        x8_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$0")));
        x8_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$1")));
        x8_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$2")));
        x8_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$3")));
        x8_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$4")));
        x8_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$5")));
        x8_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$6")));
        x8_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$7")));
        x8_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$8")));
        x8_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$9")));
        bounds.bound(x8, x8_upper);

        TupleSet x9_upper = factory.noneOf(2);
        x9_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$0")));
        x9_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$1")));
        x9_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$2")));
        x9_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$3")));
        x9_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$4")));
        x9_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$5")));
        x9_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$6")));
        x9_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$7")));
        x9_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$8")));
        x9_upper.add(factory.tuple("Man$0").product(factory.tuple("Platform$9")));
        x9_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$0")));
        x9_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$1")));
        x9_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$2")));
        x9_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$3")));
        x9_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$4")));
        x9_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$5")));
        x9_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$6")));
        x9_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$7")));
        x9_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$8")));
        x9_upper.add(factory.tuple("Man$1").product(factory.tuple("Platform$9")));
        x9_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$0")));
        x9_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$1")));
        x9_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$2")));
        x9_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$3")));
        x9_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$4")));
        x9_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$5")));
        x9_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$6")));
        x9_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$7")));
        x9_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$8")));
        x9_upper.add(factory.tuple("Man$2").product(factory.tuple("Platform$9")));
        x9_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$0")));
        x9_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$1")));
        x9_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$2")));
        x9_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$3")));
        x9_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$4")));
        x9_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$5")));
        x9_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$6")));
        x9_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$7")));
        x9_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$8")));
        x9_upper.add(factory.tuple("Man$3").product(factory.tuple("Platform$9")));
        x9_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$0")));
        x9_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$1")));
        x9_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$2")));
        x9_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$3")));
        x9_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$4")));
        x9_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$5")));
        x9_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$6")));
        x9_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$7")));
        x9_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$8")));
        x9_upper.add(factory.tuple("Man$4").product(factory.tuple("Platform$9")));
        x9_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$0")));
        x9_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$1")));
        x9_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$2")));
        x9_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$3")));
        x9_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$4")));
        x9_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$5")));
        x9_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$6")));
        x9_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$7")));
        x9_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$8")));
        x9_upper.add(factory.tuple("Man$5").product(factory.tuple("Platform$9")));
        x9_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$0")));
        x9_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$1")));
        x9_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$2")));
        x9_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$3")));
        x9_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$4")));
        x9_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$5")));
        x9_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$6")));
        x9_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$7")));
        x9_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$8")));
        x9_upper.add(factory.tuple("Man$6").product(factory.tuple("Platform$9")));
        x9_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$0")));
        x9_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$1")));
        x9_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$2")));
        x9_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$3")));
        x9_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$4")));
        x9_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$5")));
        x9_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$6")));
        x9_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$7")));
        x9_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$8")));
        x9_upper.add(factory.tuple("Man$7").product(factory.tuple("Platform$9")));
        x9_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$0")));
        x9_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$1")));
        x9_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$2")));
        x9_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$3")));
        x9_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$4")));
        x9_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$5")));
        x9_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$6")));
        x9_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$7")));
        x9_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$8")));
        x9_upper.add(factory.tuple("Man$8").product(factory.tuple("Platform$9")));
        x9_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$0")));
        x9_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$1")));
        x9_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$2")));
        x9_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$3")));
        x9_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$4")));
        x9_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$5")));
        x9_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$6")));
        x9_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$7")));
        x9_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$8")));
        x9_upper.add(factory.tuple("Man$9").product(factory.tuple("Platform$9")));
        bounds.bound(x9, x9_upper);

        bounds.boundExactly(-8, factory.range(factory.tuple("-8"), factory.tuple("-8")));
        bounds.boundExactly(-7, factory.range(factory.tuple("-7"), factory.tuple("-7")));
        bounds.boundExactly(-6, factory.range(factory.tuple("-6"), factory.tuple("-6")));
        bounds.boundExactly(-5, factory.range(factory.tuple("-5"), factory.tuple("-5")));
        bounds.boundExactly(-4, factory.range(factory.tuple("-4"), factory.tuple("-4")));
        bounds.boundExactly(-3, factory.range(factory.tuple("-3"), factory.tuple("-3")));
        bounds.boundExactly(-2, factory.range(factory.tuple("-2"), factory.tuple("-2")));
        bounds.boundExactly(-1, factory.range(factory.tuple("-1"), factory.tuple("-1")));
        bounds.boundExactly(0, factory.range(factory.tuple("0"), factory.tuple("0")));
        bounds.boundExactly(1, factory.range(factory.tuple("1"), factory.tuple("1")));
        bounds.boundExactly(2, factory.range(factory.tuple("2"), factory.tuple("2")));
        bounds.boundExactly(3, factory.range(factory.tuple("3"), factory.tuple("3")));
        bounds.boundExactly(4, factory.range(factory.tuple("4"), factory.tuple("4")));
        bounds.boundExactly(5, factory.range(factory.tuple("5"), factory.tuple("5")));
        bounds.boundExactly(6, factory.range(factory.tuple("6"), factory.tuple("6")));
        bounds.boundExactly(7, factory.range(factory.tuple("7"), factory.tuple("7")));

        Variable x13 = Variable.unary("BelowToo''_this");
        Decls x12 = x13.oneOf(x7);
        Expression x16 = x13.join(x8);
        Formula x15 = x16.one();
        Formula x17 = x16.in(x6);
        Formula x14 = x15.and(x17);
        Formula x11 = x14.forAll(x12);
        Expression x19 = x8.join(Expression.UNIV);
        Formula x18 = x19.in(x7);
        Variable x23 = Variable.unary("BelowToo''_this");
        Decls x22 = x23.oneOf(x7);
        Expression x26 = x23.join(x9);
        Formula x25 = x26.one();
        Formula x27 = x26.in(x6);
        Formula x24 = x25.and(x27);
        Formula x21 = x24.forAll(x22);
        Expression x29 = x9.join(Expression.UNIV);
        Formula x28 = x29.in(x7);
        Variable x32 = Variable.unary("BelowToo''_m");
        Decls x31 = x32.oneOf(x7);
        Variable x35 = Variable.unary("BelowToo''_n");
        Decls x34 = x35.oneOf(x7);
        Expression x37 = x35.join(x9);
        Expression x38 = x32.join(x8);
        Formula x36 = x37.eq(x38);
        Formula x33 = x36.forSome(x34);
        Formula x30 = x33.forAll(x31);
        Variable x45 = Variable.unary("NoSharing_m");
        Decls x44 = x45.oneOf(x7);
        Variable x47 = Variable.unary("NoSharing_n");
        Decls x46 = x47.oneOf(x7);
        Decls x43 = x44.and(x46);
        Formula x51 = x45.eq(x47);
        Formula x50 = x51.not();
        Expression x54 = x45.join(x9);
        Expression x55 = x47.join(x9);
        Formula x53 = x54.eq(x55);
        Expression x57 = x45.join(x8);
        Expression x58 = x47.join(x8);
        Formula x56 = x57.eq(x58);
        Formula x52 = x53.or(x56);
        Formula x49 = x50.and(x52);
        Formula x48 = x49.not();
        Formula x42 = x48.forAll(x43);
        Formula x41 = x42.not();
        Variable x61 = Variable.unary("BelowToo''_m");
        Decls x60 = x61.oneOf(x7);
        Variable x64 = Variable.unary("BelowToo''_n");
        Decls x63 = x64.oneOf(x7);
        Expression x66 = x61.join(x9);
        Expression x67 = x64.join(x8);
        Formula x65 = x66.eq(x67);
        Formula x62 = x65.forSome(x63);
        Formula x59 = x62.forAll(x60);
        Formula x40 = x41.or(x59);
        Formula x39 = x40.not();
        Formula x68 = x0.eq(x0);
        Formula x69 = x1.eq(x1);
        Formula x70 = x2.eq(x2);
        Formula x71 = x3.eq(x3);
        Formula x72 = x4.eq(x4);
        Formula x73 = x5.eq(x5);
        Formula x74 = x6.eq(x6);
        Formula x75 = x7.eq(x7);
        Formula x76 = x8.eq(x8);
        Formula x77 = x9.eq(x9);
        Formula x10 = Formula.compose(FormulaOperator.AND, x11, x18, x21, x28, x30, x39, x68, x69, x70, x71, x72, x73, x74, x75, x76, x77);

        System.out.println("Compiling...");
        Meow meow = new Meow(x10, bounds);
        long time = System.currentTimeMillis();
        meow.compile();
        time = System.currentTimeMillis() - time;
        System.out.println(meow.toString());
        System.out.println("took " + time + "ms");
    }
}
