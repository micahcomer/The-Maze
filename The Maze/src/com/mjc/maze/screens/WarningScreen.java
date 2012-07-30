package com.mjc.maze.screens;

import java.util.ArrayList;
import java.util.List;

import com.mjc.maze.R;
import com.mjc.maze.basics.Point;
import com.mjc.maze.buttons.ButtonEventType;
import com.mjc.maze.buttons.ButtonListener;
import com.mjc.maze.buttons.GameButton;
import com.mjc.maze.events.GameListener;
import com.mjc.maze.sounds.SoundManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;


public class WarningScreen extends GameScreen {

	Context context;
	Point screenDimensions;
	Paint paint;
	
	Bitmap background;
	Rect sourceRect;
	Rect onScreenRect;
	
	GameButton yesButton;
	GameButton noButton;
	
	String warningText1;
	String warningText2;
	String warningText3;
	String warningText4;
	int margin;
	int textFontSize;
	
	int text1Width;
	int text2Width;
	int text3Width;
	int text4Width;
	
	int textHeight;
	
	
	List<GameButton>Buttons;
	
	
	public WarningScreen(Context context, Point screenDimensions, int level)
	{
		this.context = context;
		this.screenDimensions = screenDimensions;
	
		background = BitmapFactory.decodeResource(context.getResources(), R.drawable.warning_screen_background);
		sourceRect = new Rect(0,0,background.getWidth(), background.getHeight());		
		onScreenRect = new Rect (25, 25, screenDimensions.getX()-25, screenDimensions.getY()-25);
		
		paint = new Paint();		
		
		CreateButtons();
		
		
		CreateWarningText(level);
		
	}
	
	private void CreateButtons()
	{
		yesButton = new GameButton(ButtonEventType.Yes);
		noButton = new GameButton(ButtonEventType.No);
		
		int buttonWidth = 250;
		int buttonHeight = 100;
		margin = 50;
		
		int yesLeft = onScreenRect.left+margin;
		int yesRight = yesLeft + buttonWidth;
		int noRight = onScreenRect.right-margin;
		int noLeft = noRight-buttonWidth;
				
		int bottom = onScreenRect.bottom - margin;
		int top = bottom-buttonHeight;
		
		yesButton.setOnScreenRectangle(yesLeft, top, yesRight, bottom);
		noButton.setOnScreenRectangle(noLeft, top, noRight, bottom);
		
		Bitmap yesPic = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_yes);
		Bitmap noPic = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_no);
		
		yesButton.setPictures(yesPic, yesPic);
		noButton.setPictures(noPic, noPic);
		
		Buttons = new ArrayList<GameButton>();
		Buttons.add(yesButton);
		Buttons.add(noButton);
	}
	
	private void CreateWarningText(int level)
	{
		paint.setFakeBoldText(true);
		paint.setAntiAlias(true);
		
		warningText1 = "Warning! You already have a game in progress,";		
		warningText2 = "and you have made it to level " + String.valueOf(level) + " .";		
		warningText3 = "If you start a new game, all of your progress will be erased.";
		warningText4 = "Do you really want to start a new game?";
		
		paint.setTextSize(findLargestPaintSize());
		
		text1Width = setTextWidth(warningText1);
		text2Width = setTextWidth(warningText2);
		text3Width = setTextWidth(warningText3);
		text4Width = setTextWidth(warningText4);
		
		textHeight = (int)paint.getTextSize();
		
	}
	
	private int findLargestPaintSize()
	{
		int p1;
		paint.setTextSize(1);
		while (paint.measureText(warningText1)<onScreenRect.width()-(margin*2))
			paint.setTextSize(paint.getTextSize()+1);
		p1 = (int) paint.getTextSize();
		
		int p2;
		paint.setTextSize(1);
		while (paint.measureText(warningText2)<onScreenRect.width()-(margin*2))
			paint.setTextSize(paint.getTextSize()+1);
		p2 = (int) paint.getTextSize();
		
		int p3;
		paint.setTextSize(1);
		while (paint.measureText(warningText3)<onScreenRect.width()-(margin*2))
			paint.setTextSize(paint.getTextSize()+1);
		p3 = (int) paint.getTextSize();
		
		int p4;
		paint.setTextSize(1);
		while (paint.measureText(warningText4)<onScreenRect.width()-(margin*2))
			paint.setTextSize(paint.getTextSize()+1);
		p4 = (int) paint.getTextSize();
		
		return pickSmallest(p1, p2, p3, p4);
	}
	
	private int pickSmallest(int p1, int p2, int p3, int p4)
	{
		int a = pickSmallest(p1, p2);
		int b = pickSmallest(a, p3);
		int c = pickSmallest (b, p4);
		
		return c;
	}
	
	private int pickSmallest(int x, int y)
	{
		if (x<y)
			return x;
		else
			return y;
	}
	
	private int setTextWidth(String string)
	{
		return (int)paint.measureText(string);
	}
	
	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Draw(Canvas canvas) {
		canvas.drawBitmap(background, sourceRect, onScreenRect, paint);	
		
		canvas.drawText(warningText1, onScreenRect.centerX() - text1Width/2, onScreenRect.top + margin, paint);		
		canvas.drawText(warningText2, onScreenRect.centerX() - text2Width/2, onScreenRect.top + textHeight + margin + textHeight, paint);
		canvas.drawText(warningText3, onScreenRect.centerX() - text3Width/2, onScreenRect.top + textHeight + margin + textHeight + margin, paint);
		canvas.drawText(warningText4, onScreenRect.centerX() - text4Width/2, onScreenRect.top + textHeight + margin + textHeight + margin + textHeight+margin, paint);
		
		for (GameButton button:Buttons)
			button.DrawButton(canvas);
	}

	public void addListener(ButtonListener listener)
	{
		yesButton.addMyEventListener(listener);
		noButton.addMyEventListener(listener);
	}
	
	public void removeListener(ButtonListener listener)
	{
		yesButton.removeMyEventListener(listener);
		noButton.removeMyEventListener(listener);
	}
	
	
	

	@Override
	public void onTouch(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
		for (GameButton button:Buttons)
		{
			if ((button.doesPointTouchButton(new Point ((int)event.getX(), (int)event.getY()))  && (button.isActive)))
			{	
				button.Process();
				if (SoundManager.getInstance().isActive())
				SoundManager.getInstance().playSound(1, 1, 0, 1);
				
			}
		}
		}
	}

}
