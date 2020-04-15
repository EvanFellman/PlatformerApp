package com.evanfellman.platformer.Sprites;

import com.evanfellman.platformer.Activites.MainActivity;

public class DisappearingWall extends Thing {
	public int disappearCount;
	public DisappearingWall(double x, double y) {
		super(x, y, "wall disappearing", 0, 3);
		disappearCount = 10;
	}
	
	public boolean move() {
		for(int i = (int)this.x - Thing.WIDTH; i < (int)this.x + Thing.WIDTH; i++) {
			for (int j = (int) this.y - Thing.HEIGHT; j < (int) this.y + Thing.HEIGHT; j++) {
				for (Thing a : MainActivity.getFromLevel(i, j)) {
					if (a.id.equals("player") || a.id.contains("enemy")) {
						disappearCount--;
						if (disappearCount <= 0) {
							this.die();
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
