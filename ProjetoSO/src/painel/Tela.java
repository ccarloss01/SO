package painel;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import trem.Armazem;
import trem.Empacotador;
import trem.Trem;


public class Tela extends JFrame {
	public JFrame frame;
	public JTextField campoTempoViagem;
	public JTextField campoCargaTrem;
	public JTextField campoMaximoArmazem;
	public JTextArea textArea;
	public static int tempoViagemTrem;
	public static int cargaTrem;
	public static int maximoArmazem;
	public int quantidadeEmpacotadores;
	private Tela2 painelExecucao;

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
	}

	public Tela() {
		this.initialize();
	}

	public void initialize() {
		
		this.frame = new JFrame();
		
		this.frame.getContentPane().setBackground(Color.WHITE);
		this.frame.setBounds(0, 0, 350, 560);
		this.frame.setDefaultCloseOperation(3);
		this.frame.getContentPane().setLayout((LayoutManager) null);
		this.frame.setResizable(false);
		
		this.campoTempoViagem = new JTextField();
		campoTempoViagem.setBackground(Color.WHITE);
		campoTempoViagem.setFont(new Font("Calibri", Font.PLAIN, 26));
		this.campoTempoViagem.setBounds(120, 60, 80, 40);
		this.frame.getContentPane().add(this.campoTempoViagem);
		
		this.campoCargaTrem = new JTextField();
		campoCargaTrem.setBackground(Color.WHITE);
		campoCargaTrem.setFont(new Font("Calibri", Font.PLAIN, 26));
		this.campoCargaTrem.setBounds(120, 160, 80, 40);
		this.frame.getContentPane().add(this.campoCargaTrem);
		
		this.campoMaximoArmazem = new JTextField();
		campoMaximoArmazem.setBackground(Color.WHITE);
		campoMaximoArmazem.setFont(new Font("Calibri", Font.PLAIN, 26));
		this.campoMaximoArmazem.setBounds(120, 260, 80, 40);
		this.frame.getContentPane().add(this.campoMaximoArmazem);
		
		final JComboBox quantidadeEmpacotadores = new JComboBox();
		quantidadeEmpacotadores.setBackground(Color.WHITE);
		quantidadeEmpacotadores.setFont(new Font("Calibri", Font.PLAIN, 26));
		quantidadeEmpacotadores.setMaximumRowCount(5);
		quantidadeEmpacotadores.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5"}));
		quantidadeEmpacotadores.setBounds(120, 360, 80, 50);
		this.frame.getContentPane().add(quantidadeEmpacotadores);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(Tela.class.getResource("/painel/jogar.jpg")));
		btnNewButton.setSelectedIcon(new ImageIcon(Tela.class.getResource("/painel/trempequeno.png")));
		
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					Tela.tempoViagemTrem = Integer.parseInt(Tela.this.campoTempoViagem.getText());
					Tela.cargaTrem = Integer.parseInt(Tela.this.campoCargaTrem.getText());
					Tela.maximoArmazem = Integer.parseInt(Tela.this.campoMaximoArmazem.getText());
				} catch (NumberFormatException n) {
					JOptionPane.showMessageDialog((Component) null, "Por favor, inserir números.");
					System.exit(0);
				}

				if (Tela.cargaTrem > Tela.maximoArmazem) {
					JOptionPane.showMessageDialog((Component) null,
							"Capacidade do armazém não é superior à carga do trem.");
					System.exit(0);
				}

				Trem piui = new Trem((double) Tela.tempoViagemTrem,Tela.this,Tela.this);
				Tela.this.quantidadeEmpacotadores = quantidadeEmpacotadores.getSelectedIndex() + 1;
				List<Empacotador> lista = new ArrayList();

				for (int i = 0; i < Tela.this.quantidadeEmpacotadores; i++) {
					try {
						int empacotamento = Integer.parseInt(JOptionPane.showInputDialog("Tempo de empacotamento do nº" + (i + 1)));
						lista.add(new Empacotador((i + 1), empacotamento, Tela.this, Tela.this));
					} catch (NumberFormatException n) {
						JOptionPane.showMessageDialog((Component) null, "Por favor, inserir números.");
						System.exit(0);
					}
				}

				piui.start();
				Iterator v = lista.iterator();

				while (v.hasNext()) {
					Empacotador empacotarTemp = (Empacotador) v.next();
					empacotarTemp.start();
				}

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Tela.this.painelExecucao = new Tela2();
							Tela.this.painelExecucao.frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					
				});

			}
		});
		
		JLabel viagem = new JLabel("Tempo de Viagem");
		viagem.setBackground(Color.BLACK);
		viagem.setFont(new Font("Calibri", Font.PLAIN, 22));
		viagem.setBounds(90, 20, 200, 30);
		frame.getContentPane().add(viagem);
		
		JLabel carga = new JLabel("Carga do Trem");
		carga.setBackground(Color.BLACK);
		carga.setFont(new Font("Calibri", Font.PLAIN, 22));
		carga.setBounds(100, 120, 200, 30);
		frame.getContentPane().add(carga);
		
		JLabel armazem = new JLabel("Capacidade do Armazém");
		armazem.setBackground(Color.BLACK);
		armazem.setFont(new Font("Calibri", Font.PLAIN, 22));
		armazem.setBounds(50, 220, 280, 30);
		frame.getContentPane().add(armazem);
		
		JLabel empacotadores = new JLabel("Quantidade de Empacotadores");
		empacotadores.setBackground(Color.BLACK);
		empacotadores.setFont(new Font("Calibri", Font.PLAIN, 22));
		empacotadores.setBounds(20, 320, 300, 30);
		frame.getContentPane().add(empacotadores);
		
		btnNewButton.setBounds(60, 460, 200, 50);
		this.frame.getContentPane().add(btnNewButton);
	}

	public void changeImg(int empacotador, String img) {
		if (this.painelExecucao != null) {
			this.painelExecucao.changeImg(empacotador, img);
		}

	}
	
	public void caixasArmazenadas(int atual) {
		if (this.painelExecucao != null) {
			if (atual < Armazem.maximoArmazem) {
				this.painelExecucao.contador.setText(String.valueOf(atual));
			} else {
				this.painelExecucao.contador.setText("M");
			}
		}
	}
	
	public void SentidoT(String sentido,double tv) {
		if (this.painelExecucao != null) {
			this.painelExecucao.sentidoTrem(sentido,tv);
		}

	}
	
	public void SentidoE(int empacotador,String sentido,double te) {
		if (this.painelExecucao != null) {
			this.painelExecucao.sentidoEmpacotador(empacotador,sentido,te);
		}

	}
}
