package com.mjc.maze;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.view.SurfaceHolder;

public class PaintThread extends Thread{
	private SurfaceHolder mSurfaceHolder;
	//private Handler mHandler;
	//private Context mContext;
	private Paint mLinePaint;
	private Paint blackPaint;
	private GameScreenManager screenManager;	
	
	public void setScreenManager(GameScreenManager screenManager) {
		this.screenManager = screenManager;
	}


	//for consistent rendering
	private long sleepTime;
	//amount of time to sleep for (in milliseconds)
	private long delay=70;

	//state of game (Running or Paused).
	int state = 1;
	public final static int RUNNING = 1;
	public final static int PAUSED = 2;

	public PaintThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {

		//data about the screen
		mSurfaceHolder = surfaceHolder;
		//this.mHandler = handler;
		//this.mContext = context;

		//standard game painter. Used to draw on the canvas
		mLinePaint = new Paint();
		mLinePaint.setARGB(255, 0, 255, 0);
		//black painter below to clear the screen before the game is rendered
		blackPaint = new Paint();
		blackPaint.setARGB(255, 0, 0, 0);
		//mLinePaint.setAntiAlias(true);


		
	}

	
	

	//This is the most important part of the code. It is invoked when the call to start() is
	//made from the SurfaceView class. It loops continuously until the game is finished or
	//the application is suspended.
	@Override
	public void run() {

		//UPDATE
		while (state==RUNNING) {
			//time before update
			long beforeTime = System.nanoTime();
			//This is where we update the game engine
			//UPDATE HERE!!!!!!!
			if ((screenManager!=null) && (!screenManager.paused))
				screenManager.Update();

			//DRAW
			Canvas c = null;
			try {
				//lock canvas so nothing else can use it
				c = mSurfaceHolder.lockCanvas(null);
				synchronized (mSurfaceHolder) {
					//clear the screen with the black painter.
					c.drawRect(0, 0, c.getWidth(), c.getHeight(), blackPaint);
					//This is where we draw the game engine.
					//DRAW HERE!!!!!!
					if (screenManager!=null)
						screenManager.Draw(c);
				}
			} finally {
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface in an
				// inconsistent state
				if (c != null) {
					mSurfaceHolder.unlockCanvasAndPost(c);
				}
			}



			//SLEEP
			//Sleep time. Time required to sleep to keep game consistent
			//This starts with the specified delay time (in milliseconds) then subtracts from that the
			//actual time it took to update and render the game. This allows our game to render smoothly.
			this.sleepTime = delay-((System.nanoTime()-beforeTime)/1000000L);

			try {
				//actual sleep code
				if(sleepTime>0){
					sleep(sleepTime);
				}
			} catch (InterruptedException ex) {
				//Logger.getLogger(PaintThread.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}
}

