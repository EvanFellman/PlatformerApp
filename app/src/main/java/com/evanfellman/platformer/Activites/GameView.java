package com.evanfellman.platformer.Activites;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.evanfellman.platformer.Sprites.Thing;

import java.util.ArrayList;

/**
 * Created by rushd on 7/5/2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private int levelNum;

    public GameView(Context context, int levelNum) {
        super(context);
        this.setLayoutParams(new LinearLayout.LayoutParams(500, 200));
        this.levelNum = levelNum;
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.start();
        thread.setRunning(true);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update(Canvas canvas) {
        MainActivity.upPressed = CustomActivity.upBtn.isPressed();
        MainActivity.leftPressed = CustomActivity.leftBtn.isPressed();
        MainActivity.rightPressed = CustomActivity.rightBtn.isPressed();
        for(int i = -1 * canvas.getWidth(); i < canvas.getWidth(); i++){
            for(int j = -1 * canvas.getHeight(); j < canvas.getHeight(); j++){
                ArrayList<Thing> listOfThings = MainActivity.getFromLevel(i, j);
                for(Thing t: listOfThings){
                    if(t.id.equals("player")){
                        continue;
                    } else {
                        t.display(canvas, MainActivity.cameraX, MainActivity.cameraY);
                    }
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        if(canvas != null) {
            //draw the sprites here
        }
    }
}