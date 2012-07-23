package com.mjc.maze.events;


import java.io.Serializable;
import java.util.EventObject;

public class GameEvent extends EventObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private MazeEventType Type;
	public MazeEventType getType()
	{
		return Type;
	}
	
	public GameEvent(Object source, MazeEventType type)
	{
		super(source);
		this.Type = type;
	}
	
	

}


