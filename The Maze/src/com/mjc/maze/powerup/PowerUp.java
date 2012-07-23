package com.mjc.maze.powerup;

import com.mjc.maze.R;
import com.mjc.maze.basics.BitmapResizer;
import com.mjc.maze.basics.Point;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class PowerUp {

	Bitmap Picture;
	PowerUpManager manager;
	
	boolean visible;
	Rect onScreenBox;
	Point origin;
	Rect sourceRect;
	Rect destRect;
	Point Position;
	
	PowerUpType type;
	public PowerUpType getType()
	{
		return type;
	}
	
	
	
	public boolean isVisible() {
		return visible;
	}

	

	public Point getPosition() {
		return Position;
	}

	public void setPosition(Point position) {
		Position = position;
		destRect.offsetTo(position.getX(), position.getY());
	}

	public PowerUp(PowerUpType type, PowerUpManager manager, int tileSize, Context context, Rect onScreenBox)
	{
		this.manager = manager;
		this.onScreenBox = onScreenBox;
		
		this.origin = new Point();
		
		
		switch (type)
		{
			case Teleporter:
				newTeleporter();
				break;
			case TimeExtender:
				newTimeExtender();
				break;
			case ScoreMultiplier:
				newScoreMultiplier();
				break;
		}
		
		BitmapResizer resizer = new BitmapResizer(context);
		Picture = resizer.getResizedBitmap(Picture, tileSize, tileSize);sourceRect = new Rect();
		destRect = new Rect(0,0,Picture.getWidth(), Picture.getHeight());
		resetSourceRect();
		visible = true;
	}
	
	private void newTeleporter()
	{
		Picture = BitmapFactory.decodeResource(manager.getResources(), R.drawable.powerup_teleporter);
		this.type = PowerUpType.Teleporter;
	}
	private void newTimeExtender()
	{
		Picture = BitmapFactory.decodeResource(manager.getResources(), R.drawable.powerup_timeextender);
		this.type = PowerUpType.TimeExtender;
	}
	private void newScoreMultiplier()
	{
		Picture = BitmapFactory.decodeResource(manager.getResources(), R.drawable.powerup_multiplier);
		this.type = PowerUpType.ScoreMultiplier;
	}
	
	private void resetSourceRect()
	{
		sourceRect.set(0,0,Picture.getWidth(), Picture.getHeight());
	}
	
	public void Update (int originLeft, int originTop, RectF playerBox)
	{
		destRect.offsetTo(originLeft+Position.getX(), originTop+Position.getY());
		updateVisibility();
		
		if (destRect.contains((int)playerBox.left, (int)playerBox.top, (int)playerBox.right, (int)playerBox.bottom))
			manager.firePowerUpEvent(this);
		
	}
	
	public void Draw(Canvas canvas, Paint paint)
	{
		canvas.drawBitmap(Picture, sourceRect, destRect, paint);
	}
	
	public void Move(Point movementAmount)
	{
		
	}

	private void updateVisibility()
	{				
			if
			(	
				(destRect.right>=onScreenBox.left) &&
				(destRect.left<=onScreenBox.right)
			)
				visible = true;			
			else
			{
				visible = false;
				resetSourceRect();
			}
	}
}
