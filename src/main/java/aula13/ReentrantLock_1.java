package aula13;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock_1 {

    /*
    * ReentrantLock - serve para realizar o bloqueio do recurso compartilhado, evitando
    * que esse mesmo recurso seja alterado ao mesmo tempo por varias threads
    * VANTAGEM: com relacao a usar o synchronized, no synchronized so permite usar
    * em um bloc, com o Lock pode ir usando em trechos do codigo, reentrant porque
    * pode ser chamado varias vezes
    *
    * */

    private static int i = -1;

    // Lock - foi criado uma instancia do lock para usar no trecho de codigo que deseja travar
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        Runnable r1 = () -> {
            // faz o lock no trecho de codigopara o dar nao para evitar
            // o uso por outras threads
            lock.lock();
            String name = Thread.currentThread().getName();
            i++;
            System.out.println(name + " lendo e incrementando " + i);
            // desfaz o lock agoras outras threads podem executar esse trecho de codigo
            lock.unlock();
        };

        for (int j = 0; j < 6; j++) {
            executor.execute(r1);
        }

        executor.shutdown();

    }

}
