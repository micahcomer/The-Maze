package com.mjc.maze.screens;

import java.util.ArrayList;
import java.util.List;

import com.mjc.maze.GameScreenManager;
import com.mjc.maze.R;
import com.mjc.maze.basics.Point;
import com.mjc.maze.buttons.ButtonEventType;
import com.mjc.maze.buttons.ButtonListener;
import com.mjc.maze.buttons.GameButton;
import com.mjc.maze.sounds.SoundManager;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class StartScreen extends GameScreen {

	Bitmap background;
	
	Paint screenPaint;
	Resources resources;
	Point fullScreenSize;
		
	//List of Buttons
	public static GameButton StartGameButton;
	public static GameButton ContinueGameButton;
	public static GameButton ExitGameButton;
	public static GameButton SoundFXToggleButton;
	public static GameButton MusicToggleButton;
	
	public static List<GameButton> Buttons;
	boolean gameInProgress;
	
	static WarningScreen WarningScreen;
	public boolean showWarningScreen;
	
	
	public StartScreen(Context context, Point fullScreenSize, GameScreenManager screenManager, boolean gameInProgress, int level)
	{
		resources = context.getResources();
		screenPaint = new Paint();	
		screenPaint.setAntiAlias(true);
		background = BitmapFactory.decodeResource(resources, R.drawable.startscreen_background);
		
		this.fullScreenSize = fullScreenSize;
		this.gameInProgress = gameInProgress;
		
		
		CreateButtons(screenManager);
		
		SoundManager.getInstance().initSounds(context, 4);
        SoundManager.getInstance().loadSound(context, 1 ,R.raw.click);
        
        WarningScreen = new WarningScreen(context, fullScreenSize, level);
        WarningScreen.addListener(screenManager);
        showWarningScreen =false;
	}
		
	private void CreateButtons(GameScreenManager screenManager)
	{
		int buttonLeftEdge = (int)(fullScreenSize.getX()*.685f);
		int buttonRightEdge = (int)(buttonLeftEdge + fullScreenSize.getX()*.33f);
		int buttonWidth = buttonRightEdge-buttonLeftEdge;
		int buttonHeight = (int)(buttonWidth/3.8f);
		int buttonTop = (int)(fullScreenSize.getY()*.15);
		int buttonSpacing = (int)((fullScreenSize.getY()*.65)-(buttonHeight*4));
		
		StartGameButton = new GameButton(ButtonEventType.StartGame);
		ContinueGameButton = new GameButton(ButtonEventType.ContinueGame);		
		ExitGameButton = new GameButton(ButtonEventType.ExitGame);
		SoundFXToggleButton = new GameButton(ButtonEventType.ToggleSoundFX);
		MusicToggleButton = new GameButton(ButtonEventType.ToggleMusic );
		
		StartGameButton.setOnScreenRectangle(new Rect (buttonLeftEdge, buttonTop, buttonRightEdge, buttonTop+buttonHeight));
		ContinueGameButton.setOnScreenRectangle(new Rect (buttonLeftEdge, buttonTop + buttonHeight + buttonSpacing, buttonRightEdge, buttonTop + (buttonHeight*2) + buttonSpacing));
		ExitGameButton.setOnScreenRectangle(new Rect (buttonLeftEdge, buttonTop + (buttonHeight*2) + (buttonSpacing*2), buttonRightEdge,buttonTop + (buttonHeight*3) + (buttonSpacing*2))); 
		SoundFXToggleButton.setOnScreenRectangle(new Rect(buttonLeftEdge+(int)(buttonHeight/2), buttonTop+((int)(buttonHeight*3.25))+(buttonSpacing*2), buttonLeftEdge+((int)(buttonHeight*1.5)), buttonTop+((int)(buttonHeight*4.25))+(buttonSpacing*2)));
		MusicToggleButton.setOnScreenRectangle(new Rect(buttonRightEdge-(int)(buttonHeight*1.5), buttonTop+((int)(buttonHeight*3.25))+(buttonSpacing*2), buttonRightEdge-(int)(buttonHeight/2), buttonTop+((int)(buttonHeight*4.25))+(buttonSpacing*2)));  
		
		StartGameButton.setPictures(BitmapFactory.decodeResource(resources, R.drawable.button_new), BitmapFactory.decodeResource(resources, R.drawable.button_new));
		StartGameButton.isActive=true;		
		
		ContinueGameButton.setPictures(BitmapFactory.decodeResource(resources, R.drawable.button_continue), BitmapFactory.decodeResource(resources, R.drawable.button_continue_inactive));
		if (gameInProgress)
		ContinueGameButton.isActive = true;
		else
		ContinueGameButton.isActive=false;
				
		ExitGameButton.setPictures(BitmapFactory.decodeResource(resources, R.drawable.button_exit), BitmapFactory.decodeResource(resources, R.drawable.button_exit));
		ExitGameButton.isActive =true;
		
		SoundFXToggleButton.setPictures(BitmapFactory.decodeResource(resources, R.drawable.soundfxbutton_active), BitmapFactory.decodeResource(resources, R.drawable.soundfxbutton_inactive));
		SoundFXToggleButton.isActive=true;
		
		MusicToggleButton.setPictures(BitmapFactory.decodeResource(resources, R.drawable.musicbutton_active), BitmapFactory.decodeResource(resources, R.drawable.musicbutton_inactive));
		MusicToggleButton.isActive = true;
		
		StartGameButton.addMyEventListener(screenManager);		
		ContinueGameButton.addMyEventListener(screenManager);
		ExitGameButton.addMyEventListener(screenManager);
		SoundFXToggleButton.addMyEventListener(screenManager);
		MusicToggleButton.addMyEventListener(screenManager);
		
		Buttons = new ArrayList<GameButton>();
		Buttons.add(StartGameButton);
		Buttons.add(ContinueGameButton);
		Buttons.add(ExitGameButton);
		Buttons.add(SoundFXToggleButton);
		Buttons.add(MusicToggleButton);
		
	}
	
	
	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Draw(Canvas canvas) {
		
		
		canvas.drawBitmap(
				background,
				new Rect (0,0,background.getWidth(), background.getHeight()),
				new Rect (0,0,fullScreenSize.getX(), fullScreenSize.getY()),
				screenPaint);		
		
		for(GameButton button:Buttons)
			button.DrawButton(canvas);
		
		if (showWarningScreen)
			WarningScreen.Draw(canvas);		
		
	}

	
	
	@Override
	public void onTouch(MotionEvent event) {
		
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if (showWarningScreen)
			{
				WarningScreen.onTouch(event);
			}
		}
		else
			
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
