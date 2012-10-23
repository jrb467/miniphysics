package entities;

import math.geometry.Float3D;
import math.geometry.Vector;

public class Entity {
	
	public static class EntityData {
		public float mass = 1;
		public float bounce = 0;
		public float friction = .5f;
		public boolean staticEntity = false;
		public byte id = 0;
		public Float3D location = new Float3D(0,0,0);
		public Float3D[] verticies = {};
	}

	public final float mass;
	public final float bounce;
	public final float friction;
	public final Float3D[] verticies; //in location to the object origin (0, 0);
	public Float3D location;
	public Vector velocity = new Vector(0, 0, 0);
	public float angle = 0;
	public float rotation = 0;
	public final boolean staticEntity;
	public byte id;
	public final static byte DEFAULT_ID = 0;
	
	private boolean ticked = false;
	
	public Entity(EntityData data){
		location = data.location;
		verticies = data.verticies;
		mass = data.mass;
		bounce = data.bounce;
		friction = data.friction;
		staticEntity = data.staticEntity;
		id = data.id;
	}
	
	public void tick(){
		if(!ticked){
			if(!this.staticEntity){
				location.x = location.x+velocity.x;
				location.y = location.y+velocity.y;
				location.z = location.z+velocity.z;
			}
			ticked = true;
		}
	}
	
	public void resetTick(){
		ticked = false;
	}
}
