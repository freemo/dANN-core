/******************************************************************************
 *                                                                             *
 *  Copyright: (c) Jeffrey Phillips Freeman                                              *
 *                                                                             *
 *  You may redistribute and modify this source code under the terms and       *
 *  conditions of the Open Source Community License - Type C version 1.0       *
 *  or any later version as published by Jeffrey Phillips Freeman at www.syncleus.com.   *
 *  There should be a copy of the license included with this file. If a copy   *
 *  of the license is not included you are granted no right to distribute or   *
 *  otherwise use this file except through a legal and valid license. You      *
 *  should also contact Jeffrey Phillips Freeman at the information below if you cannot  *
 *  find a license:                                                            *
 *                                                                             *
 *  Jeffrey Phillips Freeman                                                             *
 *  2604 South 12th Street                                                     *
 *  Philadelphia, PA 19148                                                     *
 *                                                                             *
 ******************************************************************************/
package com.syncleus.dann.math.linear.decomposition;

import com.syncleus.dann.math.OrderedAlgebraic;
import com.syncleus.dann.math.linear.*;

/** Cholesky Decomposition.
<P>
For a symmetric, positive definite matrix A, the Cholesky decomposition
is an lower triangular matrix L so that A = L*L'.
<P>
If the matrix is not symmetric or positive definite, the constructor
returns a partial decomposition and sets an internal flag that may
be queried by the isSpd() method.
 */
public interface CholeskyDecomposition<M extends Matrix<M, F>, F extends OrderedAlgebraic<F>> extends java.io.Serializable, SolvableDecomposition<M>
{
	/** Is the matrix symmetric and positive definite?
	@return     true if A is symmetric and positive definite.
	 */
	boolean isSpd();
}

