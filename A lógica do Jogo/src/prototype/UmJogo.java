package prototype;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class UmJogo extends JFrame {
	
	private final int FPS = 1000 / 20;
	
	class Elemento {
		public int x, y, largura,altura;
		public float velocidade;
		public boolean ativo = true;
		
		public Elemento(int x,int y, int largura,int altura) {
			this.x = x;
			this.y = y;
			this.altura = altura;
			this.largura = largura;
		}
	}
	
	private JPanel tela;
	private boolean jogando = true;
	private boolean fimDeJogo = false;
	
	private Elemento tiro;
	private Elemento jogador;
	private Elemento[] blocos;
	
	private int pontos;
	private int larg = 50;
	private int linhaLimite = 350;
	private java.util.Random r = new java.util.Random();
	
	private boolean[] controleTecla = new boolean[4];
	
	public UmJogo() {
		
		tela = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void paintComponent(Graphics g) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, tela.getWidth(), tela.getHeight());
				
				g.setColor(Color.RED);
				g.fillRect(tiro.x, tiro.y, tiro.largura, tela.getHeight());
				
				g.setColor(Color.GREEN);
				g.fillRect(jogador.x, jogador.y, jogador.largura, jogador.altura);
				
				g.setColor(Color.BLUE);
				for(Elemento bloco: blocos) {
					g.fillRect(bloco.x, bloco.y, bloco.largura, bloco.altura);
				}
				
				g.setColor(Color.GRAY);
				g.drawLine(0, linhaLimite, tela.getWidth(), linhaLimite);
				
				g.drawString("Pontos: " + pontos, 0, 10);
			};

		};
		
		getContentPane().add(tela);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640,480);
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
		tiro = new Elemento (0,0,1,0);
		jogador = new Elemento(0,0,larg,larg);
		jogador.velocidade = 5;
		
		jogador.x = tela.getWidth() / 2 - jogador.x /2;
		jogador.y = tela.getHeight() - jogador.altura;
		tiro.altura = tela.getHeight() - jogador.altura;
		
		blocos = new Elemento[5];
		for (int i = 0; i < blocos.length; i++) {
			int espaco = i * larg + 10 * (i+1);
			blocos[i] = new Elemento(espaco,0,larg,larg);
			blocos[i].velocidade = 1;
		}
	}
	
	private void setTecla(int key, boolean active) {
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
		}
	}
	
	private void atualizaJogo() {
		if(fimDeJogo)
			return;
		
		if(controleTecla[2])
			jogador.x -= jogador.velocidade;
		else if(controleTecla[3])
			jogador.x += jogador.velocidade;
		
		if(jogador.x < 0)
			jogador.x = tela.getWidth() - jogador.largura;
		
		if(jogador.x + jogador.largura > tela.getWidth())
			jogador.x = 0;
		
		tiro.y = 0;
		tiro.x = jogador.x + jogador.largura / 2;
		
		for(Elemento bloco: blocos) {
			if(bloco.y > linhaLimite) {
				fimDeJogo = true;
				break;
			}
			if(colide(bloco, tiro) && bloco.y > 0) {
				bloco.y -= bloco.velocidade * 2;
				tiro.y = bloco.y;
			}else {
				int sorte = r.nextInt(10);
				if(sorte == 10)
					bloco.y += bloco.velocidade + 1;
				else if(sorte == 5)
					bloco.y -= bloco.velocidade;
				else bloco.y += bloco.velocidade;
			}
		}
		pontos += blocos.length;
	}
	
	private boolean colide(Elemento a, Elemento b) {
		if(a.x + a.largura >= b.x && a.x <= b.x + b.largura) {
			return true;
		}
		return false;
	}
	
	public void inicia() {
		long prxAtualizacao = 0;
		while(jogando) {
			if(System.currentTimeMillis() >= prxAtualizacao) {
				atualizaJogo();
				tela.repaint();
				prxAtualizacao = System.currentTimeMillis() + FPS;
			}
		}

	}

	public static void main(String[] args) {

		UmJogo jogo = new UmJogo();
		jogo.inicia();
		
	}

}
