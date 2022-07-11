package aula06;

public class Volatile {

    /*
    * quando fazendo read/write de uma variavel, existe um cache local no processador,
    * em algum tempo ele sincroniza com a memoria ram, dessa forma as vezes o valor
    * da var no exemplo abaixo pode ser "0" e nao "42"
    * VOLATILE - ai entra o uso do volatile na varialvel, ele garante que o cache local
    * nao sera usado e sim a memoria ram
    *
    * DESVANTAGENS: usar o volatile perde performance devido a ida sempre na memoria
    * ram que é um recurso que fica mais distante do processador, so usar o volatile
    * em caso de paralelismo
    *
    * */

    private static volatile int numero = 0;
    private static volatile boolean preparado = false;

    private static class MeuRunnable implements Runnable {
        @Override
        public void run() {
            while (!preparado){
                Thread.yield(); // forma da thread avisar ao processador que
                                // nao tem nada para executar nesse momento, é uma pausa
            }

            if (numero != 42)
                System.out.println(numero);
            //throw new IllegalStateException("deu ruim, o numero nao e 42");

        }
    }

    public static void main(String[] args) {

        while (true){
            Thread t0 = new Thread(new MeuRunnable());
            t0.start();
            Thread t1 = new Thread(new MeuRunnable());
            t1.start();
            Thread t2 = new Thread(new MeuRunnable());
            t2.start();

            numero = 42;
            preparado = true;

            while (
                    t0.getState() != Thread.State.TERMINATED
                    || t1.getState() != Thread.State.TERMINATED
                    || t2.getState() != Thread.State.TERMINATED
            ){
                // espera
            }

            numero = 0;
            preparado = false;

        }
    }
}
