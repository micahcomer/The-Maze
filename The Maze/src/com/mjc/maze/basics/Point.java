package com.mjc.maze.basics;


import java.io.Serializable;

public class Point implements Serializable {

	private static final long serialVersionUID = 3041846802770790381L;
	int xCoordinate;
    int yCoordinate;
    boolean visible = false;
    DirectionType initialDirectionToGetToThisPoint;
    

    public DirectionType getInitialDirectionToGetToThisPoint() {
		return initialDirectionToGetToThisPoint;
	}

	public void setInitialDirectionToGetToThisPoint(
			DirectionType initialDirectionToGetToThisPoint) {
		this.initialDirectionToGetToThisPoint = initialDirectionToGetToThisPoint;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Point()
    {
        xCoordinate =0;
        yCoordinate=-0;
    }

    public Point(int x, int y)
    {
        xCoordinate =x;
        yCoordinate=y;
    }

    public int getX()
    {
        return xCoordinate;
    }
    
    public int getY()
    {
        return yCoordinate;
    }
    
    public void setX(int value)
    {
        xCoordinate = value;        
    }
    
    public void setY(int value)
    {
        yCoordinate = value;
    }
    
    public boolean ValuesAreEqualTo(Point testPoint)
    {
        return ((this.xCoordinate== testPoint.getX()) && (this.yCoordinate == testPoint.getY()));
    }
    
    public void MultiplyBy (float factor)
    {
    	xCoordinate  = (int)(xCoordinate*factor);
    	yCoordinate  = (int)(yCoordinate*factor);
    }

    public void MultiplyBy (Vector2 factor)
    {
    	xCoordinate  = (int)(xCoordinate*factor.getX());
    	yCoordinate  = (int)(yCoordinate*factor.getY());
    }
    
}
