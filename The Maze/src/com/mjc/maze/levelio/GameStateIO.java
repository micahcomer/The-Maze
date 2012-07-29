package com.mjc.maze.levelio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import com.mjc.maze.GameState;


public class GameStateIO {

	Context context;
	
	public GameStateIO(Context context)
	{this.context=context;}
	
public GameState ReadGameState(String path) throws IOException, ClassNotFoundException {
        
    	FileInputStream fis = context.openFileInput(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        
        GameState gameStateToReturn =(GameState)ois.readObject();
        
        ois.close();
        fis.close();

        return gameStateToReturn;
    }

public void WriteGameState(GameState gameState, String path) throws IOException {

    FileOutputStream fos = context.openFileOutput(path, 0);
    ObjectOutputStream oos = new ObjectOutputStream(fos);

    oos.writeObject(gameState);
    oos.close();
    fos.close();
}
	
}
