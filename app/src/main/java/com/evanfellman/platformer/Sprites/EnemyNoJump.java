package com.evanfellman.platformer.Sprites;

import com.evanfellman.platformer.Activites.MainActivity;

public class EnemyNoJump extends Enemy {
	public double speed;
	public EnemyNoJump(double x, double y, double speed) {
		super(x, y, "enemy no jump", 0, 0);
		this.speed = speed;
	}
	
	public boolean move() {
		Player player = MainActivity.player.get(0);
		for(Player p: MainActivity.player){
			if(player.dist(this) > p.dist(this)){
				player = p;
			}
		}
		if(player.getX() + (Thing.WIDTH * 2) < this.x) {
			this.dx = -1 * this.speed;
		}
		if(player.getX() - (Thing.WIDTH * 2) > this.x) {
			this.dx = this.speed;
		}
		if(this.dx == 0) {
			this.dx = player.getX() < this.x ? -1 * speed : speed;
		}
		return super.move();
	}
}
