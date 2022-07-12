package aula10;

import java.util.concurrent.*;

public class CyclicBarrier_2 {

    /*
    * Cyclic Barrier - Usar quando se tem varias threads executando, em algum
    * momento uma vai esperar a outra. ex: vc tem varias threads realizando diversas
    * operacoes e em um determinado momento vc quer executar a thread que vai sincronizar
    * os dados.
    *
    * */

    private static BlockingQueue<Double> resultados = new LinkedBlockingQueue<>();

    // (432*3) + (3^14) + (45*127/12) = ?
    public static void main(String[] args) {
        Runnable finalizacao = () -> {
            System.out.println("*** Somando tudo ***");
            double resultadoFinal = 0;
            resultadoFinal += resultados.poll();
            resultadoFinal += resultados.poll();
            resultadoFinal += resultados.poll();
            System.out.println("Processamento finalizado. Resultado: " + resultadoFinal);
        };

        // ao cliar um cyclic barrier vc informa qts participantes vao chamar essa barreira
        // nesse caso serao 3, usando o await na tarefa ele vai aguardar ate que as 3 threads finalizem,
        // o segundo parametro é a tarefa a ser executado depois que as 3 threads finalizarem, nesse
        // caso o resultado final
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, finalizacao);
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Runnable r1 = () -> {
            System.out.println(Thread.currentThread().getName());
            resultados.add(432d * 3d);
            await(cyclicBarrier);
            System.out.println(Thread.currentThread().getName());
        };

        Runnable r2 = () -> {
            System.out.println(Thread.currentThread().getName());
            resultados.add(Math.pow(3d, 14d));
            await(cyclicBarrier);
            System.out.println(Thread.currentThread().getName());

        };

        Runnable r3 = () -> {
            System.out.println(Thread.currentThread().getName());
            resultados.add(45d * 127d / 12d);
            await(cyclicBarrier);
            System.out.println(Thread.currentThread().getName());

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
