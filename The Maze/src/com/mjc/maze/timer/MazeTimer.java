package com.mjc.maze.timer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.os.SystemClock;

import com.mjc.maze.GameScreenManager;
import com.mjc.maze.R;
import com.mjc.maze.events.GameEvent;
import com.mjc.maze.events.GameListener;
import com.mjc.maze.events.MazeEventType;

public class MazeTimer implements Serializable, GameListener{


	private static final long serialVersionUID = 1382114519455987122L;
	transient Typeface TimerFont;
	float elapsedTime;
	float currentTimeInLevel;
	float startTime;
	
    GameScreenManager gsm;
    
    
    int levelTime;
    Rect onScreenMazeBox;
    int maxBarWidth;
    boolean enabled;
    public GameEvent TimerGoesOff;
    protected List<GameListener> listenerList;

    static transient Bitmap timerBar;
    public int getTimerBarWidth()
    {return timerBar.getWidth();}
    public int getTimerBarHeight()
    {return timerBar.getHeight();}

    transient Paint mazeTimerPaint;
    


    public MazeTimer(int LevelTimeInSeconds, Rect onScreenMazeBox, Context context, GameScreenManager gsm)
    {
        this.onScreenMazeBox = onScreenMazeBox;
        levelTime = LevelTimeInSeconds;     
        currentTimeInLevel = 0;
        elapsedTime =0;
        startTime = SystemClock.uptimeMillis();
        enabled = true;
        
        this.gsm=gsm;
        
        listenerList = new ArrayList<GameListener>();
        addMyEventListener(this);
        addMyEventListener(gsm);
        
        mazeTimerPaint = new Paint();        
        mazeTimerPaint.setTextSize(100);
        mazeTimerPaint.setStyle(Style.FILL);
        mazeTimerPaint.setColor(Color.WHITE);
        
        LoadContent(context);
        
        

    }

    void MazeTimer_TimerGoesOff()
    {
        enabled = false;
        gsm.gameEventOccurred(new GameEvent(this, MazeEventType.timerGoesOff));
    }


    public void Pause()
    {
        enabled = false;
    }


    public void LoadContent(Context context)
    {
        TimerFont = Typeface.createFromAsset(context.getResources().getAssets(), "dejavusans.ttf");
        timerBar = BitmapFactory.decodeResource(context.getResources(), R.drawable.timerbar);
        
    }

    public synchronized void Tick()
    {   	
    	
        if (enabled)
        {        	
        	
        	elapsedTime = SystemClock.uptimeMillis()-startTime;
            
        	if (elapsedTime>1000)
        	{
        		currentTimeInLevel++;
        		startTime = SystemClock.uptimeMillis();
        		elapsedTime=0;
        	}         
        	
        	if (levelTime-currentTimeInLevel<=10)
        		mazeTimerPaint.setColor(Color.RED);
        		else
        			mazeTimerPaint.setColor(Color.WHITE);
        	
        	if (levelTime-currentTimeInLevel<=0)
        		MazeTimer_TimerGoesOff();
        }
    }
    public void ExtendTime(int timeInSecondsToExtend)
    {
    	currentTimeInLevel -= (timeInSecondsToExtend);
    }
    public void Draw (Canvas canvas)
    {  	
    	
        if (enabled)
        
            canvas.drawText(String.valueOf((int)(levelTime-currentTimeInLevel)), 
                    onScreenMazeBox.right,
                    100,
                    mazeTimerPaint
            );
        
    }

    public void gameEventOccurred(GameEvent evt)
    {
        //if (evt.getType() == GameEventType.timerGoesOff)
           // MazeTimer_TimerGoesOff();
    }

    public void addMyEventListener(GameListener listener)
    {
        listenerList.add(listener);
    }

    public void removeMyEventListener(GameListener listener)
    {
        listenerList.remove(listener);
    }



    void fireGameEvent(GameEvent evt)

    {
        for (GameListener listener:listenerList)
        {
            listener.gameEventOccurred(evt);
        }
    }

}
