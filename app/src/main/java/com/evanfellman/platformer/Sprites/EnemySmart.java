package com.evanfellman.platformer.Sprites;

import com.evanfellman.platformer.Activites.MainActivity;

import java.util.ArrayList;

public class EnemySmart extends Enemy {
	public double speed;
	public EnemySmart(double x, double y, double speed) {
		super(x, y, "enemy smart", 2, 3);
		this.speed = speed;
	}
	
	public boolean move() {
		Player player = MainActivity.player.get(0);
		for(Player p: MainActivity.player){
			if(player.dist(this) > p.dist(this)){
				player = p;
			}
		}
		//set dx
		if(this.dx == 0) {
			this.dx = player.getX() < this.x ? this.speed * -1 : this.speed;
		} else if(player.getX() < this.x - (Thing.WIDTH * 2)) {
			this.dx = this.speed * -1;
		} else if(player.getX() > this.x + (Thing.WIDTH * 2)) {
			this.dx = this.speed;
		}
		//Figure out if allowed to jump
		boolean wallDirectlyBelow = false;
		for(int i = (int)this.x - Thing.WIDTH; i < (int) this.x + Thing.WIDTH; i++) {
			for (int j = (int) this.y; j < (int) this.y + Thing.HEIGHT; j++) {
				for (Thing a : MainActivity.getFromLevel(i, j)) {
					if (this.isNextTo(a) && !this.equals(a) && (a.id.contains("enemy") || a.id.contains("wall")) && this.y < a.y && this.x - a.x < Thing.WIDTH && a.x - this.x < Thing.WIDTH) {
						wallDirectlyBelow = true;
					}
				}
			}
		}
		if(wallDirectlyBelow) {
			//Can jump
			boolean wallNextToMe = false;
			if(this.dx < 0) {
				for(int i = (int)this.x - Thing.WIDTH; i < this.x; i++) {
					for(Thing a: MainActivity.getFromLevel(i, this.y)){
						if(a != null && this.isNextTo(a) && !this.equals(a) && (a.id.contains("enemy") || a.id.contains("wall"))) {
							wallNextToMe = true;
							break;
						}
					}
				}
			} else if(this.dx > 0) {
				for(int i = (int)this.x; i < (int)this.x + Thing.WIDTH; i++) {
					for(Thing a: MainActivity.getFromLevel(i, this.y)){
						if(a != null && this.isNextTo(a) && !this.equals(a) && (a.id.contains("enemy") || a.id.contains("wall"))) {
							wallNextToMe = true;
							break;
						}
					}
				}
			}
			//Now I know if there is a wall directly next to me
			boolean floorLeft = false;
			if(this.dx < 0) {
				for(int i = (int)this.x - Thing.WIDTH; i < (int) this.x; i++) {
					for (int j = (int) this.y; j < (int) this.y + Thing.HEIGHT; j++) {
						for (Thing a : MainActivity.getFromLevel(i, j)) {
							if (a != null && this.isNextTo(a) && !this.equals(a) && (a.id.contains("enemy") || a.id.contains("wall")) && this.y < a.y) {
								floorLeft = true;
								break;
							}
						}
					}
				}
			} else if(this.dx > 0) {
				for(int i = (int)this.x; i < (int) this.x + Thing.WIDTH; i++) {
					for (int j = (int) this.y; j < (int) this.y + Thing.HEIGHT; j++) {
						for (Thing a : MainActivity.getFromLevel(i, j)) {
							if (this.isNextTo(a) && !this.equals(a) && (a.id.contains("enemy") || a.id.contains("wall")) && this.y < a.y) {
								floorLeft = true;
								break;
							}
						}
					}
				}
			}
			//Now I know if there is no floor left
			if(wallNextToMe || !floorLeft) {
				boolean somethingToJumpTo = false;
				boolean somethingToFallTo = false;
				if(this.dx > 0) {
					for(int i = 1; i <= (this.speed == Thing.FAST_SPEED ? 4 : 3); i++) {
						for(int j = -2; j <= 1; j++) {
							for(Thing a : MainActivity.getFromLevel(this.x + (i * Thing.WIDTH), this.y + (j * Thing.HEIGHT))){
								ArrayList<Thing> aboveA = MainActivity.getFromLevel(this.x + (i * Thing.WIDTH), this.y + ((j - 1) * Thing.HEIGHT));
								boolean aboveWall = false;
								for(Thing abv: aboveA){
									if(abv.id.contains("wall")){
										aboveWall = true;
										break;
									}
								}
								if ((aboveA.isEmpty() || !aboveWall) && (a.id.contains("wall") || a.id.contains("enemy"))) {
									somethingToJumpTo = true;
								}
							}
						}
					}
					for(int i = 0; i <= (this.speed == Thing.FAST_SPEED ? 4 : 3); i++) {
						for(int j = 2; j <= 5; j++) {
							for(Thing a : MainActivity.getFromLevel(this.x + (i * Thing.WIDTH), this.y + (j * Thing.HEIGHT))) {
								ArrayList<Thing> aboveA = MainActivity.getFromLevel(this.x + (i * Thing.WIDTH), this.y + ((j - 1) * Thing.HEIGHT));
								boolean aboveWall = false;
								for (Thing abv : aboveA) {
									if (abv.id.contains("wall")) {
										aboveWall = true;
										break;
									}
								}
								if ((aboveA.isEmpty() || !aboveWall) && (a.id.contains("enemy") || a.id.contains("wall"))) {
									somethingToFallTo = true;
								}
							}
						}
					}
				} else {
					for(int i = (this.speed == Thing.FAST_SPEED ? -3 : -2); i <= -1; i++) {
						for(int j = -2; j <= 1; j++) {
							for(Thing a : MainActivity.getFromLevel(this.x + (i * Thing.WIDTH), this.y + (j * Thing.HEIGHT))){
								ArrayList<Thing> aboveA = MainActivity.getFromLevel(this.x + (i * Thing.WIDTH), this.y + ((j - 1) * Thing.HEIGHT));
								boolean aboveWall = false;
								for(Thing abv: aboveA){
									if(abv.id.contains("wall")){
										aboveWall = true;
										break;
									}
								}
								if ((aboveA.isEmpty() || !aboveWall) && (a.id.contains("wall") || a.id.contains("enemy"))) {
									somethingToJumpTo = true;
								}
							}
						}
					}
					for(int i = (this.speed == Thing.FAST_SPEED ? -3 : -2); i <= 0; i++) {
						for(int j = 2; j <= 5; j++) {
							for(Thing a : MainActivity.getFromLevel(this.x + (i * Thing.WIDTH), this.y + (j * Thing.HEIGHT))) {
								ArrayList<Thing> aboveA = MainActivity.getFromLevel(this.x + (i * Thing.WIDTH), this.y + ((j - 1) * Thing.HEIGHT));
								boolean aboveWall = false;
								for (Thing abv : aboveA) {
									if (abv.id.contains("wall")) {
										aboveWall = true;
										break;
									}
								}
								if ((aboveA.isEmpty() || !aboveWall) && (a.id.contains("enemy") || a.id.contains("wall"))) {
									somethingToFallTo = true;
								}
							}
						}
					}
				}
				if(!somethingToFallTo && somethingToJumpTo) {
					this.dy = -10;
				} else if(somethingToFallTo && somethingToJumpTo && (wallNextToMe || this.y >= player.getY())) {
					this.dy = -10;
				} else if(!somethingToFallTo && !somethingToJumpTo) {
					this.dx = 0;
				}
			}
		}
		return super.move();
	}

}
