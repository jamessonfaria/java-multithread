package aula12;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Semaphore_2 {

    /*
    * Semaphore - vc vai utilizar quando vc sabe a quantidade de threads
    * que vc quer que possam executar um trecho de codigo ao mesmo tempo
    * é mais uma forma de controlar a concorrencia e o paralelismo
    *
    * */

    // criou um semaforo com 3 vagas
    private static final Semaphore SEMAFORO = new Semaphore(3);
    private static final AtomicInteger QTD = new AtomicInteger(0);

    public static void main(String[] args) {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(501);

        Runnable r1 = () -> {
            String name = Thread.currentThread().getName();
            int usuario = new Random().nextInt(1000);

            boolean conseguiu = false;
            QTD.incrementAndGet();
            while (!conseguiu){
                conseguiu = tryAcquire();
            }
            QTD.decrementAndGet();

            System.out.println("Usuario " + usuario + " se increveu usando a thread " + name);
            sleep();
            // avisa o semaforo que esta liberado a thread para outras tarefas
            SEMAFORO.release();
        };

        Runnable r2 = () -> {
            System.out.println(QTD.get());
        };

        for (int i = 0; i < 500; i++) {
            executor.execute(r1);
        }

        executor.scheduleWithFixedDelay(r2, 0, 100, TimeUnit.MILLISECONDS);
    }

    private static void sleep() {
        try {
            int tempoEspera = new Random().nextInt(6);
            tempoEspera++;
            Thread.sleep(1000 * tempoEspera);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private static boolean tryAcquire() {
        try {
            // acquire - tenta adquirir uma vaga no semaforo, se nao tiver vaga fica esperando a liberacao
            //SEMAFORO.acquire();
            // tryAcquire - tenta adquirir uma vaga no semaforo por no maximo 1s nesse exemplo, se conseguir
            // retorna true caso contrario retorna false
            return SEMAFORO.tryAcquire(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
            return false;
        }
    }

}
