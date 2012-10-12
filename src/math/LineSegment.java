package math;

import java.awt.geom.Point2D;

public class LineSegment {
	public final Point2D.Float p1;
	public final Point2D.Float p2;
	
	public LineSegment(Point2D.Float one, Point2D.Float two){
		p1 = one;
		p2 = two;
	}
	
	public static LineSegment segmentFromVector(Vector v, Point2D.Float origin){
		return new LineSegment(origin, new Point2D.Float(origin.x+v.x, origin.y+v.y));
	}
	
	public static LineSegment segmentFromAxis(Axis a, Point2D.Float origin, float length){
		float x = (float)(length/(Math.sqrt(a.slope*a.slope+1)));
		float y = a.slope*x;
		return new LineSegment(origin, new Point2D.Float(x,y));
	}
	
	public static LineSegment segmentFromLine(Line l, float length){
		float x = (float)(length/(Math.sqrt(l.slope*l.slope+1)));
		float y = l.slope*x;
		return new LineSegment(l.origin, new Point2D.Float(x,y));
	}
	
	public static LineSegment segmentFromLine(Line l, float length1, float length2){
		float x = (float)(length1/(Math.sqrt(l.slope*l.slope+1)));
		float y = l.slope*x;
		Point2D.Float p1 = new Point2D.Float(x,y);
		x = (float)(length2/(Math.sqrt(l.slope*l.slope+1)));
		y = l.slope*x;
		Point2D.Float p2 = new Point2D.Float(x,y);
		return new LineSegment(p1,p2);
	}
	
	public static LineSegment segmentFromAxis(Axis s, Point2D.Float origin, float length1, float length2){
		float x1 = (float)(length1/(Math.sqrt(s.slope*s.slope+1)));
		float y1 = s.slope*x1;
		float x2 = (float)(length2/(Math.sqrt(s.slope*s.slope+1)));
		float y2 = s.slope*x2;
		Point2D.Float p1 = new Point2D.Float(x1,y1);
		Point2D.Float p2 = new Point2D.Float(x2,y2);
		p1 = PMath.sum(p1, origin);
		p2 = PMath.sum(p2, origin);
		return new LineSegment(p1, p2);
	}
	
	public static LineSegment segmentFromAxis(Axis s, float length1, float length2){
		float x = (float)(length1/(Math.sqrt(s.slope*s.slope+1)));
		float y = s.slope*x;
		Point2D.Float p1 = new Point2D.Float(x,y);
		x = (float)(length2/(Math.sqrt(s.slope*s.slope+1)));
		y = s.slope*x;
		Point2D.Float p2 = new Point2D.Float(x,y);
		return new LineSegment(p1, p2);
	}
}
