package com.evanfellman.platformer.Sprites;

import com.evanfellman.platformer.Activites.MainActivity;

public class SpikeDestroyer extends Thing {

	public SpikeDestroyer(double x, double y) {
		super(x, y, "spike destroyer", 1, 5);
	}
	
	public boolean move() {
		for(Player p: MainActivity.player) {
			if(p.isTouching(this)) {
				p.playerState.setValue(PlayerState.SPIKEDESTROYER);
				this.die();
			}
		}
		return false;
	}

	@Override
	public void die() {
		MainActivity.removeFromLevel(this);
	}
}
