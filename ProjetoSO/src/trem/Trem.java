package trem;

import painel.TelaExecucao;

public class Trem extends Thread {
	
	public double tempoViagem;
	
	public TelaExecucao telaExecucao;

	public Trem(double tempoViagem, TelaExecucao telaExecucao) {
		this.tempoViagem = tempoViagem * 1000;
		this.telaExecucao = telaExecucao;
	}

	public void run() {
		while (true) {

			try {
				this.telaExecucao.mudarImagemTrem(TelaExecucao.tremDormindo);
				Semaforo.armazemCheio.acquire(Armazem.capacidadeTrem);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			try {
				Semaforo.mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			this.telaExecucao.mudarImagemTrem(TelaExecucao.tremIndo);
			
			Armazem.quantidadeAtual -= Armazem.capacidadeTrem;
			
			this.telaExecucao.caixasArmazenadas(Armazem.quantidadeAtual);
			this.telaExecucao.sentidoTrem("Baixo",tempoViagem);

			Semaforo.armazemVazio.release(Armazem.capacidadeTrem);
			Semaforo.mutex.release();
			
			long time = System.currentTimeMillis();
			
			while (System.currentTimeMillis() - time < tempoViagem / 2) {
			}
			
			this.telaExecucao.mudarImagemTrem(TelaExecucao.tremVoltando);
			this.telaExecucao.sentidoTrem("Cima",tempoViagem);
			while (System.currentTimeMillis() - time < tempoViagem) {
			}

		}

	}
}