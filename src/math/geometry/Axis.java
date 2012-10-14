package math.geometry;

import java.awt.geom.Point2D;

public class Axis {
	public final float slope;
	private Vector unitVector;
	
	public Axis(float slope){
		this.slope = slope;
	}
	
	public static Axis axisFromPoints(Point2D.Float one, Point2D.Float two){
		return new Axis((two.y-one.y+.00001f)/(two.x-one.x+.00001f));
	}
	
	public static Axis axisFromLine(Line l){
		return new Axis(l.slope);
	}
	
	public static Axis axisFromVector(Vector v){
		return new Axis((v.y+.00001f)/(v.x+.00001f));
	}
	
	public static Axis axisFromSegment(LineSegment l){
		return new Axis((l.p2.y-l.p1.y+.00001f)/(l.p2.x-l.p1.x+.00001f));
	}
	
	public Vector unitVector(){
		if(unitVector == null){
			float x = (float)(1/(Math.sqrt(slope*slope+1)));
			float y = slope*x;
			unitVector = new Vector(x, y);
		}
		return unitVector;
	}
	
	public Axis perpindict(){
		return new Axis(-1/slope);
	}
}
