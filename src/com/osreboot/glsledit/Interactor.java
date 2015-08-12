package com.osreboot.glsledit;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord;
import com.osreboot.ridhvl.painter.HvlCamera;
import com.osreboot.ridhvl.painter.HvlCursor;

public class Interactor {

	public static Node mouseNode = null;
	public static Pin mousePin = null;

	private static HvlCoord displacement;

	private static boolean clicked = false;

	public static void update(float delta){
		if(Mouse.isButtonDown(0)){
			if(!clicked){
				onClick();
				clicked = true;
			}

			if(mouseNode != null){
				mouseNode.setX(mouseNode.getX() - displacement.x + HvlCursor.getCursorX());
				mouseNode.setY(mouseNode.getY() - displacement.y + HvlCursor.getCursorY());
			}

			if(mousePin != null){
				hvlDrawLine(mousePin.getX(), mousePin.getY(), (float)HvlCursor.getCursorX() + HvlCamera.getX() - (Display.getWidth()/2), (float)HvlCursor.getCursorY() + HvlCamera.getY() - (Display.getHeight()/2), Color.green);
			}

		}else{
			clicked = false;
			mouseNode = null;
			attemptConnectPin();
			mousePin = null;
		}
		if(Mouse.isButtonDown(2)){
			HvlCamera.setPosition(HvlCamera.getX() + displacement.x - HvlCursor.getCursorX(), HvlCamera.getY() + displacement.y - HvlCursor.getCursorY());
		}
		displacement = new HvlCoord(HvlCursor.getCursorX(), HvlCursor.getCursorY());
	}

	private static void onClick(){
		if(mouseNode == null && mousePin == null){
			for(Node n : Node.getNodes()){
				float x = n.getX() - HvlCamera.getX() + (Display.getWidth()/2);
				float y = n.getY() - HvlCamera.getY() + (Display.getHeight()/2);
				if(HvlCursor.getCursorX() > x && HvlCursor.getCursorX() < x + 16 && 
						HvlCursor.getCursorY() > y && HvlCursor.getCursorY() < y + 32 && mouseNode == null){
					n.setX(n.getX() - displacement.x + HvlCursor.getCursorX());
					n.setY(n.getY() - displacement.y + HvlCursor.getCursorY());
					mouseNode = n;
				}
			}
		}

		if(mouseNode == null && mousePin == null){
			for(Node n : Node.getNodes()){
				for(Pin p : n.getOutputs()){
					float x = p.getX() - HvlCamera.getX() + (Display.getWidth()/2);
					float y = p.getY() - HvlCamera.getY() + (Display.getHeight()/2);
					if(HvlCursor.getCursorX() > x - 8 && HvlCursor.getCursorX() < x + 8 && 
							HvlCursor.getCursorY() > y - 8 && HvlCursor.getCursorY() < y + 8 && mousePin == null){
						mousePin = p;
					}
				}
			}
		}
	}
	
	private static void attemptConnectPin(){
		if(mousePin != null){
			boolean set = false;
			for(Node n : Node.getNodes()){
				for(Pin p : n.getInputs()){
					float x = p.getX() - HvlCamera.getX() + (Display.getWidth()/2);
					float y = p.getY() - HvlCamera.getY() + (Display.getHeight()/2);
					if(HvlCursor.getCursorX() > x - 8 && HvlCursor.getCursorX() < x + 8 && 
							HvlCursor.getCursorY() > y - 8 && HvlCursor.getCursorY() < y + 8 && mousePin != p){
						Pin connection = Pin.findOutputConnection(p);
						if(connection != null) connection.resetConnections();
						mousePin.setConnection(p);
						set = true;
					}
				}
			}
			if(!set) mousePin.resetConnections();
		}
	}

}