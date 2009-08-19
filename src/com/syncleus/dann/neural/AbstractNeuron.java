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
package com.syncleus.dann.neural;

import com.syncleus.dann.UnexpectedDannError;
import java.util.*;
import com.syncleus.dann.neural.activation.ActivationFunction;
import com.syncleus.dann.neural.activation.HyperbolicTangentActivationFunction;
import org.apache.log4j.Logger;


/**
 * An abstract implementation of the Neuron interface. Included activation
 * function handling.
 *
 *
 * @author Syncleus, Inc.
 * @param <SN> Source Neurons allowed to connect to this Neuron.
 * @param <DN> Destination Neurons this Neuron is allowed to connect to.
 * @since 1.0
 *
 */
public abstract class AbstractNeuron<SN extends AbstractNeuron, DN extends AbstractNeuron> implements Neuron<SN, DN>
{
    // <editor-fold defaultstate="collapsed" desc="Attributes">

    /**
     * Represents the current excitation of the neuron from input
     * signals
     *
     * @since 1.0
     */
    protected double activity;

    /**
     * Represents the current output of the neuron
     *
     * @since 1.0
     */
    protected double output;

    /**
     * The current weight of the bias input. The bias is an input that is always
     * set to an on position. The bias weight usually adapts in the same manner
     * as the rest of the synapse's weights.
     *
     * @since 1.0
     */
    protected double biasWeight;

    /**
     * An array list of all the synapses that this neuron is currently
     * connection out to.
     *
     * @since 1.0
     */
    protected final Set<Synapse> destinations = new HashSet<Synapse>();

	/**
     * All the synapses currently connecting into this neuron
     *
     * @since 1.0
     */
    private final Set<Synapse> sources = new HashSet<Synapse>();

	/**
	 * The current activation function used by this neuron. This is used to
	 * calculate the output from the activity.
	 *
	 * @since 1.0
	 */
    protected ActivationFunction activationFunction;

	/**
	 * Random number generator used toproduce any needed random values
	 *
	 * @since 1.0
	 */
	protected static final Random RANDOM = new Random();
	
	private final static HyperbolicTangentActivationFunction DEFAULT_ACTIVATION_FUNCTION = new HyperbolicTangentActivationFunction();
	private final static Logger LOGGER = Logger.getLogger(AbstractNeuron.class);

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     * Creates a new instance of NeuronImpl with a random bias weight and
	 * HyperbolicTangentActivationFunction as the activation function.
	 *
     *
     * @since 1.0
     */
    public AbstractNeuron()
    {
        this.biasWeight = ((RANDOM.nextDouble() * 2.0) - 1.0) / 1000.0;
        this.activationFunction = DEFAULT_ACTIVATION_FUNCTION;
    }



