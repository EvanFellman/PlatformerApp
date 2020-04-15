package com.evanfellman.platformer.Sprites;

import android.graphics.Bitmap;

import com.evanfellman.platformer.Activites.MainActivity;

import static com.evanfellman.platformer.Activites.MainActivity.TEXTURES;

public class BlueGate extends Thing {
	public static Bitmap openPic = Bitmap.createBitmap(TEXTURES, 2 * WIDTH, 2 * HEIGHT, WIDTH, HEIGHT);
	public static Bitmap closedPic = Bitmap.createBitmap(TEXTURES, 2 * WIDTH, 1 * HEIGHT, WIDTH, HEIGHT);
	public BlueGate(float x, float y) {
		super(x, y, MainActivity.isBlueGateOpen ? "open blue gate" : "wall blue gate", 2, MainActivity.isBlueGateOpen ? 2 : 1);
	}
	
	public boolean move() {
		if(MainActivity.isBlueGateOpen && this.id.equals("wall blue gate")) {
			this.id = "open blue gate";
			this.pic = openPic;
		} else if(!MainActivity.isBlueGateOpen && this.id.equals("open blue gate")){
			this.id = "wall blue gate";
			this.pic = closedPic;
		}
		return false;
	}
}
