/******************************************************************************
 *                                                                             *
 *  Copyright: (c) Syncleus, Inc.                                              *
 *                                                                             *
 *  You may redistribute and modify this source code under the terms and       *
 *  conditions of the Open Source Community License - Type C version 1.0       *
 *  or any later version as published by Syncleus, Inc. at www.syncleus.com.   *
 *  There should be a copy of the license included with this file. If a copy   *
 *  of the license is not included you are granted no right to distribute or   *
 *  otherwise use this file except through a legal and valid license. You      *
 *  should also contact Syncleus, Inc. at the information below if you cannot  *
 *  find a license:                                                            *
 *                                                                             *
 *  Syncleus, Inc.                                                             *
 *  2604 South 12th Street                                                     *
 *  Philadelphia, PA 19148                                                     *
 *                                                                             *
 ******************************************************************************/
package com.syncleus.dann.classify.naive.bayes;

import com.syncleus.dann.classify.LanguageClassifier;

/**
 * Classifies words based on
 * <a href="http://en.wikipedia.org/wiki/Naive_Bayes_classifier">
 *     Bayes' Theorem</a> with a strong independence assumption.
 * @param <C> The categories to place words in
 * @author Jeffrey Phillips Freeman
 */
public interface LanguageNaiveBayesClassifier<C> extends NaiveBayesClassifier<String, String, C>, LanguageClassifier<C>
{
}
