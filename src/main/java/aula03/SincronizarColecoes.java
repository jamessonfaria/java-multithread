package aula03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SincronizarColecoes {

    /*
    * NAO USE synchronized com LIST ou MAP
    *
    * */

    //private static List<String> lista = new ArrayList<>();
    // para usar uma lista sincronizada utilizando Collections, ela ja protege essa lista
    // ex: syncronizedList, syncronizedMap, syncronizedSet ...
    private static List<String> lista = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws InterruptedException {
        MeuRunnable meuRunnable = new MeuRunnable();
        Thread t0 = new Thread(meuRunnable);
        Thread t1 = new Thread(meuRunnable);
        Thread t2 = new Thread(meuRunnable);

        t0.start();
        t1.start();
        t2.start();

        Thread.sleep(500);

        System.out.println(SincronizarColecoes.lista);

    }

    public static class MeuRunnable implements Runnable{
        @Override
        public void run() {
            lista.add("bem vindoooooo");
            String name = Thread.currentThread().getName();
            System.out.println(name + " inseriu na lista!");
        }
    }

}
