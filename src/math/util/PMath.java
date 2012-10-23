package math.util;

import math.geometry.Float3D;
import math.geometry.Vector;

public class PMath {
	
	public static Float3D sum(Float3D... points){
		float x = 0;
		float y = 0;
		float z = 0;
		for(Float3D p: points){
			x += p.x;
			y += p.y;
			z += p.z;
		}
		return new Float3D(x,y,z);
	}
	
	public static Float3D sub(Float3D a, Float3D b){
		return new Float3D(a.x-b.x, a.y-b.y, a.z-b.z);
	}
	
	public static Float3D sub(Float3D a, Vector b){
		return new Float3D(a.x-b.x, a.y-b.y, a.z-b.z);
	}
}
