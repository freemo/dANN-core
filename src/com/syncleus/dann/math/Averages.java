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
package com.syncleus.dann.math;

public final class Averages
{
	private Averages()
	{
	}

	public static ComplexNumber rms(ComplexNumber... values)
	{
		ComplexNumber rootSum = ComplexNumber.ZERO;
		for(ComplexNumber value : values)
			rootSum = rootSum.add(value.multiply(value));
		return rootSum.divide((double)values.length);
	}

	public static ComplexNumber mean(ComplexNumber... values)
	{
		ComplexNumber complexSum = ComplexNumber.sum(values);
		return complexSum.divide((double)values.length);
	}

	public static ComplexNumber geometricMean(ComplexNumber... values)
	{
		ComplexNumber complexProduct = ComplexNumber.multiply(values);
		return complexProduct.pow(1.0/((double)values.length));
	}

	public static double rms(double... values)
	{
		double rootSum = 0.0;
		for(double value : values)
			rootSum += value*value;
		return rootSum / ((double)values.length);
	}

	public static double mean(double... values)
	{
		double meanSum = 0.0;
		for(double value:values)
			meanSum += value;
		return meanSum / ((double)values.length);
	}

	public static double geometricMean(double... values)
	{
		double geometricProduct = 1.0;
		for(double value : values)
			geometricProduct *= value;
		return Math.pow(geometricProduct, (1.0/((double)values.length)));
	}
}
