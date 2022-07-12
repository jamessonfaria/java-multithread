package aula11;

import java.util.Random;
import java.util.concurrent.*;

public class CountDownLatch_1 {
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

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

        Runnable r1 = () -> {
            int j = new Random().nextInt(1000);
            int x = i * j;
            System.out.println(i + " x " + j + " = " + x);
            latch.countDown();
        };

        executor.scheduleAtFixedRate(r1, 0, 1, TimeUnit.SECONDS);

        // usando sleep com while para alterar o valor de i nao e a melhor forma,
        // pois pode gerar efeitos como usar 4 vezes o mesmo valor de i ...
        while (true){
            //Thread.sleep(3000);
            latch.await();
            i = new Random().nextInt(100);
            latch = new CountDownLatch(3);
        }
    }

}
