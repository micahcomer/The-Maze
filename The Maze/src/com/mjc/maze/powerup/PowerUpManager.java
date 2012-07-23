package com.mjc.maze.powerup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mjc.maze.basics.Point;
import com.mjc.maze.basics.PointList;
import com.mjc.maze.basics.Vector2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class PowerUpManager {

	Context context;
	List<PowerUp>PowerUps;
	Paint powerUpPaint;
	
	List<PowerUpListener> listeners;
	int indexToRemove;
	
	public Resources getResources() {
		return context.getResources();		
	}

	public PowerUpManager (Context context)
	{		
		this.context = context;
		PowerUps = new ArrayList<PowerUp>();
		powerUpPaint = new Paint();
		listeners = new ArrayList<PowerUpListener>();
	}
	
	public void addListener(PowerUpListener listener)
	{
		listeners.add(listener);
	}
		
	public void CreateNewPowerUpSet(int numberOfPowerUps, PointList nodes, int tileSize, Rect onScreenMazeBox)
	{
		List<PowerUpType> typeList = new ArrayList<PowerUpType>();
		typeList.add(PowerUpType.ScoreMultiplier);
		typeList.add(PowerUpType.Teleporter);
		typeList.add(PowerUpType.TimeExtender);
		
		Random random = new Random();
		
		for (int i =0; i<numberOfPowerUps; i++)
			PowerUps.add(new PowerUp(typeList.get(random.nextInt(typeList.size())), this, tileSize, context, onScreenMazeBox));
		
		AssignLocations(random, nodes);
		
	}
	
	public void Update(Vector2 PlayerPosition, int mazeOriginLeft, int mazeOriginTop, RectF playerBox)
	{
		indexToRemove=-1;
		
		for (PowerUp powerUp:PowerUps)
			powerUp.Update(mazeOriginLeft, mazeOriginTop, playerBox);
		
		if (indexToRemove>=0)
			PowerUps.remove(indexToRemove);
	}
	
	public void Draw (Canvas canvas)
	{
		for (PowerUp powerUp: PowerUps)
		{
			if (powerUp.isVisible())
			powerUp.Draw(canvas, powerUpPaint);
		}
	}

	public void MovePowerUps(Point movementAmount)
	{
		for (PowerUp powerUp:PowerUps)
		{
			powerUp.Move(movementAmount);
		}
	}

	private void AssignLocations(Random random, PointList nodes)
	{
		PointList powerUpLocations = new PointList();
		Point randomLocation = getRandomLocation(random, nodes);
		
		for (int i=0; i<PowerUps.size(); i++)
		{
			while (powerUpLocations.ContainsEquivalent(randomLocation))
			{
				randomLocation = getRandomLocation(random, nodes);
			}
			
			PowerUps.get(i).setPosition(randomLocation);
			powerUpLocations.Points.add(randomLocation);
		}
		
	}
	
	private Point getRandomLocation(Random random, PointList nodes)
	{
		return nodes.Points.get(random.nextInt(nodes.Points.size()));
	}

	
	public void firePowerUpEvent(PowerUp powerUp)
	{
		for (PowerUpListener listener:listeners)
			listener.PowerUpEventOccurred(powerUp);
		
		indexToRemove = PowerUps.indexOf(powerUp);
	}
	
	
}
