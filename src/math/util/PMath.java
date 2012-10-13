package math.util;

import java.awt.Point;
import java.awt.geom.Point2D;

import math.geometry.Vector;

public class PMath {
	
	public static Point2D.Float sum(Point2D.Float... points){
		float x = 0;
		float y = 0;
		for(Point2D.Float p: points){
			x += p.x;
			y += p.y;
		}
		return new Point2D.Float(x,y);
	}
	
	public static Point2D.Float sub(Point2D.Float a, Point2D.Float b){
		return new Point2D.Float(a.x-b.x, a.y-b.y);
	}
	
	public static Point2D.Float sub(Point2D.Float a, Vector b){
		return new Point2D.Float(a.x-b.x, a.y-b.y);
	}
	
	public static Point2D.Float toFloat(Point p){
		return new Point2D.Float(p.x, p.y);
	}
}
