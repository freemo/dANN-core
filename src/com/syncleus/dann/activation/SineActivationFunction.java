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
package com.syncleus.dann.activation;

/**
 * An implementation of an activation function using a sine function.
 *
 * <!-- Author: Jeffrey Phillips Freeman -->
 * @author Jeffrey Phillips Freeman
 * @since 1.0
 * @version 1.0
 */
public class SineActivationFunction implements ActivationFunction
{
	/**
	 * The sine activation function.
	 *
	 * <!-- Author: Jeffrey Phillips Freeman -->
	 * @param activity the neuron's current activity.
	 * @return The result of the sine activation function bound between -1
	 * and 1.
	 * @since 1.0
	 */
    public double activate(double activity)
    {
        return Math.sin(activity);
    }

	/**
	 * The derivative of the sine activation function.
	 *
	 * <!-- Author: Jeffrey Phillips Freeman -->
	 * @param activity The neuron's current activity.
	 * @return The result of the derivative of the sine activation function.
	 * @since 1.0
	 */
    public double activateDerivative(double activity)
    {
        return Math.cos(activity);
    }
}