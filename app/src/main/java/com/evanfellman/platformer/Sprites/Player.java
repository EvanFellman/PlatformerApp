package com.evanfellman.platformer.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.evanfellman.platformer.Activites.MainActivity;
import static com.evanfellman.platformer.Activites.MainActivity.TEXTURES;

public class Player extends Thing {
	public static final Bitmap deadImage = Bitmap.createBitmap(TEXTURES, 4 * WIDTH, 4 * HEIGHT, WIDTH, HEIGHT);
	public PlayerState playerState;
	private boolean didDoubleJump;
	private boolean wReleased;
	public Player(float x, float y) {
		super(x, y, "player", 1, 0);
		this.dx = 0;
		this.playerState = new PlayerState();
		this.didDoubleJump = false;
		this.wReleased = false;
	}

	@Override
	public boolean move() {
		if(!MainActivity.upPressed) {
			this.wReleased = true;
		}
		MainActivity.removeFromLevel(this);
		this.playerState.tick();
		if(!MainActivity.deadPlayer) {
			if(this.dy > 15) {
				this.dy = 15;
			}
			if(MainActivity.leftPressed) {
				if(this.dx > 0) {
					this.dx = 0;
				}
				if(this.dx > -4) {
					this.dx -= 0.5;
				}
			} else if(MainActivity.rightPressed) {
				if(this.dx < 0) {
					this.dx = 0;
				}
				if(this.dx < 4) {
					this.dx += 0.5;
				}
			} else if(this.dx > 0){
				this.dx -= 0.5;
			} else if(this.dx < 0){
				this.dx += 0.5;
			}
			this.x += this.dx;
			boolean nearWalll = false, nearWallMovingl = false, nearWallr = false, nearWallMovingr = false;
			for (int i = (int) this.x - Thing.WIDTH; i < (int) this.x + Thing.WIDTH; i++) {
				for (int j = (int) this.y - Thing.HEIGHT; j < (int) this.y + Thing.HEIGHT; j++) {
					for (Thing a : MainActivity.getFromLevel(i, j)) {
						if (a != null && ((this.playerState.equals(PlayerState.SHIELD) && a.id.equals("spike")) || a.id.contains("wall") || (!this.equals(a) && a.id.equals("player"))) && this.isTouching(a)) {
							if (a.x > this.x) {
								if (a.id.equals("wall moving")) {
									if (a.dx < 0) {
										nearWallMovingr = true;
									}
								} else {
									nearWallr = true;
								}
							} else {
								if (a.id.equals("wall moving")) {
									if (a.dx > 0) {
										nearWallMovingl = true;
									}
								} else {
									nearWalll = true;
								}
							}
							if ((a.y - this.y < a.dy + Thing.HEIGHT && this.y - a.y < Thing.WIDTH + a.dy)) {
								if (this.x <= a.x - Thing.WIDTH + this.dx && this.dx >= 0) {
									this.dx = 0;
									this.x = a.getX() - Thing.WIDTH;
								} else if (this.x >= a.x + Thing.WIDTH + this.dx && this.dx <= 0) {
									this.dx = 0;
									this.x = a.getX() + Thing.WIDTH;
								}
							}
						}
					}
				}
			}
			if((nearWalll && nearWallMovingr) || (nearWallMovingl && nearWallr) || (nearWallMovingl && nearWallMovingr)) {
				this.die();
				return true;
			}
		}
		this.dy += MainActivity.GRAVITY;
		this.y += this.dy;
		if(!MainActivity.deadPlayer) {
			boolean nearWalla = false, nearWallMovinga = false, nearWallb = false, nearWallMovingb = false;
			boolean inAir = true;
			for (int i = (int) this.x - Thing.WIDTH; i < (int) this.x + Thing.WIDTH; i++) {
				for (int j = (int) this.y - Thing.HEIGHT; j < (int) this.y + Thing.HEIGHT; j++) {
					for (Thing a : MainActivity.getFromLevel(i, j)) {
						if (!this.equals(a) && this.isTouching(a)) {
							if (a.id.contains("wall") || (a.id.equals("spike") && !this.playerState.equals(PlayerState.SPIKEDESTROYER)) || (!this.equals(a) && a.id.equals("player"))) {
								if (a.y > this.y) {
									if (a.id.equals("wall moving")) {
										if (a.dy < 0) {
											nearWallMovinga = true;
										}
									} else {
										nearWalla = true;
									}
								} else {
									if (a.id.equals("wall moving")) {
										if (a.dy > 0) {
											nearWallMovingb = true;
										}
									} else {
										nearWallb = true;
									}
								}
								if (this.dy >= 0) {
									if (MainActivity.upPressed && a.getY() > this.y) {
										this.dy = -10;
										this.wReleased = false;
									} else if (this.dy > a.dy) {
										this.dy = 0;
									}
									if (a.getY() > this.y) {
										this.y = (a.y - Thing.HEIGHT);
										this.didDoubleJump = false;
										inAir = false;
									} else if (a.getY() < this.y) {
										this.y = (a.y + Thing.HEIGHT);
									}
								} else if (this.dy < 0) {
									this.dy = 0;
									if (this.x + Thing.WIDTH > a.x && this.x - Thing.WIDTH < a.x) {
										this.y = a.y + Thing.HEIGHT;
									}
								}
								if (a.id.equals("spike")) {
									if (this.playerState.equals(PlayerState.SPIKEDESTROYER)) {
										a.die();
									} else {
										this.die();
									}
								}
							} else if (a.id.equals("next level")) {
//								Main.levelNumber += 1;
//								Main.loadLevel();
//								Main.deadPlayer = false;
								System.out.println("FINISHED LEVEL WAHOO");
								return false;
							} else if (a.id.contains("enemy")) {
								if (this.y + 3 < a.y && this.dy >= 0) {
									if (MainActivity.upPressed) {
										this.dy = -15;
									} else {
										this.dy = 0;
									}
									a.die();
								} else {
									this.die();
									return MainActivity.deadPlayer;
								}
							}
						}
					}
				}
			}
			if(MainActivity.upPressed && !this.didDoubleJump && inAir && this.wReleased && this.playerState.equals(PlayerState.DOUBLEJUMP)) {
				this.dy = -14;
				this.didDoubleJump = true;
			}
			if((nearWalla && nearWallMovingb) || (nearWallMovinga && nearWallb) || (nearWallMovinga && nearWallMovingb)) {
				this.die();
				return MainActivity.deadPlayer;
			}
			if(this.y > MainActivity.DEATH_BELOW) {
				this.die();
				return MainActivity.deadPlayer;
			}
		}
		MainActivity.putInLevel(this);
		return false;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public void die() {
		if(this.playerState.equals(PlayerState.SHIELD) && this.y <= MainActivity.DEATH_BELOW) {
			if(this.playerState.timer == -1) {
				this.playerState.timer = 60;
			}
		} else {
			this.playerState.setValue(PlayerState.DEAD);
			this.playerState.timer = 75;
			MainActivity.deadPlayerCounter = 75;
			MainActivity.deadPlayer = true;
		}
	}
	
	@Override
	public void display(Canvas c, int cameraX, int cameraY) {
		if(MainActivity.deadPlayer) {
			this.pic = deadImage;
		} else {
			this.pic = this.playerState.getImage();
		}
		super.display(c, cameraX, cameraY);
	}
}

class PlayerState{
	public final static Integer NONE = 0;
	public final static Integer DEAD = 1;
	public final static Integer SHIELD = 2;
	public final static Integer DOUBLEJUMP = 3;
	public final static Integer SPIKEDESTROYER = 4;
	//Bitmap.createBitmap(TEXTURES, 4 * Thing.WIDTH, 4 * Thing.HEIGHT, Thing.WIDTH, Thing.HEIGHT)
	private final static Bitmap[] IMAGES = {
			Bitmap.createBitmap(TEXTURES, 1 * Thing.WIDTH, 0 * Thing.HEIGHT, Thing.WIDTH, Thing.HEIGHT),
			Bitmap.createBitmap(TEXTURES, 4 * Thing.WIDTH, 4 * Thing.HEIGHT, Thing.WIDTH, Thing.HEIGHT),
			Bitmap.createBitmap(TEXTURES, 5 * Thing.WIDTH, 0 * Thing.HEIGHT, Thing.WIDTH, Thing.HEIGHT),
			Bitmap.createBitmap(TEXTURES, 5 * Thing.WIDTH, 2 * Thing.HEIGHT, Thing.WIDTH, Thing.HEIGHT),
			Bitmap.createBitmap(TEXTURES, 0 * Thing.WIDTH, 5 * Thing.HEIGHT, Thing.WIDTH, Thing.HEIGHT)
	};
	public int timer;
	private Integer value;
	
	public PlayerState(){
		this.value = NONE;
	}
	
	public PlayerState(Integer v) {
		this.value = v;
		this.timer = -1;
	}
	
	public void setValue(Integer v) {
		this.value = v;
		this.timer = -1;
	}
	
	public Bitmap getImage() {
		if(this.timer > 0 && this.timer / 4 % 2 == 0) {
			return IMAGES[NONE];
		} else {
			return IMAGES[this.value];
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Integer) {
			return (Integer) o == this.value;
		} else if(!(o instanceof PlayerState)) {
			return false;
		} else {
			return ((PlayerState) o).value == this.value;
		}
	}
	
	public void tick() {
		if(this.timer > 0) {
			this.timer --;
		}
		if(this.timer == 0) {
			this.value = NONE;
			this.timer = -1;
		}
	}
}
