/**
 * 
 */
package meow.benchmarks.tptp;

import static kodkod.ast.Expression.UNIV;
import kodkod.ast.Formula;
import kodkod.ast.Variable;
import kodkod.instance.Bounds;
import meow.Meow;

/**
 * A KK encoding of MED009+1.p from http://www.cs.miami.edu/~tptp/
 * @author Emina Torlak
 */
public final class MED009 extends MED001 {
	/**
	 * Constructs a new instance of MED007.
	 */
	public MED009() { }
	
	/**
	 * Returns transsls2_qige27 conjecture.
	 * @return transsls2_qige27
	 */
	public final Formula transsls2_qige27() {
		final Variable x0 = Variable.unary("X0");
		final Formula f0 = n0.in(s1).and(n0.join(gt).in(conditionhyper)).
			and(n0.in(bcapacitysn).not()).and(n0.in(qilt27).not());
		final Formula f1 = n0.product(x0).in(gt).not().and(x0.in(s2)).
			and(x0.join(gt).in(conditionhyper)).and(x0.in(bcapacityne.union(bcapacityex))).
			forSome(x0.oneOf(UNIV));
		return f0.implies(f1);
	}
	/**
	 * Returns the conjunction of the axioms and the negation of the hypothesis.
	 * @return axioms() && !transsls2_qige27()
	 */
	public final Formula checkTranssls2_qige27() { 
		return  axioms().and(transsls2_qige27().not());
	}
	
	private static void usage() {
		System.out.println("java examples.tptp.MED009 [univ size]");
		System.exit(1);
	}
	
	/**
	 * Usage: java examples.tptp.MED009 [univ size]
	 */
	public static void main(String[] args) {
		if (args.length < 1)
			usage();
		
		try {
			final int n = Integer.parseInt(args[0]);
			if (n < 1)
				usage();
			final MED009 model = new MED009();
			final Formula f = model.checkTranssls2_qige27();
			final Bounds b = model.bounds(n);
			Meow meow = new Meow(f, b);
			meow.compile();
			meow.writeSuite("bench/tptp/MED009-" + n);
		} catch (NumberFormatException nfe) {
			usage();
		}
	}
}
