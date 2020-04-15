package com.evanfellman.platformer.Sprites;

import com.evanfellman.platformer.Activites.MainActivity;

public class BlueSwitch extends Thing {
	private int touchedLast;
	public BlueSwitch(float x, float y) {
		super(x, y, "blue wall switch", 2, 0);
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
							MainActivity.isBlueGateOpen = !MainActivity.isBlueGateOpen;
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
