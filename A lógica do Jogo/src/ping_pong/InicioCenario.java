package ping_pong;

import java.awt.Graphics;
import java.awt.Graphics2D;

import Base.CenarioPadrao;
import Base.Menu;
import Base.Util;

public class InicioCenario extends CenarioPadrao{
	private Bola bola;
	private Menu menuModo, menuVeloc;

	public InicioCenario(int largura,int altura) {
		super(largura,altura);
	}

	@Override
	public void atualizar() {

		if(Jogo.controleTecla[Jogo.Tecla.CIMA.ordinal()] || Jogo.controleTecla[Jogo.Tecla.BAIXO.ordinal()]) {
			if(menuModo.isSelecionado()) {
				menuModo.setSelecionado(false);
				menuVeloc.setSelecionado(false);
			}else {
				menuModo.setSelecionado(true);
				menuVeloc.setSelecionado(false);
			}
		} // pag 63
		
	}
	@Override
	public void descarregar() {
		
		Jogo.velocidade = bola.getVel();
		Jogo.modoNormal = menuModo.getOpcaoId() == 0;
		
	}
	@Override
	public void carregar() {
		bola = new Bola();
		
		menuModo = new Menu("Modo");
		menuModo.addOpcoes("Normal","Em casa");
		
		menuVeloc = new Menu("Vel.");
		menuVeloc.addOpcoes("Normal","Rápido","Lento");
		
		Util.centraliza(bola, largura, altura);
		Util.centraliza(menuModo, largura, altura);
		Util.centraliza(menuVeloc, largura, altura);
		
		menuModo.setPy(menuModo.getPy()+20);
		menuVeloc.setPy(menuModo.getPy() + menuModo.getAltura());
		
		bola.setAtivo(true);
		menuModo.setSelecionado(true);
		menuModo.setAtivo(true);
		menuVeloc.setAtivo(true);
		
	}
	@Override
	public void desenhar(Graphics2D g) {
		
		menuModo.desenha(g);
		menuVeloc.desenha(g);
		
	}
}
