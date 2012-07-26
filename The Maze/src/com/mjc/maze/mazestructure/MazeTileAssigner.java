package com.mjc.maze.mazestructure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mjc.maze.R;
import com.mjc.maze.basics.BitmapResizer;
import com.mjc.maze.basics.Point;
import com.mjc.maze.basics.PointList;

public class MazeTileAssigner implements Serializable {

	//region Properties
	
	private static final long serialVersionUID = 6614855390416577039L;
	Point mazeSize;
    MazeTileType[][] TileDirections;
    public MazeTileType[][] getTileDirections()
    {return TileDirections;}

    transient Bitmap[][] TileTextures;
    public Bitmap[][] getTileTextures()
    {return  TileTextures;}

    Point tileDimensions;
    public Point getTileDimensions()
    {return  tileDimensions;}    
       
    int adjustedTileSize;
    int minimumTileSize;
    int maximumTileSize;
    
    
    
    public int getAdjustedTileSize() {
		return adjustedTileSize;
	}

	Point onScreenMazeBoxSize;

    transient Bitmap TextureUp;
    transient Bitmap TextureDown;
    transient Bitmap TextureLeft;
    transient Bitmap TextureRight;
    transient Bitmap TextureUpDown;
    transient Bitmap TextureUpLeft;
    transient Bitmap TextureUpRight;
    transient Bitmap TextureLeftRight;
    transient Bitmap TextureLeftDown;
    transient Bitmap TextureRightDown;
    transient Bitmap TextureLeftUpDown;
    transient Bitmap TextureUpRightDown;
    transient Bitmap TextureLeftRightDown;
    transient Bitmap TextureLeftRightUpDown;
    transient Bitmap TextureLeftUpRight;
    public transient Bitmap node;

  //endregion
    
    public MazeTileAssigner(Point mazeSize, Point onScreenMazeBoxSize, int minimumTileSize, int maximumTileSize)
    {
        TileDirections = new MazeTileType[mazeSize.getX()][mazeSize.getY()];
        TileTextures = new Bitmap [mazeSize.getX()][mazeSize.getY()];
        this.mazeSize = mazeSize;
        this.onScreenMazeBoxSize = onScreenMazeBoxSize;
        this.minimumTileSize = minimumTileSize;
        this.maximumTileSize = maximumTileSize;

    }

    public void AssignMazeTiles(Context context, List<PointList> allMazePaths, PointList Nodes)
    {
        loadTilePictures(context);
        Nodes.Points.clear();
        
        for (PointList Path : allMazePaths)
        {
            for (int i = 0; i < Path.Points.size(); i++)
            {            	//region Determine if the point is a Node, and if so, assign it to Nodes...
            	
                MazeTileType currentTileDirections = CompareSurroundingPathTiles(Path, i);


                if ((currentTileDirections != MazeTileType.UpDown) &&
                        (currentTileDirections != MazeTileType.LeftRight) &&
                        (currentTileDirections != MazeTileType.DownLeft) &&
                        (currentTileDirections != MazeTileType.UpLeft) &&
                        (currentTileDirections != MazeTileType.DownRight) &&
                        (currentTileDirections != MazeTileType.UpRight))
                {
                    Nodes.Points.add(Path.Points.get(i));
                }
                
                //endregion


                //If its the last tile in a path we need to join it to the path it intersects...
                
                //So, if it is the last tile in the path...
                if (i == Path.Points.size()-1)
                {
                	//...then lets find which path it intersects.  First, we look at all maze paths...
                    for (PointList PathToCompare : allMazePaths)
                    {
                    	//then we make that 1)we don't compare the path to itself, 2)we only look at paths that have already been properly handled and consolidated , thus that have a smaller index in the allMazePaths list.
                    	// and 3)we want a path that actually contains the point in question.  
                        if ((PathToCompare!=Path)&& allMazePaths.indexOf(PathToCompare)<allMazePaths.indexOf(Path)
                                && (PathToCompare.Points.contains(Path.Points.get(i))))
                        {
                        	//when we find all of that... we get the point on the path that we are talking about, call it pointOnOtherPath, and use it to update the "currentTileDirections" value to reflect the two added
                        	//together.
                            Point pointOnOtherPath = PathToCompare.Points.get(PathToCompare.Points.indexOf(Path.Points.get(i)));
                            currentTileDirections = AddDirections(TileDirections[pointOnOtherPath.getX()][pointOnOtherPath.getY()], currentTileDirections);
                            
                        }
                    }
                }                

                //Finally, once we have made that addition of the two tiles, we assign the new currentTileDirections back into the TileDirections array, and assign an appropriate picture to it.
                TileDirections[Path.Points.get(i).getX()][Path.Points.get(i).getY()] = currentTileDirections;
                TileTextures[Path.Points.get(i).getX()][Path.Points.get(i).getY()] = AssignAPicture(currentTileDirections);

            }

        }




    }
    
   

