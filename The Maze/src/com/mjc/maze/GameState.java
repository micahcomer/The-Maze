package com.mjc.maze;

import java.io.Serializable;

public class GameState implements Serializable{


	private static final long serialVersionUID = 4073804162586621733L;

	boolean GameInProgress = false;
	public boolean isGameInProgress() {
		return GameInProgress;
	}

	public void setGameInProgress(boolean gameInProgress) {
		GameInProgress = gameInProgress;
	}

	public int getCurrentLevel() {
		return CurrentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		CurrentLevel = currentLevel;
	}

	public int getCurrentScore() {
		return CurrentScore;
	}

	public void setCurrentScore(int currentScore) {
		CurrentScore = currentScore;
	}

	int CurrentLevel;
	int CurrentScore;	
	
	public void InitialSet()
	{
		GameInProgress=true;
		CurrentLevel=1;		
		CurrentScore = 0;
	}
	
	public void GoUpALevel()
	{
		CurrentLevel++;
	}
	public void GoDownALevel()
	{
		CurrentLevel--;
	}
}
