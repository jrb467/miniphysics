package math.geometry;

public class Float3D implements Cloneable{
	public float x;
	public float y;
	public float z;
	
	public Float3D(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Float3D clone(){
		return new Float3D(x,y,z);
	}
}
