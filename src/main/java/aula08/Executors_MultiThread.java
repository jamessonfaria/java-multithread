package aula08;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Executors_MultiThread {

    /*
    * Executors.newFixedThreadPool - cria um pool de threads informando a qtd e cada
    * submit executado ele vai realizar em uma thread especifica
    *
    * Executors.newCachedThreadPool - cria um pool de threads sem informar a qtd e cada
     * submit executado ele vai realizar em uma thread especifica buscar reutilizar
     * threads ja finalizadas. CUIDADO ao usar ele em algo com muitas tarefas ele vai
     * criar uma thread para cada uma sem limites
    * */

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = null;
        try{
            // newFixedThreadPool - Cria um executor com 4 threads
            //executor = Executors.newFixedThreadPool(4);
            // newCachedThreadPool - Cria um executor sem especificar o num de threads,
            // ele busca reutilizar threads que ja terminaram

            executor = Executors.newCachedThreadPool();

            ArrayList<Tarefa> lista = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                lista.add(new Tarefa());
            }

            Tarefa t1 = new Tarefa();
            Tarefa t2 = new Tarefa();
            Tarefa t3 = new Tarefa();
            Tarefa t4 = new Tarefa();

            // invokeAll - invoca todas as tasks de uma vez e retorna todas
//            List<Future<String>> futures = executor.invokeAll(lista);
//            for (Future<String> future : futures) {
//                System.out.println(future.get());
//            }

            // invokeAny - invoca todas as tasks de uma vez e retorna apenas uma
            String string = executor.invokeAny(lista);
            System.out.println(string);

//            Future<String> f1 = executor.submit(new Tarefa());
//            System.out.println(f1.get());
//            Future<String> f2 = executor.submit(new Tarefa());
//            Future<String> f3 = executor.submit(new Tarefa());
//            System.out.println(f2.get());
//            System.out.println(f3.get());
//            executor.shutdown();

        }catch (Exception e){
            throw e;
        }finally {
            if (executor != null)
                executor.shutdownNow();
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
