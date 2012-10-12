package events;

public class Event {
	public final int id;
	public final Object[] data;
	
	public Event(int id, Object... objects){
		this.id = id;
		data = objects;
	}
}
