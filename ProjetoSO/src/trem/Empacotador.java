package trem;

import painel.Tela;
import painel.Tela2;

public class Empacotador extends Thread {
	private int id;
	public int tempoEmpacotamento;
	public Tela mainInterface;
	public Tela quantidadeCaixas;

	public Empacotador(int id, int tempo_empacotamento, Tela mainInterface, Tela progresso) {
		this.id = id;
		this.tempoEmpacotamento = tempo_empacotamento * 1000;
		this.mainInterface = mainInterface;
		this.quantidadeCaixas = progresso;
	}

	public void run() {
		while (true) {
			
			
			long time = System.currentTimeMillis();
			
			while (System.currentTimeMillis() - time < (long) this.tempoEmpacotamento/2) {
			}
			this.mainInterface.changeImg(this.id, Tela2.empacotando);
			
			while (System.currentTimeMillis() - time < (long) this.tempoEmpacotamento*3/4) {	
			}
			this.mainInterface.changeImg(this.id, Tela2.terminou);
			this.mainInterface.SentidoE(this.id,"Direita", this.tempoEmpacotamento);
			
			while (System.currentTimeMillis() - time < (long) this.tempoEmpacotamento) {	
			}
			this.mainInterface.changeImg(this.id, Tela2.voltando);
			
			try {
				if (Semaforo.armazemLim.availablePermits() == 0) {
					
					this.mainInterface.changeImg(this.id, Tela2.dormindo);
				}

				Semaforo.armazemLim.acquire();
			} catch (InterruptedException i) {
				i.printStackTrace();
			}
			
			try {
				Semaforo.mutex.acquire();
			} catch (InterruptedException i) {
				i.printStackTrace();
			}

			Armazem.quantidadeAtual++;
			
			quantidadeCaixas.caixasArmazenadas(Armazem.quantidadeAtual);

			this.mainInterface.changeImg(this.id, Tela2.voltando);
			this.mainInterface.SentidoE(this.id,"Esquerda", this.tempoEmpacotamento);
			Semaforo.mutex.release();
			if (Armazem.quantidadeAtual >= Armazem.maximoTrem) {
				Semaforo.armazemSuficiente.release();
			}
		}
	}
}