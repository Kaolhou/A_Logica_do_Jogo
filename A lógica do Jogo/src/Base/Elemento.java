package Base;

import java.awt.Color;
import java.awt.Graphics2D;

public class Elemento {
	
	private int px,py,altura,largura,vel;
	private boolean ativo;
	private Color cor;
	
	//getters and setters
	public int getPx() {
		return px;
	}

	public void setPx(int px) {
		this.px = px;
	}

	public int getPy() {
		return py;
	}

	public void setPy(int py) {
		this.py = py;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public int getVel() {
		return vel;
	}

	public void setVel(int vel) {
		this.vel = vel;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Color getCor() {
		return cor;
	}

	public void setCor(Color cor) {
		this.cor = cor;
	}
	
	public int getLargura() {
		return this.largura;
	}
	
	public void setLargura(int largura) {
		this.largura = largura;
	}

	//modificar posições
	public void incPx(int x) {
		px = px+x;
	}
	public void incPy(int y) {
		py = py+y;
	}

	public void atualiza() {};
	
	public void desenha(Graphics2D g) {
		g.drawRect(px, py, largura, altura);
	};

	public Elemento() {};
	public Elemento(int px, int py, int largura, int altura) {
		this.px = px;
		this.py = py;
		this.largura = largura;
		this.altura = altura;
	}
}