	/**
	 * Creates a new instance of NEuronImpl with a random bias weight and the
	 * specified activation function.
	 *
	 *
	 * @param activationFunction The activation function used to calculate the
	 * output fromt he neuron's activity.
	 * @since 1.0
	 */
    public AbstractNeuron(ActivationFunction activationFunction)
    {
        if (activationFunction == null)
            throw new IllegalArgumentException("activationFunction can not be null");


        this.biasWeight = ((this.RANDOM.nextDouble() * 2.0) - 1.0) / 1000.0;
        this.activationFunction = activationFunction;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Topology Manipulation">

    /**
     * This method is called internally, between Neurons, to
     * facilitate the connection process.<BR>
     *
     * @since 1.0
     * @param inSynapse The synapse to connect from.
     * @see com.syncleus.dann.neural.Neuron#connectTo
     */
    public void connectFrom(final Synapse inSynapse) throws InvalidConnectionTypeDannException
    {
        //make sure you arent already connected fromt his neuron

        //add the synapse to the source list
        this.sources.add(inSynapse);
    }

    /**
     * This method is called externally to connect to another Neuron.
	 *
     *
     * @since 1.0
     * @param outUnit The Neuron to connect to.
	 * @throws com.syncleus.dann.InvalidConnectionTypeDannException Not
	 * thrown, but children are allowed to throw this exception.
     * @see com.syncleus.dann.neural.NeuronImpl#connectFrom
     */
    public Synapse connectTo(final DN outUnit) throws InvalidConnectionTypeDannException
    {
        //make sure you arent already connected to the neuron
        if (outUnit == null)
            throw new IllegalArgumentException("outUnit can not be null!");

        //connect to the neuron
        final Synapse newSynapse = new Synapse(this, outUnit, ((this.RANDOM.nextDouble() * 2.0) - 1.0) / 10000.0);
        this.destinations.add(newSynapse);
        outUnit.connectFrom(newSynapse);

		return newSynapse;
    }

    /**
     * Causes the Neuron to disconnect all connections.
	 *
     *
     * @since 1.0
     * @see com.syncleus.dann.neural.Neuron#disconnectAllSources
     * @see com.syncleus.dann.neural.Neuron#disconnectAllDestinations
     */
    public void disconnectAll()
    {
        this.disconnectAllDestinations();
        this.disconnectAllSources();
    }



    /**
     * Causes the Neuron to disconnect all outgoing connections.
	 *
     *
     * @since 1.0
     * @see com.syncleus.dann.neural.Neuron#disconnectAllSources
     * @see com.syncleus.dann.neural.Neuron#disconnectAll
     */
    public void disconnectAllDestinations()
    {
		final Synapse[] destinationsArray = new Synapse[this.destinations.size()];
		this.destinations.toArray(destinationsArray);
		
        for (Synapse currentDestination : destinationsArray)
		{
            try
            {
                this.disconnectDestination(currentDestination);
            }
            catch (SynapseNotConnectedDannException caught)
            {
                LOGGER.error("Received an unexpected exception, this should not ever happen", caught);
                throw new UnexpectedDannError("Unexpected Runtime Exception", caught);
            }
		}
    }



    /**
     * Causes the Neuron to disconnect all incomming connections.
	 *
     *
     * @since 1.0
     * @see com.syncleus.dann.neural.Neuron#disconnectAllDestinations
     * @see com.syncleus.dann.neural.Neuron#disconnectAll
     */
    public void disconnectAllSources()
    {
		final Synapse[] sourcesArray = new Synapse[this.sources.size()];
		this.sources.toArray(sourcesArray);
		
        for (Synapse currentSource : sourcesArray)
		{
            try
            {
                this.disconnectSource(currentSource);
            }
            catch (SynapseNotConnectedDannException caught)
            {
                LOGGER.error("Received an unexpected exception, this should not ever happen", caught);
                throw new UnexpectedDannError("Unexpected Runtime Exception: ", caught);
            }
		}
    }



    /**
     * Disconnects from a perticular outgoing connection.
	 *
     *
     * @since 1.0
     * @param outSynapse The outgoing synapse to disconnect from.
     * @see com.syncleus.dann.neural.NeuronImpl#removeSource
	 * @throws SynapseNotConnectedException Thrown if the specified synapse isnt
	 * currently connected.
     */
    public void disconnectDestination(final Synapse outSynapse) throws SynapseNotConnectedDannException
    {
        if (this instanceof OutputNeuron)
            throw new IllegalArgumentException("Can not disconnect a destination for a OutputNeuron");

        if (!this.destinations.remove(outSynapse))
            throw new SynapseNotConnectedDannException("can not disconnect destination, does not exist.");

        try
        {
            if (outSynapse.getDestination() != null)
                outSynapse.getDestination().removeSource(outSynapse);
        }
        catch (SynapseDoesNotExistDannException caughtException)
        {
			LOGGER.error("Incorrect state, a synapse was partially connected");
            throw new SynapseNotConnectedDannException("can not disconnect destination, this shouldnt happen because it was partially connected.", caughtException);
        }
    }



    /**
     * Disconnects from a perticular incomming connection.
	 *
     *
     * @since 1.0
     * @param inSynapse The incomming synapse to disconnect from.
     * @see com.syncleus.dann.neural.NeuronImpl#removeDestination
	 * @throws SynapseNotConnectedException Thrown if the specified synapse isnt
	 * currently connected.
     */
    public void disconnectSource(final Synapse inSynapse) throws SynapseNotConnectedDannException
    {
        if (this instanceof InputNeuron)
            throw new IllegalArgumentException("Can not disconnect a source for a InputNeuron");

        if (!this.sources.remove(inSynapse))
            throw new SynapseNotConnectedDannException("can not disconnect source, does not exist.");

        try
        {
            if (inSynapse.getSource() != null)
                inSynapse.getSource().removeDestination(inSynapse);
        }
        catch (SynapseDoesNotExistDannException caughtException)
        {
			LOGGER.error("Incorrect state, a synapse was partially connected");
            throw new SynapseNotConnectedDannException("can not disconnect source, this should never happen, it was partially connected.", caughtException);
        }
    }



    /**
     * Called internally to facilitate the removal of a connection.
	 *
     *
     * @since 1.0
     * @param outSynapse The incomming synapse to remove from memory.
     * @see com.syncleus.dann.neural.Neuron#disconnectSource
     */
    protected void removeDestination(final Synapse outSynapse) throws SynapseDoesNotExistDannException
    {
        if (this instanceof OutputNeuron)
            throw new IllegalArgumentException("Can not remove a destination for a OutputNeuron");

        if (!this.destinations.remove(outSynapse))
            throw new SynapseDoesNotExistDannException("Can not remove destination, does not exist.");
    }



    /**
     * Called internally to facilitate the removal of a connection.<BR>
     *
     * @since 1.0
     * @param inSynapse The incomming synapse to remove from memory.<BR>
     * @see com.syncleus.dann.neural.Neuron#disconnectDestination
     */
    protected void removeSource(final Synapse inSynapse) throws SynapseDoesNotExistDannException
    {
        if (this instanceof InputNeuron)
            throw new IllegalArgumentException("Can not disconnect a source for a InputNeuron");

        if (!this.sources.remove(inSynapse))
            throw new SynapseDoesNotExistDannException("Can not remove destination, does not exist.");
    }



	/**
	 * Gets all the destination Synapses this neuron's output is connected to.
	 *
	 *
	 * @return An unmodifiable Set of destination Synapses
	 * @since 1.0
	 */
    public Set<Synapse> getDestinations()
    {
        return Collections.unmodifiableSet(this.destinations);
    }



	/**
	 * Gets all the Neurons that either connect to, or are connected from, this
	 * Neuron.
	 *
	 *
	 * @return An unmodifiable Set of source and destination Neurons.
	 * @since 1.0
	 */
    public Set<Neuron> getNeighbors()
    {
        final HashSet<Neuron> neighbors = new HashSet<Neuron>();
        for (Synapse source : this.getSources())
            neighbors.add(source.getSource());
        for (Synapse destination : this.getDestinations())
            neighbors.add(destination.getDestination());

        return Collections.unmodifiableSet(neighbors);
    }



	/**
	 * Get all the source Neuron's connecting to this Neuron.
	 *
	 *
	 * @return An unmodifiable Set of source Neurons.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
    public Set<SN> getSourceNeighbors()
    {
        final HashSet<SN> neighbors = new HashSet<SN>();
		try
		{
			for (Synapse sourceSynapse : this.getSources())
				neighbors.add((SN)sourceSynapse.getSource());
		}
		catch(ClassCastException caughtException)
		{
			throw new UnexpectedDannError("unexpected class cash exception when getting sourced", caughtException);
		}

        return Collections.unmodifiableSet(neighbors);
    }



	/**
	 * Get all the destination Neuron's this Neuron connects to.
	 *
	 *
	 * @return An unmodifiable Set of destination Neurons.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
    public Set<DN> getDestinationNeighbors()
    {
        final HashSet<DN> neighbors = new HashSet<DN>();
        for (Synapse destination : this.getDestinations())
            neighbors.add((DN)destination.getDestination());

        return Collections.unmodifiableSet(neighbors);
    }

	/**
	 * Gets all the source Synapses connected to this neuron.
	 *
	 *
	 * @return An unmodifiable Set of source Synapses
	 * @since 1.0
	 */
    public Set<Synapse> getSources()
    {
        return Collections.unmodifiableSet(sources);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Propogation">

    /**
     * sets the current output on all outgoing synapses.
	 *
     *
     * @since 1.0
     * @see com.syncleus.dann.neural.backprop.BackpropNeuron#propagate
     * @param newOutput The output value.
     */
    protected void setOutput(final double newOutput)
    {
        this.output = newOutput;

        for (Synapse current : this.destinations)
            current.setInput(newOutput);
    }



    /**
     * Gets the current output.
	 *
     *
     * @since 1.0
     * @return The current output.
     */
    protected double getOutput()
    {
        return this.output;
    }



    /**
     * obtains the output as a function of the current activity. This is a bound
     * function (usually between -1 and 1) based on the current activity of the
     * neuron.
	 *
     *
     * @since 1.0
     * @return a bound value (between -1 and 1 if this function is not
     * 	overwritten). It is a function of the neuron's current activity.
     * @see com.syncleus.dann.neural.backprop.BackpropNeuron#propagate
     */
    protected final double activate()
    {
        return this.activationFunction.activate(this.activity);
    }



    /**
     * This must be the derivity of the ActivityFunction. As such it's output is
     * also based on the current activity of the neuron. If the
     * activationFunction is overwritten then this method must also be
     * overwritten with the proper derivative.
	 *
     *
     * @since 1.0
     * @return the derivative output of the activationFunction
     * @see com.syncleus.dann.neural.NeuronImpl#activationFunction
     */
    protected final double activateDerivitive()
    {
        return this.activationFunction.activateDerivative(this.activity);
    }

	
    /**
     * Propogates the current output to all outgoing synapses.
	 *
     *
     * @since 1.0
     */
    public void propagate()
    {
        //calculate the current input activity
        this.activity = 0;
        for (Synapse currentSynapse : this.getSources())
            this.activity += currentSynapse.getInput() * currentSynapse.getWeight();
        //Add the bias to the activity
        this.activity += this.biasWeight;

        //calculate the activity function and set the result as the output
        this.setOutput(this.activate());
    }

    // </editor-fold>
}