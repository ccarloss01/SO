package trem;

import java.awt.EventQueue;
import java.io.IOException;
import javax.sound.sampled.*;

import painel.TelaInicial;



class Main extends Thread { 
	
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException{

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaInicial window = new TelaInicial();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		

	}
}


