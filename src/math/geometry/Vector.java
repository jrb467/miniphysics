package math.geometry;

public class Vector {
	public static final Vector MAX_VALUE = new Vector(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
	public static final Vector DEFAULT = new Vector(0,0,0);
	public float x;
	public float y;
	public float z;
	
	public Vector(Float3D components){
		x = components.x;
		y = components.y;
		z = components.z;
	}
	
	public Vector(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static Vector vectorFromSet(Float3D baseVector, Float3D newOrigin){
		return new Vector(baseVector.x-newOrigin.x, baseVector.y-newOrigin.y, baseVector.z-newOrigin.z);
	}
	
	public static Vector vectorFromAxis(Axis a, float length){
		float x = (float)(length/(Math.sqrt(a.slopey*a.slopey+a.slopez*a.slopez+1)));
		float y = a.slopey*x;
		float z = a.slopez*x;
		return new Vector(x,y,z);
	}
	
	public static Vector vectorFromLine(Line l, float length){
		float x = (float)(length/(Math.sqrt(l.slopey*l.slopey+l.slopez*l.slopez+1)));
		float y = l.slopey*x;
		float z = l.slopez*x;
		return new Vector(x,y,z);
	}
	
	public static Vector vectorFromSegment(LineSegment l){
		return new Vector(l.p2.x-l.p1.x, l.p2.y-l.p1.y, l.p2.z-l.p1.z);
	}
	
	public static float dotProduct(Vector a, Vector b){
		return a.x*b.x + a.y*b.y + a.z*b.z;
	}
	
	public static Vector multiplyByScalar(Vector v, float s){
		return new Vector(v.x*s, v.y*s, v.z*s);
	}
	
	public static Vector sum(Vector one, Vector two){
		return new Vector(one.x+two.x, one.y+two.y, one.z+two.z);
	}
	
	public static Vector sub(Vector one, Vector two){
		return new Vector(two.x-one.x, two.y-one.y, two.z-one.z);
	}
}
