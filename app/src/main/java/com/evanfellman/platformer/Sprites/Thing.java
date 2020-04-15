package com.evanfellman.platformer.Sprites;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.evanfellman.platformer.Activites.MainActivity;
import com.evanfellman.platformer.R;

import static com.evanfellman.platformer.Activites.MainActivity.TEXTURES;

public abstract class Thing {
	private static int nextID = 0;
	private int uniqueID;
	public double x, y, dx, dy;
	public String id;
	protected Bitmap pic;
	public static final int WIDTH = 25;
	public static final int HEIGHT = 25;
	public static final double FAST_SPEED = 2;
	public static final double SLOW_SPEED = 1.5;
	public Thing(double x, double y, String id, int picX, int picY) {
		this.x = x;
		this.y = y;
		this.dx = 0;
		this.dy = 0;
		this.id = id;
		this.uniqueID = nextID++;
		this.pic = Bitmap.createBitmap(TEXTURES,picX * WIDTH,picY * HEIGHT, WIDTH, HEIGHT);
	}
	
	public double getX() { return this.x; }
	public double getY() { return this.y; }
	public double getDx() {	return this.dx; }
	public double getDy() {	return this.dy;	}
	public void setDx(double dx) { this.dx = dx; }
	public void setDy(double dy) { this.dy = dy; }
	
	public int getUniqueID() {
		return this.uniqueID;
	}
	
	public boolean isTouching(Thing other) {
		if(other == null) {
			return false;
		}
		double x = this.x - other.getX();
		double y = this.y - other.getY();
		return x < WIDTH && x > -1 * WIDTH && y < HEIGHT && y > -1 * HEIGHT; 
	}
	
	public boolean isNextTo(Thing other) {
		if(other == null) {
			return false;
		}
		double x = this.x - other.getX();
		double y = this.y - other.getY();
		return x <= WIDTH && x >= -1 * WIDTH && y <= HEIGHT && y >= -1 * HEIGHT; 
	}
	
	public boolean toLeftOf(Thing other) {
		double x = this.x - other.getX();
		double y = this.y - other.getY();
		return x <= 0 && y < HEIGHT && y > -1 * HEIGHT;
	}
	
	public boolean toRightOf(Thing other) {
		double x = this.x - other.getX();
		double y = this.y - other.getY();
		return x >= 0 && y < HEIGHT && y > -1 * HEIGHT;
	}
	
	public boolean above(Thing other) {
		double x = this.x - other.getX();
		double y = this.y - other.getY();
		return y < 0 && x < WIDTH && x > -1 * WIDTH;
	}
	
	public boolean below(Thing other) {
		double x = this.x - other.getX();
		double y = this.y - other.getY();
		return y > 0 && x < WIDTH && x > -1 * WIDTH;
	}
	
	public void display(Canvas c, int cameraX, int cameraY) {
		c.drawBitmap(this.pic, ((int)this.x) - cameraX, ((int)this.y) - cameraY, new Paint());
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Thing) {
			Thing other = (Thing) o;
			return other.uniqueID == this.uniqueID;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return this.id + " (" + Double.toString(this.x) + ", " + Double.toString(this.y) + ")";  
	}
	
	public double dist(Thing a) {
		return Math.pow(this.x - a.x, 2) + Math.pow(this.y - a.y, 2);  
	}
	
	public boolean move() { return false; }
	public void die() {}
}
