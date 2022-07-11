package aula05;

import aula02.Synchronized_1;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ClassesAtomicas {

    static AtomicInteger i = new AtomicInteger(-1);

    // Classes Atomicas realizam a operacao atomicas ou seja
    // de uma unica vez e nao sao quebradas mesmo
    // sendo chamadas por varias threads
    // ex: AtomicInteger, AtomicLong, AtomicBoolean, AtomicReference ...

    static AtomicReference<String> texto = new AtomicReference<>("inicio");

    public static void main(String[] args) {
        MeuRunnable meuRunnable = new MeuRunnable();

        Thread t0 = new Thread(meuRunnable);
        Thread t1 = new Thread(meuRunnable);
        Thread t2 = new Thread(meuRunnable);

        t0.start();
        t1.start();
        t2.start();
    }

    public static class MeuRunnable implements Runnable {

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            //System.out.println(name + " : " + i.incrementAndGet());
            System.out.println(name + " : " + texto.getAndSet("olaaaaaaaaaa"));
        }
    }


}
