package com.osreboot.glsledit.pin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Overlay;
import com.osreboot.glsledit.Pin;
import com.osreboot.ridhvl.HvlCoord;

public class PinBoolean extends Pin{

	private ArrayList<Pin> connections = new ArrayList<>();

	public PinBoolean(Node parentArg, String argName){
		super(parentArg, "bool " + argName);
	}

	@Override
	public void setConnection(Pin connectionArg){
		if(connectionArg instanceof PinBoolean && connectionArg.getParent() != getParent()){
			connections.add(connectionArg);
		}
	}

	@Override
	public void drawConnections(float deltaArg){
		for(Pin p : connections){
			Overlay.addWireConnection(new HvlCoord(getX(), getY()), new HvlCoord(p.getX(), p.getY()), Overlay.WIRE_BOOLEAN, Overlay.WIDTH_MEDTHICK);
		}
	}

	@Override
	public void resetConnections(){
		connections.clear();
	}

	@Override
	public boolean hasConnection(Pin pArg){
		return connections.contains(pArg);
	}

	@Override
	public void removeConnection(Pin pArg){
		connections.remove(pArg);
	}

	@Override
	public List<Pin> getConnections() {
		return Collections.unmodifiableList(connections);
	}

}