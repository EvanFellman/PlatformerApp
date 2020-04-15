package com.evanfellman.platformer.Sprites;

import com.evanfellman.platformer.Activites.MainActivity;

public class BlueReverseGate extends Thing {
	public BlueReverseGate(float x, float y) {
		super(x, y, MainActivity.isBlueGateOpen ? "wall blue reverse gate" : "open blue reverse gate", 2, MainActivity.isBlueGateOpen ? 1 : 2);
	}
	
	public boolean move() {
		if(MainActivity.isBlueGateOpen && this.id.equals("open blue reverse gate")) {
			this.id = "wall blue reverse gate";
			this.pic = BlueGate.closedPic;
		} else if(!MainActivity.isBlueGateOpen && this.id.equals("wall blue reverse gate")){
			this.id = "open blue reverse gate";
			this.pic = BlueGate.openPic;
		}
		return false;
	}
}