public class Synchronized_1 {
    /*
    * DESVANTAGENS DO SYNCHRONIZED
    * 1- vc utilizando ele no metodo vc perdeu a capacidade do paralelismo, pois ficaram enfileirada
    *
    * */

    static int i = -1;

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

    public static void imprime(){
        synchronized (Synchronized_1.class){
            i++;
            String name = Thread.currentThread().getName();
            System.out.println(name + " : " + i);
        }
    }

    // nao tem garantia de quem vai executar primeiro qual parte do bloco - sem usar o synchronized
    // ao usar o synchronized o metodo run cria uma porteira e so uma thread por vez por instancia
    // pode executar dentro do metodo
    public static class MeuRunnable implements Runnable {
       // static Object lock1 = new Object();
       // static Object lock2 = new Object();

        @Override
        //public synchronized void run() { // synchronized no metodo
        public void run() { // synchronized no metodo
            imprime();

            // synchronized por bloco, pode usar o this ou um objeto especifico
//            synchronized (this){
//                i++;
//                String name = Thread.currentThread().getName();
//                System.out.println(name + " : " + i);
//            }
//            synchronized (lock1){
//                i++;
//                String name = Thread.currentThread().getName();
//                System.out.println(name + " : " + i);
//            }
//            synchronized (lock2){
//                i++;
//                String name = Thread.currentThread().getName();
//                System.out.println(name + " : " + i);
//            }
        }
    }

}
