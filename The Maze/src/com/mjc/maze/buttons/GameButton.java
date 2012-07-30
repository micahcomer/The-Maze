package com.mjc.maze.buttons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mjc.maze.basics.DirectionType;
import com.mjc.maze.basics.Point;
import com.mjc.maze.mazestructure.MazeTileType;
import com.mjc.maze.player.PlayerEvent;
import com.mjc.maze.player.PlayerEventType;
import com.mjc.maze.player.PlayerListener;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


public class GameButton implements Serializable, PlayerListener
{
	private static final long serialVersionUID = -8587825700494718698L;

	transient public Bitmap activePicture;
    transient public Bitmap inactivePicture;
    transient private Rect onScreenRectangle;
    transient private Paint buttonPaint;
    public boolean isActive = true;
    private ButtonEventType Type;

    List<ButtonListener> listenerList;
    public Rect getOnScreenRectangle()    {
        return onScreenRectangle;
    }
    public void setOnScreenRectangle(Rect rectangle)    {
        this.onScreenRectangle = rectangle;
    }

    public GameButton(ButtonEventType Type) {

        this.Type = Type;
        listenerList = new ArrayList<ButtonListener>();
        buttonPaint = new Paint();
        buttonPaint.setAntiAlias(true);
    }
    
    public void setPictures(Bitmap activePicture, Bitmap inactivePicture)
    {
        this.activePicture = activePicture;
        this.inactivePicture = inactivePicture;
    }
   
    public Canvas DrawButton(Canvas canvas)
    {
        if (isActive)
            canvas.drawBitmap(activePicture, new Rect (0,0,activePicture.getWidth(), activePicture.getHeight()), onScreenRectangle, buttonPaint);
        else
            canvas.drawBitmap(inactivePicture, new Rect (0,0,inactivePicture.getWidth(), activePicture.getHeight()), onScreenRectangle, buttonPaint);
        return canvas;
    }

    public void addMyEventListener(ButtonListener listener)
    {
        listenerList.add(listener);
    }

    public void Process()
    {
        

        switch (this.Type)
        {
        
        case StartGame:
        {
        	fireButtonEvent(new ButtonEvent(this, ButtonEventType.StartGame));
        	return;
        }
        
        case ContinueGame:
        {
        	fireButtonEvent(new ButtonEvent(this, ButtonEventType.ContinueGame));
        	return;
        }
        case ExitGame:
        {
        	fireButtonEvent(new ButtonEvent(this, ButtonEventType.ExitGame));
        	return;
        }
        
        
            case MoveUp:
            {
            	isActive = false;
                //SoundManager.getInstance().playSound(1, 1, 0, 1);
                fireButtonEvent (new ButtonEvent(this, ButtonEventType.MoveUp));
                return;
            }

            case MoveDown:
            {
            	isActive = false;
                //SoundManager.getInstance().playSound(1, 1, 0, 1);
                fireButtonEvent (new ButtonEvent(this, ButtonEventType.MoveDown));
                return;
            }

            case MoveLeft:
            {
            	isActive = false;
                //SoundManager.getInstance().playSound(1, 1, 0, 1);
                fireButtonEvent (new ButtonEvent(this, ButtonEventType.MoveLeft));
                return;
            }

            case MoveRight:
            {
            	isActive = false;
                //SoundManager.getInstance().playSound(1, 1, 0, 1);
                fireButtonEvent (new ButtonEvent(this, ButtonEventType.MoveRight));
                return;
            }
            
            case ToggleMusic:
            {            	
                fireButtonEvent (new ButtonEvent(this, ButtonEventType.ToggleMusic));
                this.isActive = !this.isActive;
                return;
            }
            case ToggleSoundFX:
            {
                fireButtonEvent (new ButtonEvent(this, ButtonEventType.ToggleSoundFX));
                this.isActive = !this.isActive;
                return;
            }
		default:
			break;
           
        }


    }

    void fireButtonEvent(ButtonEvent evt)

    {
        for (ButtonListener listener:listenerList)
        {
            listener.buttonEventOccurred(evt);
        }
    }
   
    public boolean doesPointTouchButton(Point point)
    {
    	if( 
    	(point.getX()>=onScreenRectangle.left) &&
    	(point.getX()<=onScreenRectangle.right) &&
    	(point.getY()>=onScreenRectangle.top) &&
    	(point.getY()<=onScreenRectangle.bottom)
    	)    	
    		return true;
    	else
    		return false;
    	
    }
	public void onPlayerEvent(PlayerEvent playerEvent) {
		if (playerEvent.getType() == PlayerEventType.StoppedMoving)
		{
			
			switch (Type)
			{
				case MoveUp:
				{
					
					if (doesMazeTileTypeContainDirection(playerEvent.getOwner().getCurrentPossibleDirections(), DirectionType.Up))
						this.isActive = true;
					break;
				}
				case MoveDown:
				{
					if (doesMazeTileTypeContainDirection(playerEvent.getOwner().getCurrentPossibleDirections(), DirectionType.Down))
						this.isActive = true;
					break;
				}
				case MoveLeft:
				{
					if (doesMazeTileTypeContainDirection(playerEvent.getOwner().getCurrentPossibleDirections(), DirectionType.Left))
						this.isActive = true;
					break;
				}
				case MoveRight:
				{
					if (doesMazeTileTypeContainDirection(playerEvent.getOwner().getCurrentPossibleDirections(), DirectionType.Right))
						this.isActive = true;
					break;
				}				
				default:
				{
					
				}
						
			}
		}
		
	}
	
	private boolean doesMazeTileTypeContainDirection (MazeTileType directions, DirectionType direction)
	{
		boolean value = false;
		
		switch (direction)
		{
			case Up:
			{
				if (
						(directions==MazeTileType.Up)||
						(directions == MazeTileType.UpLeft)||
						(directions==MazeTileType.UpRight)||
						(directions == MazeTileType.UpDown)||
						(directions==MazeTileType.UpLeftRight)||
						(directions == MazeTileType.UpDownLeft)||
						(directions==MazeTileType.UpDownRight)||
						(directions == MazeTileType.UpDownLeftRight))
					value = true;						
				break;
			}
			case Down:
			{
				if (
						(directions==MazeTileType.Down)||
						(directions == MazeTileType.DownLeft)||
						(directions==MazeTileType.DownRight)||
						(directions == MazeTileType.UpDown)||
						(directions==MazeTileType.DownLeftRight)||
						(directions == MazeTileType.UpDownLeft)||
						(directions==MazeTileType.UpDownRight)||
						(directions == MazeTileType.UpDownLeftRight))
					value = true;						
				break;
			}
			case Left:
			{
				if (
						(directions==MazeTileType.Left)||
						(directions == MazeTileType.DownLeft)||
						(directions==MazeTileType.LeftRight)||
						(directions == MazeTileType.UpLeft)||
						(directions==MazeTileType.DownLeftRight)||
						(directions == MazeTileType.UpDownLeft)||
						(directions==MazeTileType.UpLeftRight)||
						(directions == MazeTileType.UpDownLeftRight))
					value = true;	
				break;
			}
			case Right:
			{
				if (
						(directions==MazeTileType.Right)||
						(directions == MazeTileType.LeftRight)||
						(directions==MazeTileType.DownRight)||
						(directions == MazeTileType.UpRight)||
						(directions==MazeTileType.DownLeftRight)||
						(directions == MazeTileType.UpLeftRight)||
						(directions==MazeTileType.UpDownRight)||
						(directions == MazeTileType.UpDownLeftRight))
					value = true;	
				break;
			}
		}
		
		return value;
	}

}

