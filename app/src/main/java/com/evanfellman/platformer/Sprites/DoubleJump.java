package com.evanfellman.platformer.Sprites;

import com.evanfellman.platformer.Activites.MainActivity;

public class DoubleJump extends Thing {

	public DoubleJump(double x, double y) {
		super(x, y, "double jump", 5, 3);
	}
	
	@Override
	public boolean move() {
		for(Player p: MainActivity.player) {
			if(p.isTouching(this)) {
				p.playerState.setValue(PlayerState.DOUBLEJUMP);
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
