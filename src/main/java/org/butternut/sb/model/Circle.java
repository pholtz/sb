package org.butternut.sb.model;

/**
 * Class whose instances represent circles in the x/y plane.
 * Circles can be moved by setting a new center point,
 * and resized by setting a new radius.
 * 
 * @author David Hovemeyer
 */
public class Circle {
	Point center;
	double radius;
	/**
	 * Constructor.
	 * 
	 * @param center  the center Point of the circle
	 * @param radius  the radius of the circle
	 */
	public Circle(Point center, double radius) {
		this.center = center;
		this.radius = radius;
	}
	
	/**
	 * Get the current center point of the circle.
	 * 
	 * @return the center point of the circle
	 */
	public Point getCenter() {
		Point center = this.center;
		return center;
	}
	
	/**
	 * Set the center point of the circle.
	 * 
	 * @param center the (new) center point of the circle
	 */
	public void setCenter(Point center) {
		this.center = center;
	}

	/**
	 * Get the current radius.
	 * 
	 * @return the current radius
	 */
	public double getRadius() {
		double radius = this.radius;
		return radius;
	}

	/**
	 * Set the radius.
	 * 
	 * @param radius the (new) radius of the circle
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}
}
