package com.mjc.maze;

import com.mjc.maze.basics.Point;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;

import android.content.Intent;
import android.os.Bundle;

import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

public class TheMazeActivity extends Activity {
        
	GameScreenManager manager;	
	WakeLock wakeLock;
	KeyguardLock lock;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        KeyguardManager keyguardManager = (KeyguardManager)getSystemService(Activity.KEYGUARD_SERVICE);  
        lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);  
        lock.disableKeyguard();
        
        //PowerManager pm = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
        //wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "My Lock");
        
        Point screenDimensions = new Point(getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight());
                
        manager = new GameScreenManager(this, screenDimensions);
        setContentView(manager);
        
        
        
    }
	
	public void finish()
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.onPause();
	}

	@Override
	protected void onPause() {

		manager.paused=true;	
		//wakeLock.release();
		lock.reenableKeyguard();
		super.onPause();		
		
	}

	@Override
	protected void onResume() {
		manager.InitView();
		manager.paused=false;

		super.onResume();
	//	wakeLock.acquire();		
		
				
		
	}
	
	
}