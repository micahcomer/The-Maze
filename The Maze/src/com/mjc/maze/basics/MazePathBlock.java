package com.mjc.maze.basics;

import java.io.Serializable;
import java.util.List;

public class MazePathBlock implements Serializable{

    private static final long serialVersionUID = 45L;

    PointList pathToMazeFinish;
    PointList MazePoints;
    List<PointList> allMazePaths;

    public MazePathBlock()
    {}

    public MazePathBlock(PointList pathToMazeFinish, PointList MazePoints, List<PointList> allMazePaths)
    {
        this.pathToMazeFinish = pathToMazeFinish;
        this.MazePoints = MazePoints;
        this.allMazePaths = allMazePaths;
    }

    public PointList getPathToMazeFinish()
    {return pathToMazeFinish;}

    public PointList getMazePoints()
    {return MazePoints;}
    
    public List<PointList> getAllMazePaths()
    {return allMazePaths;}
}
