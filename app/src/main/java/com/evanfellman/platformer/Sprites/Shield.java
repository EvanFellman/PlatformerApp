package com.evanfellman.platformer.Sprites;

import com.evanfellman.platformer.Activites.MainActivity;

public class Shield extends Thing{
	public Shield(double x, double y) {
		super(x, y, "shield", 5, 1);
	}
	
	@Override
	public boolean move() {
		for(Player p: MainActivity.player) {
			if(p.isTouching(this)) {
				p.playerState.setValue(PlayerState.SHIELD);
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
