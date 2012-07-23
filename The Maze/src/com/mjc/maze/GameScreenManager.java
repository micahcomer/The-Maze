package com.mjc.maze;

import java.io.Serializable;

import android.graphics.Canvas;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.mjc.maze.basics.Point;
import com.mjc.maze.buttons.ButtonEvent;
import com.mjc.maze.buttons.ButtonEventType;
import com.mjc.maze.buttons.ButtonListener;
import com.mjc.maze.events.GameEvent;
import com.mjc.maze.events.GameListener;
import com.mjc.maze.events.MazeEventType;
import com.mjc.maze.screens.*;
import com.mjc.maze.sounds.SoundManager;

public class GameScreenManager extends SurfaceView implements Serializable, SurfaceHolder.Callback, View.OnTouchListener, ButtonListener, GameListener{

		
	/**
	 * 
	 */
	private static final long serialVersionUID = 3210086315272724718L;
	
	//used to keep track of time between updates and amount of time to sleep for
    long lastUpdate = 0;
    long sleepTime=0;
    
    
  //objects which house info about the screen
    SurfaceHolder surfaceHolder;
    TheMazeActivity context;    
    
  //our Thread class which houses the game loop
    private PaintThread thread;
    
    //Screens
    private static GameScreen CurrentScreen;
    private static StartScreen StartScreen;
    private static LoadScreen LoadScreen;
    private static MazeScreen MazeScreen;
    
    final GameScreenManager gsm;
    
    //State of screenManager
    boolean currentlyLoading;

    int currentLevel = 1;
    
    //class constructor
	public GameScreenManager(TheMazeActivity context, Point deviceScreenDimensions) {
		super(context);
		
		this.context = context;
		
		
		
		StartScreen = new StartScreen(context, deviceScreenDimensions, this);		
        LoadScreen = new LoadScreen(context, deviceScreenDimensions);
        MazeScreen = new MazeScreen(context, deviceScreenDimensions);
        
        currentlyLoading = false;
        
        setOnTouchListener(this);
        
		InitView();
		setInitScreen();
		
		gsm = this;
	}

    
    //initialization code
    private void InitView(){
      //initialize our screen holder
      SurfaceHolder holder = getHolder();
      holder.addCallback( this);	
      
    //initialize our Thread class. A call will be made to start it later
      thread = new PaintThread(holder, context, new Handler());
      setFocusable(true);      
      
      thread.setScreenManager(this);
    }
	private void setInitScreen()
	{
		CurrentScreen=StartScreen;
	}
	
		
	//Update and Draw
	public void Update()
	{
		if (CurrentScreen!=null)
		CurrentScreen.Update();
	}
	
	public void Draw(Canvas canvas)
	{
		if (CurrentScreen!=null)
		CurrentScreen.Draw(canvas);
	}
		

	//region Event Handlers
	
	//Surface Interface Methods
	 //@Override 
	 public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {}

	 
	 public void surfaceCreated(SurfaceHolder arg0) {
	        if(thread.state==PaintThread.PAUSED){
	            //When game is opened again in the Android OS
	            thread = new PaintThread(getHolder(), context, new Handler());
	            thread.start();
	        }else{
	            //creating the game Thread for the first time
	            thread.start();
	        }
	    }

	 public void surfaceDestroyed(SurfaceHolder arg0) {
	        boolean retry = true;
	        //code to end gameloop
	        thread.state=PaintThread.PAUSED;
	        while (retry) {
	            try {
	                //code to kill Thread
	                thread.join();
	                retry = false;
	            } catch (InterruptedException e) {
	            }
	        }

	    }
	 

	 //OnTouch Interface Methods
	public boolean onTouch(View v, MotionEvent event) {

		CurrentScreen.onTouch(event);		
		return true;
	}


	public void buttonEventOccurred(ButtonEvent evt) {
		if (evt.getType()==ButtonEventType.StartGame)
		{
			 if (!currentlyLoading)
		        {
		            currentlyLoading = true;
		            new Thread( new Runnable() {
		                //@Override
		                public void run() {
		                    try
		                    {
		                        MazeScreen.CreateMaze(context, gsm, currentLevel);		                        
		                        CurrentScreen = MazeScreen;
		                        currentlyLoading=false;
		                    }
		                    catch (Exception e)
		                    {
		                        e.printStackTrace();
		                    }
		                }
		            }).start();		            
		        }
			 LoadScreen.setLevel(currentLevel);
			CurrentScreen = LoadScreen;		
		}
		else
			if (evt.getType()==ButtonEventType.ExitGame)
			{
				context.finish();
			}
			else
				if (evt.getType()==ButtonEventType.ToggleSoundFX)
					SoundManager.getInstance().setActive(!SoundManager.getInstance().isActive());
	}


	public void gameEventOccurred(GameEvent evt) {
		
		if (evt.getType()==MazeEventType.onLevelCompleted)
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			buttonEventOccurred(new ButtonEvent(this, ButtonEventType.StartGame));
		}
		
		
	}

	
	//endregion
	
	
}



