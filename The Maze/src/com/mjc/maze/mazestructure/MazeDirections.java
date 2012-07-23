package com.mjc.maze.mazestructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mjc.maze.basics.DirectionType;

public class MazeDirections implements Serializable {

	private static final long serialVersionUID = -6514394779263705766L;
	List<DirectionType> Directions;

    public MazeDirections()
    {
        Directions = new ArrayList<DirectionType>();
        Directions.add(DirectionType.Down);
        Directions.add(DirectionType.Right);
        Directions.add(DirectionType.Up);
        Directions.add(DirectionType.Left);

    }

    public void Reset()
    {
        Directions.clear();
        Directions.add(DirectionType.Down);
        Directions.add(DirectionType.Right);
        Directions.add(DirectionType.Up);
        Directions.add(DirectionType.Left);
    }
}
