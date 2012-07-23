package com.mjc.maze.mazestructure;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


public class MazeTile {

    Bitmap picture;
    Rect bounds;
    Rect source;
    MazeTileType type;
    Paint tilePaint;
    
    public MazeTile()
    {
        tilePaint = new Paint();
    }
    
    public MazeTile(Bitmap picture, Rect bounds, MazeTileType type)
    {
        this.picture = picture;
        this.bounds = bounds;
        this.type=type;
        source = new Rect(0,0,picture.getWidth(), picture.getHeight());
    }
    
    public void Draw (Canvas canvas)
    {
        canvas.drawBitmap(picture, source, bounds, tilePaint);
    }
    
}
