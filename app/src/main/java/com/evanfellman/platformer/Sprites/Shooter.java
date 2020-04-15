package com.evanfellman.platformer.Sprites;

import com.evanfellman.platformer.Activites.MainActivity;

public class Shooter extends Thing {
	public int direction;
	public final static int UP = 1;
	public final static int DOWN = 2;
	public final static int LEFT = 3;
	public final static int RIGHT = 4;
	public double speed;
	private int countDown;
	private int SHOOTING_WAIT = 100;
	public Shooter(double x, double y, int direction, double speed) {
		super(x, y, "wall shooter " + (direction == UP ? "up" : (direction == DOWN ? "down" : (direction == LEFT ? "left" : "right"))), direction == UP ? 2 : (direction == DOWN ? 3 : (direction == LEFT ? 0 : 1)), 4);
		this.direction = direction;
		this.speed = speed;
		SHOOTING_WAIT *= Thing.SLOW_SPEED / this.speed;
		countDown = SHOOTING_WAIT;
	}
	
	public boolean move() {
		int iS = 0;
		int iF = 0;
		int jS = 0;
		int jF = 0;
		switch(this.direction) {
		case UP:
			jF = 0;
			jS = -1 * Thing.HEIGHT;
			break;
		case DOWN:
			jF = Thing.HEIGHT;
			jS = 0;
			break;
		case LEFT:
			iF = 0;
			iS = -1 * Thing.WIDTH;
			break;
		case RIGHT:
			iF = 0;
			iS = Thing.WIDTH;
			break;
		}
		boolean empty = true;
		for(int i = iS; i < iF; i++){
			for(int j = jS; j < jF; j++){
				empty = empty && MainActivity.getFromLevel(i, j).isEmpty();
			}
		}
		if(empty) {
			this.countDown--;
			if(this.countDown <= 0) {
				Thing bullet = new EnemyBullet(this.x + (iS == 0 ? iF : iS), this.y + (jS == 0 ? jF : jS), this.direction, this.speed);
				MainActivity.putInLevel(bullet);
				this.countDown = SHOOTING_WAIT;
			}
		}
		return false;
	}
}
