package com.evanfellman.platformer.Activites;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.evanfellman.platformer.R;
import com.evanfellman.platformer.Sprites.Player;
import com.evanfellman.platformer.Sprites.Thing;
import java.util.ArrayList;

public class PlayCustomLevelActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int levelNum = getIntent().getIntExtra("levelNum", 0);
        View customView = new CustomView(this, levelNum, new View[] {findViewById(R.id.CustomLevelUp), findViewById(R.id.CustomLevelLeft), findViewById(R.id.CustomLevelRight)});
        LinearLayout layout = new LinearLayout(this);
        this.addContentView(customView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (MainActivity.screen.x * (1.0 / 2))));
//        layout.addView(customView);
//        setContentView(layout.getId());
    }

    @Override
    public void onBackPressed(){
        //don't do anything
    }
}

class CustomView extends View{
    View[] buttons;
    int levelNum;

    public CustomView(final Context context, int levelNum, View[] buttons) {
        super(context);
        this.levelNum = levelNum;
        this.buttons = buttons;
        System.out.println("hiii");
        System.out.println(findViewById(R.id.CustomLevelUp));
//        MainActivity.loadLevel("./level0.png");
    }

    @Override
    public void onDraw(Canvas c){
        Paint p = new Paint();
        p.setARGB(255, 255, 0, 0);
        c.drawRect(new Rect(0,0,c.getWidth(), c.getHeight()), p);
        System.out.println("hi");
        int ttt = 2;
        if(ttt > 1){
            return;
        }
//        MainActivity.upPressed = this.buttons[0].isPressed();
//        MainActivity.leftPressed = this.buttons[1].isPressed();
//        MainActivity.rightPressed = this.buttons[2].isPressed();
        drawBackground(c);
        if(MainActivity.deadPlayer) {
            for(int i = -1 * c.getWidth(); i < c.getWidth(); i++){
                for(int j = -1 * c.getHeight(); j < c.getHeight(); j++){
                    ArrayList<Thing> listOfThings = MainActivity.getFromLevel(i, j);
                    for(Thing t: listOfThings){
                        if(t.id.equals("MainActivity.player")){
                            continue;
                        } else {
                            t.display(c, MainActivity.cameraX, MainActivity.cameraY);
                        }
                    }
                }
            }
            for(Player i: MainActivity.player) {
                i.move();
                i.display(c, MainActivity.cameraX, MainActivity.cameraY);
                while(i.getX() - MainActivity.cameraX < MainActivity.screen.x / 4) {
                    MainActivity.cameraX--;
                }
                while(i.getX() - MainActivity.cameraX > 3 * MainActivity.screen.x / 4) {
                    MainActivity.cameraX++;
                }
                while(i.getY() - MainActivity.cameraY < MainActivity.screen.y / 4) {
                    MainActivity.cameraY--;
                }
                while(i.getY() - MainActivity.cameraY > 3 * MainActivity.screen.y / 4) {
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
            for(int i = -1 * c.getWidth(); i < c.getWidth(); i++) {
                for (int j = -1 * c.getHeight(); j < c.getHeight(); j++) {
                    ArrayList<Thing> listOfThings = MainActivity.getFromLevel(i, j);
                    for (Thing t : listOfThings) {
                        if (!t.id.equals("MainActivity.player")) {
                            MainActivity.deadPlayer = MainActivity.deadPlayer || t.move();
                            t.display(c, MainActivity.cameraX, MainActivity.cameraY);
                        }
                    }
                }
            }
            for(Player i: MainActivity.player) {
                i.display(c, MainActivity.cameraX, MainActivity.cameraY);
            }
            for(Player i: MainActivity.player) {
                i.move();
                i.display(c, MainActivity.cameraX, MainActivity.cameraY);
                while(i.getX() - MainActivity.cameraX < MainActivity.screen.x / 4) {
                    MainActivity.cameraX--;
                }
                while(i.getX() - MainActivity.cameraX > 3 * MainActivity.screen.x / 4) {
                    MainActivity.cameraX++;
                }
                while(i.getY() - MainActivity.cameraY < MainActivity.screen.y / 4) {
                    MainActivity.cameraY--;
                }
                while(i.getY() - MainActivity.cameraY > 3 * MainActivity.screen.y / 4) {
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
    }

    void drawBackground(Canvas c){
        //draw clouds
    }
}
