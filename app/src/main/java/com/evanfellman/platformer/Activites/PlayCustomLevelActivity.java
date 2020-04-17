package com.evanfellman.platformer.Activites;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.evanfellman.platformer.R;
import com.evanfellman.platformer.Sprites.Player;
import com.evanfellman.platformer.Sprites.Thing;
import java.util.ArrayList;

import static com.evanfellman.platformer.Activites.MainActivity.screen;

public class PlayCustomLevelActivity extends AppCompatActivity {
    public static Button upBtn;
    public static Button leftBtn;
    public static Button rightBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int levelNum = getIntent().getIntExtra("levelNum", 0);
//        View customView = new CustomView(this, levelNum);
//        this.addContentView(customView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (screen.x * (1.0 / 2))));
//        upBtn = new Button(this);
//        upBtn.setText("^");
//        this.addContentView(upBtn, new RelativeLayout.LayoutParams(screen.y, screen.x / 4));
//        upBtn.setY(screen.x / 2);
//        rightBtn = new Button(this);
//        rightBtn.setText(">");
//        this.addContentView(rightBtn, new RelativeLayout.LayoutParams(screen.y / 2, screen.x / 4));
//        rightBtn.setY(3 * screen.x / 4);
//        rightBtn.setX(screen.y / 2);
//        leftBtn = new Button(this);
//        leftBtn.setText("<");
//        this.addContentView(leftBtn, new RelativeLayout.LayoutParams(screen.y / 2, screen.x / 4));
//        leftBtn.setY(3 * screen.x / 4);
//        leftBtn.setX(0);


//        layout.addView(customView);
//        setContentView(layout.getId());
        this.setContentView(new GameSurface(this));
    }

    @Override
    public void onStart(){
        super.onStart();


    }

    @Override
    public void onBackPressed(){
        //don't do anything
    }
}

class CustomView extends SurfaceView implements SurfaceHolder.Callback {
    int levelNum;
    public static Canvas canvas;
    public static DrawThread drawThread;
    public CustomView(final Context context, int levelNum) {
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        this.levelNum = levelNum;
        MainActivity.loadLevel("./level0.png");
        canvas = new Canvas();
        canvas.drawColor(Color.RED);
        getHolder().addCallback(this);
    }
    public static Canvas drawMe(){
        Canvas canvas = new Canvas();
        MainActivity.upPressed = PlayCustomLevelActivity.upBtn.isPressed();
        MainActivity.leftPressed = PlayCustomLevelActivity.leftBtn.isPressed();
        MainActivity.rightPressed = PlayCustomLevelActivity.rightBtn.isPressed();
//        Paint p = new Paint();
//        p.setARGB(255, 255, 0, 0);
//        c.drawRect(new Rect(0,0,c.getWidth(), c.getHeight()), p);
//        System.out.println("hi");
//        int ttt = 2;
//        if(ttt > 1){
//            return;
//        }
//        drawBackground(canvas);
        if(MainActivity.deadPlayer) {
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
            MainActivity.deadPlayerCounter--;
            Paint paint = new Paint();
            paint.setColor(Color.argb(10 + (75 - MainActivity.deadPlayerCounter), 255, 0, 0));
            if(MainActivity.deadPlayerCounter == 0) {
                MainActivity.loadLevel("./level0.png");
            }
        } else {
            for(int i = -1 * canvas.getWidth(); i < canvas.getWidth(); i++) {
                for (int j = -1 * canvas.getHeight(); j < canvas.getHeight(); j++) {
                    ArrayList<Thing> listOfThings = MainActivity.getFromLevel(i, j);
                    for (Thing t : listOfThings) {
                        if (!t.id.equals("player")) {
                            MainActivity.deadPlayer = MainActivity.deadPlayer || t.move();
                            t.display(canvas, MainActivity.cameraX, MainActivity.cameraY);
                            System.out.println(t.toString());
                        }
                    }
                }
            }
            for(Player i: MainActivity.player) {
                i.display(canvas, MainActivity.cameraX, MainActivity.cameraY);
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
            if(MainActivity.deadPlayer) {
                for (Player i : MainActivity.player) {
                    i.setDx(0);
                    i.setDy(-15);
                }
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return canvas;
    }

    void drawBackground(Canvas c){
        //draw clouds
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(this, getHolder());
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }
}

class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;

    public GameSurface(Context context)  {
        super(context);

        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);

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

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        //call display here
//        Paint p = new Paint();
//        p.setARGB(255, 255, 0, 0);
//        canvas.drawRect(new Rect(100, 100,500,600), p);
        for(int i = -1 * canvas.getWidth(); i < canvas.getWidth(); i++) {
            for (int j = -1 * canvas.getHeight(); j < canvas.getHeight(); j++) {
                ArrayList<Thing> listOfThings = MainActivity.getFromLevel(i, j);
                for (Thing t : listOfThings) {
                    if (!t.id.equals("player")) {
//                        MainActivity.deadPlayer = MainActivity.deadPlayer || t.move();
                        t.display(canvas, MainActivity.cameraX, MainActivity.cameraY);
//                        System.out.println(t.toString());
                    }
                }
            }
        }
        for(Player i: MainActivity.player) {
            i.display(canvas, MainActivity.cameraX, MainActivity.cameraY);
        }
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.chibi1);
//        this.chibi1 = new ChibiCharacter(this,chibiBitmap1,100,50);
        MainActivity.loadLevel("./level0.png");
        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
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
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }

}


