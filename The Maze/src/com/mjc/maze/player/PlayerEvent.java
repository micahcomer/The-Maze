package com.mjc.maze.player;

public class PlayerEvent {
PlayerEventType type;
Player owner;
float speed;

public PlayerEvent(Player owner, PlayerEventType type)
{
	this.owner = owner;
	this.type = type;
}

public PlayerEvent(Player owner, PlayerEventType type, float speed)
{
	this.owner = owner;
	this.type = type;
	this.speed = speed;
}

public PlayerEventType getType() {
	return type;
}

public Player getOwner()
{
	return owner;
}

public float getSpeed()
{
	return speed;
}

}