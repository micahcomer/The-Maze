package com.mjc.maze.animation;

import java.util.ArrayList;
import java.util.List;

import com.mjc.maze.basics.Point;
import com.mjc.maze.events.GameEvent;
import com.mjc.maze.events.GameListener;
import com.mjc.maze.events.MazeEventType;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;

public class Animation {

	Bitmap FullSheet;
	Point singleCellSize;
	Point totalCellsInSheet;
	boolean loops;
	public boolean Active;
	boolean persists;
	
	
	Rect sourceRect;
	Rect destRect;
	
	int framesPerSecond;
	float elapsedTime;
	float startTime;
	
	Point currentFrame;
	MazeEventType typeOfEventThisAnimationGeneratesOnCompletion;
	
	List<GameListener> listeners;
	
	public Animation (Bitmap fullSheet, Point totalCellsInSheet, boolean loops, boolean persists)
	{
		this.FullSheet = fullSheet;		
		this.totalCellsInSheet = totalCellsInSheet;
		
		
		singleCellSize = new Point();
		currentFrame = new Point(0,0);
		
		singleCellSize.setX(fullSheet.getWidth()/totalCellsInSheet.getX());
		singleCellSize.setY(fullSheet.getHeight()/totalCellsInSheet.getY());
		
		framesPerSecond = 60;		
		Active = false;
		this.persists = persists;
		
		sourceRect = new Rect (currentFrame.getX(), currentFrame.getY(),singleCellSize.getX(), singleCellSize.getY());
		destRect = new Rect (0,0,singleCellSize.getX(), singleCellSize.getY());
		
		listeners = new ArrayList<GameListener>();
	}
	
	public void setFPS(int fps)
	{
		framesPerSecond = fps;
	}
	
	public void setAnimationEventType(MazeEventType type)
	{
		typeOfEventThisAnimationGeneratesOnCompletion=type;
	}
	
	public void Start()
	{
		startTime = SystemClock.uptimeMillis();
		elapsedTime = 0;
		Active = true;
	}
	
	public void setOnScreenRect(Rect rect)
	{
		this.destRect = rect;
	}
	
	public void Move(Point movementAmount)
	{
		destRect.offset(movementAmount.getX(), movementAmount.getY());
	}
	
	public void MoveTo(Point newLocation)
	{
		destRect.offsetTo(newLocation.getX(), newLocation.getY());
	}
	
	public void Update()
	{		
		if (Active)
			{
			elapsedTime= SystemClock.uptimeMillis()-startTime;
		
			if (elapsedTime>(framesPerSecond/1000))
			{
				advanceFrame();
				elapsedTime=0;
				startTime=SystemClock.uptimeMillis();
			}
		}
	}
	
	public void Draw (Canvas canvas, Paint paint)
	{
		if (Active)
		canvas.drawBitmap(FullSheet, sourceRect, destRect, paint);
	}
	
	private void advanceFrame()
	{
		if (currentFrame.getY()<totalCellsInSheet.getY()-1)
		{
			if (currentFrame.getX()<totalCellsInSheet.getX()-1)			
				currentFrame.setX(currentFrame.getX()+1);
			else
			{
				currentFrame.setX(0);
				currentFrame.setY(currentFrame.getY()+1);
			}						
		}
		else
		{
			if (loops)
			{
				currentFrame.setX(0);
				currentFrame.setY(0);
			}
			else
			{
				if (!persists)
				Active = false;
			
			if (typeOfEventThisAnimationGeneratesOnCompletion !=null)
			AnimationCompleted();
			}
			
			
		}
		
		sourceRect.offsetTo(currentFrame.getX()*singleCellSize.getX(), currentFrame.getY()*singleCellSize.getY());
	}
	
	public void AnimationCompleted()
	{
	for (GameListener listener:listeners)
		listener.gameEventOccurred(new GameEvent(this, typeOfEventThisAnimationGeneratesOnCompletion));
	}
	
	public void AddListener(GameListener listener)
	{
		listeners.add(listener);		
	}
	
}
