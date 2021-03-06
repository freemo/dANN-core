/*******************************************************************************
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
package com.syncleus.dann.graph.tree.mst;

import java.util.*;
import com.syncleus.dann.graph.*;

public class LinkedGraph<N, E extends TraversableCloud<N>> extends AbstractAdjacencyGraph<N, E>
{
	private final Set<N> nodes;
	private final Set<E> edges;
	private final Map<N, Set<E>> neighborEdges = new HashMap<N, Set<E>>();
	private final Map<N, List<N>> neighborNodes = new HashMap<N, List<N>>();
	private static final long serialVersionUID = -5301697513399013407L;

	public LinkedGraph(final Graph<N, E> copyGraph)
	{
		this(copyGraph.getNodes(), copyGraph.getEdges());
	}

	public LinkedGraph(final Set<N> nodes, final Set<E> edges)
	{
		this.nodes = new LinkedHashSet<N>(nodes);
		this.edges = new LinkedHashSet<E>(edges);
		for(final E edge : edges)
		{
			final Collection<N> edgeNodes = edge.getNodes();
            for(N startNode : edgeNodes)
			{
				if( !this.nodes.contains(startNode) )
					throw new IllegalArgumentException("A node that is an end point in one of the edges was not in the nodes list");

				Set<E> startNeighborEdges = this.neighborEdges.get(startNode);
				if( startNeighborEdges == null )
				{
					startNeighborEdges = new LinkedHashSet<E>();
					this.neighborEdges.put(startNode, startNeighborEdges);
				}
				startNeighborEdges.add(edge);

				List<N> startNeighborNodes = this.neighborNodes.get(startNode);
				if( startNeighborNodes == null )
				{
					startNeighborNodes = new ArrayList<N>();
					this.neighborNodes.put(startNode, startNeighborNodes);
				}

                boolean selfEncountered = false;
                for(N endNode : edgeNodes)
				{
					if( startNode.equals(endNode) && !selfEncountered)
                    {
                        selfEncountered = true;
						continue;
                    }

					startNeighborNodes.add(endNode);
				}
			}
		}
	}

	public Set<N> getNodes()
	{
		return Collections.unmodifiableSet(this.nodes);
	}

	@Override
	public Set<E> getEdges()
	{
		return Collections.unmodifiableSet(this.edges);
	}

	public Set<E> getAdjacentEdges(final N node)
	{
		if( this.neighborEdges.containsKey(node) )
			return Collections.unmodifiableSet(this.neighborEdges.get(node));
		else
			return Collections.<E>emptySet();
	}

	public List<N> getAdjacentNodes(final N node)
	{
		return Collections.unmodifiableList(new ArrayList<N>(this.neighborNodes.get(node)));
	}
}
