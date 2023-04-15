package prototype;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class InterativoMouse extends JFrame {
	
	private JPanel tela;
	private int px, py;
	private Point mouseClick = new Point();
	private boolean jogando = true;
	private final int FPS = 1000/20;
	
	public InterativoMouse() {
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				mouseClick = e.getPoint();
				
			}
		});
		tela = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, tela.getWidth(), tela.getHeight());
				
				
				g.setColor(Color.BLUE);
				g.fillRect(px, py, 40, 40);
				g.drawString("Agora estou em: x="+px+", y="+py, 5, 10);
			}
		};
		getContentPane().add(tela);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640,480);
		setVisible(true);
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
	private void atualizaJogo(){
		px = mouseClick.x;
		py = mouseClick.y;
	}

	public static void main(String[] args) {

		InterativoMouse jogo = new InterativoMouse();
		jogo.inicia();
		
	}

}
