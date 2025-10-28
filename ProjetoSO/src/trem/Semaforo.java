package trem;
import java.util.concurrent.Semaphore;


public class Semaforo {
	
	public static Semaphore armazemVazio = new Semaphore(Armazem.capacidadeArmazem);
	
	public static Semaphore armazemCheio = new Semaphore(0);
	
	public static Semaphore mutex = new Semaphore(1);
	
}
