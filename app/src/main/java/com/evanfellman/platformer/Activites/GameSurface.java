package com.evanfellman.platformer.Activites;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.evanfellman.platformer.Sprites.Player;
import com.evanfellman.platformer.Sprites.Thing;

import java.util.ArrayList;

import static com.evanfellman.platformer.Activites.MainActivity.screen;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;

    public GameSurface(Context context)  {
        super(context);
        this.gameThread = new GameThread();
        // Make Game Surface focusable so it can handle events. .
//        this.setFocusable(true);

        // Sét callback.
        this.getHolder().addCallback(this);
    }

    public void update(Canvas canvas)  {
        int a = 2;
        if(a < 5){
            return;
        }
        for(int i = -1 * canvas.getWidth(); i < canvas.getWidth(); i++) {
            for (int j = -1 * canvas.getHeight(); j < canvas.getHeight(); j++) {
                ArrayList<Thing> listOfThings = MainActivity.getFromLevel(i, j);
                for (Thing t : listOfThings) {
                    if (!t.id.equals("player")) {
                        MainActivity.deadPlayer = MainActivity.deadPlayer || t.move();
//                        t.display(canvas, MainActivity.cameraX, MainActivity.cameraY);
//                        System.out.println(t.toString());
                    }
                }
            }
        }
        for(Player i: MainActivity.player) {
            i.move();
            i.display(canvas, MainActivity.cameraX, MainActivity.cameraY);
            while(i.getX() - MainActivity.cameraX < screen.x / 4) {
                MainActivity.cameraX--;
            }
            while(i.getX() - MainActivity.cameraX > 3 * screen.x / 4) {
                MainActivity.cameraX++;
            }
            while(i.getY() - MainActivity.cameraY < screen.y / 4) {
                MainActivity.cameraY--;
            }
            while(i.getY() - MainActivity.cameraY > 3 * screen.y / 4) {
                MainActivity.cameraY++;
            }
        }
        //call move on all things here
    }
    public static int redColorCounter = 1;
    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        //call display here
        Paint p = new Paint();
        p.setARGB(255, redColorCounter, 0, 0);
        canvas.drawRect(new Rect(100, 100,500,600), p);
        redColorCounter--;
        if (redColorCounter == 0){
            redColorCounter = 255;
        }
//        for(int i = -1 * canvas.getWidth(); i < canvas.getWidth(); i++) {
//            for (int j = -1 * canvas.getHeight(); j < canvas.getHeight(); j++) {
//                ArrayList<Thing> listOfThings = MainActivity.getFromLevel(i, j);
//                for (Thing t : listOfThings) {
//                    if (!t.id.equals("player")) {
////                        MainActivity.deadPlayer = MainActivity.deadPlayer || t.move();
//                        t.display(canvas, MainActivity.cameraX, MainActivity.cameraY);
////                        System.out.println(t.toString());
//                    }
//                }
//            }
//        }
//        for(Player i: MainActivity.player) {
//            i.display(canvas, MainActivity.cameraX, MainActivity.cameraY);
//        }
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.chibi1);
//        this.chibi1 = new ChibiCharacter(this,chibiBitmap1,100,50);
//        MainActivity.loadLevel("./level0.png");
//        TestClass a = new TestClass(holder);
//        System.out.println(holder);
//        System.out.println("hi");
//        this.gameThread = new GameThread(this,holder);

//        this.gameThread.setRunning(true);
//        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            try {
//                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }

}


class TestClass{
    public SurfaceHolder x;

    public TestClass(SurfaceHolder y){
        this.x = y;
    }
}