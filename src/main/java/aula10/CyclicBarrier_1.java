package aula10;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrier_1 {

    /*
    * Cyclic Barrier - Usar quando se tem varias threads executando, em algum
    * momento uma vai esperar a outra. ex: vc tem varias threads realizando diversas
    * operacoes e em um determinado momento vc quer executar a thread que vai sincronizar
    * os dados.
    *
    * */

    // (432*3) + (3^14) + (45*127/12) = ?
    public static void main(String[] args) {
        // ao cliar um cyclic barrier vc informa qts participantes vao chamar essa barreira
        // nesse caso serao 3, usando o await na tarefa ele vai aguardar ate que as 3 threads finalizem
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Runnable r1 = () -> {
            System.out.println(432d * 3d);
            await(cyclicBarrier);
            System.out.println("Terminei o processamento 1");
        };

        Runnable r2 = () -> {
            System.out.println(Math.pow(3d, 14d));
            await(cyclicBarrier);
            System.out.println("Terminei o processamento 2");
        };

        Runnable r3 = () -> {
            System.out.println(45d * 127d / 12d);
            await(cyclicBarrier);
            System.out.println("Terminei o processamento 3");
        };

        executor.submit(r1);
        executor.submit(r2);
        executor.submit(r3);

        executor.shutdown();
    }

    private static void await(CyclicBarrier cyclicBarrier) {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

}
