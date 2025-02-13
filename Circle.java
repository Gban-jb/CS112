public class Circle extends GeometricObject {
	private double radius;
	
	//Default Counstructors
	public Circle() {	
	}
	
	//Constructors
	public Circle(double radius) {
		this.radius = radius;
	}

	// Constructors with radius, color and filled
	public Circle(double radius, String color, boolean filled) {
		this.radius = radius;
		setColor(color);
		setFilled(filled);
	}
	
	//Getters and Setters
	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	//Return Area, Diameter, Perimeter
	public double getArea() {
		return radius * radius * Math.PI;
	}
	
	public double getDiameter() {
		return 2 * radius;
	}
	
	public double getPerimeter() {
		return 2 * radius * Math.PI;
	}
	
	//Print circle info
	public void printCircle() {
		System.out.println("The circle is created "+ getDateCreated() + 
				" and the radius is " + radius);
	}
}
