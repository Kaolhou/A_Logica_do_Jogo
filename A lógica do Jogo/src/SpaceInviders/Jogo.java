package SpaceInviders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JPanel;
import SpaceInviders.Invader.Tipos;
import Base.Elemento;
import Base.Texto;
import Base.Util;
import java.util.Random;

public class Jogo extends JFrame{
	private final int FPS = 20;
	
	private JPanel tela;
	private boolean jogando = true;
	private boolean fimDeJogo = false;
	private boolean[] controleTecla = new boolean[5];
	private Invader[][] invasores = new Invader[11][5];
	private int pontos = 0;
	
	private static final int TELA_ALTURA = 680;
	private static final int TELA_LARGURA = 540;
	
	private Invader.Tipos[] tipoPorLinha = {Tipos.PEQUENO,Tipos.MEDIO,Tipos.MEDIO,Tipos.GRANDE,Tipos.GRANDE};
	private Invader chefe;
	private Tiro tiroChefe;
	private Random rand = new Random();
	private Graphics2D g2d;
	private BufferedImage buffer;
	private Tanque tanque;	
	
	private int tiroTurn = 0;
	private int espacamento = 15;
	private int linhaBase = 60;
	private int contadorEspera, contador, totalInimigos, destruidos, dir;
	private int level = 1;
	private int vidas = 3;
	private boolean moverInimigos,novaLinha;
	private boolean colideBordas = false;
	private Elemento tiroTanque;
	private Tiro[] tiros = new Tiro[3];
	private Texto texto;
	
	
	private void setTecla(int key,boolean active) {
		switch(key) {
		case KeyEvent.VK_ESCAPE:
			jogando = false;
			dispose();
			break;
		case KeyEvent.VK_UP:
			controleTecla[0] = active;
			break;
		case KeyEvent.VK_DOWN:
			controleTecla[1] = active;
			break;
		case KeyEvent.VK_LEFT:
			controleTecla[2] = active;
			break;
		case KeyEvent.VK_RIGHT:
			controleTecla[3] = active;
			break;
		case KeyEvent.VK_SPACE:
			controleTecla[4] = active;
			break;
		}
	}
	
