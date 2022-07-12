package aula14;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Exchanger_1 {

    /*
    * Exchanger- Sao classes especializadas para
    * trocar informacoes entre as threads
    * ocorre uma permuta entre as threads
    *
    * */

    private static final Exchanger<String> EXCHANGER = new Exchanger<>();

    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();

        Runnable r1 = () -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " Toma issoooo");
            String retorno = exchange(name + " - Toma issoooo");
            System.out.println(retorno);
        };

        Runnable r2 = () -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " Obrigado");
            String retorno = exchange(name + " - Obrigado");
            System.out.println(retorno);
        };

        executor.execute(r1);
        executor.execute(r2);
        executor.shutdown();
    }

    private static String exchange(String msg) {
        try {
            return EXCHANGER.exchange(msg);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

}
