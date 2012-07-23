package com.mjc.maze.basics;

import java.util.List;

public class PathConnectionData {

	int allMazePathsIndex;
	public int getAllMazePathsIndex() {
		return allMazePathsIndex;
	}

	public void setAllMazePathsIndex(int allMazePathsIndex) {
		this.allMazePathsIndex = allMazePathsIndex;
	}

	public List<PointList> getAllUpstreamPaths() {
		return allUpstreamPaths;
	}

	public void setAllUpstreamPaths(List<PointList> allUpstreamPaths) {
		this.allUpstreamPaths = allUpstreamPaths;
	}

	public PointList getDownStreamPath() {
		return downStreamPath;
	}

	public void setDownStreamPath(PointList downStreamPath) {
		this.downStreamPath = downStreamPath;
	}

	List<PointList> allUpstreamPaths;
	PointList downStreamPath;
	
	public PathConnectionData(int allMazePathsIndex)
	{
		this.allMazePathsIndex = allMazePathsIndex;
	}
	
	
	
}
