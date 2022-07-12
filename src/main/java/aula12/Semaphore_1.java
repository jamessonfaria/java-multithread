package aula12;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Semaphore_1 {

    /*
    * Semaphore - vc vai utilizar quando vc sabe a quantidade de threads
    * que vc quer que possam executar um trecho de codigo ao mesmo tempo
    * é mais uma forma de controlar a concorrencia e o paralelismo
    *
    * */

    // criou um semaforo com 3 vagas
    private static final Semaphore SEMAFORO = new Semaphore(3);

    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();

        Runnable r1 = () -> {
            String name = Thread.currentThread().getName();
            int usuario = new Random().nextInt(1000);

            // tenta adquirir acesso ao semaforo, se nao tiver vaga fica esperando a liberacao
            acquire();
            System.out.println("Usuario " + usuario + " se increveu usando a thread " + name);
            sleep();
            // avisa o semaforo que esta liberado a thread para outras tarefas
            SEMAFORO.release();
        };

        for (int i = 0; i < 500; i++) {
            executor.execute(r1);
        }

        executor.shutdown();
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

    private static void acquire() {
        try {
            SEMAFORO.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

}
