package SpaceInviders;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import Base.Elemento;

public class Tanque extends Elemento{
	private final int cano = 8;
	private final int escotilha = 10;
	private Clip destroyedSFX;
	
	public Tanque() {
		try {
			File file = new File(".//src//SpaceInviders//assets//explosion.wav");
			AudioInputStream sound = AudioSystem.getAudioInputStream(file);
			destroyedSFX = AudioSystem.getClip();
			destroyedSFX.open(sound);
		} catch (Exception e){
			System.out.println(e);
		}
		setLargura(30);
		setAltura(15);
	}
	
	public void playDestroyed() {
		destroyedSFX.setFramePosition(0);
		destroyedSFX.start();
	}
	
	@Override
	public void atualiza() {
		
	}
	
	@Override
	public void desenha(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(getPx() + getLargura() / 2 - cano / 2, getPy() - cano, cano, cano);
		g.fillRect(getPx(), getPy(), getLargura(), getAltura());
		g.setColor(Color.YELLOW);
		g.fillOval(getPx() + getLargura() / 2 - escotilha / 2, getPy() + getAltura() / 2 - escotilha / 2, escotilha, escotilha);
	}
	
}
