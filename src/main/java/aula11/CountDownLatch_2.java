package aula11;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CountDownLatch_2 {
    /*
     * CountDownLatch - quando estiver executando multiplas threads, vc quer definir
     * que alguma tarefa ex: execute 10 vez e depois 10 execucoes execute outra tarefa
     * DESVANTAGEM: o CountDownLatch nao e reutilizavel, uma vez que atinge o contador
     * ele nao reinicia para comecar novamente, para resolver isso tem que ser criado
     * manualmente uma nova instancia do CountDownLatch
     *
     *  * */

    private static volatile int i = 0;
    private static CountDownLatch latch = new CountDownLatch(3);


    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);

        // essa tarefa principal vai iniciar o countdown
        Runnable r1 = () -> {
            int j = new Random().nextInt(1000);
            int x = i * j;
            System.out.println(i + " x " + j + " = " + x);
            latch.countDown();
        };

        // essa tarefa vai alterar o valor de i
        Runnable r2 = () -> {
            await();
            i = new Random().nextInt(1000);
        };

        // essa tarefa vai instanciar novamente o countdown
        Runnable r3 = () -> {
            await();
            latch = new CountDownLatch(3);
        };

        // essa tarefa vai dar a saida no console
        Runnable r4 = () -> {
            await();
            System.out.println("Terminou! vamos comecar de novo!");
        };

        executor.scheduleAtFixedRate(r1, 0, 1, TimeUnit.SECONDS);
        executor.scheduleWithFixedDelay(r2, 0, 1, TimeUnit.SECONDS);
        executor.scheduleWithFixedDelay(r3, 0, 1, TimeUnit.SECONDS);
        executor.scheduleWithFixedDelay(r4, 0, 1, TimeUnit.SECONDS);

    }

    private static void await() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
