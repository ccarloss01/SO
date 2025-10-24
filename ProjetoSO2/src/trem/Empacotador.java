package trem;

import painel.TelaExecucao;

public class Empacotador extends Thread {
	
	public int id;
	public int tempoEmpacotamento;
	
	public TelaExecucao telaExecucao;

	public Empacotador(int id, int tempoEmpacotamento, TelaExecucao telaExecucao) {
		this.id = id;
		this.tempoEmpacotamento = tempoEmpacotamento * 1000;
		this.telaExecucao = telaExecucao;
	}

	public void run() {
		while (true) {
			if (TelaExecucao.empacotar) {
				
				long time = System.currentTimeMillis();
				
				while (System.currentTimeMillis() - time < (long) this.tempoEmpacotamento/2) {
				}
				this.telaExecucao.mudarImagemEmpacotador(this.id, TelaExecucao.empacotadorEmpacotando);
				
				while (System.currentTimeMillis() - time < (long) this.tempoEmpacotamento*3/4) {	
				}
				this.telaExecucao.mudarImagemEmpacotador(this.id, TelaExecucao.empacotadorFinalizou);
				this.telaExecucao.sentidoEmpacotador(this.id,"Direita", this.tempoEmpacotamento);
				
				while (System.currentTimeMillis() - time < (long) this.tempoEmpacotamento) {	
				}
				this.telaExecucao.mudarImagemEmpacotador(this.id, TelaExecucao.empacotadorRetornando);
				
				try {
					if (Semaforo.armazemVazio.availablePermits() == 0) {
						this.telaExecucao.mudarImagemEmpacotador(this.id, TelaExecucao.empacotadorDormindo);
					}

					Semaforo.armazemVazio.acquire();
				} catch (InterruptedException i) {
					i.printStackTrace();
				}
				
				try {
					Semaforo.mutex.acquire();
				} catch (InterruptedException i) {
					i.printStackTrace();
				}

				Armazem.quantidadeAtual++;
				
				this.telaExecucao.caixasArmazenadas(Armazem.quantidadeAtual);
				this.telaExecucao.mudarImagemEmpacotador(this.id, TelaExecucao.empacotadorRetornando);
				this.telaExecucao.sentidoEmpacotador(this.id,"Esquerda", this.tempoEmpacotamento);
				
				Semaforo.mutex.release();
				Semaforo.armazemCheio.release();
			}
		}
	}
}