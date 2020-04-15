package com.evanfellman.platformer.Sprites;

import com.evanfellman.platformer.Activites.MainActivity;

public class RedReverseGate extends Thing {
	public RedReverseGate(float x, float y) {
		super(x, y, MainActivity.isRedGateOpen ? "wall red reverse gate" : "open red reverse gate", 3, MainActivity.isRedGateOpen ? 1 : 2);
	}
	
	public boolean move() {
		if(MainActivity.isRedGateOpen && this.id.equals("open red reverse gate")) {
			this.id = "wall red reverse gate";
			this.pic = RedGate.closedPic;
		} else if(!MainActivity.isRedGateOpen && this.id.equals("wall red reverse gate")){
			this.id = "open red reverse gate";
			this.pic = RedGate.openPic;
		}
		return false;
	}
}
