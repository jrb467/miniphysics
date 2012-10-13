package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;

import entities.Entity;

import math.Collision;
import math.CollisionResult;
import math.geometry.Vector;

import graphic.RenderParameters;
/**
 * The containing "world" for all the physics calculations and rendering.
 * 
 * @author jacob
 */
public class World extends Thread{
	public int width;
	public int height;
	public float gravity;
	private float viscosity;
	public ArrayList<Entity> entities;
	private boolean running;
	private boolean alive;
	private RenderParameters render = null;
	
	public World(){
		width = 0; //TODO change
		height = 0;
		gravity = 0;
		viscosity = 0;
		entities = new ArrayList<Entity>();
	}
	
	public void start(){
		alive = true;
		running = true;
		super.start();
	}
	
	public void run(){
		while(alive){
			if(running){
				//TODO calculate intersects then draw
			}
		}
	}
	
	public void setRenderParameters(RenderParameters p){
		render = p;
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
	
	/*TODO what should happen
	 *	velocity should be updated
	 *	Collision detection should occur, be taken care of (including velocity & such)
	 *	positions should be updated
	 */
	public void tick(){
		synchronized(entities){
			//TODO get rid of this prevs/news system. it should be all based on the collision results
			Float[] prevs = new Float[entities.size()];
			Float[] news = new Float[entities.size()];
			for(int e = 0; e < entities.size(); e++){
				prevs[e] = (Float)entities.get(e).location.clone();
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
							Float p = one.location;
							Float q = two.location;
							//TODO do this based on math
							//the ratio should be minTrans * (cur.mass/(m1+m2))
							if(one.staticEntity){
								q.setLocation(-temp.minimumTranslation.x + q.x, -temp.minimumTranslation.y + q.y);
							}else if(two.staticEntity){
								p.setLocation(temp.minimumTranslation.x + p.x, temp.minimumTranslation.y + p.y);
							}else{
								p.setLocation(temp.minimumTranslation.x/2 + p.x, temp.minimumTranslation.y/2 + p.y);
								q.setLocation(-temp.minimumTranslation.x/2 + q.x, -temp.minimumTranslation.y/2 + q.y);
							}
						}
					}
				}
			}
			for(Entity e: entities){
				e.tick();
			}
			for(int i = 0; i < news.length; i++){
				if(!entities.get(i).staticEntity)
					entities.get(i).velocity = new Vector(news[i].x-prevs[i].x, news[i].y-prevs[i].y);
			}
		}
	}
	
	public void draw(Graphics g, int x, int y){
		if(render != null){
			if(render.backgroundStyle == RenderParameters.SOLID_BACKGROUND){
				g.setColor(render.backgroundColor);
			}
		}else{
			g.setColor(Color.white);
		}
		g.fillRect(0, 0, x, y);
		synchronized(entities){
			for(Entity e: entities){
				e.draw(g, render);
			}
		}
	}
}
