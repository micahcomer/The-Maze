package com.mjc.maze.screens;

import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class GameScreen {

	public abstract void Update();
	public abstract void Draw(Canvas canvas);
	public abstract void onTouch(MotionEvent event);
	
}
