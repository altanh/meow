package meow.test.alloy;

import java.util.Arrays;
import java.util.List;

import kodkod.ast.*;
import kodkod.ast.operator.*;
import kodkod.instance.*;
import meow.Meow;
import meow.engine.GraphCompiler;

/* 
  ==================================================
    kodkod formula: 
  ==================================================
    no (this/And & this/Or) && 
    no ((this/And + this/Or) & this/Not) && 
    no ((this/And + this/Or + this/Not) & this/Var) && 
    no (this/True & this/False) && 
    (all checkTheorem_this: this/And + this/Or + this/Not + this/Var | 
      (checkTheorem_this . this/Formula.children) in (this/And + this/Or + 
      this/Not + this/Var)) && 
    (this/Formula.children . univ) in (this/And + this/Or + this/Not + this/Var) && 
    (ordering/Ord . ordering/Ord.First) in (this/True + this/False) && 
    (ordering/Ord . ordering/Ord.Next) in ((this/True + this/False) -> (
    this/True + this/False)) && 
    (all v4: this/True + this/False | 
      (v4 = (ordering/Ord . ordering/Ord.First) || 
       one ((ordering/Ord . ordering/Ord.Next) . v4)) && 
      (v4 = ((this/True + this/False) - ((ordering/Ord . ordering/Ord.Next) . (
       this/True + this/False))) || 
       one (v4 . (ordering/Ord . ordering/Ord.Next))) && 
      !(v4 in (v4 . ^(ordering/Ord . ordering/Ord.Next)))) && 
    (this/True + this/False) in ((ordering/Ord . ordering/Ord.First) . *(
    ordering/Ord . ordering/Ord.Next)) && 
    no ((ordering/Ord . ordering/Ord.Next) . (ordering/Ord . ordering/Ord.First)
    ) && 
    (all checkTheorem_v: this/Var | 
      no (checkTheorem_v . this/Formula.children)) && 
    (all checkTheorem_n: this/Not | 
      one (checkTheorem_n . this/Formula.children)) && 
    (all checkTheorem_a: this/And | 
      some checkTheorem_c1: checkTheorem_a . this/Formula.children, 
      checkTheorem_c2: checkTheorem_a . this/Formula.children | 
       !(checkTheorem_c1 = checkTheorem_c2)) && 
    (all checkTheorem_o: this/Or | 
      some checkTheorem_c1: checkTheorem_o . this/Formula.children, 
      checkTheorem_c2: checkTheorem_o . this/Formula.children | 
       !(checkTheorem_c1 = checkTheorem_c2)) && 
    (all checkTheorem_f: this/And + this/Or + this/Not + this/Var | 
      !(checkTheorem_f in (checkTheorem_f . ^this/Formula.children))) && 
    !(all MonotonicityOfNNF_f: this/And + this/Or + this/Not + this/Var, 
      MonotonicityOfNNF_V1: set (this/And + this/Or + this/Not + this/Var) -> (
      this/True + this/False), MonotonicityOfNNF_V2: set (this/And + this/Or + 
      this/Not + this/Var) -> (this/True + this/False) | 
       (MonotonicityOfNNF_V1 in ((this/And + this/Or + this/Not + this/Var) -> (
        this/True + this/False)) && 
        MonotonicityOfNNF_V2 in ((this/And + this/Or + this/Not + this/Var) -> (
        this/True + this/False))) => 
       (!((all NNF_n: MonotonicityOfNNF_f . (^this/Formula.children + (iden & ((
           ints + String + this/And + this/Or + this/Not + this/Var + this/True + 
           this/False + ordering/Ord) -> univ))) | 
            !(NNF_n in this/Not) || 
            (NNF_n . this/Formula.children) in this/Var) && 
          (all valuation_f: this/And + this/Or + this/Not + this/Var | 
            one (valuation_f . MonotonicityOfNNF_V1)) && 
          (all valuation_n: this/Not | 
            (valuation_n . MonotonicityOfNNF_V1) = this/True <=> 
            ((valuation_n . this/Formula.children) . MonotonicityOfNNF_V1) = 
            this/False) && 
          (all valuation_o: this/Or | 
            (valuation_o . MonotonicityOfNNF_V1) = this/True <=> 
            (some valuation_c: valuation_o . this/Formula.children | 
              (valuation_c . MonotonicityOfNNF_V1) = this/True)) && 
          (all valuation_a: this/And | 
            (valuation_a . MonotonicityOfNNF_V1) = this/True <=> 
            (all valuation_ac: valuation_a . this/Formula.children | 
              (valuation_ac . MonotonicityOfNNF_V1) = this/True)) && 
          (all valuation_f: this/And + this/Or + this/Not + this/Var | 
            one (valuation_f . MonotonicityOfNNF_V1)) && 
          (all valuation_n: this/Not | 
            (valuation_n . MonotonicityOfNNF_V1) = this/True <=> 
            ((valuation_n . this/Formula.children) . MonotonicityOfNNF_V1) = 
            this/False) && 
          (all valuation_o: this/Or | 
            (valuation_o . MonotonicityOfNNF_V1) = this/True <=> 
            (some valuation_c: valuation_o . this/Formula.children | 
              (valuation_c . MonotonicityOfNNF_V1) = this/True)) && 
          (all valuation_a: this/And | 
            (valuation_a . MonotonicityOfNNF_V1) = this/True <=> 
            (all valuation_ac: valuation_a . this/Formula.children | 
              (valuation_ac . MonotonicityOfNNF_V1) = this/True)) && 
          (MonotonicityOfNNF_f . MonotonicityOfNNF_V1) = this/True && 
          {pos_l: this/Var + (this/Not & (this/Formula.children . this/Var)) | 
          pos_l in (MonotonicityOfNNF_f . (^this/Formula.children + (iden & ((
          ints + String + this/And + this/Or + this/Not + this/Var + this/True + 
          this/False + ordering/Ord) -> univ)))) && 
          (pos_l . MonotonicityOfNNF_V1) = this/True} in {pos_l: this/Var + (
          this/Not & (this/Formula.children . this/Var)) | pos_l in (
          MonotonicityOfNNF_f . (^this/Formula.children + (iden & ((ints + 
          String + this/And + this/Or + this/Not + this/Var + this/True + 
          this/False + ordering/Ord) -> univ)))) && 
          (pos_l . MonotonicityOfNNF_V2) = this/True} && 
          (all valuation_f: this/And + this/Or + this/Not + this/Var | 
            one (valuation_f . MonotonicityOfNNF_V2)) && 
          (all valuation_n: this/Not | 
            (valuation_n . MonotonicityOfNNF_V2) = this/True <=> 
            ((valuation_n . this/Formula.children) . MonotonicityOfNNF_V2) = 
            this/False) && 
          (all valuation_o: this/Or | 
            (valuation_o . MonotonicityOfNNF_V2) = this/True <=> 
            (some valuation_c: valuation_o . this/Formula.children | 
              (valuation_c . MonotonicityOfNNF_V2) = this/True)) && 
          (all valuation_a: this/And | 
            (valuation_a . MonotonicityOfNNF_V2) = this/True <=> 
            (all valuation_ac: valuation_a . this/Formula.children | 
              (valuation_ac . MonotonicityOfNNF_V2) = this/True))) || 
        ((all valuation_f: this/And + this/Or + this/Not + this/Var | 
           one (valuation_f . MonotonicityOfNNF_V2)) && 
         (all valuation_n: this/Not | 
           (valuation_n . MonotonicityOfNNF_V2) = this/True <=> 
           ((valuation_n . this/Formula.children) . MonotonicityOfNNF_V2) = 
           this/False) && 
         (all valuation_o: this/Or | 
           (valuation_o . MonotonicityOfNNF_V2) = this/True <=> 
           (some valuation_c: valuation_o . this/Formula.children | 
             (valuation_c . MonotonicityOfNNF_V2) = this/True)) && 
         (all valuation_a: this/And | 
           (valuation_a . MonotonicityOfNNF_V2) = this/True <=> 
           (all valuation_ac: valuation_a . this/Formula.children | 
             (valuation_ac . MonotonicityOfNNF_V2) = this/True)) && 
         (MonotonicityOfNNF_f . MonotonicityOfNNF_V2) = this/True))) && 
    Int/min = Int/min && 
    Int/zero = Int/zero && 
    Int/max = Int/max && 
    Int/next = Int/next && 
    seq/Int = seq/Int && 
    String = String && 
    this/And = this/And && 
    this/Or = this/Or && 
    this/Not = this/Not && 
    this/Var = this/Var && 
    this/True = this/True && 
    this/False = this/False && 
    ordering/Ord = ordering/Ord && 
    this/Formula.children = this/Formula.children && 
    ordering/Ord.First = ordering/Ord.First && 
    ordering/Ord.Next = ordering/Ord.Next
  ==================================================
*/
public final class NNF {