class GameThread extends Thread {

    private boolean running;
    private GameSurface gameSurface;
    private SurfaceHolder surfaceHolder;

    public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder)  {
        this.gameSurface= gameSurface;
        this.surfaceHolder= surfaceHolder;
    }

    @Override
    public void run()  {
        long startTime = System.currentTimeMillis();

        while(running)  {
            System.out.println("updating visuals!");
            Canvas canvas= new Canvas();
            try {
                // Get Canvas from Holder and lock it.
                canvas = this.surfaceHolder.lockCanvas(null);
                this.gameSurface.draw(canvas);
                // Synchronized
                synchronized (canvas)  {
//                    this.gameSurface.update(canvas);

                }
            }catch(Exception e)  {
                e.printStackTrace();
                System.out.println("oopsss");
                // Do nothing.
            } finally {
                if(canvas!= null)  {
                    // Unlock Canvas.
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            System.out.println("got herereeee");
//            long now = System.nanoTime() ;
            // Interval to redraw game
            // (Change nanoseconds to milliseconds)
            long now = System.currentTimeMillis();
            long waitTime = (now - startTime)/100;
            if(waitTime < 10)  {
                waitTime= 10; // Millisecond.
            }
            System.out.print(" Wait Time="+ waitTime);

            try {
                // Sleep.
                this.sleep(waitTime);
            } catch(InterruptedException e)  {

            }
            startTime = System.nanoTime();
            System.out.print(".");
        }
    }

    public void setRunning(boolean running)  {
        this.running= running;
    }
}

class DrawThread extends Thread {
    private CustomView cv;
    private SurfaceHolder surfaceHolder;

    DrawThread(CustomView cv, SurfaceHolder surfaceHolder) {
        this.cv = cv;
        this.surfaceHolder = surfaceHolder;
    }

    public void run() {
        Canvas canvas;
        while (true) {
//            startTime = System.nanoTime();
            canvas = null;

            try {
//                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
//                    canvas = CustomView.drawMe();
//                    this.cv.draw(canvas);
                }
            } catch (Exception e) {       }
            finally {
                if (true || canvas != null){
                    try {
//                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            timeMillis = (System.nanoTime() - startTime) / 1000000;
//            waitTime = targetTime - timeMillis;

//            try {
//                this.sleep(waitTime);
//            } catch (Exception e) {}

//            totalTime += System.nanoTime() - startTime;
//            frameCount++;
//            if (frameCount == targetFPS)        {
//                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
//                frameCount = 0;
//                totalTime = 0;
//                System.out.println(averageFPS);
//            }
        }
//        while (true) {
//            CustomView.canvas = null;
//            System.out.println("in while loop thread thing");
//            try {
//                CustomView.canvas = this.surfaceHolder.lockCanvas();
//                synchronized(surfaceHolder) {
//                    CustomView.canvas.drawARGB(255, 255, 0, 0);
//                    this.cv.draw(CustomView.canvas);
//                }
//            } catch (Exception e) {} finally {
//                if (CustomView.canvas != null) {
//                    try {
//                        surfaceHolder.unlockCanvasAndPost(CustomView.canvas);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//        }
//        System.out.println("hi");
//        System.out.println(cv.levelNum);
//        MainActivity.upPressed = PlayCustomLevelActivity.upBtn.isPressed();
//        MainActivity.leftPressed = PlayCustomLevelActivity.leftBtn.isPressed();
//        MainActivity.rightPressed = PlayCustomLevelActivity.rightBtn.isPressed();
//        Canvas c = new Canvas();
//        Paint p = new Paint();
//        p.setARGB(255, 128, 0, 0);
//        c.drawRect(new Rect(0, 0, 100, 500), p);
//        cv.draw(c);
//        for(int i = -1 * canvas.getWidth(); i < canvas.getWidth(); i++){
//            for(int j = -1 * canvas.getHeight(); j < canvas.getHeight(); j++){
//                ArrayList<Thing> listOfThings = MainActivity.getFromLevel(i, j);
//                for(Thing t: listOfThings){
//                    if(t.id.equals("player")){
//                        continue;
//                    } else {
//                        t.display(canvas, MainActivity.cameraX, MainActivity.cameraY);
//                    }
//                }
//            }
//        }
//        System.out.println("got here");
//        while(true) {
//            Canvas c = new Canvas();
//            Paint p = new Paint();
//            p.setARGB(255, 128, 0, 0);
//            c.drawRect(new Rect(0, 0, 100, 500), p);
//
//            c.drawColor(Color.RED);
//            cv.draw(c);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("hi");
////            cv.drawMe();
//            Canvas c = new Canvas();
//            c.drawRect(new Re);
//        }
    }
}