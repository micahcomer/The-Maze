package com.mjc.maze.basics;

import android.graphics.Rect;
import android.graphics.RectF;

public class GameRect extends RectF{

	static RectF rectF;
	static Rect rect;
	
	public float bottom;
	public float left;
	public float top;
	public float right;
	
	public GameRect(float i, float j, float width, float height) {
		rectF = new RectF();
		left = i;
		top = j;
		right = i + width;
		bottom = j + height;
		
		rect = new Rect((int)i,(int)j,(int)width,(int)height);
	}
	
	
	public void Move (float x, float y)
	{
		left +=x;
		right+=x;
		top+=y;
		bottom+=y;
	}
	
	public void MoveTo(float x, float y)
	{
		float width = rectF.width();
		float height = rectF.height();
		
		rectF.left = x;
		rectF.right = left+width;
		rectF.top = y;
		rectF.bottom = top+height;
		
		rect.left = (int)x;
		rect.right = (int)(rect.left + width);
		rect.top = (int)y;
		rect.left = (int)(top+height);
	}
	
	public RectF ConvertToRectF()
	{
		return rectF;
	}
	public Rect ConvertToRect()
	{
		return rect;
	}
}
