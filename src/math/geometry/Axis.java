package math.geometry;

public class Axis {
	public final float slopey;
	public final float slopez;
	private Vector unitVector;
	
	public Axis(float slopey, float slopez){
		this.slopey = slopey;
		this.slopez = slopez;
	}
	
	public static Axis axisFromPoints(Float3D one, Float3D two){
		return new Axis((two.y-one.y)/(two.x-one.x+.00001f), (two.z-one.z)/(two.x-one.x+.00001f));
	}
	
	public static Axis axisFromLine(Line l){
		return new Axis(l.slopey, l.slopez);
	}
	
	public static Axis axisFromVector(Vector v){
		return new Axis(v.y/(v.x+.00001f), v.z/(v.x+.00001f));
	}
	
	public static Axis axisFromSegment(LineSegment l){
		return new Axis((l.p2.y-l.p1.y)/(l.p2.x-l.p1.x+.00001f), (l.p2.z-l.p1.z)/(l.p2.x-l.p1.x+.00001f));
	}
	
	public Vector unitVector(){
		if(unitVector == null){
			float x = (float)(1/(Math.sqrt(slopey*slopey+slopez*slopez+1)));
			float y = slopey*x;
			float z = slopez*x;
			unitVector = new Vector(x, y, z);
		}
		return unitVector;
	}
	
	//TODO a perpendict would actually create a plane... so yeah
	public Axis perpindict(){
		return new Axis(-1/slopey, slopez);
	}
}
