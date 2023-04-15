package prototype;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class Prototype extends JFrame{
	
	private JPanel tela;
	
	public Prototype() {
		tela = new JPanel() { 
			@Override
			public void paintComponent(Graphics g) {
				g.setColor(Color.BLUE);
				g.drawLine(0, 240, 640, 240);
				g.drawRect(10, 25, 20, 20);
				g.drawOval(30, 20, 40, 30);
				
				g.setColor(Color.YELLOW);
				g.drawLine(320, 0, 320, 480);
				g.fillRect(110, 125, 120, 120);
				g.fillOval(230, 220, 240, 230);
				
				g.setColor(Color.RED);
				g.drawString("pindamonhangaba", 5, 10);
			}
		};
		
		super.getContentPane().add(tela);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640,480);
		setVisible(true);
		tela.repaint();
	}

	public static void main(String[] args) {

		new Prototype();
		
	}

}
