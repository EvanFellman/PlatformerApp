package com.evanfellman.platformer.Activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import static com.evanfellman.platformer.Activites.MainActivity.loadLevel;
import static com.evanfellman.platformer.Activites.MainActivity.screen;

public class CustomActivity extends Activity {
    public static Button upBtn;
    public static Button leftBtn;
    public static Button rightBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int levelNum = 0;
        View customView = new GameView(this, levelNum);
//        setContentView(customView);
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
        loadLevel("baaaaaaaa");
    }
}
