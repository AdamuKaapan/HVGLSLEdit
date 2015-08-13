package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeArbitraryFloat extends Node{

	private PinFloat next;

	private float value;
	
	public NodeArbitraryFloat(float valueArg, float x, float y){
		super("float " + valueArg, x, y, Node.COLOR_ARBITRARY);
		value = valueArg;
		next = new PinFloat(this, "out"){
			@Override
			public String getOutput(){
				return value + "";
			}
		};
		setOutputs(new ArrayList<Pin>(Arrays.asList(next)));
	}

	@Override
	public ArrayList<String> getContent(){
		return null;
	}

	@Override
	public Node getNext(){
		return null;
	}

}