	public Jogo() {
		tela = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(buffer, 0,0,null);
			}
		};
		
		buffer = new BufferedImage(TELA_LARGURA,TELA_ALTURA, BufferedImage.TYPE_INT_RGB);

		g2d = buffer.createGraphics();

		
		getContentPane().add(tela);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(TELA_LARGURA,TELA_ALTURA);
		setVisible(true);
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {
				setTecla(e.getKeyCode(),false);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				setTecla(e.getKeyCode(),true);
			}
		});
	}
	

	

	
	private void carregarJogo() {
		
				
		texto = new Texto();

		tanque = new Tanque();
		tanque.setVel(2);
		tanque.setAtivo(true);
		tanque.setPx(tela.getWidth() / 2 - tanque.getAltura() / 2);
		tanque.setPy(tela.getHeight() - tanque.getAltura() - linhaBase);
		
		tiroTanque = new Tiro(false);
		tiroTanque.setVel(-10);
		
		chefe = new Invader(Tipos.CHEFE);
		tiroChefe = new Tiro(true);
		tiroChefe.setVel(6);
		tiroChefe.setAltura(15);
		
		for(int i = 0; i < invasores.length; i++) {
			for(int j = 0; j < invasores[i].length; j++) {
				Invader e = new Invader(tipoPorLinha[j]);
				e.setAtivo(true);
				e.setPx(i * e.getLargura() + (i + 1) * espacamento);
				e.setPy(j * e.getAltura() + j * espacamento + linhaBase);
				
				invasores[i][j] = e;
			}
		}
		
		
		for(int i = 0; i < tiros.length; i++) {
			tiros[i] = new Tiro(true);
		}
		
		dir = 1;
		totalInimigos = invasores.length * invasores[0].length;

		contadorEspera = totalInimigos / level;
		System.out.print(contadorEspera);
	}
	
	

	public void addTiroInimigo(Elemento inimigo, Elemento tiro) {
		tiro.setAtivo(true);
		tiro.setVel(10);
		tiro.setPx(inimigo.getPx() + inimigo.getLargura() / 2 - tiro.getLargura() / 2);
		tiro.setPy(inimigo.getPy() + inimigo.getAltura());
	}
	
	
	public void iniciaJogo() {
		long prxAtualizacao = 0;
		while(jogando) {
			if(System.currentTimeMillis() >= prxAtualizacao) {
				g2d.setColor(Color.BLACK);
				g2d.fillRect(0, 0, TELA_LARGURA, TELA_ALTURA);
				g2d.setColor(Color.WHITE);
				texto.desenha(g2d,"score: " + pontos, 10,20);
				if(contador > contadorEspera) {
					moverInimigos = true;
					contador = 0;
					contadorEspera = (totalInimigos - destruidos - level * level)*3;
				}else {
					contador++;
				}
				
				if(tanque.isAtivo()) {
					if(controleTecla[2]) {
						tanque.setPx(tanque.getPx() - tanque.getVel());
					}else if(controleTecla[3]) {
						tanque.setPx(tanque.getPx() + tanque.getVel());
					}
				}
				
				for(Elemento tiro: tiros) {
					if(tiro.getPy()>= TELA_ALTURA)
						tiro.setAtivo(false);
					if(tiro.isAtivo()) {
						tiro.incPy(tiro.getVel());
					}
				}
				
				if(controleTecla[4] && !tiroTanque.isAtivo()) {
					tiroTanque.setPx(tanque.getPx() + tanque.getLargura() / 2 - tiroTanque.getLargura() / 2);
					tiroTanque.setPy(tanque.getPy() - tiroTanque.getAltura());
					tiroTanque.setAtivo(true);
				}
				
				if(tiroTanque.isAtivo()) {
					tiroTanque.incPy(tiroTanque.getVel());
					if(Util.colide(tiroTanque, chefe)) {
						tiroTanque.setAtivo(false);
						chefe.setAtivo(false);
						pontos += chefe.getPremio();
					}
				}
				if(tiroTanque.getPy() <= 0) 
					tiroTanque.setAtivo(false);
				
				
				if(chefe.isAtivo()) {
					chefe.incPx(tanque.getVel() - 1);
					if(!tiroChefe.isAtivo() && Util.colideX(chefe, tanque)) {
						addTiroInimigo(chefe,tiroChefe);
					}
					
					if(chefe.getPx() > tela.getWidth()) {
						chefe.setAtivo(false);
					}
				}
				if(tiroChefe.isAtivo()) {
					tiroChefe.incPx(tiroChefe.getVel());
					if(Util.colide(tiroChefe, tanque)) {
						vidas--;
						tiroChefe.setAtivo(false);
					}else if(tiroChefe.getPy() > tela.getHeight() - linhaBase - tiroChefe.getAltura()) {
						tiroChefe.setAtivo(false);
					}else {
						tiroChefe.desenha(g2d);
					}
				}

				
				for(int j = invasores[0].length - 1; j >= 0; j--) {
					for(int i = 0; i < invasores.length;i++) {

						Invader inv = invasores[i][j];
						if(!inv.isAtivo()) {
							continue;
						}
						if(Util.colide(tiroTanque, inv)) {
							inv.setAtivo(false);
							inv.playDestroyed();
							tiroTanque.setAtivo(false);
							destruidos++;
							pontos += inv.getPremio() * level;
							continue;
						}
						if(!tiros[tiroTurn].isAtivo()) {
							if(rand.nextInt(150) == 4) {
								addTiroInimigo(inv,tiros[tiroTurn]);
								System.out.print(tiros[tiroTurn].getVel());
							}
							
							if(tiroTurn==2) tiroTurn=0;
							else tiroTurn++;
						}
						
						if(moverInimigos) {
							inv.atualiza();
							if(novaLinha) {
								inv.setPy(inv.getPy() + inv.getAltura() + espacamento);
							}else {
								inv.incPx(espacamento*dir);
							}
							
							int pxEsq = inv.getPx() - espacamento;
							int pxDir = inv.getPx() + inv.getLargura() + espacamento;
							
							if((pxEsq <=0 && dir == -1) || (pxDir >= tela.getWidth()-10 && dir == 1) && inv.isAtivo()) {
								colideBordas = true;
							}
							
							
							
							/*if(!tiros[0].isAtivo() && inv.getPx() < tanque.getPx()) {
								addTiroInimigo(inv,tiros[0]);
							} else if(!tiros[1].isAtivo() && inv.getPx() > tanque.getPx() && inv.getPx() < tanque.getPx() +  tanque.getLargura()) {
								addTiroInimigo(inv,tiros[1]);
							} else if(!tiros[2].isAtivo() && inv.getPx() > tanque.getPx()) {
								addTiroInimigo(inv,tiros[2]);
							}*/
							
							if(!chefe.isAtivo() && rand.nextInt(1500) == destruidos) {
								chefe.setPx(0);
								chefe.setAtivo(true);
							}
							
						}
					}
				}
				novaLinha = false;
				if(moverInimigos && colideBordas) {
					dir *= -1;
					novaLinha = true;
					colideBordas = false;
				}
				
				
				// Desenhe aqui para as naves ficarem acima dos tiros
				for (int i = 0; i < invasores.length; i++) {
					for (int j = 0; j < invasores[i].length; j++) {
						Invader e = invasores[i][j];
						e.desenha(g2d);
					}
				}
				moverInimigos = false;

				tanque.atualiza();
				tanque.desenha(g2d);
				
				tiroTanque.atualiza();
				tiroTanque.desenha(g2d);

				for(Elemento tiro: tiros ) {
					tiro.atualiza();
					tiro.desenha(g2d);
				}
				
				chefe.atualiza();
				chefe.desenha(g2d);

				g2d.setColor(Color.WHITE);
				// Linha base
				g2d.setColor(Color.GREEN);
				g2d.drawLine(0, tela.getHeight() - linhaBase, tela.getWidth(), tela.getHeight() - linhaBase);

				tela.repaint();

				prxAtualizacao = System.currentTimeMillis() + FPS;
			}
		}
	}
	
	public static void main(String[] args) {
		Jogo jogo = new Jogo();
		jogo.carregarJogo();
		jogo.iniciaJogo();
	}
	
}
