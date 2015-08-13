package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeFloatLerp extends Node{

	private PinFloat input1, input2, input3, output;

	public NodeFloatLerp(float x, float y){
		super("float lerp", x, y, Node.COLOR_MLT);
		input1 = new PinFloat(this, "1");
		input2 = new PinFloat(this, "lerp");
		input3 = new PinFloat(this, "2");
		setInputs(new ArrayList<Pin>(Arrays.asList(input1, input2, input3)));
		output = new PinFloat(this, "out"){
			@Override
			public String getOutput(){
				return getContent().get(0);
			}
		};
		setOutputs(new ArrayList<Pin>(Arrays.asList(output)));
	}

	@Override
	public ArrayList<String> getContent(){
		return new ArrayList<String>(Arrays.asList("(" + Pin.getConnectionOutput(input1, "0") + " + " + Pin.getConnectionOutput(input2, "0") + " * ( " + Pin.getConnectionOutput(input3, "0") + " - " + Pin.getConnectionOutput(input1, "0") + "))"));
	}

	@Override
	public Node getNext(){
		return null;
	}

}
