package aula15;

import java.util.Random;
import java.util.concurrent.*;

public class ProdutorConsumidor_3 {

    /*
     * Produtor x Consumidor
     * => Utilizando as ferramentas certas
     * */

    private static final BlockingQueue<Integer> FILA = new LinkedBlockingQueue<>(5);

    public static void main(String[] args) {

        Runnable produtor = () -> {

            simulaProcessamento();
            System.out.println("Produzindo");
            int numero = new Random().nextInt(10000);
            try {
                FILA.put(numero);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        };

        Runnable consumidor = () -> {
            simulaProcessamento();
            System.out.println("Consumindo");
            try {
                FILA.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        executor.scheduleWithFixedDelay(produtor, 0, 10, TimeUnit.MILLISECONDS);
        executor.scheduleWithFixedDelay(consumidor, 0, 10, TimeUnit.MILLISECONDS);
    }

    private static final void simulaProcessamento() {
        int tempo = new Random().nextInt(10);
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

}
