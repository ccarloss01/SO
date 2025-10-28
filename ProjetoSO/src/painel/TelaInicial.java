package painel;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class TelaInicial extends JFrame {
	public JFrame frame;
	
	public JTextField campoTempoViagem;
	public JTextField campoCargaTrem;
	public JTextField campoMaximoArmazem;
	
	public static int tempoViagemTrem;
	public static int capacidadeTrem;
	public static int capacidadeArmazem;
	
	private TelaExecucao telaExecucao;

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
	}

	public TelaInicial() {
		this.initialize();
	}

	public void initialize() {
		
		this.frame = new JFrame();
		
		this.frame.getContentPane().setBackground(Color.WHITE);
		this.frame.setBounds(0, 0, 350, 450);
		this.frame.setDefaultCloseOperation(3);
		this.frame.getContentPane().setLayout((LayoutManager) null);
		this.frame.setResizable(false);
		
		JLabel viagem = new JLabel("Tempo de Viagem");
		viagem.setBackground(Color.BLACK);
		viagem.setFont(new Font("Calibri", Font.PLAIN, 22));
		viagem.setBounds(85, 20, 200, 30);
		this.frame.getContentPane().add(viagem);
		
		this.campoTempoViagem = new JTextField();
		this.campoTempoViagem.setBackground(Color.WHITE);
		this.campoTempoViagem.setFont(new Font("Calibri", Font.PLAIN, 26));
		this.campoTempoViagem.setBounds(120, 60, 80, 40);
		this.frame.getContentPane().add(this.campoTempoViagem);
		
		JLabel carga = new JLabel("Carga do Trem");
		carga.setBackground(Color.BLACK);
		carga.setFont(new Font("Calibri", Font.PLAIN, 22));
		carga.setBounds(100, 120, 200, 30);
		this.frame.getContentPane().add(carga);
		
		this.campoCargaTrem = new JTextField();
		this.campoCargaTrem.setBackground(Color.WHITE);
		this.campoCargaTrem.setFont(new Font("Calibri", Font.PLAIN, 26));
		this.campoCargaTrem.setBounds(120, 160, 80, 40);
		this.frame.getContentPane().add(this.campoCargaTrem);
		
		JLabel armazem = new JLabel("Capacidade do Armazém");
		armazem.setBackground(Color.BLACK);
		armazem.setFont(new Font("Calibri", Font.PLAIN, 22));
		armazem.setBounds(50, 220, 280, 30);
		this.frame.getContentPane().add(armazem);
		
		this.campoMaximoArmazem = new JTextField();
		this.campoMaximoArmazem.setBackground(Color.WHITE);
		this.campoMaximoArmazem.setFont(new Font("Calibri", Font.PLAIN, 26));
		this.campoMaximoArmazem.setBounds(120, 260, 80, 40);
		this.frame.getContentPane().add(this.campoMaximoArmazem);
			
		JButton iniciar = new JButton("");
		iniciar.setIcon(new ImageIcon(TelaInicial.class.getResource("/painel/iniciar.png")));
		
		iniciar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					TelaInicial.tempoViagemTrem = Integer.parseInt(TelaInicial.this.campoTempoViagem.getText());
					TelaInicial.capacidadeTrem = Integer.parseInt(TelaInicial.this.campoCargaTrem.getText());
					TelaInicial.capacidadeArmazem = Integer.parseInt(TelaInicial.this.campoMaximoArmazem.getText());
				} catch (NumberFormatException n) {
					System.exit(0);
				}

				if (TelaInicial.capacidadeTrem > TelaInicial.capacidadeArmazem) {
					JOptionPane.showMessageDialog((Component) null, "Capacidade do armazém deve ser superior à carga do trem.");
					System.exit(0);
				}

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							TelaInicial.this.telaExecucao = new TelaExecucao();
							TelaInicial.this.telaExecucao.frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					
				});

			}
		});
		
		iniciar.setBounds(60, 350, 200, 50);
		this.frame.getContentPane().add(iniciar);
	}
}
