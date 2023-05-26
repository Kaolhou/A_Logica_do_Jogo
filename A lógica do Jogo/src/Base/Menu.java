package Base;

import java.awt.Color;
import java.awt.Graphics2D;

public class Menu extends Texto {

	
	private short idx;
	private String rotulo;
	private String[] opcoes;
	private boolean selecionado;
	
	
	public Menu(String rotulo) {
		super();
		this.rotulo = rotulo;
		setLargura(120);
		setAltura(20);
		setCor(Color.WHITE);
	}
	
	@Override
	public void desenha(Graphics2D g) {
		if(opcoes == null)
			return;
		
		g.setColor(getCor());
		super.desenha(g, getRotulo() + ": <" + opcoes[idx] + ">", getPx(), getPy() + getAltura());
		
		if(selecionado) 
			g.drawLine(getPx(), getPy() + getAltura() + 5, getPx() + getLargura(), getPy() + getAltura() + 5);
		
	}
	
	public void setTrocaOpcao(boolean esquerda) {
		if(!isSelecionado() || !isAtivo())
			return;
		
		idx += esquerda ? -1 : 1;
		
		if(idx < 0) {
			idx = (short) (opcoes.length -1);
		} else if(idx == opcoes.length)
			idx = 0;
	}
	
	public void addOpcoes(String... opcao) {
		opcoes = opcao;
	}

	public short getIdx() {
		return idx;
	}

	public void setIdx(short idx) {
		this.idx = idx;
	}

	public String getRotulo() {
		return rotulo;
	}

	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}

	public int getOpcaoId() {
		return idx;
	}

	public void setOpcoes(String[] opcoes) {
		this.opcoes = opcoes;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

}
