package math.geometry;

public class Line {
	public final float slopey;
	public final float slopez;
	public final Float3D origin;
	
	public Line(float slopey, float slopez, Float3D origin){
		this.slopey = slopey;
		this.slopez = slopez;
		this.origin = origin;
	}
	
	public static Line lineFromSet(Float3D origin, Float3D member){
		return new Line((member.y-origin.y)/(member.x-origin.x), (member.z-origin.z)/(member.x-origin.x), origin);
	}
	
	public static Line lineFromAxis(Axis s, Float3D origin){
		return new Line(s.slopey, s.slopez, origin);
	}
	
	public static Line lineFromVector(Vector v, Float3D origin){
		return new Line(v.y/v.x, v.z/v.x, origin);
	}
	
	public static Line lineFromSegment(LineSegment l){
		return new Line((l.p2.y-l.p1.y)/(l.p2.x-l.p1.x), (l.p2.z-l.p1.z)/(l.p2.x-l.p1.x), l.p1);
	}
}
