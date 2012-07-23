package com.mjc.maze.player;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.FloatMath;
import android.view.MotionEvent;

import com.mjc.maze.R;
import com.mjc.maze.basics.BitmapResizer;
import com.mjc.maze.basics.DirectionType;
import com.mjc.maze.basics.Point;
import com.mjc.maze.basics.PointList;
import com.mjc.maze.basics.Vector2;
import com.mjc.maze.basics.WhichToMove;
import com.mjc.maze.buttons.ButtonEvent;
import com.mjc.maze.buttons.ButtonListener;
import com.mjc.maze.events.GameEvent;
import com.mjc.maze.events.GameListener;
import com.mjc.maze.mazestructure.Maze;
import com.mjc.maze.mazestructure.MazeTileType;
import com.mjc.maze.sounds.SoundManager;

public class Player implements ButtonListener, GameListener{

	//region Properties
	
	//Boolean variables
    boolean alreadyEvaluated;
    boolean isInTransition;
    public boolean isInTransition()
    {
    	return isInTransition;
    }
    static boolean canMoveUp;
    static boolean canMoveDown;
    static boolean canMoveLeft;
    static boolean canMoveRight;
    boolean finished = false;
    
    //Pictures
    static Bitmap Picture;
    static Bitmap downPicture;
    static Bitmap upPicture;
    static Bitmap leftPicture;
    static Bitmap rightPicture;
    static Rect playerRect;
    
    
    Point tileSize;
    
    //Position relative to the maze's origin.
	Vector2 PositionInMaze;
	
	public Vector2 getPosition()
	{
		return PositionInMaze;
	}
	//Position relative to the maze's origin.
    
    
    //Position on the screen
    static Vector2 PositionOnScreen;
    
    //Player layout info
    static RectF playerOnScreenBox;
    public RectF getPlayerOnScreenBox()
    {
    return playerOnScreenBox;	
    }
    
    public void offsetPlayerOnScreenBoxToNewCenter(Vector2 newCenter)
    {
    	playerOnScreenBox.offsetTo(newCenter.getX()-(Picture.getWidth()/2), newCenter.getY()-(Picture.getHeight()/2));
    	
    }
    
    static public Vector2 mazeOrigin;
    static Rect onScreenMazeBox;
        
    Vector2 nextPoint;
    Paint playerPaint;
    int speed;
    Vector2 finishPoint;
    
    //Maze parts
    static Maze Maze;
    PointList Nodes;
    PointList visibleNodes;
    PointList MazePoints;
    MazeTileType[][] TileDirections;    
    PlayerMovementDirection currentDirection;        
    static MazeTileType currentPossibleDirections;
    public MazeTileType getCurrentPossibleDirections() {
		return currentPossibleDirections;
	}
    List<PlayerListener> listenerList;
    
    

    //endregion 
        
    //region Constructor and initial methods
    
    public Player(Context context, Point startPosition, int adjustedTileSize, Vector2 mazeOrigin, Rect onScreenMazeBox, Maze maze)
    {
        playerPaint = new Paint();
        playerOnScreenBox = new RectF(mazeOrigin.getX(), mazeOrigin.getY(), mazeOrigin.getX()+adjustedTileSize, mazeOrigin.getY()+adjustedTileSize);
        PositionInMaze = new Vector2(startPosition.getX(), startPosition.getY());
        PositionOnScreen = new Vector2(startPosition.getX(), startPosition.getY());
        
        currentDirection = PlayerMovementDirection.None;
        isInTransition = false;
        speed = 20;
        listenerList = new ArrayList<PlayerListener>();
        Maze = maze;
        
        Player.mazeOrigin = new Vector2(0,0);
        
        LoadContent(context, adjustedTileSize);
        SoundManager.getInstance().loadSound(context, 2, R.raw.footsteps);        
        Player.mazeOrigin.setX(onScreenMazeBox.left);
        Player.mazeOrigin.setY(onScreenMazeBox.top);
        
        Player.onScreenMazeBox = onScreenMazeBox;      
        
    }

    
    
