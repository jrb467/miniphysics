package events;

import java.awt.Point;

import entities.Entity;

public class VelocityCollisionEvent extends Event {
	
	public VelocityCollisionEvent(Entity one, Entity two, Point velocity){
		super(3, one, two, velocity);
	}
}
