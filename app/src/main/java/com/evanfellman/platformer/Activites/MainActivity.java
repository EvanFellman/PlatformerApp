package com.evanfellman.platformer.Activites;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Display;
import android.view.View;
import com.evanfellman.platformer.R;
import com.evanfellman.platformer.Sprites.BlueGate;
import com.evanfellman.platformer.Sprites.BlueReverseGate;
import com.evanfellman.platformer.Sprites.BlueSwitch;
import com.evanfellman.platformer.Sprites.DisappearingWall;
import com.evanfellman.platformer.Sprites.DoubleJump;
import com.evanfellman.platformer.Sprites.EnemyBullet;
import com.evanfellman.platformer.Sprites.EnemyDumb;
import com.evanfellman.platformer.Sprites.EnemyNoJump;
import com.evanfellman.platformer.Sprites.EnemyOnlyJump;
import com.evanfellman.platformer.Sprites.EnemySmart;
import com.evanfellman.platformer.Sprites.NextLevel;
import com.evanfellman.platformer.Sprites.Player;
import com.evanfellman.platformer.Sprites.RedGate;
import com.evanfellman.platformer.Sprites.RedReverseGate;
import com.evanfellman.platformer.Sprites.RedSwitch;
import com.evanfellman.platformer.Sprites.Shield;
import com.evanfellman.platformer.Sprites.Shooter;
import com.evanfellman.platformer.Sprites.Spike;
import com.evanfellman.platformer.Sprites.SpikeDestroyer;
import com.evanfellman.platformer.Sprites.Thing;
import com.evanfellman.platformer.Sprites.Wall;
import com.evanfellman.platformer.Sprites.WallMoving;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static Bitmap TEXTURES;
    public static Bitmap TEST_LEVEL;
    public static int cameraX;
    public static int cameraY;
    public static int startX;
    public static int startY;
    public static boolean isBlueGateOpen = false;
    public static boolean isRedGateOpen = false;
    public static ArrayList<Player> player;
    public static boolean deadPlayer;
    public static int deadPlayerCounter;
    public static Point screen;
    public static final double GRAVITY = 0.5;
    public static double DEATH_BELOW;
    public static boolean upPressed = false;
    public static boolean leftPressed = false;
    public static boolean rightPressed = false;
    //Indexed by x coordinate then y coordinate then its a set
    public static SparseArray<SparseArray<ArrayList<Thing>>> level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TEST_LEVEL = BitmapFactory.decodeResource(getResources(), R.drawable.test_level);
        TEXTURES = BitmapFactory.decodeResource(getResources(), R.drawable.textures);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Display display = getWindowManager().getDefaultDisplay();
        screen = new Point();
        display.getSize(screen);
    }

    public void goToCustomLevel(View view){
        Intent goToCustomLevelIntent = new Intent(this, PlayCustomLevelActivity.class);
        goToCustomLevelIntent.putExtra("levelNum", 0);
        startActivity(goToCustomLevelIntent);
    }

    public void goToRandomLevel(View view){
        Intent goToRandomLevelIntent = new Intent(this, PlayRandomLevelActivity.class);
        startActivity(goToRandomLevelIntent);
    }

    @Override
    public void onBackPressed(){
        //don't do anything
    }

    public static ArrayList<Thing> getFromLevel(double xd, double yd){
        int x = (int) xd;
        int y = (int) yd;
        return getFromLevel(x, y);
    }

    public static ArrayList<Thing> getFromLevel(int x, int y){
        if(level.indexOfKey(x) >= 0 && level.get(x).indexOfKey(y) >= 0){
            return level.get(x).get(y);
        } else {
            return new ArrayList<>();
        }
    }

    public static void putInLevel(Thing x){
        if(level.indexOfKey((int) x.getX()) < 0){
            ArrayList<Thing> a = new ArrayList<>();
            a.add(x);
            SparseArray<ArrayList<Thing>> h = new SparseArray<>();
            level.put((int) x.getX(), h);
        } else if(level.get((int) x.getX()).indexOfKey((int) x.getY()) < 0){
            ArrayList<Thing> a = new ArrayList<>();
            a.add(x);
            level.get((int) x.getX()).put((int) x.getY(), a);
        } else {
            level.get((int) x.getX()).get((int) x.getY()).add(x);
        }
    }

    public static void removeFromLevel(Thing x){
        if(!(level.indexOfKey((int) x.getX()) < 0 || level.get((int) x.getX()).indexOfKey((int) x.getY()) < 0)){
            level.get((int)x.getX()).get((int)x.getY()).remove(x);
        }
    }

    public static void loadLevel(String file) {
        //load from file
        upPressed = false;
        leftPressed = false;
        rightPressed = false;
//        Bitmap levelImg = BitmapFactory.decodeFile(file);
        Bitmap levelImg = TEST_LEVEL;
        player = new ArrayList<Player>();
        level = new SparseArray<>();
        isBlueGateOpen = false;
        isRedGateOpen = false;
        deadPlayer = false;
        deadPlayerCounter = 100;
        System.out.println(levelImg);
        DEATH_BELOW = levelImg.getHeight() * Thing.HEIGHT;
        for(int x = 0; x < levelImg.getWidth(); x++) {
            for (int y = 0; y < levelImg.getHeight(); y++) {
                int pixel = levelImg.getPixel(x, y);
                if (Color.red(pixel) == 0 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new Wall(Thing.WIDTH * x, Thing.HEIGHT * y));
                } else if (Color.red(pixel) == 0 && Color.green(pixel) == 255 && Color.blue(pixel) == 0) {
                    putInLevel(new NextLevel(Thing.WIDTH * x, Thing.HEIGHT * y));
                } else if (Color.red(pixel) == 0 && Color.green(pixel) == 0 && Color.blue(pixel) == 255) {
                    startX = Thing.WIDTH * x;
                    startY = Thing.HEIGHT * y;
                    Player p = new Player(Thing.WIDTH * x, Thing.HEIGHT * y);
                    player.add(p);
                    putInLevel(p);
                } else if (Color.red(pixel) == 255 && Color.green(pixel) == 255 && Color.blue(pixel) == 2) {
                    putInLevel(new EnemyNoJump(Thing.WIDTH * x, Thing.HEIGHT * y, Thing.FAST_SPEED));
                } else if (Color.red(pixel) == 255 && Color.green(pixel) == 255 && Color.blue(pixel) == 1) {
                    putInLevel(new EnemyNoJump(Thing.WIDTH * x, Thing.HEIGHT * y, Thing.SLOW_SPEED));
                } else if (Color.red(pixel) == 255 && Color.green(pixel) == 255 && Color.blue(pixel) == 3) {
                    putInLevel(new EnemyOnlyJump(Thing.WIDTH * x, Thing.HEIGHT * y));
                } else if (Color.red(pixel) == 0 && Color.green(pixel) == 0 && Color.blue(pixel) == 254) {
                    putInLevel(new BlueGate(Thing.WIDTH * x, Thing.HEIGHT * y));
                } else if (Color.red(pixel) == 0 && Color.green(pixel) == 0 && Color.blue(pixel) == 253) {
                    putInLevel(new BlueReverseGate(Thing.WIDTH * x, Thing.HEIGHT * y));
                } else if (Color.red(pixel) == 0 && Color.green(pixel) == 0 && Color.blue(pixel) == 252) {
                    putInLevel(new BlueSwitch(Thing.WIDTH * x, Thing.HEIGHT * y));
                } else if (Color.red(pixel) == 254 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new RedGate(Thing.WIDTH * x, Thing.HEIGHT * y));
                } else if (Color.red(pixel) == 253 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new RedReverseGate(Thing.WIDTH * x, Thing.HEIGHT * y));
                } else if (Color.red(pixel) == 252 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new RedSwitch(Thing.WIDTH * x, Thing.HEIGHT * y));
                } else if (Color.red(pixel) == 251 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new EnemyDumb(Thing.WIDTH * x, Thing.HEIGHT * y, true, Thing.FAST_SPEED));
                } else if (Color.red(pixel) == 250 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new EnemyDumb(Thing.WIDTH * x, Thing.HEIGHT * y, true, Thing.SLOW_SPEED));
                } else if (Color.red(pixel) == 248 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new EnemyDumb(Thing.WIDTH * x, Thing.HEIGHT * y, false, Thing.FAST_SPEED));
                } else if (Color.red(pixel) == 247 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new EnemyDumb(Thing.WIDTH * x, Thing.HEIGHT * y, false, Thing.SLOW_SPEED));
                } else if (Color.red(pixel) == 0 && Color.green(pixel) == 254 && Color.blue(pixel) == 0) {
                    putInLevel(new DisappearingWall(Thing.WIDTH * x, Thing.HEIGHT * y));
                } else if (Color.red(pixel) == 0 && Color.green(pixel) == 253 && Color.blue(pixel) == 0) {
                    putInLevel(new WallMoving(Thing.WIDTH * x, Thing.HEIGHT * y, WallMoving.UP, Thing.SLOW_SPEED));
                } else if (Color.red(pixel) == 0 && Color.green(pixel) == 252 && Color.blue(pixel) == 0) {
                    putInLevel(new WallMoving(Thing.WIDTH * x, Thing.HEIGHT * y, WallMoving.DOWN, Thing.SLOW_SPEED));
                } else if (Color.red(pixel) == 0 && Color.green(pixel) == 251 && Color.blue(pixel) == 0) {
                    putInLevel(new WallMoving(Thing.WIDTH * x, Thing.HEIGHT * y, WallMoving.LEFT, Thing.SLOW_SPEED));
                } else if (Color.red(pixel) == 0 && Color.green(pixel) == 250 && Color.blue(pixel) == 0) {
                    putInLevel(new WallMoving(Thing.WIDTH * x, Thing.HEIGHT * y, WallMoving.RIGHT, Thing.SLOW_SPEED));
                } else if (Color.red(pixel) == 0 && Color.green(pixel) == 249 && Color.blue(pixel) == 0) {
                    putInLevel(new WallMoving(Thing.WIDTH * x, Thing.HEIGHT * y, WallMoving.UP, Thing.FAST_SPEED));
                } else if (Color.red(pixel) == 0 && Color.green(pixel) == 248 && Color.blue(pixel) == 0) {
                    putInLevel(new WallMoving(Thing.WIDTH * x, Thing.HEIGHT * y, WallMoving.DOWN, Thing.FAST_SPEED));
                } else if (Color.red(pixel) == 0 && Color.green(pixel) == 247 && Color.blue(pixel) == 0) {
                    putInLevel(new WallMoving(Thing.WIDTH * x, Thing.HEIGHT * y, WallMoving.LEFT, Thing.FAST_SPEED));
                } else if (Color.red(pixel) == 0 && Color.green(pixel) == 246 && Color.blue(pixel) == 0) {
                    putInLevel(new WallMoving(Thing.WIDTH * x, Thing.HEIGHT * y, WallMoving.RIGHT, Thing.FAST_SPEED));
                } else if (Color.red(pixel) == 245 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new Spike(Thing.WIDTH * x, Thing.HEIGHT * y));
                } else if (Color.red(pixel) == 244 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new EnemySmart(Thing.WIDTH * x, Thing.HEIGHT * y, Thing.FAST_SPEED));
                } else if (Color.red(pixel) == 243 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new EnemySmart(Thing.WIDTH * x, Thing.HEIGHT * y, Thing.SLOW_SPEED));
                } else if (Color.red(pixel) == 242 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new EnemyBullet(Thing.WIDTH * x, Thing.HEIGHT * y, EnemyBullet.LEFT, Thing.FAST_SPEED));
                } else if (Color.red(pixel) == 241 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new EnemyBullet(Thing.WIDTH * x, Thing.HEIGHT * y, EnemyBullet.LEFT, Thing.SLOW_SPEED));
                } else if (Color.red(pixel) == 240 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new EnemyBullet(Thing.WIDTH * x, Thing.HEIGHT * y, EnemyBullet.RIGHT, Thing.FAST_SPEED));
                } else if (Color.red(pixel) == 239 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new EnemyBullet(Thing.WIDTH * x, Thing.HEIGHT * y, EnemyBullet.RIGHT, Thing.SLOW_SPEED));
                } else if (Color.red(pixel) == 238 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new EnemyBullet(Thing.WIDTH * x, Thing.HEIGHT * y, EnemyBullet.UP, Thing.FAST_SPEED));
                } else if (Color.red(pixel) == 237 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new EnemyBullet(Thing.WIDTH * x, Thing.HEIGHT * y, EnemyBullet.UP, Thing.SLOW_SPEED));
                } else if (Color.red(pixel) == 236 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new EnemyBullet(Thing.WIDTH * x, Thing.HEIGHT * y, EnemyBullet.DOWN, Thing.FAST_SPEED));
                } else if (Color.red(pixel) == 235 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new EnemyBullet(Thing.WIDTH * x, Thing.HEIGHT * y, EnemyBullet.DOWN, Thing.SLOW_SPEED));
                } else if (Color.red(pixel) == 234 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new Shooter(Thing.WIDTH * x, Thing.HEIGHT * y, Shooter.LEFT, Thing.FAST_SPEED));
                } else if (Color.red(pixel) == 233 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new Shooter(Thing.WIDTH * x, Thing.HEIGHT * y, Shooter.LEFT, Thing.SLOW_SPEED));
                } else if (Color.red(pixel) == 232 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new Shooter(Thing.WIDTH * x, Thing.HEIGHT * y, Shooter.RIGHT, Thing.FAST_SPEED));
                } else if (Color.red(pixel) == 231 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new Shooter(Thing.WIDTH * x, Thing.HEIGHT * y, Shooter.RIGHT, Thing.SLOW_SPEED));
                } else if (Color.red(pixel) == 230 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new Shooter(Thing.WIDTH * x, Thing.HEIGHT * y, Shooter.UP, Thing.FAST_SPEED));
                } else if (Color.red(pixel) == 229 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new Shooter(Thing.WIDTH * x, Thing.HEIGHT * y, Shooter.UP, Thing.SLOW_SPEED));
                } else if (Color.red(pixel) == 228 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new Shooter(Thing.WIDTH * x, Thing.HEIGHT * y, Shooter.DOWN, Thing.FAST_SPEED));
                } else if (Color.red(pixel) == 227 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new Shooter(Thing.WIDTH * x, Thing.HEIGHT * y, Shooter.DOWN, Thing.SLOW_SPEED));
                } else if (Color.red(pixel) == 226 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new Shield(Thing.WIDTH * x, Thing.HEIGHT * y));
                } else if (Color.red(pixel) == 225 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new DoubleJump(Thing.WIDTH * x, Thing.HEIGHT * y));
                } else if (Color.red(pixel) == 224 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    putInLevel(new SpikeDestroyer(Thing.WIDTH * x, Thing.HEIGHT * y));
                }
                cameraX = (int) (player.get(0).getX() - (screen.x * 0.5));
                cameraY = (int) (player.get(0).getY() - (screen.y * 0.5));
            }
        }
        System.out.println(level);
    }
}
