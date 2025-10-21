package trem;
import java.util.concurrent.Semaphore;


public class Semaforo {
	
	public static Semaphore armazemLim = new Semaphore(Armazem.maximoArmazem);
	
	public static Semaphore armazemSuficiente = new Semaphore(0);
	
	public static Semaphore mutex = new Semaphore(1);
	
}
