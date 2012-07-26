package com.mjc.maze.basics;

import com.mjc.maze.R;
import com.mjc.maze.mazestructure.MazeTileType;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;


public class BitmapResizer {

	Resources resources;
	
	public BitmapResizer(Context context)
	{
		this.resources = context.getResources();
	}
	
	
	
	 public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
	        int width = bm.getWidth();
	        int height = bm.getHeight();
	        float scaleWidth = ((float) newWidth) / width;
	        float scaleHeight = ((float) newHeight) / height;
	        // CREATE A MATRIX FOR THE MANIPULATION
	        Matrix matrix = new Matrix();
	        // RESIZE THE BIT MAP
	        matrix.postScale(scaleWidth, scaleHeight);


	        // RECREATE THE NEW BITMAP
	        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	        return resizedBitmap;
	    }
	 
	 public Bitmap[][] resizeBitmapArray(Bitmap[][] bitmapArray, int newHeight, int newWidth)
	 {
		 int arrayHeight = bitmapArray.length;
		 int arrayWidth = bitmapArray[0].length;
		 
		 for (int y=0; y<arrayHeight; y++)
		 {
			 for (int x=0; x<arrayWidth; x++)
			 {
				 if (bitmapArray[x][y]!=null)
				 {
				 if ((bitmapArray[x][y].getWidth()!=newWidth)||(bitmapArray[x][y].getHeight()!=newHeight))
				 bitmapArray[x][y] = getResizedBitmap(bitmapArray[x][y], newHeight, newWidth);
				 }
			 }
		 }
		 
		 return bitmapArray;
	 }
	 
	 public Bitmap[][] reloadBitmapsAtNewSize(Bitmap[][]bitmapArray, int newHeight, int newWidth, MazeTileType[][] TileDirections)
	 {
		 for (int y=0; y<TileDirections.length; y++)
		 {
			 for (int x=0; x<TileDirections[0].length; x++)
			 {
				 bitmapArray[x][y]=getResizedBitmap(pickABitmap(TileDirections[x][y]), newHeight, newWidth);
			 }
		 }
		 
		 return bitmapArray;
	 }
	 
	 private Bitmap pickABitmap(MazeTileType tile)
	 {
		 Bitmap bitmapToReturn=null;
		 
		 switch (tile)
		 {
			 case Down:
				 bitmapToReturn= BitmapFactory.decodeResource(resources, R.drawable.trees_down);
				 break;
			 case Up:
				 bitmapToReturn = BitmapFactory.decodeResource(resources, R.drawable.trees_up);
				 break;
			 case Left:
				bitmapToReturn = BitmapFactory.decodeResource(resources, R.drawable.trees_left);
				break;
			 case Right:
				bitmapToReturn = BitmapFactory.decodeResource(resources, R.drawable.trees_right);
				break;
			 case UpLeft:
				bitmapToReturn = BitmapFactory.decodeResource(resources, R.drawable.trees_upleft);
				break;
			 case UpRight:
				bitmapToReturn = BitmapFactory.decodeResource(resources, R.drawable.trees_upright);
				break;
			 case UpDown:
				bitmapToReturn = BitmapFactory.decodeResource(resources, R.drawable.trees_updown);
				break;
			 case DownLeft:
				bitmapToReturn = BitmapFactory.decodeResource(resources, R.drawable.trees_downleft);
				break;
			 case DownRight:
				bitmapToReturn = BitmapFactory.decodeResource(resources, R.drawable.trees_downright);
				break;
			 case LeftRight:
				bitmapToReturn = BitmapFactory.decodeResource(resources, R.drawable.trees_leftright);
				break;		 
			 case UpLeftRight:
				bitmapToReturn = BitmapFactory.decodeResource(resources, R.drawable.trees_upleftright);
				break;
			 case UpDownLeft:
				bitmapToReturn = BitmapFactory.decodeResource(resources, R.drawable.trees_upleftdown);
				break;
			 case UpDownRight:
				bitmapToReturn = BitmapFactory.decodeResource(resources, R.drawable.trees_uprightdown);
				break;
			 case DownLeftRight:
				bitmapToReturn = BitmapFactory.decodeResource(resources, R.drawable.trees_downleftright);
				break;
			 case UpDownLeftRight:
				bitmapToReturn = BitmapFactory.decodeResource(resources, R.drawable.trees_upleftrightdown);
				
				break;
		default:
			break;
			 
		 }
		 return bitmapToReturn;
	 }

}
