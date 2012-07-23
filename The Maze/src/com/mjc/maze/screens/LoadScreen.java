package com.mjc.maze.screens;

import com.mjc.maze.R;
import com.mjc.maze.basics.Point;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class LoadScreen extends GameScreen {

	Bitmap background;
	Paint screenPaint;
	Point fullScreenSize;
	int level;
	
	public LoadScreen(Context context, Point deviceFullScreenSize)
	{		
		screenPaint = new Paint();
		screenPaint.setTextSize(60);
		screenPaint.setAntiAlias(true);
		background = BitmapFactory.decodeResource(context.getResources(), R.drawable.loadingbackground);
		fullScreenSize = deviceFullScreenSize;
	}
	
	public void setLevel(int level)
	{
		this.level = level;
	}
	@Override
	public void Update() {
				
	}

	@Override
	public void Draw(Canvas canvas) {
		canvas.drawBitmap(
				background, 
				new Rect(0,0,background.getWidth(), background.getHeight()),
				new Rect(0,0,fullScreenSize.getX(), fullScreenSize.getY()),
				screenPaint);
		
		int dropShadowOffset = -2;
		Rect textBounds = new Rect ();
		screenPaint.getTextBounds("Loading Level "+String.valueOf(level), 0, 10, textBounds);
		screenPaint.setColor(Color.BLACK);
		canvas.drawText("Loading Level "+String.valueOf(level),(int)( (fullScreenSize.getX()/2) - (textBounds.width()/2) ) ,(int)((fullScreenSize.getY()/2)-(textBounds.height()/2)), screenPaint);
		screenPaint.setColor(Color.DKGRAY);
		canvas.drawText("Loading Level "+String.valueOf(level),(int)( (fullScreenSize.getX()/2) - (textBounds.width()/2) )+ dropShadowOffset,(int)((fullScreenSize.getY()/2)-(textBounds.height()/2)+dropShadowOffset), screenPaint);
		
		
	}

	@Override
	public void onTouch(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
