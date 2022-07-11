package aula04;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ColecoesParaConcorrencia {

    //private static List<String> lista = new ArrayList<>();
    //private static List<String> lista = new CopyOnWriteArrayList<>();
    //private static Map<Integer, String> mapa = new ConcurrentHashMap<>();
    private static Queue<String> fila = new LinkedBlockingQueue<>();

    // Colecoes que sao Thread-safe, sao colecoes que
    // sao seguras para usar thread, evitam problemas colaterais,

    // CopyOnWriteArrayList - porem ela é pesada para ser usada, ela copia um array inteiro
    // para fazer as operacoes, adicionar, remover, se usar muita escrita
    // nao deve usar

    // ConcurrentHashMap - desvantagem que também usa o syncronized na
    // sua implementacao

    // LinkedBlockingQueue - boa alternativa para usar lista
    // LinkedBlockingDeque - vc pode tanto adicionar/remover
    // elementos no inicio ou no fim da fila
    public static void main(String[] args) throws InterruptedException {
        MeuRunnable meuRunnable = new MeuRunnable();
        Thread t0 = new Thread(meuRunnable);
        Thread t1 = new Thread(meuRunnable);
        Thread t2 = new Thread(meuRunnable);

        t0.start();
        t1.start();
        t2.start();

        Thread.sleep(500);

        //System.out.println(lista);
        //System.out.println(mapa);
        System.out.println(fila);

    }

    public static class MeuRunnable implements Runnable{
        @Override
        public void run() {
            //lista.add("bem vindoooooo");
            //mapa.put(new Random().nextInt(3),"bem vindoooooo");
            fila.add("bem vindoooooo");
            String name = Thread.currentThread().getName();
            System.out.println(name + " inseriu na lista!");
        }
    }

}
