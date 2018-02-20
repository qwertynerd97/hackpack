package hackpack;

public class Geometry {
	
	public static boolean doIntersect(Point[] line1, Point[] line2){
		return ((line1[0].x <= line2[1].x && line1[1].x >= line2[0].x) || (line1[0].x >= line2[1].x && line1[1].x <= line2[0].x)) // x overlaps
				&& ((line1[0].y <= line2[1].y && line1[1].y >= line2[0].y) || (line1[0].y >= line2[1].y && line1[1].y <= line2[0].y)); // y overlaps
	}
	public static boolean doIntersect(Circle c1, Circle c2){
		return Math.pow(c1.centerX - c2.centerX,2)+ Math.pow(c1.centerY - c2.centerY,2) > 0;
		 
	}
	/**
	 * Checks if two line segments intersect.  If they do, generates
	 * and returns the intersection point
	 * 
	 * @param line1 an array containing the end points of the line
	 * @param line2 an array containing the end points of the line
	 * @return intersection point if exists, otherwise null
	 */
	public static Point segmentsIntersect(Point[] line1, Point[] line2){
		//check that bounds overlap
		if(doIntersect(line1,line2)){ 
			// Check if either line is vertical
			if(line1[0].x == line1[1].x && line2[0].x == line2[1].x){
				return new Point("Lines are both vertical",-1,-1);
			}else if(line1[0].x == line1[1].x){	
				double a2 = (line2[1].y-line2[0].y)/(line2[1].x-line2[0].x);
				double b2 = line2[0].y - a2*line2[0].x;
				
				double y = line1[0].y;
				double x = (y-b2)/a2;
				return new Point("Lines intersect",x,y);
			}else if(line2[0].x == line2[1].x){
				double a1 = (line1[1].y-line1[0].y)/(line1[1].x-line1[0].x);
				double b1 = line1[0].y - a1*line1[0].x;
				
				double y = line2[0].y;
				double x = (y-b1)/a1;
				return new Point("Lines intersect",x,y);
			}else{
				// find equations by y = ax+b
				double a1 = (line1[1].y-line1[0].y)/(line1[1].x-line1[0].x);
				double b1 = line1[0].y - a1*line1[0].x; 
				double a2 = (line2[1].y-line2[0].y)/(line2[1].x-line2[0].x);
				double b2 = line2[0].y - a2*line2[0].x;
				
				if(a1 == a2){
					return new Point("Lines are parallel",-1,-1);
				}
				//System.out.println("Slope: " + a1 + " Intercept: " + b1);
				//System.out.println("Slope: " + a2 + " Intercept: " + b2);
				
				double x = -(b1-b2)/(a1-a2);
				double y = a1*x + b1; 
				return new Point("Intersection",x,y);
			}
			
		}
		return null;
	}
	
	public static Point[] circlesIntersect(Circle circle1, Circle circle2){
		double distSquared = Math.pow(circle1.centerX - circle2.centerX,2)+ Math.pow(circle1.centerY - circle2.centerY,2);
		double dist = Math.sqrt(distSquared);
		if(dist > 0){
			double dLeft = (Math.pow(circle1.radius,2) - Math.pow(circle2.radius,2) + distSquared) / (2*dist);
			double height = Math.sqrt(Math.pow(circle1.radius,2)-Math.pow(dLeft,2));
			 
			double midX = circle1.centerX + dLeft*(circle2.centerX-circle1.centerX)/dist;
			double midY = circle1.centerY + dLeft*(circle2.centerY-circle1.centerY)/dist;
			Point mid = new Point("midpoint",midX,midY);
			 
			double x1 = mid.x + (height*(circle2.centerY - circle1.centerY)/dist);
			double y1 = mid.y - (height*(circle2.centerX - circle1.centerX)/dist);
			double x2 = mid.x - (height*(circle2.centerY - circle1.centerY)/dist);
			double y2 = mid.y + (height*(circle2.centerX - circle1.centerX)/dist);
			 
			return new Point[]{new Point("Intersection",x1,y1),new Point("Intersection",x2,y2)};
		 }
		return null;
	}
	
	static Point[] lineCircleIntersect(Circle c,Point[] l){
		Point[] ps = new Point[2];
		
		double deltaX = l[1].x - l[0].x;
		double deltaY = l[1].y - l[0].y;
		double cToLX = c.centerX - l[0].x;
		double cToLY = c.centerY - l[0].y;
		System.out.println("dx: " + deltaX + " dy: " + deltaY);
		System.out.println("c2lx: " + cToLX + " c2ly: " + cToLY);
		
		double lineLengthSqrd = Math.pow(deltaX,2) + Math.pow(deltaY,2);
		System.out.println("Line squared: " + lineLengthSqrd);
		double bBy2 = deltaX*cToLX + deltaY*cToLY;
		System.out.println("bby2: " + bBy2);
		double distSqrd = Math.pow(cToLX, 2) + Math.pow(cToLY, 2) - Math.pow(c.radius, 2);
		System.out.println("distSqrd: " + distSqrd);
		
		double pBy2 = bBy2/lineLengthSqrd;
		double vector = distSqrd / lineLengthSqrd;
		
		double disc = Math.pow(pBy2,2) - vector;
		System.out.println("disc: " + disc);
		if(disc < 0){
			// They don't intersect, so both are null
			return ps;
		}
		
		double root = Math.sqrt(disc);
		double scalingPos = -pBy2 + root;
		double scalingNeg = -pBy2 - root;
		
		ps[0] = new Point("Intersection",l[0].x - deltaX*scalingPos,l[0].y - deltaY*scalingPos);
		if(disc == 0){
			return ps;
		}
		
		ps[1] = new Point("Intersection",l[0].x - deltaX*scalingNeg,l[0].y - deltaY*scalingNeg);
		
		return ps;
	}
	
	public static void main(String[] args){
		Circle c1 = new Circle(2,3,3);
		Circle c2 = new Circle(1,-1,4);
		
		Point[] points = circlesIntersect(c1,c2);
		System.out.println(points[0]);
		System.out.println(points[1]);
		
		Point[] l1 = {new Point(1,4),new Point(5,0)};
		Point[] l2 = {new Point(1,0),new Point(5,4)};
		System.out.println(segmentsIntersect(l1,l2));
		
		Circle c3 = new Circle(2,2,1);
		Point[] l3 = {new Point("a",0,2),new Point("b",4,2)};
		Point[] ans = lineCircleIntersect(c3, l3);
		System.out.println(ans[0]);
		System.out.println(ans[1]);
	}
}

class Circle{
	double centerX;
	double centerY;
	double radius;
	
	public Circle(double x, double y, double rad){
		centerX = x;
		centerY = y;
		radius = rad;
	}
}
