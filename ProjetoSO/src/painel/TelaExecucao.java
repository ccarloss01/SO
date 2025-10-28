
package painel;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;

import KentHipos.Kensoft;
import trem.Armazem;
import trem.Empacotador;
import trem.Trem;

import java.awt.Color;
import java.awt.Component;

public class TelaExecucao extends JFrame {
	public JFrame frame;

	private JLabel[] empacotadores;
	private int id_empacotador = 1;
	List<Empacotador> lista = new ArrayList<Empacotador>();
	public static boolean empacotar = true;
	public static String empacotadorDormindo = "/painel/empacotadorDormindo.png";
	public static String empacotadorEmpacotando = "/painel/empacotadorCaixaAberta.png";
	public static String empacotadorFinalizou = "/painel/empacotadorCaixaFechada.png";
	public static String empacotadorRetornando = "/painel/empacotadorRetornando.png";
	public static String empacotadorDesabilitado = "/painel/empacotadorDesabilitado.png";

	JLabel contadorCaixas = new JLabel("0");
	public static String caixa = "/painel/caixa.png";
	public static String caixaMaior = "/painel/caixaMaior.png";

	private JLabel trem;
	public static String tremIndo = "/painel/tremIndo.png";
	public static String tremVoltando = "/painel/tremVoltando.png";
	public static String tremDormindo = "/painel/tremDormindo.png";

	Kensoft animate = new Kensoft();
	
	public static void main(String[] args) {
	}

	public TelaExecucao() {
		this.initialize();
	}

	public void initialize() {
		
		this.frame = new JFrame();
		
		this.frame.setBounds(0, 0, 1366, 768);
		this.frame.setDefaultCloseOperation(3);
		this.frame.setResizable(false);
		this.frame.getContentPane().setLayout((LayoutManager) null);
		
		this.trem = new JLabel("");
		this.trem.setIcon(new ImageIcon(TelaExecucao.class.getResource(tremDormindo)));
		this.trem.setBounds(960, 40, 100, 250);
		this.frame.getContentPane().add(trem);
		
		this.empacotadores = new JLabel[5];
		JLabel empacotadorlabel;
		for (int i = 0; i < 5; i++) {
			empacotadorlabel = new JLabel("");
			empacotadorlabel.setIcon(new ImageIcon(TelaExecucao.class.getResource(empacotadorDesabilitado)));
			empacotadorlabel.setBounds(170, 50 + 130 * i, 64, 89);
			this.frame.getContentPane().add(empacotadorlabel);
			this.empacotadores[i] = empacotadorlabel;
		}
		
		this.contadorCaixas.setFont(new Font("Calibri", Font.PLAIN, 40));
		this.contadorCaixas.setBackground(Color.WHITE);
		this.contadorCaixas.setBounds(695, 325, 40, 40);
		this.frame.getContentPane().add(this.contadorCaixas);
		
		JLabel Cenario = new JLabel("");
		Cenario.setIcon(new ImageIcon(TelaExecucao.class.getResource("/painel/cenario.png")));
		Cenario.setBounds(0, 0, 1366, 768);
		this.frame.getContentPane().add(Cenario);
		
		Trem trem = new Trem((double) TelaInicial.tempoViagemTrem, TelaExecucao.this);
		trem.start();
		
		JButton pararEmpacotamento = new JButton("");
		pararEmpacotamento.setIcon(new ImageIcon(TelaInicial.class.getResource("/painel/pararEmpacotador.png")));
		
		pararEmpacotamento.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if (TelaExecucao.empacotar) {
					TelaExecucao.empacotar = false;
				} else {
					TelaExecucao.empacotar = true;
				}
			}
		});
		
		pararEmpacotamento.setBounds(1130, 250, 200, 100);
		this.frame.getContentPane().add(pararEmpacotamento);
		
		JButton adicionarEmpacotador = new JButton("");
		adicionarEmpacotador.setIcon(new ImageIcon(TelaInicial.class.getResource("/painel/adicionarEmpacotador.png")));
		
		adicionarEmpacotador.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (id_empacotador < 6) {
					
					try {
						int empacotamento = Integer.parseInt(JOptionPane.showInputDialog("Tempo de empacotamento do nº" + id_empacotador));
						lista.add(new Empacotador(id_empacotador, empacotamento, TelaExecucao.this));
					} catch (NumberFormatException n) {
						JOptionPane.showMessageDialog((Component) null, "Por favor, inserir números.");
						System.exit(0);
					}

					lista.get(id_empacotador - 1).start();
					id_empacotador++;
					
				}				
			}
		});
		
		adicionarEmpacotador.setBounds(1130, 400, 200, 100);
		this.frame.getContentPane().add(adicionarEmpacotador);
		
		
	}

	public void mudarImagemEmpacotador(int empacotador, String imagem) {
		this.empacotadores[empacotador - 1].setIcon(new ImageIcon(TelaExecucao.class.getResource(imagem)));
	}
	
	public void mudarImagemTrem(String img) {
		this.trem.setIcon(new ImageIcon(TelaExecucao.class.getResource(img)));
	}
	
	public void caixasArmazenadas(int atual) {
		if (atual < Armazem.capacidadeArmazem) {
			this.contadorCaixas.setText(String.valueOf(atual));
		} else {
			this.contadorCaixas.setText("M");
		}
	}
	
	public void sentidoTrem(String sentido,double tv) {
		tv /= 2;
		tv /= 480;
		int atraso = (int) Math.round(tv);
		
		if(sentido.equals("Baixo")) {
			animate.jLabelYDown(40, 445, atraso - 2, 1, trem);
		}else if(sentido.equals("Cima")) {
			animate.jLabelYUp(445, 40, atraso - 2, 1, trem);
		}
	}
	public void sentidoEmpacotador(int empacotador,String sentido,double te) {
		if(sentido.equals("Direita")) {
			animate.jLabelXRight(170, 450, 1, 1, empacotadores[empacotador - 1]);
		}
		
		if(sentido.equals("Esquerda")) {
			animate.jLabelXLeft(450, 170, 1, 1, empacotadores[empacotador - 1]);
		}
	}
	
	
}
