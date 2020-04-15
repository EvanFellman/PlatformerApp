package com.evanfellman.platformer.Sprites;

import com.evanfellman.platformer.Activites.MainActivity;

public class EnemyOnlyJump extends Enemy {

	public EnemyOnlyJump(double x, double y) {
		super(x, y, "enemy only jump", 0, 2);
	}
	
	public boolean move() {
		if(MainActivity.upPressed) {
			for(int i = (int) this.x - 1; i <= (int) this.x + 1; i++){
				for(int j = (int) this.y; j <= (int) this.y + 1; j++) {
					for (Thing a : MainActivity.getFromLevel(i, j)) {
						if (!a.equals(this) && (a.id.contains("wall") || a.id.contains("enemy")) && this.dy >= 0 && this.above(a) && this.y + Thing.HEIGHT + 1 >= a.y) {
							this.dy = -10;
						}
					}
				}
			}
		}
		return super.move();
	}
}
