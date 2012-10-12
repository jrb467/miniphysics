package events;
import entities.Entity;


public class CollisionEvent extends Event {
	
	public CollisionEvent(Entity one, Entity two){
		super(1, one, two);
	}
}
