package prototype;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Interativo2 extends JFrame {
	
	private JPanel tela;
	private int px;
	private int py;
	private boolean jogando = true;
	private boolean[] controleTecla = new boolean[4];
	
	private final int FPS = 1000 / 20;
	
	public Interativo2() {
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
		tela = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, tela.getWidth(), tela.getHeight());
				
				int x = tela.getWidth() / 2 - 20 + px;
				int y = tela.getHeight() / 2 -20 + py;
				
				g.setColor(Color.BLUE);
				g.fillRect(x, y, 40, 40);
				g.drawString("Agora estou em: x="+x+", y="+y, 5, 10);
			}
		};
		getContentPane().add(tela);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640,480);
		setVisible(true);
	}
	
	private void atualizaJogo() {
		if(controleTecla[0]) 
			py--;
		else if(controleTecla[1]) 
			py++;
		
		if(controleTecla[2]) 
			px--;
		else if(controleTecla[3])
			px++;
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

		Interativo2 jogo = new Interativo2();
		jogo.inicia();
		
	}

}
