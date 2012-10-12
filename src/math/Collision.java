package math;

import java.awt.geom.Point2D.Float;

import entities.Entity;

public class Collision {
	/*
	 * Tests if the two polygons are going to collide, are colliding, and what distance needs to be
	 * moved in order to account for collision
	 * 
	 * STEPS:
	 * 
	 * 		1)  Pick an edge on either polygon
	 * 		2)  Find the axis/Vector perpindicular to that axis (call it pAxis)
	 * 		3) 	Project each polygon onto that axis, using these steps:
	 * 			1) Set the intitial min/max values as the first point coordinates' dot product
	 * 			2) Loop through the rest of the verticies, testing their dot product vs min & max
	 * 			3) Return the min & max values
	 * 			NOTE: dot product and projection is calculated with:
	 * 				proj(a onto b) = (a*b)/mag(b)
	 * 		4)Test if projections overlap, if not BREAK(intersecting=FALSE)
	 * 		5)If they overlap, test to see if the overlap is less than the minimum (to test which axis to move along)
	 * 		6)Repeat for all sides on each polygon
	 * 		7)Deal with the translation vector
	 */
	//TODO implement predictive collisions w/ velocity
	
	public static CollisionResult testCollision(Entity one, Entity two){
		CollisionResult result = new CollisionResult();
		Float[] set1 = one.verticies;
		Float[] set2 = two.verticies;
		Axis translationAxis = new Axis(1);
		Float minimumInterval = new Float(-java.lang.Float.MAX_VALUE, 0);
		Axis currentAxis;
		Float currentInterval;
		Float proj1;
		Float proj2;
		for(int i = 0; i < set1.length+set2.length; i++){
			if(i < set1.length-1){
				currentAxis = Axis.axisFromPoints(set1[i], set1[i+1]).perpindict();
			}else if(i == set1.length-1){
				currentAxis = Axis.axisFromPoints(set1[set1.length-1], set1[0]).perpindict();
			}else if(i == set1.length+set2.length-1){
				currentAxis = Axis.axisFromPoints(set2[set2.length-1], set2[0]).perpindict();
			}else{
				currentAxis = Axis.axisFromPoints(set2[i-set1.length], set2[i-set1.length+1]).perpindict();
			}
			proj1 = projectEntity(currentAxis, one);
			proj2 = projectEntity(currentAxis, two);
			currentInterval = intervalDistance(proj1, proj2);
			if(currentInterval.x > 0){
				result.willIntersect = false;
				result.minimumTranslation = new Vector(0,0);
				return result;
			}
			if(currentInterval.x > minimumInterval.x){
				minimumInterval = currentInterval;
				translationAxis = currentAxis;
			}
		}
		if(minimumInterval.y == 0){
			result.minimumTranslation = Vector.vectorFromAxis(translationAxis, minimumInterval.x);
		}else{
			result.minimumTranslation = Vector.vectorFromAxis(translationAxis, -minimumInterval.x);
		}
		result.willIntersect = true;
		return result;
	}
	
	private static Float intervalDistance(Float a, Float b){
		if(a.x < b.x){
			return new Float(b.x-a.y, 0);
		}
		return new Float(a.x-b.y, 1);
	}
	
	/*
	 * Gives the projection of an entity over the vector defining one of it's sides.
	 */
	public static Float projectEntity(Axis axis, Entity entity){
		Float[] set = entity.verticies;
		Vector vect = axis.unitVector();
		Vector entityVect = new Vector(PMath.sum(set[0],entity.location));
		float dotProduct = Vector.dotProduct(vect, entityVect);
		float min = dotProduct;
		float max = dotProduct;
		for(int i = 0; i < set.length; i++){
			entityVect = new Vector(PMath.sum(set[i],entity.location));
			dotProduct = Vector.dotProduct(vect, entityVect);
			if(dotProduct < min){
				min = dotProduct;
			}else if(dotProduct > max){
				max = dotProduct;
			}
		}
		return new Float(min,max);
	}
}
