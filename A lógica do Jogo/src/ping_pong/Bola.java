package ping_pong;

import java.awt.Color;

import Base.Elemento;

public class Bola extends Elemento{
	
	public static final int VEL_INICIAL = 3;
	private int dirX = -1;
	private int dirY = -1;
	private float velX, velY;
	

	public Bola() {
		velX = velY = VEL_INICIAL;
		setAltura(10);
		setLargura(10);
		setCor(Color.WHITE);
	}
	
	public void incVel(float vx,float vy) {
		velX = vx;
		velY = vy;
	}
	
	public void setVel(int vel) {
		velX = velY = vel;
	}
	
	public int getVel() {
		return (int) (velX);
	}

	public int getDirX() {
		return dirX;
	}

	public void setDirX(int dirX) {
		this.dirX = dirX;
	}

	public int getDirY() {
		return dirY;
	}

	public void setDirY(int dirY) {
		this.dirY = dirY;
	}

	public void incPx() {
		incPx((int) velX * dirX);
	}
	
	public void incPy() {
		incPx((int) velY * dirY);
	}
	
	public float getVelX() {
		return velX;
	}
	
	public float getVelY() {
		return velY;
	}

	
	
	public void inverterX() {
		dirX *= -1;
	}
	
	public void inverterY() {
		dirY *= -1;
	}
}
