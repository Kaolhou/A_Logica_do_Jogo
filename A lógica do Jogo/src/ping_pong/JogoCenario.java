package ping_pong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import Base.CenarioPadrao;
import Base.Elemento;
import Base.Texto;
import Base.Util;

public class JogoCenario extends CenarioPadrao {
	
	private float inc = 0.5f;
	private Ponto pontoA,pontoB;
	private Bola bola;
	private Elemento esquerda,direita;
	private boolean reiniciarJogada;
	private final Texto textoPausa = new Texto(Ponto.fonte.getFontName());
	
	private int idx;
	private Bola[] bolaArr = new Bola[0];
	private Random rand = new Random();
	

	public JogoCenario(int largura,int altura) {
		super(largura,altura);
		bola = new Bola();
		esquerda = new Elemento();
		direita = new Elemento();
		pontoA = new Ponto();
		pontoB = new Ponto();
	}
	
	public void rebate(Elemento raquete, Bola bola) {
		float vx = bola.getVelX();
		float vy = bola.getVelY();
		
		if(bola.getPy() < raquete.getPy() + raquete.getAltura() / 3) {
			bola.setDirY(-1);
			vx += inc;
			vy += inc;
			
			if(bola.getPy() < raquete.getPy()) {
				vy += inc;
			}
		} else if(bola.getPy() > raquete.getPy() + raquete.getAltura() - raquete.getAltura() / 3) {
			bola.setDirY(1);
			vx += inc;
			vy += inc;
			
			if(bola.getPy() + bola.getAltura() > raquete.getPy() + raquete.getAltura()) {
				vy += inc;
			}
		}else {
			vx += inc;
			vy = 1;
		}
		
		bola.inverterX();
		bola.incVel(vx,vy);
		
		if(bolaArr.length > 0) {
			if(idx < bolaArr.length) {
				bolaArr[idx++].setAtivo(true);
			}else {
				idx = 0;
			}
		}
		
	}
	
	private boolean validaColisao(Bola b) {
		boolean saiu = false;
		if(Util.colide(esquerda, b)) {
			rebate(esquerda,b);
		}else if(Util.colide(direita, b)) {
			rebate(direita,b);
		}else if(b.getPx() < 0 || b.getPx() + b.getLargura() > largura) {
			
			saiu = true;
			
			if(b.getPx() < 0) {
				pontoB.add();
			}else {
				pontoA.add();
			}
			
		} else if(b.getPy() <= 0 || b.getPy() + b.getAltura() >= altura) {
			b.inverterY();
		}
		return saiu;
	}
	
	@Override
	public void atualizar() {

		if(Jogo.pausado)
			return;
		
		bola.incPx();
		bola.incPy();
		
		if(Jogo.controleTecla[Jogo.Tecla.CIMA.ordinal()]) {
			esquerda.incPy(esquerda.getVel() * -1);
		}else if(Jogo.controleTecla[Jogo.Tecla.BAIXO.ordinal()]) {
			esquerda.incPy(esquerda.getVel());
		}
		
		if(direita.getPy() + direita.getAltura() / 2 > Jogo.mouseY + direita.getVel()) {
			direita.incPy(direita.getVel() * -1);
		}else if(direita.getPy() + direita.getAltura() / 2 < Jogo.mouseY - direita.getVel()) {
			direita.incPy(direita.getVel());
		}
		
		//validaPosicao(esquerda)
		//validaPosicao(diraita)
		
		if(reiniciarJogada) {
			reiniciarJogada = false;
			bola.inverterX();
			bola.setVel(Jogo.velocidade);
			Util.centraliza(bola, largura, altura);
		} else {
			reiniciarJogada = validaColisao(bola);
		}
		
	}
	@Override
	public void carregar() {

		bola.setVel(Jogo.velocidade);
		
		pontoA.setPx(largura / 2 - 120);
		pontoA.setPy(Ponto.TAMANHO_FONTE);
		
		pontoB.setPx(largura / 2 + 120 - Ponto.TAMANHO_FONTE / 2);
		pontoB.setPy(Ponto.TAMANHO_FONTE);
		
		esquerda.setVel(5);
		esquerda.setAltura(70);
		esquerda.setLargura(5);
		esquerda.setCor(Color.WHITE);
		
		direita.setVel(5);
		direita.setAltura(70);
		direita.setLargura(5);
		direita.setCor(Color.WHITE);
		direita.setPx(largura - direita.getLargura());
		
		Util.centraliza(bola, largura, altura);
		Util.centraliza(direita, 0, altura);
		Util.centraliza(esquerda, 0, altura);
		
		bola.setAtivo(true);
		direita.setAtivo(true);
		esquerda.setAtivo(true);
		
		if(!Jogo.modoNormal) {

			bolaArr = new Bola[30];
			
			for(int i = 0; i < bolaArr.length; i++) {
				int v = rand.nextInt(3) + 1;
				bolaArr[i] = new Bola();
				bolaArr[i].setDirX(i%2 == 0 ? -1 : 1);
				
				bolaArr[i].setVel(Bola.VEL_INICIAL * v);
				bolaArr[i].setAltura(bola.getAltura() * v);
				bolaArr[i].setLargura(bola.getLargura() * v);
				
				Util.centraliza(bolaArr[i], largura, altura);
			}
			
		}

		
		
	}
	@Override
	public void desenhar(Graphics2D g) {
		for (int i = 0; i < altura; i += 20) {
			g.setColor(Color.WHITE);
			g.drawRect(largura / 2 - 2, i, 4, 10);
		}
		pontoA.desenha(g);
		pontoB.desenha(g);
		
		bola.desenha(g);
		
		for(Bola b: bolaArr) {
			b.desenha(g);
		}
		
		esquerda.desenha(g);
		direita.desenha(g);
		
		
		if(Jogo.pausado) {
			textoPausa.desenha(g, "PAUSA",altura / 2 - Ponto.TAMANHO_FONTE,altura / 2);
		}
		
	}
	@Override
	public void descarregar() {
		
		
		
	}

}
