package math;

import math.geometry.Axis;
import math.geometry.Float3D;
import math.geometry.Vector;
import math.util.PMath;

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
	 * 		2)  Find the axis/Vector perpindicular to that axis
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
	//TODO change this so that it only test perpendicular to faces
	public static CollisionResult testCollision(Entity one, Entity two){
		/* Sets up the variables:
		 * 		puts both entities verticies into two sets (for shorter names)
		 * 		initialize the translationAxis for the final translation
		 * 		NOTE: Float3D corresponds to Point2D.Float3D
		 * 		Set up a point for the minimum interval
		 * 		Find the relative velocity of the two objects, store as a vector
		 * 		set up variables for the current projection axis and current translation distance
		 * 		Set up two points (format: (minVal, maxVal)), for the current projections
		 */
		CollisionResult result = new CollisionResult();
		Float3D[] set1 = one.verticies;
		Float3D[] set2 = two.verticies;
		Axis translationAxis = new Axis(1,1);
		//NOTE: this is a Point2D.Float3D because the second float is only an indicator which side
		//each entity was on during the collision
		Float minimumInterval = new Float(-java.lang.Float.MAX_VALUE, 0);
		Vector relativeVelocity = Vector.sub(two.velocity, one.velocity);
		Axis currentAxis = null;
		Float currentInterval;
		Float proj1;
		Float proj2;
		//Begin to loop through every side of the polygons
		for(int i = 0; i < set1.length+set2.length; i++){
			currentAxis = createCurrentAxis(set1, set2, i);
			//Project the entities, taking into account relative velocity
			proj1 = projectEntity(currentAxis, one, relativeVelocity);
			proj2 = projectEntity(currentAxis, two, new Vector(0,0,0));
			//Test the interval distance
			currentInterval = intervalDistance(proj1, proj2);
			//if they don't overlap on this axis, they aren't colliding, so return false and break
			if(currentInterval.x > 0){
				return result;
			}
			//If its less than the minimum translation, store as minimum translation and take note of the translation axis
			if(currentInterval.x > minimumInterval.x){
				minimumInterval = currentInterval;
				translationAxis = currentAxis;
			}
		} //End loop
		if(minimumInterval.y != 0){
			minimumInterval.x = -minimumInterval.x;
		}
		if(one.staticEntity){
			result.entityTwoTranslation = Vector.vectorFromAxis(translationAxis, -minimumInterval.x);
			result.entityTwoVelocity = Vector.vectorFromAxis(translationAxis.perpindict(), -Vector.dotProduct(translationAxis.perpindict().unitVector(), two.velocity));
		}else if(two.staticEntity){
			result.entityOneTranslation = Vector.vectorFromAxis(translationAxis, minimumInterval.x);
			result.entityOneVelocity = Vector.vectorFromAxis(translationAxis.perpindict(), Vector.dotProduct(translationAxis.perpindict().unitVector(), one.velocity));
		}else{
			//calculates relative masses
			float r1 = two.mass/(one.mass+two.mass+.00001f);
			float r2 = one.mass/(one.mass+two.mass+.00001f);
			//calculates how much each would move based on relative mass
			float t1 = minimumInterval.x * r1;
			float t2 = -minimumInterval.x * r2;
			result.entityOneTranslation = Vector.vectorFromAxis(translationAxis, t1);
			result.entityTwoTranslation = Vector.vectorFromAxis(translationAxis, t2);
			result.entityOneVelocity = Vector.vectorFromAxis(translationAxis, Vector.dotProduct(translationAxis.unitVector(), Vector.multiplyByScalar(one.velocity,r2)));
			result.entityTwoVelocity = Vector.vectorFromAxis(translationAxis, Vector.dotProduct(translationAxis.unitVector(), Vector.multiplyByScalar(two.velocity,r1)));
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
	
	//Creates the current projection axis from an index and a set of points
	private static Axis createCurrentAxis(Float3D[] set1, Float3D[] set2, int index){
		Axis current;
		if(index < set1.length-1){
			current = Axis.axisFromPoints(set1[index], set1[index+1]).perpindict();
		}else if(index == set1.length-1){
			current = Axis.axisFromPoints(set1[set1.length-1], set1[0]).perpindict();
		}else if(index == set1.length+set2.length-1){
			current = Axis.axisFromPoints(set2[set2.length-1], set2[0]).perpindict();
		}else{
			current = Axis.axisFromPoints(set2[index-set1.length], set2[index-set1.length+1]).perpindict();
		}
		return current;
	}
	
	/*
	 * Gives the projection of an entity (+relative velocity) over the vector defining one of it's sides.
	 * Returns it as a Point2D.Float3D in the form (min,max)
	 */
	public static Float projectEntity(Axis axis, Entity entity, Vector relativeVelocity){
		/*	Initialize Variables:
		 * 		put array verticies into a convenient set
		 * 		create a unit vector for projection along the axis
		 * 		create a vector for the first point of the entity (factoring in location)
		 * 		create a float for the dot product of the current point
		 * 		create the dot product for the velocity (handled later)
		 * 		set up the min and max values for projection
		 */
		Float3D[] set = entity.verticies;
		Vector vect = axis.unitVector();
		Vector entityVect = new Vector(PMath.sum(set[0],entity.location));
		float dotProduct = Vector.dotProduct(vect, entityVect);
		float veloDotProduct = Vector.dotProduct(vect, relativeVelocity);
		float min = dotProduct;
		float max = dotProduct;
		//start looping through all the other points
		for(int i = 1; i < set.length; i++){
			//recalculate the entity vector for the next point
			entityVect = new Vector(PMath.sum(set[i],entity.location));
			//recalculate the dot product
			dotProduct = Vector.dotProduct(vect, entityVect);
			//test against min and max, and alter accordingly
			if(dotProduct < min){
				min = dotProduct;
			}else if(dotProduct > max){
				max = dotProduct;
			}
		}
		//factor in the relative velocity vector
		if(veloDotProduct < 0){
			min += veloDotProduct;
		}else{
			max += veloDotProduct;
		}
		//return a new Point2D.Float3D in the form (min,max)
		return new Float(min,max);
	}
}
