package aula09;

import java.util.Random;
import java.util.concurrent.*;

public class Executors_Scheduled {

    /*
     * Executors.newScheduledThreadPool - cria um pool de threads informando a qtd de threas e
     * o tempo de agendamento, ele vai esperar o tempo de agendamento e vai executar
     *
     * */

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // cria um pool schedule com 3 threads
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
        //ScheduledFuture<String> future = executor.schedule(new Tarefa(), 2, TimeUnit.SECONDS);
        //executor.schedule(new TarefaRunnable(), 2, TimeUnit.SECONDS);
        // scheduleAtFixedRate - vai executar a cada um segundo no exemplo abaixo, se a tarefa demorar
        // mais do que 1s quando terminar ele executa novamente
        //executor.scheduleAtFixedRate(new TarefaRunnable(), 0, 1, TimeUnit.SECONDS);
        // scheduleWithFixedDelay - vai executar a cada um segundo no exemplo abaixo, se a tarefa demorar
        // mais do que 1s ele vai garantir o intervalor de 1s, ex: se a tarefa levar 2s quando ela
        // terminar ele vai esperar o delay para executar novamente
        executor.scheduleWithFixedDelay(new TarefaRunnable(), 0, 1, TimeUnit.SECONDS);
        //System.out.println(future.get());
        //executor.shutdown();

    }

    public static class Tarefa implements Callable<String> {
        @Override
        public String call() throws Exception {
            Thread.sleep(2000);
            String name = Thread.currentThread().getName();
            int nextInt = new Random().nextInt(1000);
            return name + ": ola mundo! " + nextInt;
        }
    }

    public static class TarefaRunnable implements Runnable {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            int nextInt = new Random().nextInt(1000);
            System.out.println(name + ": ola mundo! " + nextInt);
        }
    }



}
