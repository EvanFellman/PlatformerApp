package com.evanfellman.platformer.Sprites;

import android.graphics.Bitmap;

import com.evanfellman.platformer.Activites.MainActivity;

import static com.evanfellman.platformer.Activites.MainActivity.TEXTURES;

public class RedGate extends Thing {
	public static Bitmap openPic = Bitmap.createBitmap(TEXTURES, 3 * WIDTH, 2 * HEIGHT, WIDTH, HEIGHT);
	public static Bitmap closedPic = Bitmap.createBitmap(TEXTURES, 3 * WIDTH, 1 * HEIGHT, WIDTH, HEIGHT);
	public RedGate(float x, float y) {
		super(x, y, MainActivity.isRedGateOpen ? "open red gate" : "wall red gate", 2, MainActivity.isRedGateOpen ? 2 : 1);
	}

	public boolean move() {
		if(MainActivity.isRedGateOpen && this.id.equals("wall red gate")) {
			this.id = "open red gate";
			this.pic = openPic;
		} else if(!MainActivity.isRedGateOpen && this.id.equals("open red gate")){
			this.id = "wall red gate";
			this.pic = closedPic;
		}
		return false;
	}
}