    public static void main(String[] args) throws Exception {

        Relation x0 = Relation.unary("Int/min");
        Relation x1 = Relation.unary("Int/zero");
        Relation x2 = Relation.unary("Int/max");
        Relation x3 = Relation.nary("Int/next", 2);
        Relation x4 = Relation.unary("seq/Int");
        Relation x5 = Relation.unary("String");
        Relation x6 = Relation.unary("this/And");
        Relation x7 = Relation.unary("this/Or");
        Relation x8 = Relation.unary("this/Not");
        Relation x9 = Relation.unary("this/Var");
        Relation x10 = Relation.unary("this/True");
        Relation x11 = Relation.unary("this/False");
        Relation x12 = Relation.unary("ordering/Ord");
        Relation x13 = Relation.nary("this/Formula.children", 2);
        Relation x14 = Relation.nary("ordering/Ord.First", 2);
        Relation x15 = Relation.nary("ordering/Ord.Next", 3);

        List<String> atomlist = Arrays.asList(
                "-1", "-2", "-3", "-4", "-5",
                "-6", "-7", "-8", "0", "1", "2",
                "3", "4", "5", "6", "7", "False$0",
                "Formula$0", "Formula$1", "Formula$2", "Formula$3", "Formula$4", "Formula$5",
                "Formula$6", "Formula$7", "True$0", "ordering/Ord$0"
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
        bounds.boundExactly(x4, x4_upper);

        TupleSet x5_upper = factory.noneOf(1);
        bounds.boundExactly(x5, x5_upper);

        TupleSet x6_upper = factory.noneOf(1);
        x6_upper.add(factory.tuple("Formula$0"));
        x6_upper.add(factory.tuple("Formula$1"));
        x6_upper.add(factory.tuple("Formula$2"));
        x6_upper.add(factory.tuple("Formula$3"));
        x6_upper.add(factory.tuple("Formula$4"));
        x6_upper.add(factory.tuple("Formula$5"));
        x6_upper.add(factory.tuple("Formula$6"));
        x6_upper.add(factory.tuple("Formula$7"));
        bounds.bound(x6, x6_upper);

        TupleSet x7_upper = factory.noneOf(1);
        x7_upper.add(factory.tuple("Formula$0"));
        x7_upper.add(factory.tuple("Formula$1"));
        x7_upper.add(factory.tuple("Formula$2"));
        x7_upper.add(factory.tuple("Formula$3"));
        x7_upper.add(factory.tuple("Formula$4"));
        x7_upper.add(factory.tuple("Formula$5"));
        x7_upper.add(factory.tuple("Formula$6"));
        x7_upper.add(factory.tuple("Formula$7"));
        bounds.bound(x7, x7_upper);

        TupleSet x8_upper = factory.noneOf(1);
        x8_upper.add(factory.tuple("Formula$0"));
        x8_upper.add(factory.tuple("Formula$1"));
        x8_upper.add(factory.tuple("Formula$2"));
        x8_upper.add(factory.tuple("Formula$3"));
        x8_upper.add(factory.tuple("Formula$4"));
        x8_upper.add(factory.tuple("Formula$5"));
        x8_upper.add(factory.tuple("Formula$6"));
        x8_upper.add(factory.tuple("Formula$7"));
        bounds.bound(x8, x8_upper);

        TupleSet x9_upper = factory.noneOf(1);
        x9_upper.add(factory.tuple("Formula$0"));
        x9_upper.add(factory.tuple("Formula$1"));
        x9_upper.add(factory.tuple("Formula$2"));
        x9_upper.add(factory.tuple("Formula$3"));
        x9_upper.add(factory.tuple("Formula$4"));
        x9_upper.add(factory.tuple("Formula$5"));
        x9_upper.add(factory.tuple("Formula$6"));
        x9_upper.add(factory.tuple("Formula$7"));
        bounds.bound(x9, x9_upper);

        TupleSet x10_upper = factory.noneOf(1);
        x10_upper.add(factory.tuple("True$0"));
        bounds.boundExactly(x10, x10_upper);

        TupleSet x11_upper = factory.noneOf(1);
        x11_upper.add(factory.tuple("False$0"));
        bounds.boundExactly(x11, x11_upper);

        TupleSet x12_upper = factory.noneOf(1);
        x12_upper.add(factory.tuple("ordering/Ord$0"));
        bounds.boundExactly(x12, x12_upper);

        TupleSet x13_upper = factory.noneOf(2);
        x13_upper.add(factory.tuple("Formula$0").product(factory.tuple("Formula$0")));
        x13_upper.add(factory.tuple("Formula$0").product(factory.tuple("Formula$1")));
        x13_upper.add(factory.tuple("Formula$0").product(factory.tuple("Formula$2")));
        x13_upper.add(factory.tuple("Formula$0").product(factory.tuple("Formula$3")));
        x13_upper.add(factory.tuple("Formula$0").product(factory.tuple("Formula$4")));
        x13_upper.add(factory.tuple("Formula$0").product(factory.tuple("Formula$5")));
        x13_upper.add(factory.tuple("Formula$0").product(factory.tuple("Formula$6")));
        x13_upper.add(factory.tuple("Formula$0").product(factory.tuple("Formula$7")));
        x13_upper.add(factory.tuple("Formula$1").product(factory.tuple("Formula$0")));
        x13_upper.add(factory.tuple("Formula$1").product(factory.tuple("Formula$1")));
        x13_upper.add(factory.tuple("Formula$1").product(factory.tuple("Formula$2")));
        x13_upper.add(factory.tuple("Formula$1").product(factory.tuple("Formula$3")));
        x13_upper.add(factory.tuple("Formula$1").product(factory.tuple("Formula$4")));
        x13_upper.add(factory.tuple("Formula$1").product(factory.tuple("Formula$5")));
        x13_upper.add(factory.tuple("Formula$1").product(factory.tuple("Formula$6")));
        x13_upper.add(factory.tuple("Formula$1").product(factory.tuple("Formula$7")));
        x13_upper.add(factory.tuple("Formula$2").product(factory.tuple("Formula$0")));
        x13_upper.add(factory.tuple("Formula$2").product(factory.tuple("Formula$1")));
        x13_upper.add(factory.tuple("Formula$2").product(factory.tuple("Formula$2")));
        x13_upper.add(factory.tuple("Formula$2").product(factory.tuple("Formula$3")));
        x13_upper.add(factory.tuple("Formula$2").product(factory.tuple("Formula$4")));
        x13_upper.add(factory.tuple("Formula$2").product(factory.tuple("Formula$5")));
        x13_upper.add(factory.tuple("Formula$2").product(factory.tuple("Formula$6")));
        x13_upper.add(factory.tuple("Formula$2").product(factory.tuple("Formula$7")));
        x13_upper.add(factory.tuple("Formula$3").product(factory.tuple("Formula$0")));
        x13_upper.add(factory.tuple("Formula$3").product(factory.tuple("Formula$1")));
        x13_upper.add(factory.tuple("Formula$3").product(factory.tuple("Formula$2")));
        x13_upper.add(factory.tuple("Formula$3").product(factory.tuple("Formula$3")));
        x13_upper.add(factory.tuple("Formula$3").product(factory.tuple("Formula$4")));
        x13_upper.add(factory.tuple("Formula$3").product(factory.tuple("Formula$5")));
        x13_upper.add(factory.tuple("Formula$3").product(factory.tuple("Formula$6")));
        x13_upper.add(factory.tuple("Formula$3").product(factory.tuple("Formula$7")));
        x13_upper.add(factory.tuple("Formula$4").product(factory.tuple("Formula$0")));
        x13_upper.add(factory.tuple("Formula$4").product(factory.tuple("Formula$1")));
        x13_upper.add(factory.tuple("Formula$4").product(factory.tuple("Formula$2")));
        x13_upper.add(factory.tuple("Formula$4").product(factory.tuple("Formula$3")));
        x13_upper.add(factory.tuple("Formula$4").product(factory.tuple("Formula$4")));
        x13_upper.add(factory.tuple("Formula$4").product(factory.tuple("Formula$5")));
        x13_upper.add(factory.tuple("Formula$4").product(factory.tuple("Formula$6")));
        x13_upper.add(factory.tuple("Formula$4").product(factory.tuple("Formula$7")));
        x13_upper.add(factory.tuple("Formula$5").product(factory.tuple("Formula$0")));
        x13_upper.add(factory.tuple("Formula$5").product(factory.tuple("Formula$1")));
        x13_upper.add(factory.tuple("Formula$5").product(factory.tuple("Formula$2")));
        x13_upper.add(factory.tuple("Formula$5").product(factory.tuple("Formula$3")));
        x13_upper.add(factory.tuple("Formula$5").product(factory.tuple("Formula$4")));
        x13_upper.add(factory.tuple("Formula$5").product(factory.tuple("Formula$5")));
        x13_upper.add(factory.tuple("Formula$5").product(factory.tuple("Formula$6")));
        x13_upper.add(factory.tuple("Formula$5").product(factory.tuple("Formula$7")));
        x13_upper.add(factory.tuple("Formula$6").product(factory.tuple("Formula$0")));
        x13_upper.add(factory.tuple("Formula$6").product(factory.tuple("Formula$1")));
        x13_upper.add(factory.tuple("Formula$6").product(factory.tuple("Formula$2")));
        x13_upper.add(factory.tuple("Formula$6").product(factory.tuple("Formula$3")));
        x13_upper.add(factory.tuple("Formula$6").product(factory.tuple("Formula$4")));
        x13_upper.add(factory.tuple("Formula$6").product(factory.tuple("Formula$5")));
        x13_upper.add(factory.tuple("Formula$6").product(factory.tuple("Formula$6")));
        x13_upper.add(factory.tuple("Formula$6").product(factory.tuple("Formula$7")));
        x13_upper.add(factory.tuple("Formula$7").product(factory.tuple("Formula$0")));
        x13_upper.add(factory.tuple("Formula$7").product(factory.tuple("Formula$1")));
        x13_upper.add(factory.tuple("Formula$7").product(factory.tuple("Formula$2")));
        x13_upper.add(factory.tuple("Formula$7").product(factory.tuple("Formula$3")));
        x13_upper.add(factory.tuple("Formula$7").product(factory.tuple("Formula$4")));
        x13_upper.add(factory.tuple("Formula$7").product(factory.tuple("Formula$5")));
        x13_upper.add(factory.tuple("Formula$7").product(factory.tuple("Formula$6")));
        x13_upper.add(factory.tuple("Formula$7").product(factory.tuple("Formula$7")));
        bounds.bound(x13, x13_upper);

        TupleSet x14_upper = factory.noneOf(2);
        x14_upper.add(factory.tuple("ordering/Ord$0").product(factory.tuple("True$0")));
        bounds.boundExactly(x14, x14_upper);

        TupleSet x15_upper = factory.noneOf(3);
        x15_upper.add(factory.tuple("ordering/Ord$0").product(factory.tuple("True$0")).product(factory.tuple("False$0")));
        bounds.boundExactly(x15, x15_upper);

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

        Expression x18 = x6.intersection(x7);
        Formula x17 = x18.no();
        Expression x21 = x6.union(x7);
        Expression x20 = x21.intersection(x8);
        Formula x19 = x20.no();
        Expression x24 = x21.union(x8);
        Expression x23 = x24.intersection(x9);
        Formula x22 = x23.no();
        Expression x26 = x10.intersection(x11);
        Formula x25 = x26.no();
        Variable x29 = Variable.unary("checkTheorem_this");
        Expression x30 = x24.union(x9);
        Decls x28 = x29.oneOf(x30);
        Expression x32 = x29.join(x13);
        Formula x31 = x32.in(x30);
        Formula x27 = x31.forAll(x28);
        Expression x34 = x13.join(Expression.UNIV);
        Formula x33 = x34.in(x30);
        Expression x37 = x12.join(x14);
        Expression x38 = x10.union(x11);
        Formula x36 = x37.in(x38);
        Expression x40 = x12.join(x15);
        Expression x41 = x38.product(x38);
        Formula x39 = x40.in(x41);
        Variable x46 = Variable.unary("v4");
        Decls x45 = x46.oneOf(x38);
        Expression x51 = x12.join(x14);
        Formula x50 = x46.eq(x51);
        Expression x54 = x12.join(x15);
        Expression x53 = x54.join(x46);
        Formula x52 = x53.one();
        Formula x49 = x50.or(x52);
        Expression x58 = x54.join(x38);
        Expression x57 = x38.difference(x58);
        Formula x56 = x46.eq(x57);
        Expression x60 = x46.join(x54);
        Formula x59 = x60.one();
        Formula x55 = x56.or(x59);
        Formula x48 = x49.and(x55);
        Expression x64 = x54.closure();
        Expression x63 = x46.join(x64);
        Formula x62 = x46.in(x63);
        Formula x61 = x62.not();
        Formula x47 = x48.and(x61);
        Formula x44 = x47.forAll(x45);
        Expression x67 = x54.reflexiveClosure();
        Expression x66 = x51.join(x67);
        Formula x65 = x38.in(x66);
        Formula x43 = x44.and(x65);
        Expression x69 = x54.join(x51);
        Formula x68 = x69.no();
        Formula x42 = x43.and(x68);
        Variable x72 = Variable.unary("checkTheorem_v");
        Decls x71 = x72.oneOf(x9);
        Expression x74 = x72.join(x13);
        Formula x73 = x74.no();
        Formula x70 = x73.forAll(x71);
        Variable x77 = Variable.unary("checkTheorem_n");
        Decls x76 = x77.oneOf(x8);
        Expression x79 = x77.join(x13);
        Formula x78 = x79.one();
        Formula x75 = x78.forAll(x76);
        Variable x82 = Variable.unary("checkTheorem_a");
        Decls x81 = x82.oneOf(x6);
        Variable x86 = Variable.unary("checkTheorem_c1");
        Expression x87 = x82.join(x13);
        Decls x85 = x86.oneOf(x87);
        Variable x89 = Variable.unary("checkTheorem_c2");
        Decls x88 = x89.oneOf(x87);
        Decls x84 = x85.and(x88);
        Formula x91 = x86.eq(x89);
        Formula x90 = x91.not();
        Formula x83 = x90.forSome(x84);
        Formula x80 = x83.forAll(x81);
        Variable x94 = Variable.unary("checkTheorem_o");
        Decls x93 = x94.oneOf(x7);
        Variable x98 = Variable.unary("checkTheorem_c1");
        Expression x99 = x94.join(x13);
        Decls x97 = x98.oneOf(x99);
        Variable x101 = Variable.unary("checkTheorem_c2");
        Decls x100 = x101.oneOf(x99);
        Decls x96 = x97.and(x100);
        Formula x103 = x98.eq(x101);
        Formula x102 = x103.not();
        Formula x95 = x102.forSome(x96);
        Formula x92 = x95.forAll(x93);
        Variable x106 = Variable.unary("checkTheorem_f");
        Decls x105 = x106.oneOf(x30);
        Expression x110 = x13.closure();
        Expression x109 = x106.join(x110);
        Formula x108 = x106.in(x109);
        Formula x107 = x108.not();
        Formula x104 = x107.forAll(x105);
        Variable x115 = Variable.unary("MonotonicityOfNNF_f");
        Decls x114 = x115.oneOf(x30);
        Variable x117 = Variable.nary("MonotonicityOfNNF_V1", 2);
        Expression x118 = x30.product(x38);
        Decls x116 = x117.setOf(x118);
        Variable x120 = Variable.nary("MonotonicityOfNNF_V2", 2);
        Expression x121 = x30.product(x38);
        Decls x119 = x120.setOf(x121);
        Decls x113 = x114.and(x116).and(x119);
        Expression x125 = x30.product(x38);
        Formula x124 = x117.in(x125);
        Expression x127 = x30.product(x38);
        Formula x126 = x120.in(x127);
        Formula x123 = x124.and(x126);
        Variable x134 = Variable.unary("NNF_n");
        Expression x137 = x13.closure();
        Expression x144 = Expression.INTS.union(x5);
        Expression x143 = x144.union(x30);
        Expression x142 = x143.union(x38);
        Expression x141 = x142.union(x12);
        Expression x140 = x141.product(Expression.UNIV);
        Expression x138 = Expression.IDEN.intersection(x140);
        Expression x136 = x137.union(x138);
        Expression x135 = x115.join(x136);
        Decls x133 = x134.oneOf(x135);
        Formula x148 = x134.in(x8);
        Formula x147 = x148.not();
        Expression x150 = x134.join(x13);
        Formula x149 = x150.in(x9);
        Formula x146 = x147.or(x149);
        Formula x132 = x146.forAll(x133);
        Variable x157 = Variable.unary("valuation_f");
        Decls x156 = x157.oneOf(x30);
        Expression x159 = x157.join(x117);
        Formula x158 = x159.one();
        Formula x155 = x158.forAll(x156);
        Variable x163 = Variable.unary("valuation_n");
        Decls x162 = x163.oneOf(x8);
        Expression x166 = x163.join(x117);
        Formula x165 = x166.eq(x10);
        Expression x169 = x163.join(x13);
        Expression x168 = x169.join(x117);
        Formula x167 = x168.eq(x11);
        Formula x164 = x165.iff(x167);
        Formula x161 = x164.forAll(x162);
        Variable x172 = Variable.unary("valuation_o");
        Decls x171 = x172.oneOf(x7);
        Expression x175 = x172.join(x117);
        Formula x174 = x175.eq(x10);
        Variable x178 = Variable.unary("valuation_c");
        Expression x179 = x172.join(x13);
        Decls x177 = x178.oneOf(x179);
        Expression x181 = x178.join(x117);
        Formula x180 = x181.eq(x10);
        Formula x176 = x180.forSome(x177);
        Formula x173 = x174.iff(x176);
        Formula x170 = x173.forAll(x171);
        Formula x160 = x161.and(x170);
        Formula x154 = x155.and(x160);
        Variable x184 = Variable.unary("valuation_a");
        Decls x183 = x184.oneOf(x6);
        Expression x187 = x184.join(x117);
        Formula x186 = x187.eq(x10);
        Variable x190 = Variable.unary("valuation_ac");
        Expression x191 = x184.join(x13);
        Decls x189 = x190.oneOf(x191);
        Expression x193 = x190.join(x117);
        Formula x192 = x193.eq(x10);
        Formula x188 = x192.forAll(x189);
        Formula x185 = x186.iff(x188);
        Formula x182 = x185.forAll(x183);
        Formula x153 = x154.and(x182);
        Variable x199 = Variable.unary("valuation_f");
        Decls x198 = x199.oneOf(x30);
        Expression x201 = x199.join(x117);
        Formula x200 = x201.one();
        Formula x197 = x200.forAll(x198);
        Variable x205 = Variable.unary("valuation_n");
        Decls x204 = x205.oneOf(x8);
        Expression x208 = x205.join(x117);
        Formula x207 = x208.eq(x10);
        Expression x211 = x205.join(x13);
        Expression x210 = x211.join(x117);
        Formula x209 = x210.eq(x11);
        Formula x206 = x207.iff(x209);
        Formula x203 = x206.forAll(x204);
        Variable x214 = Variable.unary("valuation_o");
        Decls x213 = x214.oneOf(x7);
        Expression x217 = x214.join(x117);
        Formula x216 = x217.eq(x10);
        Variable x220 = Variable.unary("valuation_c");
        Expression x221 = x214.join(x13);
        Decls x219 = x220.oneOf(x221);
        Expression x223 = x220.join(x117);
        Formula x222 = x223.eq(x10);
        Formula x218 = x222.forSome(x219);
        Formula x215 = x216.iff(x218);
        Formula x212 = x215.forAll(x213);
        Formula x202 = x203.and(x212);
        Formula x196 = x197.and(x202);
        Variable x226 = Variable.unary("valuation_a");
        Decls x225 = x226.oneOf(x6);
        Expression x229 = x226.join(x117);
        Formula x228 = x229.eq(x10);
        Variable x232 = Variable.unary("valuation_ac");
        Expression x233 = x226.join(x13);
        Decls x231 = x232.oneOf(x233);
        Expression x235 = x232.join(x117);
        Formula x234 = x235.eq(x10);
        Formula x230 = x234.forAll(x231);
        Formula x227 = x228.iff(x230);
        Formula x224 = x227.forAll(x225);
        Formula x195 = x196.and(x224);
        Expression x237 = x115.join(x117);
        Formula x236 = x237.eq(x10);
        Formula x194 = x195.and(x236);
        Formula x152 = x153.and(x194);
        Variable x241 = Variable.unary("pos_l");
        Expression x244 = x13.join(x9);
        Expression x243 = x8.intersection(x244);
        Expression x242 = x9.union(x243);
        Decls x240 = x241.oneOf(x242);
        Expression x249 = x13.closure();
        Expression x251 = x141.product(Expression.UNIV);
        Expression x250 = Expression.IDEN.intersection(x251);
        Expression x248 = x249.union(x250);
        Expression x247 = x115.join(x248);
        Formula x246 = x241.in(x247);
        Expression x253 = x241.join(x117);
        Formula x252 = x253.eq(x10);
        Formula x245 = x246.and(x252);
        Expression x239 = x245.comprehension(x240);
        Variable x256 = Variable.unary("pos_l");
        Decls x255 = x256.oneOf(x242);
        Expression x261 = x13.closure();
        Expression x263 = x141.product(Expression.UNIV);
        Expression x262 = Expression.IDEN.intersection(x263);
        Expression x260 = x261.union(x262);
        Expression x259 = x115.join(x260);
        Formula x258 = x256.in(x259);
        Expression x265 = x256.join(x120);
        Formula x264 = x265.eq(x10);
        Formula x257 = x258.and(x264);
        Expression x254 = x257.comprehension(x255);
        Formula x238 = x239.in(x254);
        Formula x151 = x152.and(x238);
        Formula x131 = x132.and(x151);
        Variable x270 = Variable.unary("valuation_f");
        Decls x269 = x270.oneOf(x30);
        Expression x272 = x270.join(x120);
        Formula x271 = x272.one();
        Formula x268 = x271.forAll(x269);
        Variable x276 = Variable.unary("valuation_n");
        Decls x275 = x276.oneOf(x8);
        Expression x279 = x276.join(x120);
        Formula x278 = x279.eq(x10);
        Expression x282 = x276.join(x13);
        Expression x281 = x282.join(x120);
        Formula x280 = x281.eq(x11);
        Formula x277 = x278.iff(x280);
        Formula x274 = x277.forAll(x275);
        Variable x285 = Variable.unary("valuation_o");
        Decls x284 = x285.oneOf(x7);
        Expression x288 = x285.join(x120);
        Formula x287 = x288.eq(x10);
        Variable x291 = Variable.unary("valuation_c");
        Expression x292 = x285.join(x13);
        Decls x290 = x291.oneOf(x292);
        Expression x294 = x291.join(x120);
        Formula x293 = x294.eq(x10);
        Formula x289 = x293.forSome(x290);
        Formula x286 = x287.iff(x289);
        Formula x283 = x286.forAll(x284);
        Formula x273 = x274.and(x283);
        Formula x267 = x268.and(x273);
        Variable x297 = Variable.unary("valuation_a");
        Decls x296 = x297.oneOf(x6);
        Expression x300 = x297.join(x120);
        Formula x299 = x300.eq(x10);
        Variable x303 = Variable.unary("valuation_ac");
        Expression x304 = x297.join(x13);
        Decls x302 = x303.oneOf(x304);
        Expression x306 = x303.join(x120);
        Formula x305 = x306.eq(x10);
        Formula x301 = x305.forAll(x302);
        Formula x298 = x299.iff(x301);
        Formula x295 = x298.forAll(x296);
        Formula x266 = x267.and(x295);
        Formula x130 = x131.and(x266);
        Formula x129 = x130.not();
        Variable x312 = Variable.unary("valuation_f");
        Decls x311 = x312.oneOf(x30);
        Expression x314 = x312.join(x120);
        Formula x313 = x314.one();
        Formula x310 = x313.forAll(x311);
        Variable x318 = Variable.unary("valuation_n");
        Decls x317 = x318.oneOf(x8);
        Expression x321 = x318.join(x120);
        Formula x320 = x321.eq(x10);
        Expression x324 = x318.join(x13);
        Expression x323 = x324.join(x120);
        Formula x322 = x323.eq(x11);
        Formula x319 = x320.iff(x322);
        Formula x316 = x319.forAll(x317);
        Variable x327 = Variable.unary("valuation_o");
        Decls x326 = x327.oneOf(x7);
        Expression x330 = x327.join(x120);
        Formula x329 = x330.eq(x10);
        Variable x333 = Variable.unary("valuation_c");
        Expression x334 = x327.join(x13);
        Decls x332 = x333.oneOf(x334);
        Expression x336 = x333.join(x120);
        Formula x335 = x336.eq(x10);
        Formula x331 = x335.forSome(x332);
        Formula x328 = x329.iff(x331);
        Formula x325 = x328.forAll(x326);
        Formula x315 = x316.and(x325);
        Formula x309 = x310.and(x315);
        Variable x339 = Variable.unary("valuation_a");
        Decls x338 = x339.oneOf(x6);
        Expression x342 = x339.join(x120);
        Formula x341 = x342.eq(x10);
        Variable x345 = Variable.unary("valuation_ac");
        Expression x346 = x339.join(x13);
        Decls x344 = x345.oneOf(x346);
        Expression x348 = x345.join(x120);
        Formula x347 = x348.eq(x10);
        Formula x343 = x347.forAll(x344);
        Formula x340 = x341.iff(x343);
        Formula x337 = x340.forAll(x338);
        Formula x308 = x309.and(x337);
        Expression x350 = x115.join(x120);
        Formula x349 = x350.eq(x10);
        Formula x307 = x308.and(x349);
        Formula x128 = x129.or(x307);
        Formula x122 = x123.implies(x128);
        Formula x112 = x122.forAll(x113);
        Formula x111 = x112.not();
        Formula x351 = x0.eq(x0);
        Formula x352 = x1.eq(x1);
        Formula x353 = x2.eq(x2);
        Formula x354 = x3.eq(x3);
        Formula x355 = x4.eq(x4);
        Formula x356 = x5.eq(x5);
        Formula x357 = x6.eq(x6);
        Formula x358 = x7.eq(x7);
        Formula x359 = x8.eq(x8);
        Formula x360 = x9.eq(x9);
        Formula x361 = x10.eq(x10);
        Formula x362 = x11.eq(x11);
        Formula x363 = x12.eq(x12);
        Formula x364 = x13.eq(x13);
        Formula x365 = x14.eq(x14);
        Formula x366 = x15.eq(x15);
        Formula x16 = Formula.compose(FormulaOperator.AND, x17, x19, x22, x25, x27, x33, x36, x39, x42, x70, x75, x80, x92, x104, x111, x351, x352, x353, x354, x355, x356, x357, x358, x359, x360, x361, x362, x363, x364, x365, x366);

        System.out.println("Compiling...");
        Meow meow = new Meow(x16, bounds);
        long time = System.currentTimeMillis();
        meow.compile();
        time = System.currentTimeMillis() - time;
        System.out.println(meow.toString());
        System.out.println("took " + time + "ms");
    }
}
