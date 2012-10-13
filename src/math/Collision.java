package math;

import java.awt.geom.Point2D.Float;

import math.geometry.Axis;
import math.geometry.Vector;
import math.util.PMath;

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
	//TODO implement predictive collisions w/ velocity
	
	public static CollisionResult testCollision(Entity one, Entity two){
		/* Sets up the variables:
		 * 		puts both entities verticies into two sets (for shorter names)
		 * 		initialize the translationAxis for the final translation
		 * 		NOTE: Float corresponds to Point2D.Float
		 * 		Set up a point for the minimum interval
		 * 		Find the relative velocity of the two objects, store as a vector
		 * 		set up variables for the current projection axis and current translation distance
		 * 		Set up two points (format: (minVal, maxVal)), for the current projections
		 */
		CollisionResult result = new CollisionResult();
		Float[] set1 = one.verticies;
		Float[] set2 = two.verticies;
		Axis translationAxis = new Axis(1);
		Float minimumInterval = new Float(-java.lang.Float.MAX_VALUE, 0);
		Vector relativeVelocity = Vector.sub(two.velocity, one.velocity);
		Axis currentAxis;
		Float currentInterval;
		Float proj1;
		Float proj2;
		//Begin to loop through every side of the polygons
		//TODO possibly change to iterate through a set of lineSegments return by the entities themselves
		for(int i = 0; i < set1.length+set2.length; i++){
			//Test which two points should be included in creating the axis based on 'i'
			if(i < set1.length-1){
				currentAxis = Axis.axisFromPoints(set1[i], set1[i+1]).perpindict();
			}else if(i == set1.length-1){
				currentAxis = Axis.axisFromPoints(set1[set1.length-1], set1[0]).perpindict();
			}else if(i == set1.length+set2.length-1){
				currentAxis = Axis.axisFromPoints(set2[set2.length-1], set2[0]).perpindict();
			}else{
				currentAxis = Axis.axisFromPoints(set2[i-set1.length], set2[i-set1.length+1]).perpindict();
			}
			//Project the entities, taking into account relative velocity
			proj1 = projectEntity(currentAxis, one, relativeVelocity);
			proj2 = projectEntity(currentAxis, two, new Vector(0,0));
			//Test the interval distance
			currentInterval = intervalDistance(proj1, proj2);
			//if they don't overlap on this axis, they aren't colliding, so return false and break
			if(currentInterval.x > 0){
				result.willIntersect = false;
				result.minimumTranslation = new Vector(0,0);
				return result;
			}
			//If its less than the minimum translation, store as minimum translation and take note of the translation axis
			if(currentInterval.x > minimumInterval.x){
				minimumInterval = currentInterval;
				translationAxis = currentAxis;
			}
		} //End loop
		
		//APPARANTLY, if the maximum of the 
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
	 * Gives the projection of an entity (+relative velocity) over the vector defining one of it's sides.
	 * Returns it as a Point2D.Float in the form (min,max)
	 */
	public static Float projectEntity(Axis axis, Entity entity, Vector relativeVelocity){
		//Put the entities verticies into a nice, short-named set
		Float[] set = entity.verticies;
		//Convert the axis into a unit vector for projection
		Vector vect = axis.unitVector();
		//Initialize an vector for the entities first verticie (taking into account it's location)
		Vector entityVect = new Vector(PMath.sum(set[0],entity.location));
		//set up float for the dot product of the current point being iterated over
		float dotProduct = Vector.dotProduct(vect, entityVect);
		//calculate the dot product of the relative velocity
		float veloDotProduct = Vector.dotProduct(vect, relativeVelocity);
		//set up the min and max values
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
		//return a new Point2D.Float in the form (min,max)
		return new Float(min,max);
	}
}