    //Constructor uses this
    private void LoadContent(Context context, int adjustedTileSize)
    {
    	BitmapResizer resizer = new BitmapResizer(context);
    	
        upPicture = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_up);
        downPicture = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_down);
        leftPicture = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_left);
        rightPicture = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_right);
        
        upPicture = resizer.getResizedBitmap(upPicture, adjustedTileSize, adjustedTileSize);
        downPicture = resizer.getResizedBitmap(downPicture, adjustedTileSize, adjustedTileSize);
        leftPicture = resizer.getResizedBitmap(leftPicture, adjustedTileSize, adjustedTileSize);
        rightPicture = resizer.getResizedBitmap(rightPicture, adjustedTileSize, adjustedTileSize);
        
        playerRect = new Rect(0,0,upPicture.getWidth(), upPicture.getHeight());
        
        setStartPicture();
    }

    //Constructor uses this
    private void setStartPicture()
    {
    	if (
    			(currentPossibleDirections == MazeTileType.Down) ||
    			(currentPossibleDirections == MazeTileType.DownLeft)
    		)

    		Picture = downPicture;
    	else
    		Picture = rightPicture;
    }
    
    //Called independently
    public void SetPoints(PointList MazePoints, PointList Nodes, MazeTileType[][] TileDirections, Point adjustedTileSize, Vector2 finishPoint)
    {
        this.MazePoints = MazePoints;
        this.Nodes = Nodes;
        this.TileDirections = TileDirections;
        this.tileSize = adjustedTileSize;
        this.visibleNodes = new PointList();
        this.finishPoint = finishPoint;
        EvaluatePossibleMoveDirections();    
        EvaluateAdjacentNodes();
            
    }
    
    //endregion
    
    public void Update (Point currentTileSize)
    {
    	tileSize = currentTileSize;
    	
    	if (!finished)
    	{
    	if (
    			(PositionInMaze.getX()==finishPoint.getX()) &&
    			(PositionInMaze.getY()==finishPoint.getY())
    		)
    		playerEventOccurred(new PlayerEvent(this,PlayerEventType.FinishedMaze));
    	else
    	{
        if (isInTransition)
        	UpdatePosition();
    	}       
    	}
    }
    
    public void Draw (Canvas canvas)
    {       
        canvas.drawBitmap(Picture,
                playerRect,
                playerOnScreenBox,          
                playerPaint);
    }

    //region Movement
    
    private void StartMovement(PlayerMovementDirection direction)
    {
        currentDirection = direction;        
        isInTransition = true;
        switch (direction)
        {
            case Up:
            {            	
                nextPoint = new Vector2(PositionInMaze.getX(), (PositionInMaze.getY()-tileSize.getY()));
                Picture = upPicture;
                break;
            }
            case Down:
            {
                nextPoint = new Vector2(PositionInMaze.getX(), (PositionInMaze.getY()+tileSize.getY()));
                Picture = downPicture;
                break;
                
            }
            case Left:
            {
                nextPoint = new Vector2((PositionInMaze.getX()-tileSize.getX()), PositionInMaze.getY());
                Picture = leftPicture;
                break;
            }
            case Right:
            {
                nextPoint = new Vector2((PositionInMaze.getX()+tileSize.getX()), PositionInMaze.getY());
                Picture = rightPicture;
                break;
            }
        }
    }

    private void UpdatePosition()
    {
        alreadyEvaluated = false;
        
        switch (DetermineWhichToMove())
        {
        case Maze:
        {
        	
        	MoveTheMaze(currentDirection, MovePlayer(currentDirection));      	
        	
        	break;
        }
        case Player:
        {
        	MovePlayer(currentDirection);
        	 
        }
        	break;
        }
        
        if (!alreadyEvaluated)
        EvaluateNewPosition();
        
        PositionOnScreen.setX(PositionInMaze.getX()+mazeOrigin.getX());
        PositionOnScreen.setY(PositionInMaze.getY()+mazeOrigin.getY());
        
        playerOnScreenBox.offsetTo(PositionOnScreen.getX(), PositionOnScreen.getY());
        

    }
    
    private WhichToMove DetermineWhichToMove ()
    {
    	//The canMoveXDirection variables in Maze refer to the offsetMazeBox's ability to move over the FullRect picture.
    	
    	WhichToMove whichToMove;
    	whichToMove=WhichToMove.Player;
    	
    	switch (currentDirection)
    	{
    	case Up:
    	{
    		if ((Maze.canMoveDown)&&(playerOnScreenBox.centerY()<=onScreenMazeBox.centerY()))
    		whichToMove=WhichToMove.Maze;
    		break;
    	}
    	case Down:
    	{
    		if ((Maze.canMoveUp)&&(playerOnScreenBox.centerY()>=onScreenMazeBox.centerY()))
    			whichToMove=WhichToMove.Maze;
    		break;
    	}
    	case Right:
    	{
    		if ((Maze.canMoveLeft)&&(playerOnScreenBox.centerX()>=onScreenMazeBox.centerX()))
    			whichToMove=WhichToMove.Maze;
    		break;
    	}
    	case Left:
    	{
    		if ((Maze.canMoveRight)&&(playerOnScreenBox.centerX()<=onScreenMazeBox.centerX()))
    			whichToMove=WhichToMove.Maze;
    		break;
    	}
    	}
    	return whichToMove;
    }
    
    
    
    private void MoveTheMaze(PlayerMovementDirection direction, int nextSpeed)
    {
    	Maze.MoveMaze(direction, nextSpeed);
    }
    private int MovePlayer(PlayerMovementDirection direction)
    {
    	int absoluteDistanceTraveled = speed;
    	
    	switch (direction)
        {
            case Up:
            {            	
                PositionInMaze.setY(PositionInMaze.getY()-speed);
            	//Did the player overshoot the next point?  If so, correct.
                if (PositionInMaze.getY()<nextPoint.getY())
                {
                	absoluteDistanceTraveled = (int)(speed-(nextPoint.getY()-PositionInMaze.getY()));
                    PositionInMaze.setY(nextPoint.getY());
                    EvaluateNewPosition();
                    alreadyEvaluated = true;
                }
                break;
            }
            case Down:
            {            	            	
                PositionInMaze.setY(PositionInMaze.getY()+speed);
                
                if (PositionInMaze.getY()>nextPoint.getY())
                {
                	absoluteDistanceTraveled = (int)(speed - (PositionInMaze.getY()-nextPoint.getY()));
                    PositionInMaze.setY(nextPoint.getY());
                    EvaluateNewPosition();
                    alreadyEvaluated = true;
                }
                break;
            }
            case Left:
            {
            	                	
                PositionInMaze.setX(PositionInMaze.getX()-speed);
                
                if (PositionInMaze.getX()<nextPoint.getX())
                {
                	absoluteDistanceTraveled = (int)(speed-(nextPoint.getX()-PositionInMaze.getX()));
                    PositionInMaze.setX(nextPoint.getX());
                    EvaluateNewPosition();
                    alreadyEvaluated = true;
                }
                break;
            }
            case Right:
            {            	                	
                PositionInMaze.setX(PositionInMaze.getX()+speed);
                if (PositionInMaze.getX()>nextPoint.getX())
                {
                	absoluteDistanceTraveled = (int)(speed - (PositionInMaze.getX()-nextPoint.getX()));
                    PositionInMaze.setX(nextPoint.getX());
                    EvaluateNewPosition();
                    alreadyEvaluated = true;
                }
                break;
            }
        }
    	return absoluteDistanceTraveled;
    }
    
   
    private void EvaluateNewPosition()
    {
        //If we are on a point...
        if (MazePoints.ContainsEquivalent(PositionInMaze))
        {
            //then, if we are on a node, stop moving.
            if (Nodes.ContainsEquivalent(PositionInMaze))
            {
                isInTransition = false;
                currentDirection=PlayerMovementDirection.None;                
                EvaluatePossibleMoveDirections();                
                
                EvaluateAdjacentNodes();
                
                playerEventOccurred(new PlayerEvent(this, PlayerEventType.StoppedMoving));
                
                if ((this.finishPoint.getX()==this.PositionInMaze.getX()) && (this.finishPoint.getY()==this.PositionInMaze.getY()))
                playerEventOccurred(new PlayerEvent(this, PlayerEventType.FinishedMaze));
            }
            //Otherwise we are on a point but not a node...
            else
            {
                  OnAPointButNotANode();
            }

        }
    }

    private void OnAPointButNotANode()
    {


        switch (TileDirections[(int)(PositionInMaze.getX()/tileSize.getX())][(int)(PositionInMaze.getY()/tileSize.getY())])
        {
            case UpLeft:
            {
                if (currentDirection == PlayerMovementDirection.Down)
                {
                    currentDirection=PlayerMovementDirection.Left;
                    Picture = leftPicture;
                    nextPoint.setX(PositionInMaze.getX()-tileSize.getX());
                }
                else
                {
                    currentDirection = PlayerMovementDirection.Up;
                    Picture = upPicture;
                    nextPoint.setY(PositionInMaze.getY()-tileSize.getY());
                }
                break;
            }
            case UpRight:
            {
                if (currentDirection == PlayerMovementDirection.Down)
                {
                    currentDirection=PlayerMovementDirection.Right;
                    Picture = rightPicture;
                    nextPoint.setX(PositionInMaze.getX()+tileSize.getX());
                }
                else
                {
                    currentDirection = PlayerMovementDirection.Up;
                    Picture = upPicture;
                    nextPoint.setY(PositionInMaze.getY()-tileSize.getY());
                }
                break;
            }
            case DownLeft:
            {
                if (currentDirection == PlayerMovementDirection.Up)
                {
                    currentDirection=PlayerMovementDirection.Left;
                    Picture = leftPicture;
                    nextPoint.setX(PositionInMaze.getX()-tileSize.getX());
                }
                else
                {
                    currentDirection = PlayerMovementDirection.Down;
                    Picture = downPicture;
                    nextPoint.setY(PositionInMaze.getY()+tileSize.getY());
                }
                break;
            }
            case DownRight:
            {
                if (currentDirection == PlayerMovementDirection.Up)
                {
                    currentDirection=PlayerMovementDirection.Right;
                    Picture = rightPicture;
                    nextPoint.setX(PositionInMaze.getX()+tileSize.getX());
                }
                else
                {
                    currentDirection = PlayerMovementDirection.Down;
                    Picture = downPicture;
                    nextPoint.setY(PositionInMaze.getY()+tileSize.getY());
                }
                break;
            }
            case UpDown:
            {
                if (currentDirection == PlayerMovementDirection.Up)
                {
                    currentDirection = PlayerMovementDirection.Up;
                    Picture = upPicture;
                    nextPoint.setY(PositionInMaze.getY()-tileSize.getY());
                }
                else
                {
                    currentDirection = PlayerMovementDirection.Down;
                    Picture = downPicture;
                    nextPoint.setY(PositionInMaze.getY()+tileSize.getY());
                }
                break;
            }
            case LeftRight:
            {
                if(currentDirection==PlayerMovementDirection.Left)
                {
                    currentDirection = PlayerMovementDirection.Left;
                    Picture = leftPicture;
                    nextPoint.setX(PositionInMaze.getX()-tileSize.getX());
                }
                else
                {
                    currentDirection = PlayerMovementDirection.Right;
                    Picture = rightPicture;
                    nextPoint.setX(PositionInMaze.getX()+tileSize.getX());
                }
                break;
            }
            default:
                break;

    }
    }

    private void EvaluatePossibleMoveDirections()
    {
        currentPossibleDirections = TileDirections[(int)(PositionInMaze.getX()/tileSize.getX())][(int)(PositionInMaze.getY()/tileSize.getY())];

        canMoveUp = true;
        canMoveDown = true;
        canMoveLeft = true;
        canMoveRight = true;
        if( ! ((currentPossibleDirections == MazeTileType.Up) ||
            (currentPossibleDirections == MazeTileType.UpDown) ||
            (currentPossibleDirections == MazeTileType.UpLeft) ||
            (currentPossibleDirections == MazeTileType.UpRight) ||
            (currentPossibleDirections == MazeTileType.UpDownLeft) ||
            (currentPossibleDirections == MazeTileType.UpDownRight) ||
            (currentPossibleDirections == MazeTileType.UpLeftRight) ||
            (currentPossibleDirections == MazeTileType.UpDownLeftRight))  )
        	
        	canMoveUp = false;

        if( ! ((currentPossibleDirections == MazeTileType.Down) ||
                (currentPossibleDirections == MazeTileType.UpDown) ||
                (currentPossibleDirections == MazeTileType.DownLeft) ||
                (currentPossibleDirections == MazeTileType.DownRight) ||
                (currentPossibleDirections == MazeTileType.UpDownLeft) ||
                (currentPossibleDirections == MazeTileType.UpDownRight) ||
                (currentPossibleDirections == MazeTileType.DownLeftRight) ||
                (currentPossibleDirections == MazeTileType.UpDownLeftRight) ) )
                    		
        	canMoveDown = false;


        if( !((currentPossibleDirections == MazeTileType.Left) ||
                (currentPossibleDirections == MazeTileType.DownLeft) ||
                (currentPossibleDirections == MazeTileType.UpLeft) ||
                (currentPossibleDirections == MazeTileType.LeftRight) ||
                (currentPossibleDirections == MazeTileType.UpDownLeft) ||
                (currentPossibleDirections == MazeTileType.UpLeftRight) ||
                (currentPossibleDirections == MazeTileType.DownLeftRight) ||
                (currentPossibleDirections == MazeTileType.UpDownLeftRight)  ))
            
        	canMoveLeft = false;

        if( !((currentPossibleDirections == MazeTileType.Right) ||
                (currentPossibleDirections == MazeTileType.UpRight) ||
                (currentPossibleDirections == MazeTileType.LeftRight) ||
                (currentPossibleDirections == MazeTileType.DownRight) ||
                (currentPossibleDirections == MazeTileType.DownLeftRight) ||
                (currentPossibleDirections == MazeTileType.UpDownRight) ||
                (currentPossibleDirections == MazeTileType.UpLeftRight) ||
                (currentPossibleDirections == MazeTileType.UpDownLeftRight)  ))
            
            canMoveRight = false;

    }

    //endregion
        
    //region Rescale
    
    public void Rescale (Point currentTileSize, BitmapResizer resizer, PointList MazePoints, PointList Nodes, float scaleFactor)
    {
    	this.tileSize = currentTileSize;
    	
    	downPicture = resizer.getResizedBitmap(downPicture, currentTileSize.getY(), currentTileSize.getX());
        upPicture= resizer.getResizedBitmap(upPicture, currentTileSize.getY(), currentTileSize.getX());
        leftPicture= resizer.getResizedBitmap(leftPicture, currentTileSize.getY(), currentTileSize.getX());
        rightPicture= resizer.getResizedBitmap(rightPicture, currentTileSize.getY(), currentTileSize.getX());
        Picture = resizer.getResizedBitmap(Picture, currentTileSize.getY(), currentTileSize.getX());
        
        this.MazePoints = MazePoints;
        this.Nodes = Nodes;
        
        this.PositionInMaze.MultiplyBy(scaleFactor);
        playerRect = new Rect (0,0,Picture.getWidth(), Picture.getHeight());
        playerOnScreenBox.left = mazeOrigin.getX()+PositionInMaze.getX();
        playerOnScreenBox.top = mazeOrigin.getY()+PositionInMaze.getY();
        playerOnScreenBox.right = playerOnScreenBox.left+Picture.getWidth();
        playerOnScreenBox.bottom= playerOnScreenBox.top+Picture.getHeight();
        
        speed *=scaleFactor;
        
        
    }
    
    //endregion
    
    //region Node Evaluation
    
    public void EvaluateAdjacentNodes()
    {
    	for (Point point:Nodes.Points)
    		point.setVisible(false);
    	
    	List<DirectionType> possibleInitialDirectionsToSeek = getPossibleDirectionsToGoFromHere(this.PositionInMaze);
    	visibleNodes.Points.clear();
    	for (int i=0; i<possibleInitialDirectionsToSeek.size(); i++)
    	{
    		visibleNodes.Points.add(seekADirectionAndFindANode(PositionInMaze, possibleInitialDirectionsToSeek.get(i)));
    		visibleNodes.Points.get(i).setVisible(true);
    		visibleNodes.Points.get(i).setInitialDirectionToGetToThisPoint(possibleInitialDirectionsToSeek.get(i));
    	}
    }
    
    private List<DirectionType> getPossibleDirectionsToGoFromHere(Vector2 position)
    {
    	List<DirectionType> possibleInitialDirectionsToSeek = new ArrayList<DirectionType>();

    	MazeTileType tile = TileDirections[(int)(position.getX()/tileSize.getX())][(int)(position.getY()/tileSize.getY())];

    	if (
    			(tile==MazeTileType.Up) ||
    			(tile==MazeTileType.UpDown) ||
    			(tile==MazeTileType.UpDownLeft) ||
    			(tile==MazeTileType.UpDownRight) ||
    			(tile==MazeTileType.UpLeft) ||
    			(tile==MazeTileType.UpRight) ||
    			(tile==MazeTileType.UpLeftRight) ||
    			(tile==MazeTileType.UpDownLeftRight)
    			)
    		possibleInitialDirectionsToSeek.add(DirectionType.Up);

    	if (
    			(tile==MazeTileType.Down) ||
    			(tile==MazeTileType.UpDown) ||
    			(tile==MazeTileType.DownLeft) ||
    			(tile==MazeTileType.DownRight) ||
    			(tile==MazeTileType.DownLeftRight) ||
    			(tile==MazeTileType.UpDownLeft) ||
    			(tile==MazeTileType.UpDownRight) ||
    			(tile==MazeTileType.UpDownLeftRight)    		
    			)
    		possibleInitialDirectionsToSeek.add(DirectionType.Down);

    	if (
    			(tile==MazeTileType.Left) ||
    			(tile==MazeTileType.DownLeft) ||
    			(tile==MazeTileType.UpDownLeft) ||
    			(tile==MazeTileType.DownLeftRight) ||
    			(tile==MazeTileType.UpLeft) ||
    			(tile==MazeTileType.LeftRight) ||
    			(tile==MazeTileType.UpLeftRight) ||
    			(tile==MazeTileType.UpDownLeftRight)
    			)
    		possibleInitialDirectionsToSeek.add(DirectionType.Left);

    	if (
    			(tile==MazeTileType.Right) ||
    			(tile==MazeTileType.LeftRight) ||
    			(tile==MazeTileType.UpRight) ||
    			(tile==MazeTileType.DownRight) ||
    			(tile==MazeTileType.DownLeftRight) ||
    			(tile==MazeTileType.UpDownRight) ||
    			(tile==MazeTileType.UpLeftRight) ||
    			(tile==MazeTileType.UpDownLeftRight)    		
    			)
    		possibleInitialDirectionsToSeek.add(DirectionType.Right);


    	return possibleInitialDirectionsToSeek;
    }
    
    private DirectionType getNextDirection(DirectionType currentDirection, float x, float y)
    {
    	
    	//current direction is the direction of move just executed to get to tile x,y
    	MazeTileType tileType = TileDirections[(int)(x/tileSize.getX())][(int)(y/tileSize.getY())];
    	
    	switch (tileType)
    	{
    	case UpDown:
    		if (currentDirection == DirectionType.Up)
    			return DirectionType.Up;
    		else
    			return DirectionType.Down;    		
    	case LeftRight:
    		if (currentDirection == DirectionType.Left)
    			return DirectionType.Left;
    		else
    			return DirectionType.Right;    		
    	
    	case UpLeft:
    		if (currentDirection == DirectionType.Down)
    			return DirectionType.Left;
    		else
    			return DirectionType.Up;
    	case UpRight:
    		if (currentDirection == DirectionType.Down)
    			return DirectionType.Right;
    		else
    			return DirectionType.Up;    	
    	
    	case DownLeft:
    		if (currentDirection == DirectionType.Up)
    			return DirectionType.Left;
    		else
    			return DirectionType.Down;
    	case DownRight:
    		if (currentDirection == DirectionType.Up)
    			return DirectionType.Right;
    		else
    			return DirectionType.Down;
    		default:
    			return null;
    		
    	}
    }
    
    private Point seekADirectionAndFindANode(Vector2 positionToStartSeekingFrom, DirectionType directionToSeek)
    {
    	switch (directionToSeek)
    	{
    	case Up:
    		if (Nodes.ContainsEquivalent((int)positionToStartSeekingFrom.getX(), (int)positionToStartSeekingFrom.getY()-tileSize.getY()))
    				return Nodes.returnEquivalentTo((int)positionToStartSeekingFrom.getX(), (int)positionToStartSeekingFrom.getY()-tileSize.getY());
    		else
    		{
    			DirectionType nextDirection = getNextDirection(directionToSeek, positionToStartSeekingFrom.getX(), positionToStartSeekingFrom.getY()-tileSize.getY());
    			return seekADirectionAndFindANode(new Vector2 (positionToStartSeekingFrom.getX(), positionToStartSeekingFrom.getY()-tileSize.getY()), nextDirection);
    		}    		
    	case Down:
    		if (Nodes.ContainsEquivalent((int)positionToStartSeekingFrom.getX(), (int)positionToStartSeekingFrom.getY()+tileSize.getY()))
				return Nodes.returnEquivalentTo((int)positionToStartSeekingFrom.getX(), (int)positionToStartSeekingFrom.getY()+tileSize.getY());
		else
		{
			DirectionType nextDirection = getNextDirection(directionToSeek, positionToStartSeekingFrom.getX(), positionToStartSeekingFrom.getY()+tileSize.getY());
			return seekADirectionAndFindANode(new Vector2 (positionToStartSeekingFrom.getX(), positionToStartSeekingFrom.getY()+tileSize.getY()), nextDirection);
		}    		
    	case Left:
    		if (Nodes.ContainsEquivalent((int)positionToStartSeekingFrom.getX()-tileSize.getX(), (int)positionToStartSeekingFrom.getY()))
				return Nodes.returnEquivalentTo((int)positionToStartSeekingFrom.getX()-tileSize.getX(), (int)positionToStartSeekingFrom.getY());
		else
		{
			DirectionType nextDirection = getNextDirection(directionToSeek, positionToStartSeekingFrom.getX()-tileSize.getX(), positionToStartSeekingFrom.getY());
			return seekADirectionAndFindANode(new Vector2 (positionToStartSeekingFrom.getX()-tileSize.getX(), positionToStartSeekingFrom.getY()), nextDirection);
		}   	
    		
    	case Right:
    		if (Nodes.ContainsEquivalent((int)positionToStartSeekingFrom.getX()+tileSize.getX(), (int)positionToStartSeekingFrom.getY()))
				return Nodes.returnEquivalentTo((int)positionToStartSeekingFrom.getX()+tileSize.getX(), (int)positionToStartSeekingFrom.getY());
		else
		{
			DirectionType nextDirection = getNextDirection(directionToSeek, positionToStartSeekingFrom.getX()+tileSize.getX(), positionToStartSeekingFrom.getY());
			return seekADirectionAndFindANode(new Vector2 (positionToStartSeekingFrom.getX()+tileSize.getX(), positionToStartSeekingFrom.getY()), nextDirection);
		}
    		default:
    			return null;
    	}
    }
    
    //endregion
    
    //region Event Handlers
    
    //@Override
    public void buttonEventOccurred(ButtonEvent evt) {
        if (!isInTransition)
        {
        switch (evt.getType())
        {
            case MoveLeft:
            {
                if (canMoveLeft)
                {
                	
                	StartMovement(PlayerMovementDirection.Left);
                }
                break;
            }
            case MoveRight:
            {
                if (canMoveRight)
                {
                	
                	StartMovement(PlayerMovementDirection.Right);
                }
                break;
            }
            case MoveUp:
            {
                if (canMoveUp)
                {
                	
                	StartMovement(PlayerMovementDirection.Up);
                }
                break;
            }
            case MoveDown:
            {
                if (canMoveDown)
                {
                	
                	StartMovement(PlayerMovementDirection.Down);
                }
                break;
            }
        }
        }
    }
    
    public void playerEventOccurred(PlayerEvent playerEvent)
    {
    	if (playerEvent.getType()==PlayerEventType.FinishedMaze)
    		finished = true;
    	for (PlayerListener listener:listenerList)
    		listener.onPlayerEvent(playerEvent);
    	
    }
    
    public void addPlayerListener(PlayerListener listener)
    {
    	listenerList.add(listener);
    }
    
    public void removePlayerListener(PlayerListener listener)
    {
    	try {
			listenerList.remove(listener);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
       
    //@Override
    public void gameEventOccurred(GameEvent evt) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onTouch(MotionEvent event, Rect offsetOnScreenMazeBox)
    {if (!isInTransition)
    {
    	Point eventCoord = new Point ((int)(event.getX()-(tileSize.getX()/2)-onScreenMazeBox.left+offsetOnScreenMazeBox.left), (int)(event.getY()-(tileSize.getY()/2)-onScreenMazeBox.top+offsetOnScreenMazeBox.top));
    	PointList nearbyNodes = new PointList();
    	for (Point point:visibleNodes.Points)
    	{
    		if (spacing(point, eventCoord)<75)
    		{
    			nearbyNodes.Points.add(point);
    		}
    	}
    	
    	if (nearbyNodes.Points.size()==1)
    	StartMovement(convertDirectionToPlayerDirection(nearbyNodes.Points.get(0).getInitialDirectionToGetToThisPoint()));
    	else
    	{
    		if (nearbyNodes.Points.size()>1)
    		StartMovement(convertDirectionToPlayerDirection(findClosestNode(nearbyNodes, eventCoord).getInitialDirectionToGetToThisPoint()));
    	}
    }
    }
    
    private float spacing(Point pointA, Point pointB) 
	{
			float x = pointA.getX() - pointB.getX();
			float y = pointA.getY() - pointB.getY();
			return FloatMath.sqrt(x * x + y * y);
	}
    
    private Point findClosestNode(PointList nodes, Point eventCoord)
    {    	
    	Point pointToReturn = nodes.Points.get(0);
    	float currentSpacing = spacing(pointToReturn, eventCoord);
    	
    	for (Point point:nodes.Points)
    	{
    		if (spacing(point, eventCoord)<=currentSpacing)
    		{
    			currentSpacing = spacing(point, eventCoord);
    			pointToReturn = point;
    		}   
    	}
    	return pointToReturn;
    }
    private PlayerMovementDirection convertDirectionToPlayerDirection(DirectionType direction)
    {
    	PlayerMovementDirection playerDirection = null;
    	
    	switch(direction)
    	{
    	case Up:
    		playerDirection= PlayerMovementDirection.Up;
    		break;
    	case Down:
    		playerDirection= PlayerMovementDirection.Down;
    		break;
    	case Left:
    		playerDirection= PlayerMovementDirection.Left;
    		break;
    	case Right:    	
    		playerDirection= PlayerMovementDirection.Right;
    		break;
    	}
    	
    	return playerDirection;
    }
    
    //endregion
	
}
