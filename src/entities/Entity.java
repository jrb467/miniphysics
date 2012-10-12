package entities;

import graphic.RenderParameters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D.Float;

import math.PMath;
import math.Vector;

public class Entity {
	public final float mass;
	public final float hardness;
	public final float friction;
	public final Float[] verticies; //in location to the object origin (0, 0);
	public Float location;
	public Vector velocity;
	public float angle = 0;
	public float rotation = 0;
	public final boolean staticEntity;
	public byte id;
	public final static byte DEFAULT_ID = 0;
	
	public Entity(Float location, boolean staticEnt, float mass, float hardness, float friction, Float... verticies){
		this.location = location;
		velocity = new Vector(0, 0);
		id = 0;
		staticEntity = staticEnt;
		this.verticies = verticies;
		this.mass = mass;
		this.hardness = hardness;
		this.friction = friction;
	}
	
	public Entity(Float location, boolean staticEnt, byte id, float mass, float hardness, float friction, Float... verticies){
		this.location = location;
		this.id = id;
		velocity = new Vector(0, 0);
		this.verticies = verticies;
		this.mass = mass;
		this.hardness = hardness;
		this.friction = friction;
		this.staticEntity = staticEnt;
	}
	
	public Entity(Float location, boolean staticEnt, byte id, Float... verticies){
		this.location = location;
		this.id = id;
		velocity = new Vector(0, 0);
		this.verticies = verticies;
		this.mass = 0; //TODO change to defaults once implemented
		this.hardness = 0;
		this.friction = 0;
		this.staticEntity = staticEnt;
	}
	
	public Entity(Float location, boolean staticEnt, Float... verticies){
		this.location = location;
		this.id = 0;
		velocity = new Vector(0, 0);
		this.verticies = verticies;
		this.mass = 0; //TODO change to defaults once implemented
		this.hardness = 0;
		this.friction = 0;
		this.staticEntity = staticEnt;
	}
	
	public void tick(){
		if(!this.staticEntity){
			velocity.x *= .98f;
			velocity.y *= .98f;
			location.setLocation(location.x+velocity.x, location.y+velocity.y);
		}
	}
	
	public void draw(Graphics g, RenderParameters r){
		if(r != null){
			fillPolygon(g,r);
		}
		if(r != null && r.edgeStyle == RenderParameters.EDGED_ENTITIES){
			g.setColor(r.lineColor);
		}else{
			g.setColor(Color.black);
		}
		if(r == null || r.edgeStyle == RenderParameters.EDGED_ENTITIES){
			for(int i = 0; i < verticies.length-1; i++){
				drawLine(g, PMath.sum(verticies[i], location), PMath.sum(verticies[i+1], location));
			}
			drawLine(g, PMath.sum(verticies[verticies.length-1], location), PMath.sum(verticies[0], location));
		}
	}
	
	public void drawLine(Graphics g, Float a, Float b){
		g.drawLine((int)a.x, (int)a.y, (int)b.x, (int)b.y);
	}
	
	private void fillPolygon(Graphics g, RenderParameters r){
		int[] x = new int[verticies.length];
		int[] y = new int[x.length];
		Float p;
		for(int i = 0; i < x.length; i++){
			p = PMath.sum(verticies[i], location);
			x[i] = (int)p.x;
			y[i] = (int)p.y;
		}
		g.setColor(r.shapeColor);
		g.fillPolygon(x, y, x.length);
	}
}
