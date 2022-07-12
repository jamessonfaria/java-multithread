package aula10;

import java.util.concurrent.*;

public class CyclicBarrier_3 {

    /*
    * Cyclic Barrier - Usar quando se tem varias threads executando, em algum
    * momento uma vai esperar a outra. ex: vc tem varias threads realizando diversas
    * operacoes e em um determinado momento vc quer executar a thread que vai sincronizar
    * os dados. Ele é ciclico porque eu posso re-executar o meu cyclic barrier novamente varias vezes
    *
    * */

    private static BlockingQueue<Double> resultados = new LinkedBlockingQueue<>();
    private static ExecutorService executor;
    private static Runnable r1;
    private static Runnable r2;
    private static Runnable r3;

    // (432*3) + (3^14) + (45*127/12) = ?
    public static void main(String[] args) throws InterruptedException {
        Runnable finalizacao = () -> {
            System.out.println("*** Somando tudo ***");
            double resultadoFinal = 0;
            resultadoFinal += resultados.poll();
            resultadoFinal += resultados.poll();
            resultadoFinal += resultados.poll();
            System.out.println("Processamento finalizado. Resultado: " + resultadoFinal);
            System.out.println("-------------------------------");
            try {
                restart();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        // ao cliar um cyclic barrier vc informa qts participantes vao chamar essa barreira
        // nesse caso serao 3, usando o await na tarefa ele vai aguardar ate que as 3 threads finalizem,
        // o segundo parametro é a tarefa a ser executado depois que as 3 threads finalizarem, nesse
        // caso o resultado final
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, finalizacao);
        executor = Executors.newFixedThreadPool(3);

        r1 = () -> {
            resultados.add(432d * 3d);
            await(cyclicBarrier);
        };

        r2 = () -> {
            resultados.add(Math.pow(3d, 14d));
            await(cyclicBarrier);
        };

        r3 = () -> {
            resultados.add(45d * 127d / 12d);
            await(cyclicBarrier);
        };

        restart();
//        executor.shutdown();
    }

    private static void await(CyclicBarrier cyclicBarrier) {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private static void restart() throws InterruptedException {
        Thread.sleep(1000);
        executor.submit(r1);
        executor.submit(r2);
        executor.submit(r3);
    }

}
