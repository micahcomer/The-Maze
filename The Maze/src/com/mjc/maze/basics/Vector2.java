package com.mjc.maze.basics;

public class Vector2 {
    float x;
    float y;

    public Vector2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }


    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }

    public void setX(float x)
    {
        this.x = x;
    }
    public void setY(float y)
    {
        this.y = y;
    }
    
    public void MultiplyBy (float factor)
    {
    	x = x*factor;
    	y = y*factor;
    }
}
