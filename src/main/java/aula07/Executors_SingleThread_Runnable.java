package aula07;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Executors_SingleThread_Runnable {

    /*
    * Executors - permitem criar threads
    * ex: Executors.newSingleThreadExecutor - cria uma unica thread
    * depois de executada a thread deve ser parada, caso contrario
    * ela fica executando
    *
    * */

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = null;

        try {
            executor = Executors.newSingleThreadExecutor();

            executor.execute(new Tarefa());
            executor.execute(new Tarefa()); // o executor pode rodar varias vezes
            executor.execute(new Tarefa());
            Future<?> future = executor.submit(new Tarefa()); // submit permite retorna um future

            System.out.println(future.isDone());
            executor.shutdown(); // espera as tarefas enviadas terminaram e nao aceite mais novas
            executor.awaitTermination(10, TimeUnit.SECONDS); // para aguardar terminar a execucao em "x" tempo
            System.out.println(future.isDone());

        }catch (Exception e) {
            throw e;
        }finally {
            if (executor != null)
                //executor.shutdown(); // espera as tarefas finalizarem
                executor.shutdownNow(); // parada forcada
        }

    }

    public static class Tarefa implements Runnable {

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println(name + ": ola mundo");
        }
    }

}
