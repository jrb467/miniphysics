package main;

import java.util.ArrayList;

import entities.Entity;

import math.Collision;
import math.CollisionResult;
import math.geometry.Float3D;
import math.geometry.Vector;
/**
 * The containing "world" for all the physics calculations and rendering.
 * 
 * @author jacob
 */
public class World{
	public int width;
	public int height;
	public float gravity;
	private float viscosity;
	public ArrayList<Entity> entities;
	
	public World(){
		width = 0; //TODO change
		height = 0;
		gravity = 0;
		viscosity = 0;
		entities = new ArrayList<Entity>();
	}
	
	public boolean setViscosity(float v){
		if(v >= 0){
			viscosity = v;
			return true;
		}
		return false;
	}
	
	public float getViscosity(){
		return viscosity;
	}
	
	public void addEntity(Entity e){
		synchronized(entities){
			entities.add(e);
		}
	}
	
	public void removeEntity(int index){
		synchronized(entities){
			entities.remove(index);
		}
	}
	
	public void removeEntity(Entity e){
		synchronized(entities){
			entities.remove(e);
		}
	}
	
	public void tick(){
		synchronized(entities){
			//TODO get rid of this prevs/news system. it should be all based on the collision results
			Float3D[] prevs = new Float3D[entities.size()];
			Float3D[] news = new Float3D[entities.size()];
			for(int e = 0; e < entities.size(); e++){
				prevs[e] = (Float3D)entities.get(e).location.clone();
				news[e] = entities.get(e).location;
				if(!entities.get(e).staticEntity){
					entities.get(e).velocity.y += 3;
				}
			}
			/**/
			//iterates through the entities testing collisions
			for(int i = 0; i < entities.size()-1; i++){
				for(int j = i+1; j < entities.size(); j++){
					Entity one = entities.get(i);
					Entity two = entities.get(j);
					if(!(one.staticEntity && two.staticEntity)){
						CollisionResult temp = Collision.testCollision(one, two);
						if(temp.willIntersect){
							one.location.x += temp.entityOneTranslation.x;
							one.location.y += temp.entityOneTranslation.y;
							one.location.z += temp.entityOneTranslation.z;
							two.location.x += temp.entityTwoTranslation.x;
							two.location.y += temp.entityTwoTranslation.y;
							two.location.z += temp.entityTwoTranslation.z;
							one.tick();
							two.tick();
							one.velocity = temp.entityOneVelocity;
							two.velocity = temp.entityTwoVelocity;
						}
					}
				}
			}
			for(Entity e: entities){
				e.tick();
				e.resetTick();
			}
			for(int i = 0; i < news.length; i++){
				if(!entities.get(i).staticEntity)
					entities.get(i).velocity = new Vector(news[i].x-prevs[i].x, news[i].y-prevs[i].y, news[i].z-prevs[i].z);
			}
		}
	}
}
