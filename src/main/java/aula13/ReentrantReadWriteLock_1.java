package aula13;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLock_1 {

    /*
    * ReentrantReadWriteLock - serve para realizar o bloqueio do recurso compartilhado, evitando
    * que esse mesmo recurso seja alterado ao mesmo tempo por varias threads
    * VANTAGEM: podemos usar o "readLock" ele nao vai bloquear outras threds de leitura vai bloquear
    * apenas de escrita, o "writeLock"
    * vai bloquear tanto outras threads de leitura como de escrita
    * "writeLock" é exclusivo
    * "readLock" - so garante que enquanto estou lendo ninguem mais escreve
    *
    * */

    private static int i = -1;

    // Lock - foi criado uma instancia do lock para usar no trecho de codigo que deseja travar
    private static ReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        Runnable r1 = () -> {
            // faz o lock no trecho de codigopara o dar nao para evitar
            // o uso por outras threads
            Lock writeLock = lock.writeLock();
            writeLock.lock();
            String name = Thread.currentThread().getName();
            System.out.println(name + " - Escrevendo: " + i);
            i++;
            System.out.println(name + " - Escrito: " + i);
            // desfaz o lock agoras outras threads podem executar esse trecho de codigo
            writeLock.unlock();
        };

        Runnable r2 = () -> {
            Lock readLock = lock.readLock();
            readLock.lock();
            String name = Thread.currentThread().getName();
            System.out.println("Lendo: " + i);
            System.out.println("Lido: " + i);
            readLock.unlock();
        };

        for (int j = 0; j < 6; j++) {
            executor.execute(r1);
            executor.execute(r2);
        }

        executor.shutdown();

    }

}
