package aula14;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueue_1 {

    /*
    * Synchronous Queue - Sao classes especializadas para
    * trocar informacoes entre as threads, no minimo duas threads,
    * é necessario uma thread colocar na fila e outra thread retirar da
    * fila para o processo finalizar
    *
    *
    * */

    private static final SynchronousQueue<String> FILA = new SynchronousQueue<>();

    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();

        Runnable r1 = () -> {
            put();
            String name = Thread.currentThread().getName();
            System.out.println(name + " - Escreveu na fila");
        };

        Runnable r2 = () -> {
            String msg = take();
            // .pool igual ao take porem com timeout
            //return FILA.poll(timeout, unit);
            String name = Thread.currentThread().getName();
            System.out.println(name + " - Pegou da fila: " + msg);
        };

        executor.execute(r1);
        executor.execute(r2);
        executor.shutdown();

    }

    private static void put() {
        try {
            // so escreve na fila, precisa que outra tarefa leia da fila
            FILA.put("Inscreva-se");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private static String take() {
        try {
            // vai ler da fila dai o processo se completa
            return FILA.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
