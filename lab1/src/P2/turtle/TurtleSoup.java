/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.*;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        double angle=180-360/(double)sides;
        return angle;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
    	//Ϊ�ų�С���Խ����Ӱ�죬����Math.rint��ʹ�ý����Ϊ����
        int sides = (int)Math.rint(360/(180 - angle));
        return sides;
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double angle = 180 - calculateRegularPolygonAngle(sides);
    	for(int i = 1; i <= sides; i++) {
        	turtle.forward(sideLength);
            turtle.turn(angle);
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
    	//���÷����Ǻ�������Ŀ����뵱ǰ������б����x������ļн�
    	double angle = Math.atan2(targetY - currentY, targetX - currentX) * 180.0 / Math.PI;
    	//�Ƕ�Ϊ���ģ�����Ϊ��ֵ
        if(angle < 0)
            angle += 360.0;
        //����б����y������֮��ļнǣ�y��Ϊʼ�ߣ�˳ʱ�뷽��Ϊ���򣩣��ټ�ȥ��ǰƫ�ƽǶ�
        double bearing = (360 - angle + 90 >= 360 ? 90 - angle : 360 - angle + 90) - currentBearing;
        //������Ϊ0-360��֮��
        return bearing < 0 ? 360.0 + bearing : bearing;
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        int numofpoint = xCoords.size();
        List<Double> angle = new ArrayList<Double>();
        double ang = 0;
        for(int i = 0; i < numofpoint - 1; i++) {
        	ang = calculateBearingToPoint(ang, xCoords.get(i), yCoords.get(i), xCoords.get(i+1), yCoords.get(i+1));
        	angle.add(ang);
        }
        return angle;
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
    	//��ĸ���С�ڵ���3ʱ�����е㶼��͹����
    	if (points.size() <= 3) 
    		return points;
        Set<Point> convexHullPoints = new HashSet<Point>();
        Point a = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        //���ҳ���ƽ���������½ǵĵ�
        for(Point i : points){
            if(i.x() < a.x() || (i.x() == a.x() && i.y() < a.y()))
                a = i;
        }
        convexHullPoints.add(a);
        Point curpoint = a, nextpoint = null;
        double angle = 0, mintheta = 360, theta = 0, distance = 0;
        while(nextpoint != a) {
        	for(Point i : points) {//����calculateBearingToPoint�ķ������ҳ�ʹ����ת�Ƕ���С�ĵ�
        		//���÷����Ǻ�������Ŀ����뵱ǰ������б����x������ļн�
        		double angle1 = Math.atan2(i.y() - curpoint.y(), i.x() - curpoint.x()) * 180.0 / Math.PI;
        		//�Ƕ�Ϊ���ģ�����Ϊ��ֵ
        		if(angle1 < 0)
        			angle1 += 360.0;
        		//����б����y������֮��ļнǣ�y��Ϊʼ�ߣ�˳ʱ�뷽��Ϊ���򣩣��ټ�ȥ��ǰƫ�ƽǶ�
        		double bearing = (360 - angle1 + 90 >= 360 ? 90 - angle1 : 360 - angle1 + 90) - angle;
        		//������Ϊ0-360��֮��
        		if(bearing < 0)
        			theta = 360.0 + bearing;
        		else
        			theta = bearing;
        		//����ʹ����ת�Ƕ���С�Ľ�
        		if((!convexHullPoints.contains(i) || (i == a && curpoint != a)) && theta < mintheta) {
        			mintheta = theta;
        			nextpoint = i;
        			distance = Math.sqrt((i.y() - curpoint.y())*(i.y() - curpoint.y())+(i.x() - curpoint.x())*(i.x() - curpoint.x()));
        		}
        		//����ת�Ƕ���ͬʱ��ѡ����뵱ǰ���Զ�ĵ�
        		if((!convexHullPoints.contains(i) || (i == a && curpoint != a)) && theta == mintheta) {
        			if(Math.sqrt((i.y() - curpoint.y())*(i.y() - curpoint.y())+(i.x() - curpoint.x())*(i.x() - curpoint.x())) > distance) {
        				nextpoint = i;
            			distance = Math.sqrt((i.y() - curpoint.y())*(i.y() - curpoint.y())+(i.x() - curpoint.x())*(i.x() - curpoint.x()));
        			}
        		}
        	}
        	convexHullPoints.add(nextpoint);
        	curpoint = nextpoint;
        	angle += mintheta;
        	if(angle > 360)
        		angle -= 360;
        	mintheta = 360;
        }
        return convexHullPoints;
    }
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        int Step = 300;
        for (int i = 1; i <= Step; i++){
            switch (i % 7) {
                case 0:
                    turtle.color(PenColor.RED);
                    break;
                case 1:
                    turtle.color(PenColor.ORANGE);
                    break;
                case 2:
                    turtle.color(PenColor.YELLOW);
                    break;
                case 3:
                    turtle.color(PenColor.GREEN);
                    break;
                case 4:
                    turtle.color(PenColor.CYAN);
                    break;
                case 5:
                    turtle.color(PenColor.BLUE);
                    break;
                case 6:
                	turtle.color(PenColor.PINK);
                    break;
            }
            turtle.forward(i);
            turtle.turn(45);
        }
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();
        
        /*drawSquare(turtle, 40);*/
        
        /*drawRegularPolygon(turtle, 5, 40);*/
        
        drawPersonalArt(turtle);

        // draw the window
        turtle.draw();
    }

}
