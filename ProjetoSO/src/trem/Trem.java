package trem;

import painel.Tela;

public class Trem extends Thread {
	public static double tempoViagem;
	public Tela quantidadeCaixas;
	public Tela direcao;

	public Trem(double tempoViagem, Tela quantidadeCaixas, Tela direcao) {
		Trem.tempoViagem = tempoViagem * 1000;
		this.quantidadeCaixas = quantidadeCaixas;
		this.direcao = direcao;
	}

	public void run() {
		while (true) {

			if (Armazem.quantidadeAtual < Armazem.maximoTrem) {
				try {
					Semaforo.armazemSuficiente.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			try {
				Semaforo.mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Armazem.quantidadeAtual -= Armazem.maximoTrem;
			quantidadeCaixas.caixasArmazenadas(Armazem.quantidadeAtual);
			Semaforo.armazemLim.release(Armazem.maximoTrem);
			this.direcao.SentidoT("Baixo",tempoViagem);

			Semaforo.mutex.release();
			
			long time = System.currentTimeMillis();
			
			while (System.currentTimeMillis() - time < tempoViagem / 2) {
			}
			this.direcao.SentidoT("Cima",tempoViagem);
			while (System.currentTimeMillis() - time < tempoViagem) {
			}

		}

	}
}