package math.geometry;

import math.util.PMath;

public class LineSegment {
	public final Float3D p1;
	public final Float3D p2;
	
	public LineSegment(Float3D one, Float3D two){
		p1 = one;
		p2 = two;
	}
	
	public static LineSegment segmentFromVector(Vector v, Float3D origin){
		return new LineSegment(origin, new Float3D(origin.x+v.x, origin.y+v.y, origin.z+v.z));
	}
	
	public static LineSegment segmentFromAxis(Axis a, Float3D origin, float length){
		float x = (float)(length/(Math.sqrt(a.slopey*a.slopey+a.slopez*a.slopez+1)));
		float y = a.slopey*x;
		float z = a.slopez*x;
		return new LineSegment(origin, new Float3D(x,y,z));
	}
	
	public static LineSegment segmentFromLine(Line l, float length){
		float x = (float)(length/(Math.sqrt(l.slopey*l.slopey+l.slopez*l.slopez+1)));
		float y = l.slopey*x;
		float z = l.slopez*x;
		return new LineSegment(l.origin, new Float3D(x,y,z));
	}
	
	public static LineSegment segmentFromLine(Line l, float length1, float length2){
		float unitx = (float)Math.sqrt(l.slopey*l.slopey+l.slopez*l.slopez+1);
		float x = (float)(length1/unitx);
		float y = l.slopey*x;
		float z = l.slopez*x;
		Float3D p1 = new Float3D(x,y,z);
		x = (float)(length2/unitx);
		y = l.slopey*x;
		z = l.slopez*x;
		Float3D p2 = new Float3D(x,y,z);
		return new LineSegment(p1,p2);
	}
	
	public static LineSegment segmentFromAxis(Axis s, Float3D origin, float length1, float length2){
		float unitx = (float)Math.sqrt(s.slopey*s.slopey+s.slopez*s.slopez+1);
		float x1 = (float)(length1/unitx);
		float y1 = s.slopey*x1;
		float z1 = s.slopez*x1;
		float x2 = (float)(length2/unitx);
		float y2 = s.slopey*x2;
		float z2 = s.slopez*x2;
		Float3D p1 = new Float3D(x1,y1,z1);
		Float3D p2 = new Float3D(x2,y2,z2);
		p1 = PMath.sum(p1, origin);
		p2 = PMath.sum(p2, origin);
		return new LineSegment(p1, p2);
	}
	
	public static LineSegment segmentFromAxis(Axis s, float length1, float length2){
		float unitx = (float)Math.sqrt(s.slopey*s.slopey+s.slopez*s.slopez+1);
		float x = length1/unitx;
		float y = s.slopey*x;
		float z = s.slopez*x;
		Float3D p1 = new Float3D(x,y,z);
		x = length2/unitx;
		y = s.slopey*x;
		z = s.slopez*x;
		Float3D p2 = new Float3D(x,y,z);
		return new LineSegment(p1, p2);
	}
}
