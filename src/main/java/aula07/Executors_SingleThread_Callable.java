package aula07;

import java.util.Random;
import java.util.concurrent.*;

public class Executors_SingleThread_Callable {

    /*
    * Callable - interface Callable permite retornar um valor,
    * executamos a tarefa e conseguimos ter um retorno
    *
    * */

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executor = null;

        try {
            executor = Executors.newSingleThreadExecutor();
            Future<String> future = executor.submit(new Tarefa()); // submit permite retorna um future

            System.out.println(future.isDone());
           // System.out.println(future.get()); // o get ja espera a tarefa terminar, nesse caso nao precisa do await
            System.out.println(future.get(1, TimeUnit.SECONDS)); // o get acima com timeout
            System.out.println(future.isDone());

            //executor.shutdown(); // espera as tarefas enviadas terminaram e nao aceite mais novas
            //executor.awaitTermination(10, TimeUnit.SECONDS); // para aguardar terminar a execucao em "x" tempo
            //System.out.println(future.isDone());

        }catch (Exception e) {
            throw e;
        }finally {
            if (executor != null)
                //executor.shutdown(); // espera as tarefas finalizarem
                executor.shutdownNow(); // parada forcada
        }

    }

    public static class Tarefa implements Callable<String> {

        @Override
        public String call() throws Exception {
            Thread.sleep(1000);
            String name = Thread.currentThread().getName();
            int nextInt = new Random().nextInt(1000);
            return name + ": ola mundo! " + nextInt;
        }
    }

}
