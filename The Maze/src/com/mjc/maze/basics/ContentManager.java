package com.mjc.maze.basics;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;

public class ContentManager  implements Serializable
{
  
	private static final long serialVersionUID = -2513582125516473794L;
	AssetManager assetManager;

    public ContentManager(AssetManager assetManager)
    {
        this.assetManager = assetManager;

    }

    public Bitmap Load (String path)
    {
        InputStream inputStream = null;
        Bitmap bitmap;
        try
        {
            inputStream = assetManager.open(path, AssetManager.ACCESS_STREAMING);

        }
        catch (IOException e)
        {            
            e.printStackTrace();
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        bitmap = BitmapFactory.decodeStream(inputStream, null, options);

        return bitmap;
    }

    public Typeface LoadFont (String path)
    {
        return Typeface.createFromAsset(assetManager, path);
    }
}
