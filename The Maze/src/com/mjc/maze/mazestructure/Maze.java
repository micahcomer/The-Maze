package com.mjc.maze.mazestructure;

import java.io.Serializable;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.mjc.maze.R;
import com.mjc.maze.basics.BitmapResizer;
import com.mjc.maze.basics.PathConnectionData;
import com.mjc.maze.basics.Point;
import com.mjc.maze.basics.PointList;
import com.mjc.maze.basics.Vector2;
import com.mjc.maze.buttons.ButtonEvent;
import com.mjc.maze.buttons.ButtonListener;
import com.mjc.maze.events.GameEvent;
import com.mjc.maze.player.Player;
import com.mjc.maze.player.PlayerEvent;
import com.mjc.maze.player.PlayerListener;
import com.mjc.maze.player.PlayerMovementDirection;
import com.mjc.maze.powerup.PowerUpManager;


public class Maze implements com.mjc.maze.events.GameListener, Serializable,
		ButtonListener, PlayerListener {

	// region Properties

	// Maze class properties
	private static final long serialVersionUID = 3883500963863601726L;
	Point mazeSize;
	Context context;
	

	// Parts of the maze.
	PointList MazePoints;
	PointList pathToFinish;

	public int getPathToFinishSize() {
		return pathToFinish.Points.size();
	}

	PointList pointsNotIncluded;
	PointList pointsIncluded;
	PointList Nodes;
	MazeTileType[][] TileDirections;
	transient Bitmap[][] TileTextures;
	List<PointList> allMazePaths;
	public List<PointList>getAllMazePaths()
	{
		return allMazePaths;
	}
	public List<PathConnectionData> ConnectionData;
	
	// Maze layout information:
	Point currentTileSize;

	public Point getCurrentTileSize() {
		return currentTileSize;
	}

	int minimumTileSize;
	int maximumTileSize;
	static Rect FullSizeMazeRect;
	static Rect onScreenMazeBox;
	static Rect offsetOnScreenMazeBox;
	static RectF offsetOnScreenMazeBoxFloat;

	public boolean canMoveLeft;
	public boolean canMoveRight;
	public boolean canMoveUp;
	public boolean canMoveDown;
	
	
	// Items used to draw the maze:
	static Bitmap FullSizeBitmap;
	Bitmap node;
	static Canvas FullSizeCanvas;
	transient Paint mazePaint;
	boolean showNodes;

	// The player
	Player player;

	public PowerUpManager powerUpManager;
	
	public Player getPlayer() {
		return player;
	}
	
	
	// endregion Properties

	public void CreateMaze(Context context, int level,Rect onScreenMazeBox) 
	{

		this.context = context;

		// Create a new Maze with a maze creator.		
		MazeCreator creator = new MazeCreator();
		creator.CreateMaze(context, level);
		this.MazePoints = creator.getMazePoints();
		this.pathToFinish = creator.getPathToFinish();
		this.pointsIncluded = creator.getPointsIncluded();
		this.pointsNotIncluded = creator.getPointsNotIncluded();
		this.Nodes = creator.getNodes();
		this.allMazePaths = creator.getAllMazePaths();
		this.mazeSize=creator.getMazeSize();
		
		//CreateConnectionData();
		
		maximumTileSize = 125;
		minimumTileSize = 40;

		// Assign appropriate tiles to each maze point.
		MazeTileAssigner assigner = new MazeTileAssigner(mazeSize, new Point(
				onScreenMazeBox.width(), onScreenMazeBox.height()),
				minimumTileSize, maximumTileSize);
		
		assigner.AssignMazeTiles(context, this.allMazePaths, this.Nodes);
		this.TileDirections = assigner.getTileDirections();
		this.TileTextures = assigner.getTileTextures();
		this.currentTileSize = new Point(assigner.getAdjustedTileSize(),
				assigner.getAdjustedTileSize());
		Vector2 MazePosition = new Vector2(onScreenMazeBox.left,
				onScreenMazeBox.top);
		FullSizeMazeRect = new Rect();
		Maze.onScreenMazeBox = onScreenMazeBox;
		Maze.offsetOnScreenMazeBoxFloat = new RectF(0, 0,
				onScreenMazeBox.width(), onScreenMazeBox.height());
		Maze.offsetOnScreenMazeBox = new Rect(0, 0, onScreenMazeBox.width(),
				onScreenMazeBox.height());

		ConvertMazePointsToMultiplesOfTileSize(currentTileSize);

		// Create the player
		Point startPosition = new Point(0, 0);
		player = new Player(context, startPosition,
				this.currentTileSize.getX(), MazePosition, onScreenMazeBox,
				this);
		player.SetPoints(MazePoints, Nodes, TileDirections,
				this.currentTileSize, new Vector2((mazeSize.getX()-1)*currentTileSize.getX(), (mazeSize.getY()-1)*currentTileSize.getY())); 
		player.addPlayerListener(this);

		mazePaint = new Paint();
		
		showNodes=false;
		DrawFullMazeFromTiles();

		UpdatePossibleMovementDirections();
		
		powerUpManager = new PowerUpManager(context);
		powerUpManager.CreateNewPowerUpSet((int)Nodes.Points.size()/4, Nodes, currentTileSize.getX(), onScreenMazeBox);
		
		

	}

	
	//region CreateConnData Method deprecated
	
/*
	private void CreateConnectionData()
	{
		ConnectionData = new ArrayList<PathConnectionData>();
		for (PointList path:allMazePaths)
		{
			ConnectionData.add(new PathConnectionData (allMazePaths.indexOf(path)));					
		}
		
		for (int i=0; i < allMazePaths.size(); i++)
		{
			PointList path = allMazePaths.get(i);
			PointList pathToCompare;
			
			
			if (i==0)
			{
				List<PointList>upStreamPaths = new ArrayList<PointList>();
				for (int j=1; j<allMazePaths.size();j++)
				{
					pathToCompare = allMazePaths.get(j);
					if (path.Intersects(pathToCompare))
					{
						upStreamPaths.add(pathToCompare);
					}
					
				}
				
				ConnectionData.get(i).setAllUpstreamPaths(upStreamPaths);
			}
			
			
			else
			{
				List<PointList>upStreamPaths = new ArrayList<PointList>();				
				for (int j=0; j<allMazePaths.size(); j++)
				{
					if (i!=j)
					{
						pathToCompare = allMazePaths.get(j);
						if (j<i)
						{							
							if (path.Intersects(pathToCompare))
							{
								ConnectionData.get(i).setDownStreamPath(pathToCompare);
							}
						}
						else
						{
							if (path.Intersects(pathToCompare))
								upStreamPaths.add(pathToCompare);								
						}
					}
				}
				ConnectionData.get(i).setAllUpstreamPaths(upStreamPaths);
				
			}
		}
		
				
		
	}
	
	*/
	//endregion
	private void DrawFullMazeFromTiles() {
		
		// Draw the maze to the bitmap we use in the draw method...
		FullSizeBitmap = Bitmap.createBitmap(
				currentTileSize.getX() * mazeSize.getX(),
				currentTileSize.getY() * mazeSize.getY(),
				Bitmap.Config.ARGB_8888);
		FullSizeCanvas = new Canvas(FullSizeBitmap);
		
		
			node = BitmapFactory.decodeResource(context.getResources(), R.drawable.node);
			BitmapResizer resizer = new BitmapResizer(context);
			node = resizer.getResizedBitmap(node, currentTileSize.getX(), currentTileSize.getY());
		
		
		for (int y = 0; y < mazeSize.getY(); y++) 
		{
			for (int x = 0; x < mazeSize.getX(); x++) 
			{
				FullSizeCanvas.drawBitmap
				(
						TileTextures[x][y],
						currentTileSize.getX() * x, 
						currentTileSize.getY() * y,
						mazePaint
				);
				
				if ((showNodes) && Nodes.ContainsEquivalent(x*currentTileSize.getX(),y*currentTileSize.getY()))
				{
					FullSizeCanvas.drawBitmap
					(
							node,
							currentTileSize.getX() * x, 
							currentTileSize.getY() * y,
							mazePaint
					);
				}
			}
		}

		FullSizeMazeRect = new Rect(0, 0, FullSizeCanvas.getWidth(),
				FullSizeCanvas.getHeight());
	}

	public void Update() {

		player.Update(currentTileSize);
		UpdatePossibleMovementDirections();
		powerUpManager.Update(player.getPosition(), onScreenMazeBox.left - offsetOnScreenMazeBox.left, onScreenMazeBox.top - offsetOnScreenMazeBox.top, player.getPlayerOnScreenBox());
		
	}

	public void Draw(Canvas screenCanvas) {
		screenCanvas.drawColor(Color.BLACK);

		screenCanvas.drawBitmap(FullSizeBitmap, offsetOnScreenMazeBox,
				onScreenMazeBox, mazePaint);

		powerUpManager.Draw(screenCanvas);
		
		if (!player.isInTransition())
		for (Point point:Nodes.Points)
		{
			if (point.isVisible())
			{
				screenCanvas.drawBitmap(node, onScreenMazeBox.left - offsetOnScreenMazeBox.left+point.getX(), onScreenMazeBox.top - offsetOnScreenMazeBox.top + point.getY(), mazePaint);
			}
		}
		
		
		player.Draw(screenCanvas);
		
	}

	private void ConvertMazePointsToMultiplesOfTileSize(Point newTileSize) {
		MazePoints.scaleUpToSize(newTileSize);
		Nodes.scaleUpToSize(newTileSize);
	}

	public void MoveMaze(PlayerMovementDirection direction, int speed) {
		switch (direction) {
		case Up: {
			offsetOnScreenMazeBox.offsetTo(offsetOnScreenMazeBox.left,
					offsetOnScreenMazeBox.top - speed);
			// OffsetMazeIfNeeded();
			break;
		}
		case Down: {
			// Maze is going to appear to move down, so the offset box needs to
			// move up...
			offsetOnScreenMazeBox.offsetTo(offsetOnScreenMazeBox.left,
					offsetOnScreenMazeBox.top + speed);
			// OffsetMazeIfNeeded();
			break;
		}
		case Left: {
			// Maze is going to appear to move left, so the offset box needs to
			// move right...
			offsetOnScreenMazeBox.offsetTo(offsetOnScreenMazeBox.left - speed,
					offsetOnScreenMazeBox.top);
			// OffsetMazeIfNeeded();
			break;
		}
		case Right: {
			// Maze is going to appear to move right, so the offset box needs to
			// move left...
			offsetOnScreenMazeBox.offsetTo(offsetOnScreenMazeBox.left + speed,
					offsetOnScreenMazeBox.top);
			// OffsetMazeIfNeeded();
			break;
		}
		default:
			break;
		}

		Player.mazeOrigin.setX(onScreenMazeBox.left
				- offsetOnScreenMazeBox.left);
		Player.mazeOrigin.setY(onScreenMazeBox.top - offsetOnScreenMazeBox.top);
	}

	private void UpdatePossibleMovementDirections() {
		canMoveRight = true;
		canMoveLeft = true;
		canMoveDown = true;
		canMoveUp = true;

		if (offsetOnScreenMazeBox.left <= 0)
			canMoveRight = false;
		if (offsetOnScreenMazeBox.top <= 0)
			canMoveDown = false;
		if (offsetOnScreenMazeBox.bottom >= FullSizeMazeRect.bottom)
			canMoveUp = false;
		if (offsetOnScreenMazeBox.right >= FullSizeMazeRect.right)
			canMoveLeft = false;
	}

	//region Zooming Methods
	
	private long getMemoryUsage() {

		MemoryInfo mi = new MemoryInfo();
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService("activity");
		activityManager.getMemoryInfo(mi);
		return mi.availMem;
	}

	private Point clampNewTileSize(float scaleFactor)
	{
		Point newTileSize = new Point(
				(int) (currentTileSize.getX() * scaleFactor),
				(int) (currentTileSize.getY() * scaleFactor));
		if (newTileSize.getX() < minimumTileSize)
			newTileSize.setX(minimumTileSize);
		if (newTileSize.getY() < minimumTileSize)
			newTileSize.setY(minimumTileSize);

		if (newTileSize.getX() > maximumTileSize)
			newTileSize.setX(maximumTileSize);
		if (newTileSize.getY() > maximumTileSize)
			newTileSize.setY(maximumTileSize);
		
		return newTileSize;
		
	}
	
	private boolean enoughSpaceToRedraw()
	{
		long freeSpace = getMemoryUsage();
		long expectedNewMazeSize = (mazeSize.getX() * currentTileSize.getX())
				* (mazeSize.getY() * currentTileSize.getY());
		
		if (expectedNewMazeSize < freeSpace)
			return true;
		else
			return false;
	}
	
	public void Rescale(float scaleFactor) {
		
		Vector2 playerOldCenter = new Vector2 (player.getPlayerOnScreenBox().centerX(), player.getPlayerOnScreenBox().centerY());
		
		currentTileSize = clampNewTileSize(scaleFactor);
		
		System.gc();

		if (enoughSpaceToRedraw())

		{
			ConvertMazePointsToMultiplesOfTileSize(currentTileSize);
			BitmapResizer resizer = new BitmapResizer(context);
			TileTextures = resizer.reloadBitmapsAtNewSize(TileTextures,
					currentTileSize.getX(), currentTileSize.getY(),
					TileDirections);
			DrawFullMazeFromTiles();
			
			

			Player.mazeOrigin.setX(onScreenMazeBox.left
					- offsetOnScreenMazeBox.left);
			Player.mazeOrigin.setY(onScreenMazeBox.top - offsetOnScreenMazeBox.top);
			player.Rescale(currentTileSize, resizer, MazePoints, Nodes,scaleFactor);
			
			
			player.offsetPlayerOnScreenBoxToNewCenter(playerOldCenter);
		}

	}

	//endregion
	
	// region Event Handlers

	public void buttonEventOccurred(ButtonEvent evt) {

		player.buttonEventOccurred(evt);

	}

	public void gameEventOccurred(GameEvent evt) {
		

	}

	public void onPlayerEvent(PlayerEvent playerEvent) {
			
		
	}

	public void onTouch(MotionEvent event)
	{
		player.onTouch(event, offsetOnScreenMazeBox);
	}
	
	// endregion Event Handlers

}
