package aula02;

public class Synchronized_2 {
    /*
    * USANDO NA VIDA REAL
    *
    * */

    static int i = 0;

    public static void main(String[] args) {
        MeuRunnable meuRunnable = new MeuRunnable();

        Thread t0 = new Thread(meuRunnable);
        Thread t1 = new Thread(meuRunnable);
        Thread t2 = new Thread(meuRunnable);
        Thread t3 = new Thread(meuRunnable);
        Thread t4 = new Thread(meuRunnable);

        t0.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    public static class MeuRunnable implements Runnable {

        @Override
        public void run() {
            int j;
            synchronized (this){
                i++;
                j = i * 2;
            }

            double jElevadoA100 = Math.pow(j, 100);
            double sqrt = Math.sqrt(jElevadoA100);
            System.out.println(Thread.currentThread().getName() + " : " + sqrt);

        }
    }

}
