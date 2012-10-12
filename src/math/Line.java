package math;

import java.awt.geom.Point2D;

public class Line {
	public final float slope;
	public final Point2D.Float origin;
	
	public Line(float slope, Point2D.Float origin){
		this.slope = slope;
		this.origin = origin;
	}
	
	public static Line lineFromSet(Point2D.Float origin, Point2D.Float member){
		return new Line((member.y-origin.y)/(member.x-origin.x), origin);
	}
	
	public static Line lineFromAxis(Axis s, Point2D.Float origin){
		return new Line(s.slope, origin);
	}
	
	public static Line lineFromVector(Vector v, Point2D.Float origin){
		return new Line(v.y/v.x, origin);
	}
	
	public static Line lineFromSegment(LineSegment l){
		return new Line((l.p2.y-l.p1.y)/(l.p2.x-l.p1.x), l.p1);
	}
}
