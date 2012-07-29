package com.mjc.maze.mazestructure;

import android.content.Context;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.mjc.maze.basics.DirectionType;
import com.mjc.maze.basics.MazePathBlock;
import com.mjc.maze.basics.Point;
import com.mjc.maze.basics.PointList;
import com.mjc.maze.levelio.LevelIO;


public class MazeCreator implements Serializable {


	//region Properties
	
	private static final long serialVersionUID = -7000581746872053071L;
	
	Context context;
	int mazeLevel;
	int maxLevel = 99;
	int minSize = 10;
	int maxSize = 50;
    Random random;
    
    Point mazeSize;
    public Point getMazeSize()
    {return mazeSize;}
    PointList MazePoints;
    public PointList getMazePoints()
    {return  MazePoints;}
    
    int leftEdgeOfMaze;
    int topEdgeOfMaze;
    
    PointList pathToFinish;
    public PointList getPathToFinish()
    {return  pathToFinish;}
    
    PointList pointsNotIncluded;
    public PointList getPointsNotIncluded()
    {return  pointsNotIncluded;}
    
    PointList pointsIncluded;
    public PointList getPointsIncluded()
    {return  pointsIncluded;}
    
    PointList Nodes;
    public PointList getNodes()
    {return  Nodes;}
    
    List<PointList> allMazePaths;
    public List<PointList> getAllMazePaths()
    {return  allMazePaths;}

    //endregion    
    
    
    public MazeCreator()
    {
        MazePoints = new PointList();
        pointsNotIncluded = new PointList();
        pointsIncluded = new PointList();
        Nodes = new PointList();
        
        allMazePaths = new ArrayList<PointList>();        
    }
    
    public void CreateMaze(Context context, int level) {

        random = new Random(level);       
        MazePathBlock block;
        this.context = context;
        
        if (level<=maxLevel)
        setMazeSize(level);
        
        
       	if (DoesLevelExist(context, level))
       		block = ReadMazeInFromDisk(level);
       	else
       	
       	block = CreateMazesFromScratchAndWriteToDisk(context, level);       	
       	
       	this.allMazePaths = block.getAllMazePaths();
       	this.pathToFinish = block.getPathToMazeFinish();
       	this.MazePoints = block.getMazePoints();
       	
       	
    }

    private void setMazeSize(int level)
    {
    	if (level <10)
    	this.mazeSize = new Point(minSize+level, minSize+level);
    	
    	else
    	if (level <20)
    		this.mazeSize = new Point(minSize + (int)(level/2),minSize + (int)(level/2));
    	
    	else
    	if (level <30)
    	this.mazeSize = new Point (minSize + (int)(level/3), minSize + (int)(level/3));
    	
    	else if (level<40)
    	this.mazeSize = new Point (minSize + (int)(level/4), minSize + (int)(level/4));
    	
    	else if (level<50)
        	this.mazeSize = new Point (minSize + (int)(level/5), minSize + (int)(level/5));
    	
    	else if (level<60)
    		this.mazeSize = new Point (minSize + 50, minSize + 50);
    	
    	else if (level<70)
    		this.mazeSize = new Point (minSize + 60, minSize + 60);
    	
    	else if (level<80)
    		this.mazeSize = new Point (minSize + 70, minSize + 70);
    	
    	else if (level<90)
    		this.mazeSize = new Point (minSize + 80, minSize + 80);
    	
    	else
    		this.mazeSize = new Point (minSize + 90, minSize + 90);
    	
    }
    
    private boolean DoesLevelExist(Context context, int level)
    {
    	return context.getFileStreamPath("mazeLevel_"+String.valueOf(level)).exists();
    }
    
    private MazePathBlock CreateMazesFromScratchAndWriteToDisk(Context context, int level) {
        
    	LevelIO io = new LevelIO(context);
    	
    	
    	MazePoints = createAllMazePoints();
        pathToFinish = createPathToFinish();
        allMazePaths.add(pathToFinish);
        allMazePaths = createAllExtraMazePaths(allMazePaths);
        

        MazePathBlock block = new MazePathBlock(pathToFinish, MazePoints, allMazePaths);
        String path = "mazeLevel_"+String.valueOf(level);
        io.WriteMazePathBlock(block, path);
        
        return block;
            
    }

