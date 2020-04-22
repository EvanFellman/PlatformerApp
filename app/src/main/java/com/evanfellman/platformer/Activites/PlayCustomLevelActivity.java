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
        View customView = new GameView(this, levelNum);
        this.addContentView(customView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (screen.x * (1.0 / 2))));
        upBtn = new Button(this);
        upBtn.setText("^");
        this.addContentView(upBtn, new RelativeLayout.LayoutParams(screen.y, screen.x / 4));
        upBtn.setY(screen.x / 2);
        rightBtn = new Button(this);
        rightBtn.setText(">");
        this.addContentView(rightBtn, new RelativeLayout.LayoutParams(screen.y / 2, screen.x / 4));
        rightBtn.setY(3 * screen.x / 4);
        rightBtn.setX(screen.y / 2);
        leftBtn = new Button(this);
        leftBtn.setText("<");
        this.addContentView(leftBtn, new RelativeLayout.LayoutParams(screen.y / 2, screen.x / 4));
        leftBtn.setY(3 * screen.x / 4);
        leftBtn.setX(0);


//        layout.addView(customView);
//        setContentView(layout.getId());\
        this.setContentView(new GameSurface(this));
//        GameSurface a = new GameSurface(this);
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

//class CustomView extends SurfaceView implements SurfaceHolder.Callback {
//    int levelNum;
//    public static Canvas canvas;
//    public static DrawThread drawThread;
//    public CustomView(final Context context, int levelNum) {
//        super(context);
//        SurfaceHolder holder = getHolder();
//        holder.addCallback(this);
//        this.levelNum = levelNum;
//        MainActivity.loadLevel("./level0.png");
//        canvas = new Canvas();
//        canvas.drawColor(Color.RED);
//        getHolder().addCallback(this);
//    }
//    public static Canvas drawMe(){
//        Canvas canvas = new Canvas();
//        MainActivity.upPressed = PlayCustomLevelActivity.upBtn.isPressed();
//        MainActivity.leftPressed = PlayCustomLevelActivity.leftBtn.isPressed();
//        MainActivity.rightPressed = PlayCustomLevelActivity.rightBtn.isPressed();
////        Paint p = new Paint();
////        p.setARGB(255, 255, 0, 0);
////        c.drawRect(new Rect(0,0,c.getWidth(), c.getHeight()), p);
////        System.out.println("hi");
////        int ttt = 2;
////        if(ttt > 1){
////            return;
////        }
////        drawBackground(canvas);
//        if(MainActivity.deadPlayer) {
//            for(int i = -1 * canvas.getWidth(); i < canvas.getWidth(); i++){
//                for(int j = -1 * canvas.getHeight(); j < canvas.getHeight(); j++){
//                    ArrayList<Thing> listOfThings = MainActivity.getFromLevel(i, j);
//                    for(Thing t: listOfThings){
//                        if(t.id.equals("player")){
//                            continue;
//                        } else {
//                            t.display(canvas, MainActivity.cameraX, MainActivity.cameraY);
//                        }
//                    }
//                }
//            }
//            for(Player i: MainActivity.player) {
//                i.move();
//                i.display(canvas, MainActivity.cameraX, MainActivity.cameraY);
//                while(i.getX() - MainActivity.cameraX < screen.x / 4) {
//                    MainActivity.cameraX--;
//                }
//                while(i.getX() - MainActivity.cameraX > 3 * screen.x / 4) {
//                    MainActivity.cameraX++;
//                }
//                while(i.getY() - MainActivity.cameraY < screen.y / 4) {
//                    MainActivity.cameraY--;
//                }
//                while(i.getY() - MainActivity.cameraY > 3 * screen.y / 4) {
//                    MainActivity.cameraY++;
//                }
//            }
//            MainActivity.deadPlayerCounter--;
//            Paint paint = new Paint();
//            paint.setColor(Color.argb(10 + (75 - MainActivity.deadPlayerCounter), 255, 0, 0));
//            if(MainActivity.deadPlayerCounter == 0) {
//                MainActivity.loadLevel("./level0.png");
//            }
//        } else {
//            for(int i = -1 * canvas.getWidth(); i < canvas.getWidth(); i++) {
//                for (int j = -1 * canvas.getHeight(); j < canvas.getHeight(); j++) {
//                    ArrayList<Thing> listOfThings = MainActivity.getFromLevel(i, j);
//                    for (Thing t : listOfThings) {
//                        if (!t.id.equals("player")) {
//                            MainActivity.deadPlayer = MainActivity.deadPlayer || t.move();
//                            t.display(canvas, MainActivity.cameraX, MainActivity.cameraY);
//                            System.out.println(t.toString());
//                        }
//                    }
//                }
//            }
//            for(Player i: MainActivity.player) {
//                i.display(canvas, MainActivity.cameraX, MainActivity.cameraY);
//            }
//            for(Player i: MainActivity.player) {
//                i.move();
//                i.display(canvas, MainActivity.cameraX, MainActivity.cameraY);
//                while(i.getX() - MainActivity.cameraX < screen.x / 4) {
//                    MainActivity.cameraX--;
//                }
//                while(i.getX() - MainActivity.cameraX > 3 * screen.x / 4) {
//                    MainActivity.cameraX++;
//                }
//                while(i.getY() - MainActivity.cameraY < screen.y / 4) {
//                    MainActivity.cameraY--;
//                }
//                while(i.getY() - MainActivity.cameraY > 3 * screen.y / 4) {
//                    MainActivity.cameraY++;
//                }
//            }
//            if(MainActivity.deadPlayer) {
//                for (Player i : MainActivity.player) {
//                    i.setDx(0);
//                    i.setDy(-15);
//                }
//            }
//        }
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return canvas;
//    }
//
//    void drawBackground(Canvas c){
//        //draw clouds
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
////        drawThread = new DrawThread(this, getHolder());
////        drawThread.start();
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        boolean retry = true;
//        while (retry) {
//            try {
//                drawThread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            retry = false;
//        }
//    }
//}