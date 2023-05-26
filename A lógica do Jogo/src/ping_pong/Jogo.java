package ping_pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Base.CenarioPadrao;
import Base.Elemento;

public class Jogo extends JFrame{
	
	private CenarioPadrao cenario;
	
	private Graphics2D g2d;
	
	private int TELA_ALTURA,TELA_LARGURA;
	
	private int FPS = 20;
	private JPanel tela;
	private BufferedImage buffer;
	public enum Tecla{
		CIMA,BAIXO,ESQUERDA,DIREITA,BA,BB
	}
	public static int mouseY;
	public static int nivel = 1;
	public static int velocidade;
	public static boolean modoNormal;


	
	public static boolean pausado;
	public static boolean[] controleTecla =  new boolean[Tecla.values().length];
	
	public static void liberaTeclas() {
		for (int i = 0; i < controleTecla.length; i++) {
			controleTecla[i] = false;
		}
	}
	
	public void setTecla(boolean ativo,int tecla) {
		switch(tecla) {
		case KeyEvent.VK_UP:
			controleTecla[Tecla.CIMA.ordinal()] = ativo;
			break;
		case KeyEvent.VK_DOWN:
			controleTecla[Tecla.BAIXO.ordinal()] = ativo;
			break;
		case KeyEvent.VK_LEFT:
			controleTecla[Tecla.ESQUERDA.ordinal()] = ativo;
			break;
		case KeyEvent.VK_RIGHT:
			controleTecla[Tecla.DIREITA.ordinal()] = ativo;
			break;
		case KeyEvent.VK_SPACE:
			controleTecla[Tecla.BA.ordinal()] = ativo;
			break;
		case KeyEvent.VK_ENTER:
			controleTecla[Tecla.BA.ordinal()] = ativo;
			break;
		case KeyEvent.VK_ESCAPE:
			controleTecla[Tecla.BB.ordinal()] = ativo;
			break;
		}
	}
	
	
	public void carregaJogo() {

		cenario = new InicioCenario(tela.getWidth(), tela.getHeight());
		cenario.carregar();
		
	}
	
	public void iniciaJogo() {
		long prxAtualizacao = 0;
		while(true) {
			if(System.currentTimeMillis() >= prxAtualizacao) {
				g2d.setColor(Color.BLACK);
				g2d.fillRect(0, 0, TELA_LARGURA, TELA_ALTURA);
				
				
				if(cenario == null) {
					g2d.setColor(Color.WHITE);
					g2d.drawString("Carregando", 20, 20);
				}else {
					cenario.atualizar();
					cenario.desenhar(g2d);
				}
				
				if(controleTecla[Tecla.BA.ordinal()]) {
					if(cenario instanceof InicioCenario) {
						cenario.descarregar();
						cenario = new JogoCenario(tela.getWidth(),tela.getHeight());
						cenario.carregar();
					}else if(cenario instanceof JogoCenario) {
						Jogo.pausado = !Jogo.pausado;
					}
					liberaTeclas();
				}else if(controleTecla[Tecla.BB.ordinal()]) {
					if(cenario instanceof JogoCenario) {
						cenario.descarregar();
						cenario = new InicioCenario(tela.getWidth(),tela.getHeight());
						cenario.carregar();
					}
					liberaTeclas();
				}
				
				tela.repaint();
				prxAtualizacao = System.currentTimeMillis() + FPS;
				
			}
		}
	}

	public Jogo(int w,int h) {
		TELA_ALTURA = h;
		TELA_LARGURA = w;

		buffer = new BufferedImage(TELA_LARGURA,TELA_ALTURA, BufferedImage.TYPE_INT_RGB);
		g2d = buffer.createGraphics();

		
		tela = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(buffer, 0, 0, null);
			}
		};
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {
				setTecla(false,e.getKeyCode());				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				setTecla(true,e.getKeyCode());				
			}
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				mouseY = e.getY();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {}
		});
		
		
		getContentPane().add(tela);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(TELA_LARGURA,TELA_ALTURA);
		setVisible(true);
		
	}

	
	public static void main(String[] args) {
		Jogo jogo = new Jogo(640,480);
		jogo.carregaJogo();
		jogo.iniciaJogo();
	}
	

}
