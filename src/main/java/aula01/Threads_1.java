package aula01;

public class Threads_1 {

    public static void main(String[] args) {

        // saber qual a thread esta executando, a thread principal do java se chama main
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName());

        // criar uma nova thread
        Thread t1 = new Thread(new MeuRunnable());
        // t1.run(); - roda na mesma thread que seria a main
        t1.start(); // o start vai iniciar uma thread nova, a jvm vai decidir quando iniciar

        // Runnable como lambda
        Thread t2 = new Thread(
                () -> System.out.println(Thread.currentThread().getName()));
        t2.start();

        // nao se pode iniciar a mesma thread mais de uma vez
        //t2.start();

        // Varias Threads
        Thread t3 = new Thread(new MeuRunnable());
        t3.start();



    }

}
