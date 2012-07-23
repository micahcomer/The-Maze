package com.mjc.maze.basics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PointList implements Serializable {

	
	//region Properties
	
	private static final long serialVersionUID = -4529746880188724652L;
	public List<Point> Points;
	
	//endregion
	
	
    public PointList()
    {
        Points = new ArrayList<Point>();        
    }
    
    public Point returnEquivalentTo(Point testPoint)
    {
        Point pointToReturn;
        
        int index = -1;
        
        for (Point point:Points)
        {
            if (point.ValuesAreEqualTo(testPoint))
                index = Points.indexOf(point);
        }
        
        if (index>=0)
            pointToReturn = Points.get(index);
        else
            pointToReturn = new Point(-1,-1);
        
        return pointToReturn;
    }
    
    public Point returnEquivalentTo(int x, int y)
    {
    	return returnEquivalentTo(new Point(x,y));
    }
    
    public boolean ContainsEquivalent(Point testPoint)
    {
        boolean contains = false;
        for (Point point:Points)
        {
            if (point.ValuesAreEqualTo(testPoint))
                contains = true;
        }
        
        return contains;
    }

    public boolean ContainsEquivalent(Vector2 testPoint)
    {
        boolean contains = false;

        float diffX = (int)testPoint.getX() - testPoint.getX();
        float diffY = (int)testPoint.getY() - testPoint.getY();

        if ((diffX==0)&&(diffY==0))
        {
            contains = ContainsEquivalent(new Point((int)testPoint.getX(), (int)testPoint.getY()));
        }
        return contains;
    }
    
    public boolean ContainsEquivalent(int x, int y)
    {
    	boolean value = false;
    	for (Point point:Points)
    	{
    		if ((point.getX()==x)&&(point.getY()==y))
    			value = true;
    	}
    	
    	return value;
    }
    
    public void RemoveDuplicates()
    {
        for (int i = 0; i<Points.size()-1; i++)
        {
            for (int j = i+1; j<Points.size(); j++)
            {
                if (Points.get(i).ValuesAreEqualTo(Points.get(j)))
                {
                    Points.remove(j);
                }
            }
        }

    }
    
    public void scaleUpToSize(Point newSize)
    {
    	Vector2 scaleFactor = new Vector2 (
    									newSize.getX()/findSmallestNonZeroFactor().getX(),
    									newSize.getY()/findSmallestNonZeroFactor().getY()
    								);
    	
    	for (Point point:Points)
    	{
    		point.MultiplyBy(scaleFactor);
    	}
    }
    
    private Vector2 findSmallestNonZeroFactor()
    {   
    	float xVal= Points.get(0).getX();
    	for (Point point: Points)
    	{
    		if (point.getX()>xVal)
    			xVal=point.getX();    		
    	}
    	for (Point point: Points)
    	{
    		if ( (point.getX()>0) && (point.getX()<xVal) )
    				xVal = point.getX();
    	}
    	
    	float yVal= Points.get(0).getX();
    	for (Point point: Points)
    	{
    		if (point.getX()>yVal)
    			yVal=point.getY();    		
    	}
    	for (Point point: Points)
    	{
    		if ( (point.getY()>0) && (point.getY()<yVal))
    				yVal = point.getY();
    	}
    	
    	return new Vector2 (xVal, yVal);
    }

    public boolean Intersects(PointList path)
    {	
    	for (Point point:path.Points)
    	{
    		if (this.ContainsEquivalent(point))
    		{
    			return true;    			
    		}
    	}
    	
    	return false;
    }
    
}
