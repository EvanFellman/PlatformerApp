package com.evanfellman.platformer.Sprites;

import com.evanfellman.platformer.Activites.MainActivity;

public class Spike extends Thing {

	public Spike(double x, double y) {
		super(x, y, "spike", 1, 3);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public boolean move() {
		for(int i = (int)this.x - Thing.WIDTH; i < (int)this.x + Thing.WIDTH; i++) {
			for(int j = (int) this.y - Thing.HEIGHT; j < (int) this.y + Thing.HEIGHT; j++) {
				for(Thing a: MainActivity.getFromLevel(i, j)){
					if( this.isTouching(a)) {
						if(a.id.contains("enemy")) {
							a.die();
						} else if(a.id.equals("player")) {
							if(((Player) a).playerState.equals(PlayerState.SPIKEDESTROYER)) {
								this.die();
							} else {
								a.die();
								return MainActivity.deadPlayer;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public void die() {
		MainActivity.removeFromLevel(this);
	}
}
