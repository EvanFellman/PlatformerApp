package com.evanfellman.platformer.Sprites;

import com.evanfellman.platformer.Activites.MainActivity;

public class RedSwitch extends Thing {
	private int touchedLast;
	public RedSwitch(float x, float y) {
		super(x, y, "red wall switch", 3, 0);
		this.touchedLast = 0;
	}
	
	public boolean move() {
		boolean wasTouched = false;
		if(this.touchedLast <= 0) {
			for (int i = (int) this.x - Thing.WIDTH; i < (int) this.x + Thing.WIDTH; i++) {
				for (int j = (int) this.y - Thing.HEIGHT; j < (int) this.y + Thing.HEIGHT; j++) {
					for (Thing a : MainActivity.getFromLevel(i, j)) {
						if (this.isNextTo(a) && (a.id.equals("player") || a.id.contains("enemy"))) {
							wasTouched = true;
							this.touchedLast = 5;
							MainActivity.isRedGateOpen = !MainActivity.isRedGateOpen;
						}
					}
				}
			}
		} else {
			this.touchedLast--;
		}
		return false;
	}
}
