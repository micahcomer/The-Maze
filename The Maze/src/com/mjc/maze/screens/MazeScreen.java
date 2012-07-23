package com.mjc.maze.screens;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mjc.maze.GameScreenManager;
import com.mjc.maze.R;
import com.mjc.maze.animation.Animation;
import com.mjc.maze.basics.Point;
import com.mjc.maze.basics.Vector2;
import com.mjc.maze.buttons.ButtonEvent;
import com.mjc.maze.buttons.ButtonListener;
import com.mjc.maze.events.MazeEventType;
import com.mjc.maze.mazestructure.Maze;
import com.mjc.maze.player.PlayerEvent;
import com.mjc.maze.player.PlayerEventType;
import com.mjc.maze.player.PlayerListener;
import com.mjc.maze.powerup.PowerUp;
import com.mjc.maze.powerup.PowerUpListener;
import com.mjc.maze.powerup.PowerUpType;
import com.mjc.maze.timer.MazeTimer;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.FloatMath;
import android.view.MotionEvent;



public class MazeScreen extends GameScreen implements Serializable, ButtonListener, PowerUpListener, PlayerListener{

	//region Properties
	
	private static final long serialVersionUID = 3883500963863601726L;

	//Global maze components:
	Bitmap background;
	Paint screenPaint;
	Point screenDimensions;
	Rect leftBorder;
	Rect rightBorder;
	
	//Stuff for zooming
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;
	float oldDist;
	float newDist;
	Vector2 startPointForFinger1;
	Vector2 startPointForFinger2;
	Vector2 endPointForFinger1;
	Vector2 endPointForFinger2;
	
	//The Maze Screen has 3 parts.  The maze, the buttons and the timer:
	//Buttons:
		
	//Maze:
	Maze maze;
	int currentLevel;
	
	Rect onScreenMazeBox;
	
	List<Animation>animations;
	Animation levelCompletedAnimation;
	
		
	//Timer:
	MazeTimer timer;
	
	//endregion Properties
	
	//region Constructor and methods called when creating the maze.
	
	public MazeScreen(Context context, Point screenDimensions)
	{		
		screenPaint=new Paint();
		currentLevel =0;		
		this.screenDimensions = screenDimensions;
		maze = new Maze();
		onScreenMazeBox = new Rect ((int)((screenDimensions.getX()/2) - (screenDimensions.getY()/2)), 0, (int)((screenDimensions.getX()/2) + (screenDimensions.getY()/2)), screenDimensions.getY());		
	}
	
	//CreateMaze calls...

	
	//GameScreenManager calls on a "Start Game" event...
	public void CreateMaze(Context context, GameScreenManager gsm, int currentLevel)
	{	
		this.currentLevel = currentLevel;
		maze.CreateMaze(context, currentLevel, onScreenMazeBox);
		leftBorder = new Rect (0,0,onScreenMazeBox.left, onScreenMazeBox.bottom);
		rightBorder = new Rect (onScreenMazeBox.right, 0, screenDimensions.getX(), onScreenMazeBox.bottom);
		timer = new MazeTimer(maze.getPathToFinishSize(), onScreenMazeBox, context);
		maze.powerUpManager.addListener(this);
		maze.getPlayer().addPlayerListener(this);
	
		animations = new ArrayList<Animation>();
		levelCompletedAnimation = new Animation(BitmapFactory.decodeResource(context.getResources(), R.drawable.animation_levelcompleted), new Point(1,5), false, true);
		levelCompletedAnimation.setFPS(20);
		animations.add(levelCompletedAnimation);
		levelCompletedAnimation.setOnScreenRect(new Rect(onScreenMazeBox.left+50, onScreenMazeBox.top +100, onScreenMazeBox.right-50, onScreenMazeBox.bottom-100));
		levelCompletedAnimation.setAnimationEventType(MazeEventType.onLevelCompleted);
		levelCompletedAnimation.AddListener(gsm);
	}
	
	//endregion
		
	//region Update and Draw
	
	@Override
	public void Update() {
		
		maze.Update();
		timer.Tick();
		for (Animation animation:animations)
			animation.Update();
		
	}

	@Override
	public void Draw(Canvas canvas) {
						
		maze.Draw(canvas);
		drawBorders(canvas);
		timer.Draw(canvas);
		
		for (Animation animation:animations)
			animation.Draw(canvas, screenPaint);
	}

	//endregion
	
	private void drawBorders(Canvas canvas)
	{
		canvas.drawRect(leftBorder, screenPaint);
		canvas.drawRect(rightBorder, screenPaint);
	}
	
	//region Event Handlers
	
	@Override
	public void onTouch(MotionEvent event) {
		
		
		switch (event.getAction() & MotionEvent.ACTION_MASK)
		{
		case MotionEvent.ACTION_DOWN:
		
			startPointForFinger1 = new Vector2(event.getX(), event.getY());
			
			if (onScreenMazeBox.contains((int)event.getX(),(int) event.getY()))
			maze.onTouch(event);
			
			
			break;
		
		case MotionEvent.ACTION_POINTER_DOWN:
			
			startPointForFinger2 = new Vector2(event.getX(1), event.getY(1));
			oldDist = spacing(event);			   
			   if (oldDist > 5f) 
			   {
			      savedMatrix.set(matrix);			      
			      mode = ZOOM;
			   }
		      break;
		
		case MotionEvent.ACTION_UP:
		
			//endPointForFinger1 = new Vector2(event.getX(0), event.getY(0));
			if (mode==ZOOM)
			{
				mode=NONE;
				oldDist = spacing (startPointForFinger1, startPointForFinger2);
				newDist = spacing (endPointForFinger1, endPointForFinger2);
				float scaleFactor = newDist/oldDist;
				PinchZoom(scaleFactor);
			}
			break;
		
		case MotionEvent.ACTION_POINTER_UP:
		
			endPointForFinger2 = new Vector2(event.getX(1), event.getY(1));
			endPointForFinger1 = new Vector2(event.getX(), event.getY());
			break;
		
		
		}
		
		
		
		
	}
	public void buttonEventOccurred(ButtonEvent evt) 
	{
		// TODO Auto-generated method stub		
	}
	
	//endregion
	
	//region Zooming Methods
	private void PinchZoom(float scaleFactor)
	{
		maze.Rescale(scaleFactor);
	}
	
	private float spacing(MotionEvent event) 
	{
			float x = event.getX(0) - event.getX(1);
			float y = event.getY(0) - event.getY(1);
			return FloatMath.sqrt(x * x + y * y);
	}
	
	private float spacing(Vector2 pointA, Vector2 pointB) 
	{
			float x = pointA.getX() - pointB.getX();
			float y = pointA.getY() - pointB.getY();
			return FloatMath.sqrt(x * x + y * y);
	}

	public void PowerUpEventOccurred(PowerUp source) {
		
		if (source.getType()==PowerUpType.TimeExtender)
		{
			ExtendTime();
		}
		
	}

	//endregion

	private void ExtendTime()
	{
		timer.ExtendTime(3);
	}

	public void onPlayerEvent(PlayerEvent playerEvent) {
		if (playerEvent.getType()==PlayerEventType.FinishedMaze)
			levelCompletedAnimation.Start();	
	}
	
}


