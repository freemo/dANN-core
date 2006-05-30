package com.syncleus.dann;

/**
 * This is a special type of neuron that receives input.<BR>
 * <!-- Author: Jeffrey Phillips Freeman -->
 * @author Jeffrey Phillips Freeman
 * @since 0.1
 * @see com.syncleus.dann.InputLayer
 * @see com.syncleus.dann.OutputNeuron
 */
public class InputNeuron extends Neuron
{
	/**
	 * Holds the current input value for this neuron<BR>
	 * <!-- Author: Jeffrey Phillips Freeman -->
	 * @since 0.1
	 */
	protected double input = 0;
    
    /**
	  * Creates a new instance of InputNeuron<BR>
	  * <!-- Author: Jeffrey Phillips Freeman -->
	  * @since 0.1
	  * @param OwnedDNAToSet This dna class will determine the various properties
	  *	of the layer.
	  * @param OwningLayerToSet This is the layer the new neuron will belong to.
	  *	This can be null if you arent using layers.
	  */
    public InputNeuron(Layer OwningLayerToSet, DNA OwnedDNAToSet)
    {
        super(OwningLayerToSet, OwnedDNAToSet);
    }
    
    /**
	  * This method sets the current input on the neuron.<BR>
	  * <!-- Author: Jeffrey Phillips Freeman -->
	  * @since 0.1
	  * @param InputToSet The value to set the current input to.
	  * @see com.syncleus.dann.InputLayer#SetInput
	  */
    public void SetInputNeuronInput(double InputToSet)
    {
        this.input = InputToSet;
    }
	 
	/**
	 * Refreshes the output of the neuron based on the current input<BR>
	 * <!-- Author: Jeffrey Phillips Freeman -->
	 * @since 0.1
	 */
	 public void Propogate()
	 {
		 super.Activity = this.input;
		 
		 super.Output = super.ActivationFunction();
	 }
	 
	/**
	 * Calculates the new training set and adjusts weights accordingly.<BR>
	 * <!-- Author: Jeffrey Phillips Freeman -->
	 * @since 0.1
	 */
	public void BackPropogate()
	{
		super.CalculateDeltaTrain();
	}
    
}