    private void loadTilePictures(Context context)
    {   
    	adjustedTileSize = (onScreenMazeBoxSize.getY()/(mazeSize.getX()));
    	
    	if (adjustedTileSize>maximumTileSize)
    		adjustedTileSize = maximumTileSize;
    	else
    		if (adjustedTileSize<minimumTileSize)
    			adjustedTileSize = minimumTileSize;
    	
    	BitmapResizer resizer = new BitmapResizer(context);
    	
        TextureUp = BitmapFactory.decodeResource(context.getResources(), R.drawable.trees_up) ;
        TextureUp = resizer.getResizedBitmap(TextureUp, (int)adjustedTileSize, (int)adjustedTileSize);
        
        TextureDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.trees_down);
        TextureDown = resizer.getResizedBitmap(TextureDown, (int)adjustedTileSize, (int)adjustedTileSize);
        
        TextureLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.trees_left);
        TextureLeft = resizer.getResizedBitmap(TextureLeft, (int)adjustedTileSize, (int)adjustedTileSize);
        
        TextureRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.trees_right);
        TextureRight = resizer.getResizedBitmap(TextureRight, (int)adjustedTileSize, (int)adjustedTileSize);

        TextureUpDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.trees_updown);
        TextureUpDown = resizer.getResizedBitmap(TextureUpDown,(int)adjustedTileSize, (int)adjustedTileSize);
        
        TextureUpLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.trees_upleft);
        TextureUpLeft = resizer.getResizedBitmap(TextureUpLeft, (int)adjustedTileSize, (int)adjustedTileSize);
        
        TextureUpRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.trees_upright);
        TextureUpRight = resizer.getResizedBitmap(TextureUpRight, (int)adjustedTileSize, (int)adjustedTileSize);
        
        TextureLeftRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.trees_leftright);
        TextureLeftRight = resizer.getResizedBitmap(TextureLeftRight, (int)adjustedTileSize, (int)adjustedTileSize);
        
        TextureLeftDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.trees_downleft);
        TextureLeftDown = resizer.getResizedBitmap(TextureLeftDown, (int)adjustedTileSize, (int)adjustedTileSize);
        
        TextureRightDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.trees_downright);
        TextureRightDown = resizer.getResizedBitmap(TextureRightDown, (int)adjustedTileSize, (int)adjustedTileSize);

        TextureLeftUpRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.trees_upleftright);
        TextureLeftUpRight = resizer.getResizedBitmap(TextureLeftUpRight, (int)adjustedTileSize, (int)adjustedTileSize);
        
        TextureLeftUpDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.trees_upleftdown);
        TextureLeftUpDown = resizer.getResizedBitmap(TextureLeftUpDown, (int)adjustedTileSize, (int)adjustedTileSize);
        
        TextureUpRightDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.trees_uprightdown);
        TextureUpRightDown = resizer.getResizedBitmap(TextureUpRightDown, (int)adjustedTileSize, (int)adjustedTileSize);
        
        TextureLeftRightDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.trees_downleftright);
        TextureLeftRightDown = resizer.getResizedBitmap(TextureLeftRightDown, (int)adjustedTileSize, (int)adjustedTileSize);

        TextureLeftRightUpDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.trees_upleftrightdown);
        TextureLeftRightUpDown = resizer.getResizedBitmap(TextureLeftRightUpDown, (int)adjustedTileSize, (int)adjustedTileSize);
        
        node = BitmapFactory.decodeResource(context.getResources(), R.drawable.node);
        node = resizer.getResizedBitmap(node, (int)adjustedTileSize, (int)adjustedTileSize);
        

    }
    
   
    private MazeTileType CompareSurroundingPathTiles(PointList Path, int currentPointIndex)
    {

        Point currentPoint = Path.Points.get(currentPointIndex);
        MazeTileType directionsOfTile= MazeTileType.None;

        //Figure out which direction the next point in the maze path is.

        //If we are at the first point in the maze path, it must be
        //we only need to look at the point ahead of us.



        if (currentPointIndex == 0)
        {
            Point nextPoint = Path.Points.get(currentPointIndex + 1) ;

            //Look at the next point and see which direction it is away from the current point.
            if (nextPoint.getX() - currentPoint.getX() == -1)
                directionsOfTile = MazeTileType.Left;
            else
            if (nextPoint.getX() - currentPoint.getX() == 1)
                directionsOfTile = MazeTileType.Right;
            else
            if (nextPoint.getY() - currentPoint.getY() == -1)
                directionsOfTile = MazeTileType.Up;
            else
            if (nextPoint.getY() - currentPoint.getY() == 1)
                directionsOfTile = MazeTileType.Down;

        }
        else

            //If we are at the last point in the maze path...
            if (currentPointIndex == Path.Points.size() - 1)
            {
                Point previousPoint = Path.Points.get(currentPointIndex - 1);

                if (previousPoint.getX() - currentPoint.getX() == -1)
                    directionsOfTile = MazeTileType.Left;
                else
                if (previousPoint.getX() - currentPoint.getX() == 1)
                    directionsOfTile = MazeTileType.Right;
                else
                if (previousPoint.getY() - currentPoint.getY() == -1)
                    directionsOfTile = MazeTileType.Up;
                else
                if (previousPoint.getY() - currentPoint.getY() == 1)
                    directionsOfTile = MazeTileType.Down;

            }

            else
            //If we are in the middle of the path, we need to look at the points before and after the current one.
            {

                Point nextPoint = Path.Points.get(currentPointIndex + 1);
                Point previousPoint = Path.Points.get(currentPointIndex - 1);

                if ((previousPoint.getX() - currentPoint.getX() == -1) && (nextPoint.getX() - currentPoint.getX() == 1)
                        || (previousPoint.getX() - currentPoint.getX() == 1) && (nextPoint.getX() - currentPoint.getX() == -1))
                    directionsOfTile = MazeTileType.LeftRight;
                else
                if ((previousPoint.getY() - currentPoint.getY() == -1) && (nextPoint.getY() - currentPoint.getY() == 1)
                        || (previousPoint.getY() - currentPoint.getY() == 1) && (nextPoint.getY() - currentPoint.getY() == -1))
                    directionsOfTile = MazeTileType.UpDown;
                else
                if ((previousPoint.getX() - currentPoint.getX() == -1) && (nextPoint.getY() - currentPoint.getY() == -1)
                        || (nextPoint.getX() - currentPoint.getX() == -1) && (previousPoint.getY() - currentPoint.getY() == -1))
                    directionsOfTile = MazeTileType.UpLeft;
                else
                if ((previousPoint.getX() - currentPoint.getX() == 1) && (nextPoint.getY() - currentPoint.getY() == -1)
                        || (nextPoint.getX() - currentPoint.getX() == 1) && (previousPoint.getY() - currentPoint.getY() == -1))
                    directionsOfTile = MazeTileType.UpRight;
                else
                if ((previousPoint.getX() - currentPoint.getX() == -1) && (nextPoint.getY() - currentPoint.getY() == 1)
                        || (nextPoint.getX() - currentPoint.getX() == -1) && (previousPoint.getY() - currentPoint.getY() == 1))
                    directionsOfTile = MazeTileType.DownLeft;
                else
                if ((previousPoint.getX() - currentPoint.getX() == 1) && (nextPoint.getY() - currentPoint.getY() == 1)
                        || (nextPoint.getX() - currentPoint.getX() == 1) && (previousPoint.getY() - currentPoint.getY() == 1))
                    directionsOfTile = MazeTileType.DownRight;
            }

        return directionsOfTile;
    }

    private MazeTileType AddDirections(MazeTileType firstDirection, MazeTileType secondDirection)
    {
        boolean Up = false;
        boolean Down = false;
        boolean Left = false;
        boolean Right = false;

        List<MazeTileType> directions = new ArrayList<MazeTileType>();
        directions.add(firstDirection);
        directions.add(secondDirection);

        if (directions.contains(MazeTileType.Up) ||
                directions.contains(MazeTileType.UpDown) ||
                directions.contains(MazeTileType.UpRight) ||
                directions.contains(MazeTileType.UpLeft) ||
                directions.contains(MazeTileType.UpDownLeft) ||
                directions.contains(MazeTileType.UpLeftRight) ||
                directions.contains(MazeTileType.UpDownRight) ||
                directions.contains(MazeTileType.UpDownLeftRight)
                )

            Up = true;

        if (directions.contains(MazeTileType.Down) ||
                directions.contains(MazeTileType.UpDown) ||
                directions.contains(MazeTileType.DownRight) ||
                directions.contains(MazeTileType.DownLeft) ||
                directions.contains(MazeTileType.UpDownLeft) ||
                directions.contains(MazeTileType.DownLeftRight) ||
                directions.contains(MazeTileType.UpDownRight) ||
                directions.contains(MazeTileType.UpDownLeftRight)
                )
            Down = true;

        if (directions.contains(MazeTileType.Left) ||
                directions.contains(MazeTileType.LeftRight) ||
                directions.contains(MazeTileType.UpLeft) ||
                directions.contains(MazeTileType.DownLeft) ||
                directions.contains(MazeTileType.UpDownLeft) ||
                directions.contains(MazeTileType.UpLeftRight) ||
                directions.contains(MazeTileType.DownLeftRight) ||
                directions.contains(MazeTileType.UpDownLeftRight)
                )
            Left = true;

        if (directions.contains(MazeTileType.Right) ||
                directions.contains(MazeTileType.DownRight) ||
                directions.contains(MazeTileType.UpRight) ||
                directions.contains(MazeTileType.LeftRight) ||
                directions.contains(MazeTileType.UpDownRight) ||
                directions.contains(MazeTileType.UpLeftRight) ||
                directions.contains(MazeTileType.DownLeftRight) ||
                directions.contains(MazeTileType.UpDownLeftRight)
                )
            Right = true;

        if (Up)
        {
            if (Down)
            {
                if (Left)
                {
                    if (Right)
                    {
                        return MazeTileType.UpDownLeftRight;
                    }
                    else
                    {//if not Right
                        return MazeTileType.UpDownLeft;
                    }
                }
                else
                {//if not Left
                    if (Right)
                    {
                        return MazeTileType.UpDownRight;
                    }
                    else
                    {//if not Right
                        return MazeTileType.UpDown;
                    }
                }
            }
            else
            {//if not Down
                if (Left)
                {
                    if (Right)
                    {
                        return
                                MazeTileType.UpLeftRight;
                    }
                    else
                    {//if not Right
                        return
                                MazeTileType.UpLeft;
                    }
                }
                else
                {//if not left

                    return MazeTileType.UpRight;

                }
            }

        }
        else
        if (Down)
        {
            if (Left)
            {
                if (Right)
                    return MazeTileType.DownLeftRight;
                else
                    return MazeTileType.DownLeft;
            }
            else

                return MazeTileType.DownRight;

        }
        else

            return MazeTileType.LeftRight;



    }

    private Bitmap AssignAPicture(MazeTileType direction)
    {
        Bitmap texture;
        switch (direction)
        {
            case Up:
            {
                texture = TextureUp;
                break;
            }
            case Left:
            {
                texture = TextureLeft;
                break;
            }
            case Right:
            {
                texture = TextureRight;
                break;
            }
            case Down:
            {
                texture = TextureDown;
                break;
            }

            case UpLeft:
            {
                texture = TextureUpLeft;
                break;
            }
            case LeftRight:
            {
                texture  = TextureLeftRight;
                break;
            }
            case DownRight:
            {
                texture = TextureRightDown;
                break;
            }
            case DownLeft:
            {
                texture = TextureLeftDown;
                break;
            }
            case UpDown:
            {
                texture = TextureUpDown;
                break;
            }
            case UpRight:
            {
                texture = TextureUpRight;
                break;
            }
            case DownLeftRight:
            {
                texture = TextureLeftRightDown;
                break;
            }
            case UpDownLeft:
            {
                texture = TextureLeftUpDown;
                break;
            }
            case UpLeftRight:
            {
                texture = TextureLeftUpRight;
                break;
            }
            case UpDownRight:
            {
                texture = TextureUpRightDown;
                break;
            }
            case UpDownLeftRight:
            {
                texture = TextureLeftRightUpDown;
                break;
            }
            default:
            {
                texture = null;
                break;
            }
        }
        return texture;
    }
}
