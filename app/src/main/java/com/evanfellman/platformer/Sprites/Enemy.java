package com.evanfellman.platformer.Sprites;

import com.evanfellman.platformer.Activites.MainActivity;

public abstract class Enemy extends Thing {
	public Enemy(double x, double y, String id, int picX, int picY) {
		super(x, y, id, picX, picY);
	}
	
	public boolean move() {
		MainActivity.removeFromLevel(this);
		if(this.dy > 15) {
			this.dy = 15;
		}
		boolean nearWalll = false, nearWallMovingl = false, nearWallr = false, nearWallMovingr = false;
		this.x += this.dx;
		for(int i = (int)this.x - Thing.WIDTH; i < (int)this.x + Thing.WIDTH; i++){
			for(int j = (int) this.y - Thing.HEIGHT; j < (int)this.y + Thing.HEIGHT; j++){
				for(Thing a: MainActivity.getFromLevel(i, j)){
					if((a.id.contains("wall") || (!this.equals(a) && a.id.equals("player"))) && this.isTouching(a)) {
						if(a.x > this.x) {
							if(a.id.equals("wall moving")) {
								if(a.dx < 0) {
									nearWallMovingr = true;
								}
							} else {
								nearWallr = true;
							}
						} else {
							if(a.id.equals("wall moving")) {
								if(a.dx > 0) {
									nearWallMovingl = true;
								}
							} else {
								nearWalll = true;
							}
						}
						if((a.y - this.y < a.dy + Thing.HEIGHT && this.y - a.y < Thing.HEIGHT + a.dy)){
							if(this.x < a.x && this.dx >= 0) {
								this.dx = 0;
								this.x = a.getX() - Thing.WIDTH;
							} else if(this.x > a.x && this.dx <= 0) {
								this.dx = 0;
								this.x = a.getX() + Thing.WIDTH;
							}
						}
					}
					if(!this.equals(a) && a.id.contains("enemy") && this.isTouching(a)) {
						if(this.dx > 0 && this.toLeftOf(a)) {
							this.dx = 0;
							this.x = a.getX() - Thing.WIDTH;
						} else if(this.dx < 0 && this.toRightOf(a)) {
							this.dx = 0;
							this.x = a.getX() + Thing.WIDTH;
						}
					} else if(this.isNextTo(a) && (this.above(a) || this.toLeftOf(a) || this.toRightOf(a)) && a.id.equals("player")) {
						if(a.dy > this.dy && a.y + 1 < this.y) {
							if(MainActivity.upPressed) {
								a.dy = -2.5f;
							} else {
								a.dy = 0;
							}
							this.die();
						} else {
							a.die();
							return true;
						}
					}
				}
			}
		}
		if((nearWalll && nearWallMovingr) || (nearWallMovingl && nearWallr) || (nearWallMovingl && nearWallMovingr)) {
			this.die();
			return true;
		}
		this.dy += MainActivity.GRAVITY;
		this.y += this.dy;
		boolean nearWalla = false, nearWallMovinga = false, nearWallb = false, nearWallMovingb = false;
		for(int i = (int)this.x - Thing.WIDTH; i < (int)this.x + Thing.WIDTH; i++){
			for(int j = (int) this.y - Thing.HEIGHT; j < (int)this.y + Thing.HEIGHT; j++){
				for (Thing a : MainActivity.getFromLevel(i, j)) {
					if(this.isTouching(a) && a.id.contains("wall")) {
						if(a.y > this.y) {
							if(a.id.equals("wall moving")) {
								if(a.dy < 0) {
									nearWallMovinga = true;
								}
							} else {
								nearWalla = true;
							}
						} else {
							if(a.id.equals("wall moving")) {
								if(a.dy > 0) {
									nearWallMovingb = true;
								}
							} else {
								nearWallb = true;
							}
						}
						if(this.dy >= 0) {
							if(this.dy > 0 && a.dy <= 0) {
								this.dy = 0;
							}
							if(a.getY() > this.y) {
								this.y = a.y - Thing.HEIGHT;
							}
						} else if(this.dy < 0) {
							this.dy = 0;
							if(this.x + Thing.WIDTH > a.x && this.x - Thing.WIDTH < a.x) {
								this.y = a.y + Thing.HEIGHT;
							}
						}
					}
					if(!this.equals(a) && this.isTouching(a) && a.id.contains("enemy")) {
						if(this.dy > 0 && this.above(a)) {
							if(a.dy < 0){
								this.dy = 0;
							}
							if(a.getY() > this.y && a.y < MainActivity.DEATH_BELOW - Thing.HEIGHT) {
								this.y = a.y - Thing.HEIGHT;
							}
							break;
						} else if(this.dy < a.dy) {
							this.dy = 0;
							this.y = a.y + Thing.HEIGHT;
							break;
						}
					}
					if(!this.equals(a) && a.id.contains("bullet") && this.isTouching(a)) {
						this.die();
						return false;
					}
				}
			}
		}
		if((nearWalla && nearWallMovingb) || (nearWallMovinga && nearWallb) || (nearWallMovinga && nearWallMovingb)) {
			this.die();
			return true;
		}
		if(this.y > MainActivity.DEATH_BELOW) {
			this.die();
		}
		MainActivity.putInLevel(this);
		return false;
	}
	
	public void die() {
		MainActivity.removeFromLevel(this);
	}
}
