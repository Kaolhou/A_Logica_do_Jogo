package SpaceInviders;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import Base.Elemento;

public class Invader extends Elemento {
	
	private Clip destroyedSFX;
	private Clip shootSFX;

	enum  Tipos{
		PEQUENO, MEDIO, GRANDE, CHEFE
	}
	private Tipos tipo;
	private boolean aberto;
	public boolean novaLinha = false;
	
	private BufferedImage image0,image1;
	


	public BufferedImage scaleImage(BufferedImage img, int width, int height,Color background) {
	    int imgWidth = img.getWidth();
	    int imgHeight = img.getHeight();
	    if (imgWidth*height < imgHeight*width) {
	        width = imgWidth*height/imgHeight;
	    } else {
	        height = imgHeight*width/imgWidth;
	    }
	    BufferedImage newImage = new BufferedImage(width, height,
	            BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = newImage.createGraphics();
	    try {
	        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	        g.setBackground(background);
	        g.clearRect(0, 0, width, height);
	        g.drawImage(img, 0, 0, width, height, null);
	    } finally {
	        g.dispose();
	    }
	    return newImage;
	}
	
	public Invader(Tipos tipo) {
		
		setLargura(22);
		setAltura(22);
		try {
			File fileDestroyed = new File(".//src//SpaceInviders//assets//invaderkilled.wav");
			AudioInputStream sound = AudioSystem.getAudioInputStream(fileDestroyed);
			destroyedSFX = AudioSystem.getClip();
			destroyedSFX.open(sound);
			
			File fileShoot = new File(".//src//SpaceInviders//assets//shoot.wav");
			AudioInputStream soundShoot = AudioSystem.getAudioInputStream(fileShoot);
			shootSFX = AudioSystem.getClip();
			shootSFX.open(soundShoot);
			switch(tipo) {
			case PEQUENO:
				this.image0 = scaleImage(ImageIO.read(new File(".//src//SpaceInviders//assets//sprites//sm_sprite_0.png")), getAltura(), getLargura(), Color.BLACK);

				this.image1 = scaleImage(ImageIO.read(new File(".//src//SpaceInviders//assets//sprites//sm_sprite_1.png")), getAltura(),getLargura(), Color.BLACK);
				break;
			case MEDIO:
				this.image0 = scaleImage(ImageIO.read(new File(".//src//SpaceInviders//assets//sprites//md_sprite_0.png")), getAltura(), getLargura(), Color.BLACK);

				this.image1 = scaleImage(ImageIO.read(new File(".//src//SpaceInviders//assets//sprites//md_sprite_1.png")), getAltura(),getLargura(), Color.BLACK);
				break;
			case GRANDE:
				this.image0 = scaleImage(ImageIO.read(new File(".//src//SpaceInviders//assets//sprites//lg_sprite_0.png")), getAltura(), getLargura(), Color.BLACK);

				this.image1 = scaleImage(ImageIO.read(new File(".//src//SpaceInviders//assets//sprites//lg_sprite_1.png")), getAltura(),getLargura(), Color.BLACK);
				break;
			}
			
		} catch (Exception e){
			System.out.println(e);
		}
		this.tipo = tipo;

	}
	@Override
	public void atualiza() {
		aberto = !aberto;
	}
	public int getPremio() {
		switch(tipo) {
		case PEQUENO:
			return 10;
		case MEDIO:
			return 20;
		case GRANDE:
			return 50;
		default:
			return 1000;
		}
	}
	
	public boolean isAberto(){
		return aberto;
	}
	
	public void playShoot() {
		shootSFX.setMicrosecondPosition(0);
		shootSFX.start();
	}
		
	public void playDestroyed() {
		destroyedSFX.setFramePosition(0);
		destroyedSFX.start();
	}
	
	@Override
	public void desenha(Graphics2D g) {
		//todo colocar frames como imagens
		if(!isAtivo())
			return;
		
		int larg = getLargura();
		if (tipo == Tipos.PEQUENO) {
			
			try {
				//larg = larg - 2;

				g.setColor(Color.BLUE);

				if (aberto) {
					g.drawImage(this.image0,getPx(),getPy(),null);

				} else {
					g.drawImage(this.image1,getPx(),getPy(),null);
				}			} catch(Exception e){
				System.out.println(e);
				
			}
		} else if (tipo == Tipos.MEDIO) {
			try {				
				//larg = larg - 2;

				g.setColor(Color.BLUE);

				if (aberto) {
					g.drawImage(this.image0,getPx(),getPy(),null);

				} else {
					g.drawImage(this.image1,getPx(),getPy(),null);
				}
			} catch(Exception e){
				System.out.println(e);
				
			}

		} else if (tipo == Tipos.GRANDE) {

			try {
				//larg = larg - 2;

				g.setColor(Color.BLUE);

				if (aberto) {
					g.drawImage(this.image0,getPx(),getPy(),null);

				} else {
					g.drawImage(this.image1,getPx(),getPy(),null);
				}
			} catch(Exception e){
				System.out.println(e);
				
			}


		} else {
			larg = larg + 10;

			g.setColor(Color.RED);
			g.fillOval(getPx(), getPy(), larg, getAltura());

			if (aberto) {
				g.setColor(Color.WHITE);

				g.fillRect(getPx() + 7, getPy() + getAltura() / 2 - 2, 4, 4);
				g.fillRect(getPx() + 13, getPy() + getAltura() / 2 - 2, 4, 4);
				g.fillRect(getPx() + 19, getPy() + getAltura() / 2 - 2, 4, 4);
			}
		}
		
	}
}
