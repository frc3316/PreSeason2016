package org.usfirst.frc.team3316.robot.utils;

import static org.ejml.ops.CommonOps.addEquals;
import static org.ejml.ops.CommonOps.mult;
import static org.ejml.ops.CommonOps.multTransA;
import static org.ejml.ops.CommonOps.multTransB;
import static org.ejml.ops.CommonOps.subtract;
import static org.ejml.ops.CommonOps.subtractEquals;

import org.ejml.data.DenseMatrix64F;
import org.ejml.factory.LinearSolverFactory;
import org.ejml.interfaces.linsol.LinearSolver;


/**
 * A Kalman filter that is implemented using the operations API, which is
 * procedural. Much of the excessive memory creation/destruction has been
 * reduced from the KalmanFilterSimple. A specialized solver is under to invert
 * the SPD matrix.
 *
 * @author Peter Abeles
 */
public class KalmanFilter
{

	// kinematics description
	private DenseMatrix64F F, B, Q, H;

	// system state estimate
	private DenseMatrix64F x, P;

	// these are predeclared for efficiency reasons
	private DenseMatrix64F a, b, e;

	private DenseMatrix64F y, S, S_inv, c, d;

	private DenseMatrix64F K;

	private LinearSolver < DenseMatrix64F > solver;

	/**
	 * Initialization of 
	 * @param F
	 * @param B
	 * @param Q
	 * @param H
	 */
	public void configure ( DenseMatrix64F F, DenseMatrix64F B,
							DenseMatrix64F Q, DenseMatrix64F H )
	{
		this.F = F;

		this.B = B;

		this.Q = Q;
		this.H = H;

		int dimenX = F.numCols;
		int dimenZ = H.numRows;

		a = new DenseMatrix64F(dimenX, 1);
		b = new DenseMatrix64F(dimenX, dimenX);

		e = new DenseMatrix64F(dimenX, 1);

		y = new DenseMatrix64F(dimenZ, 1);
		S = new DenseMatrix64F(dimenZ, dimenZ);
		S_inv = new DenseMatrix64F(dimenZ, dimenZ);
		c = new DenseMatrix64F(dimenZ, dimenX);
		d = new DenseMatrix64F(dimenX, dimenZ);
		K = new DenseMatrix64F(dimenX, dimenZ);

		x = new DenseMatrix64F(dimenX, 1);
		P = new DenseMatrix64F(dimenX, dimenX);

		// covariance matrices are symmetric positive semi-definite
		solver = LinearSolverFactory.symmPosDef(dimenX);
	}

	public void setState ( DenseMatrix64F x, DenseMatrix64F P )
	{
		this.x.set(x);
		this.P.set(P);
	}

	public void predict ( DenseMatrix64F u )
	{

		// x = F x + B u
		mult(F, x, a);
		x.set(a);

		mult(B, u, e);
		addEquals(x, e);

		// P = F P F' + Q
		mult(F, P, b);
		multTransB(b, F, P);
		addEquals(P, Q);
	}

	public void update ( DenseMatrix64F z, DenseMatrix64F R )
	{
		// y = z - H x
		mult(H, x, y);
		subtract(z, y, y);

		// S = H P H' + R
		mult(H, P, c);
		multTransB(c, H, S);
		addEquals(S, R);

		// K = PH'S^(-1)
		if ( !solver.setA(S) ) throw new RuntimeException("Invert failed");
		solver.invert(S_inv);
		multTransA(H, S_inv, d);
		mult(P, d, K);

		// x = x + Ky
		mult(K, y, a);
		addEquals(x, a);

		// P = (I-kH)P = P - (KH)P = P-K(HP)
		mult(H, P, c);
		mult(K, c, b);
		subtractEquals(P, b);
	}

	public DenseMatrix64F getState ()
	{
		return x;
	}

	public DenseMatrix64F getCovariance ()
	{
		return P;
	}
}
