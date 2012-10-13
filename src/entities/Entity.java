package entities;

import graphic.RenderParameters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D.Float;

import math.geometry.Vector;
import math.util.PMath;

public class Entity {
	
	public static class EntityData {
		public float mass = 0;
		public float hardness = 0;
		public float friction = 0;
		public boolean staticEntity = false;
		public byte id = 0;
		public Float location = new Float(0,0);
		public Float[] verticies = {};
	}

	public final float mass;
	public final float hardness;
	public final float friction;
	public final Float[] verticies; //in location to the object origin (0, 0);
	public Float location;
	public Vector velocity = new Vector(0, 0);
	public float angle = 0;
	public float rotation = 0;
	public final boolean staticEntity;
	public byte id;
	public final static byte DEFAULT_ID = 0;
	
	public Entity(EntityData data){
		location = data.location;
		verticies = data.verticies;
		mass = data.mass;
		hardness = data.hardness;
		friction = data.friction;
		staticEntity = data.staticEntity;
		id = data.id;
	}
	
	public void tick(){
		if(!this.staticEntity){
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
