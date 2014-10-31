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
package com.syncleus.dann.math.counting;

import org.junit.*;

public class TestCombinationCounter {
    @Test
    public void testCombinationCount() {
        Counter generator = new CombinationCounter(5, 3);
        Assert.assertTrue("Generator produced incorrect number of combinations, expected 10, received: " + generator.getTotal(), generator.getTotal().intValue() == 10);

        generator = new CombinationCounter(5, 5);
        Assert.assertTrue("Generator produced incorrect number of combinations, expected 1, received: " + generator.getTotal(), generator.getTotal().intValue() == 1);

        generator = new CombinationCounter(5, 0);
        Assert.assertTrue("Generator produced incorrect number of combinations, expected 0, received: " + generator.getTotal(), generator.getTotal().intValue() == 0);

        generator = new CombinationCounter(0, 0);
        Assert.assertTrue("Generator produced incorrect number of combinations, expected 0, received: " + generator.getTotal(), generator.getTotal().intValue() == 0);
    }
}
