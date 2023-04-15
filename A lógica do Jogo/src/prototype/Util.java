package prototype;

import prototype.UmJogo.Elemento;

public class Util {

	public static boolean colide(Elemento a, Elemento b) {
		if(!a.ativo||!b.ativo)
			return false;
		
		final int plA = a.x + a.largura;
		final int plB = b.x + b.largura;
		
		final int paA = a.y + a.altura;
		final int paB = b.y + b.altura;
		
		if(plA > b.x && a.x < plB && paA > b.y && a.y < paB) {
			return true;
		}
		
		return false;
	}
	
}
