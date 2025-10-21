
package painel;

import java.awt.LayoutManager;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import java.awt.Font;
import javax.swing.UIManager;
import KentHipos.Kensoft;
import trem.Armazem;

import java.awt.Color;

public class Tela2 extends JFrame {
	public JFrame frame;
	public static String dormindo = "/painel/empacotadorDormindo.png";
	public static String empacotando = "/painel/empacotadorCaixaAberta.png";
	public static String terminou = "/painel/empacotadorCaixaFechada.png";
	public static String voltando = "/painel/empacotadorRetornando.png";
	public static String desabilitado = "/painel/empacotadorDesabilitado.png";
	public static String caixa = "/painel/caixa.png";
	public static String caixaMaior = "/painel/caixaMaior.png";
	private JLabel[] empacotadores;
	JLabel contador = new JLabel("0");
	private JLabel trem;

	public static void main(String[] args) {
	}

	public Tela2() {
		this.initialize();
	}

	public void initialize() {
		
		this.frame = new JFrame();
		this.frame.setBounds(0, 0, 1366, 768);
		this.frame.setDefaultCloseOperation(3);
		this.frame.setResizable(false);
		this.frame.getContentPane().setLayout((LayoutManager) null);
		
		trem = new JLabel("");
		trem.setIcon(new ImageIcon(Tela2.class.getResource("/painel/trem.png")));
		trem.setBounds(1000, 20, 100, 250);
		this.frame.getContentPane().add(trem);
		
		this.empacotadores = new JLabel[10];
		JLabel empacotadorlabel;
		for (int i = 0; i < 10; i++) {
			empacotadorlabel = new JLabel("");
			empacotadorlabel.setIcon(new ImageIcon(Tela2.class.getResource(desabilitado)));
			empacotadorlabel.setBounds(120, 30 + 150 * i, 64, 89);
			this.frame.getContentPane().add(empacotadorlabel);
			this.empacotadores[i] = empacotadorlabel;
		}
		
		JLabel caixaLabel;
		for (int i = 0; i < 10; i++) {
			caixaLabel = new JLabel("");
			caixaLabel.setIcon(new ImageIcon(Tela2.class.getResource(caixa)));
			caixaLabel.setBounds(20, 30 + 150 * i, 80, 80);
			this.frame.getContentPane().add(caixaLabel);
		}
		
		contador.setFont(new Font("Calibri", Font.PLAIN, 40));
		contador.setBackground(Color.WHITE);
		contador.setBounds(675, 305, 40, 40);
		this.frame.getContentPane().add(this.contador);
		
		JLabel Cenario = new JLabel("");
		Cenario.setIcon(new ImageIcon(Tela2.class.getResource("/painel/cenario.png")));
		Cenario.setBounds(0, 0, 1366, 768);
		this.frame.getContentPane().add(Cenario);
	}

	public void changeImg(int empacotador, String img) {
		this.empacotadores[empacotador - 1].setIcon(new ImageIcon(Tela2.class.getResource(img)));
	}
	
	public void sentidoTrem(String sentido,double tv) {
		Kensoft animate = new Kensoft();
		tv /= 2;
		tv /= 480;
		int tempot = (int) Math.round(tv);
		if(sentido.equals("Baixo")) {
			animate.jLabelYDown(20, 500, tempot - 2, 1, trem);
		}
		if(sentido.equals("Cima")) {
			animate.jLabelYUp(500, 20, tempot - 2, 1, trem);
		}
	}
	public void sentidoEmpacotador(int empacotador,String sentido,double te) {
		Kensoft animate = new Kensoft();
		if(sentido.equals("Direita")) {
			animate.jLabelXRight(120, 400, 1, 1, empacotadores[empacotador - 1]);
		}
		
		if(sentido.equals("Esquerda")) {
			animate.jLabelXLeft(400, 120, 1, 1, empacotadores[empacotador - 1]);
		}
	}
	
	
}
