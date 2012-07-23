package com.mjc.maze.events;


import java.util.EventListener;

public interface GameListener extends EventListener
{
    public void gameEventOccurred(GameEvent evt);
}