    private MazePathBlock ReadMazeInFromDisk(int level){

		LevelIO io = new LevelIO(context);        
        String path;
       
        path = "mazeLevel_"+String.valueOf(level);
        
        MazePathBlock block = new MazePathBlock();
        
        try {
            block = io.ReadMazePathBlock(path);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return block;
    }
    
    private PointList createAllMazePoints()
    {
        int maxX = mazeSize.getX();
        int maxY = mazeSize.getY();


        for (int y = 0; y<maxY; y++)
        {
            for (int x = 0; x<maxX; x++)
            {
                MazePoints.Points.add(new Point(x,y));
            }
        }

        for (int i =0; i<MazePoints.Points.size(); i++)
        {
            pointsNotIncluded.Points.add(MazePoints.Points.get(i));
        }

        return MazePoints;
    }
	
    private PointList createPathToFinish()
    {
        pathToFinish = new PointList();
        leftEdgeOfMaze = 0;
        topEdgeOfMaze = 0;

        Point startPoint = MazePoints.Points.get(0);
        Point finishPoint = MazePoints.Points.get((MazePoints.Points.size()-1));

        Point currentPoint = startPoint;
        Point previousPoint;

        pathToFinish = addPointToMazePath( startPoint, pathToFinish);
        

        while (!currentPoint.ValuesAreEqualTo(finishPoint))
        {
            previousPoint = currentPoint;
            currentPoint = getNextMazePoint(currentPoint, previousPoint);
            addPointToMazePath(currentPoint, pathToFinish);

            if (isThereALoop(pathToFinish))
                pathToFinish = removeLoop(pathToFinish);
        }

        return pathToFinish;
    }

    private Point getNextMazePoint(Point currentPoint, Point previousPoint)
    {
        Point nextPoint;

        MazeDirections possibleDirections = new MazeDirections();
        possibleDirections = removeInvalidDirections(possibleDirections, currentPoint, previousPoint);
        DirectionType directionToMove = pickARandomDirection(possibleDirections);

        previousPoint = currentPoint;
        nextPoint = MoveInADirection(currentPoint, directionToMove);

        return nextPoint;
    }
    
    private PointList addPointToMazePath(Point point, PointList Path)
    {
        Path.Points.add(point);

        if (isThereALoop(Path))
            Path = removeLoop(Path);

        includePoint(point);

        return Path;
    }
 
    private boolean isThereALoop(PointList Path)
    {
        boolean isThereALoop=false;

        Point lastPoint = Path.Points.get(Path.Points.size()-1);

        for (int i =0; i<Path.Points.size()-1; i++)
        {
            if (Path.Points.get(i).ValuesAreEqualTo(lastPoint))
                isThereALoop = true;
        }

        return isThereALoop;

    }

    private PointList removeLoop (PointList Path)
    {
        Point lastPoint = Path.Points.get(Path.Points.size()-1);

        int startIndex = -1;

        for (int i = 0; i<Path.Points.size()-1; i++)
            if (Path.Points.get(i).ValuesAreEqualTo(lastPoint))
                startIndex = i;

        if (startIndex>=0)
        {
            for (int j=Path.Points.size()-1; j>startIndex; j--)
            {
                excludePoint(Path.Points.get(j));
                Path.Points.remove(j);
            }

        }

        return Path;
    }

    private void includePoint(Point point)
    {
        pointsIncluded.Points.add(point);
        pointsNotIncluded.Points.remove(point);
    }

    private void excludePoint(Point point)
    {
        pointsNotIncluded.Points.add(point);
        pointsIncluded.Points.remove(point);
    }

    private MazeDirections removeInvalidDirections(MazeDirections directions, Point currentPoint, Point previousPoint)
    {
        if (currentPoint.getX()<=leftEdgeOfMaze)
            directions.Directions.remove(DirectionType.Left);
        if (currentPoint.getY()<=topEdgeOfMaze)
            directions.Directions.remove(DirectionType.Up);
        if (currentPoint.getX()==mazeSize.getX()-1)
            directions.Directions.remove(DirectionType.Right);
        if (currentPoint.getY()==mazeSize.getY()-1)
            directions.Directions.remove(DirectionType.Down);

        if (!currentPoint.ValuesAreEqualTo(previousPoint))
        {
            if (currentPoint.getX() - previousPoint.getX()==1)
                directions.Directions.remove(DirectionType.Left);
            else
            if (currentPoint.getX() - previousPoint.getX() == -1)
                directions.Directions.remove(DirectionType.Right);
            else
            if (currentPoint.getY() - previousPoint.getY()== 1)
                directions.Directions.remove(DirectionType.Up);
            else
            if (currentPoint.getY()-previousPoint.getY()==-1)
                directions.Directions.remove(DirectionType.Down);
        }

        return directions;
    }

    private DirectionType pickARandomDirection(MazeDirections possibleDirections)
    {
       Random thisrandom = new Random();
       return possibleDirections.Directions.get(thisrandom.nextInt(possibleDirections.Directions.size()));

    }

    private Point MoveInADirection(Point currentPoint, DirectionType directionToMove)
    {
        Point pointToReturn;

        switch (directionToMove)
        {
            case Up:
            {
                pointToReturn = MazePoints.returnEquivalentTo(new Point(currentPoint.getX(), currentPoint.getY()-1));
                break;
            }
            case Down:
            {
                pointToReturn = MazePoints.returnEquivalentTo(new Point(currentPoint.getX(), currentPoint.getY()+1));
                break;
            }
            case Left:
            {
                pointToReturn = MazePoints.returnEquivalentTo(new Point(currentPoint.getX()-1, currentPoint.getY()));
                break;
            }
            case Right:
            {
                pointToReturn = MazePoints.returnEquivalentTo(new Point(currentPoint.getX()+1, currentPoint.getY()));
                break;
            }
            default:
                pointToReturn = new Point(-1,-1);
                break;
        }

        return pointToReturn;
    }

    private List<PointList> createAllExtraMazePaths(List<PointList> allMazePaths)
    {

        while (pointsNotIncluded.Points.size()>0)
        {
            PointList newPath = createAnExtraMazePath(allMazePaths);
            allMazePaths.add(newPath);
        }

        pointsIncluded.RemoveDuplicates();
        return allMazePaths;
    }

    private PointList createAnExtraMazePath(List<PointList> allMazePaths)
    {
        PointList Path = new PointList();
        Point startPoint = obtainRandomPathStartPoint();
        addPointToMazePath(startPoint, Path);

        boolean alreadyContainedInMaze = false;
        Point currentPoint = startPoint;
        Point previousPoint;


        while (!alreadyContainedInMaze)
        {
            previousPoint = currentPoint;
            currentPoint = getNextMazePoint(currentPoint, previousPoint);
            addPointToMazePath(currentPoint, Path);

            if (isThereALoop(Path))
                Path = removeLoop(Path);

            for (PointList pathToCompare:allMazePaths)
            {
                if (pathToCompare.ContainsEquivalent(currentPoint))
                    alreadyContainedInMaze = true;
            }
        }

        return  Path;

    }

    private Point obtainRandomPathStartPoint()
    {
    	Random random = new Random();
    	/*
    	
    	int x=0;
    	int y=0;
    	Point testPoint = new Point(x,y);
    	
    	while (!pointsNotIncluded.ContainsEquivalent(testPoint))
    	{
    		testPoint.setX(random.nextInt(mazeSize.getX()-1));
        	testPoint.setY(random.nextInt(mazeSize.getY()-1)); 
    	}
        //return pointsNotIncluded.Points.get(random.nextInt(pointsNotIncluded.Points.size()));
         * 
         */
    	
    	return pointsNotIncluded.Points.get((random.nextInt(pointsNotIncluded.Points.size())));
    }
        



}


