package com.mjc.maze.levelio;




import java.io.*;
import java.util.ArrayList;

import android.content.Context;

import com.mjc.maze.basics.MazePathBlock;
import com.mjc.maze.basics.PointList;
import com.mjc.maze.mazestructure.Maze;

public class LevelIO implements Serializable {
    

	private static final long serialVersionUID = 9164501093954513354L;
	private Context context;

	public LevelIO (Context context2)
	{
		this.context = context2;
	}
	
	public void WriteMaze(Maze maze, String path) throws IOException {

        FileOutputStream fos = context.openFileOutput(path, 0);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(maze);
        oos.close();
        fos.close();
    }
    
    public void WriteMazePaths(ArrayList<PointList> allMazePaths, String path) throws IOException {
    	FileOutputStream fos = context.openFileOutput(path, 0);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(allMazePaths);
        oos.close();
        fos.close();
    }
    
    public ArrayList<PointList> ReadMazePaths (String path)throws IOException, ClassNotFoundException
    {
    	FileInputStream fis = context.openFileInput(path);
        ObjectInputStream ois = new ObjectInputStream(fis);

        @SuppressWarnings("unchecked")
		ArrayList<PointList> allMazePaths= (ArrayList<PointList>)ois.readObject();
        ois.close();
        fis.close();

        return allMazePaths;
    }
    
    public Maze ReadMaze(String path) throws IOException, ClassNotFoundException {
        
    	FileInputStream fis = context.openFileInput(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        
        Maze mazeToReturn = (Maze)ois.readObject();
        ois.close();
        fis.close();

        return mazeToReturn;
    }
    
    public void WriteMazePathBlock(MazePathBlock block, String path) {
        
    	try {
    		FileOutputStream fos = context.openFileOutput(path, 0);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(block);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
    }
    
    public MazePathBlock ReadMazePathBlock(String path) throws IOException, ClassNotFoundException {
    	
    	FileInputStream fis = context.openFileInput(path);
    	ObjectInputStream ois = new ObjectInputStream(fis);
    	
        MazePathBlock blockToReturn = (MazePathBlock) ois.readObject();
        ois.close();
        fis.close();

        return blockToReturn ;
    }
}
