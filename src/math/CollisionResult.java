package math;

import math.geometry.Vector;

public class CollisionResult {
	public boolean willIntersect;
	public Vector minimumTranslation;
	
	public CollisionResult(){
		willIntersect = false;
		minimumTranslation = new Vector(0,0);
	}
}
