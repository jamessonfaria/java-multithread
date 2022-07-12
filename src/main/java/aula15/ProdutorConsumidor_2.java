package aula15;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProdutorConsumidor_2 {

    /*
    * Produtor x Consumidor
    * => "regiao critica / sessao critica": concorrencia esta acontecendo, é a sessao critica do programa
    * => "mutex / exclusao mutua": quando um recurso estiver acessando a informacao nao sera permitido
    * que outras threads acessem
    * */

    private static final BlockingQueue<Integer> FILA = new LinkedBlockingQueue<>(5);
    private static volatile boolean produzindo = true;
    private static volatile  boolean consumindo = true;
    private static final Lock LOCK = new ReentrantLock();


    public static void main(String[] args) {

        Thread produtor = new Thread(() -> {
            try {
                while (true) {
                    simulaProcessamento();
                    if (produzindo) {
                        LOCK.lock();
                        System.out.println("Produzindo");
                        int numero = new Random().nextInt(10000);
                        FILA.add(numero);
                        if (FILA.size() == 5) {
                            System.out.println("Pausando produtor");
                            produzindo = false;
                        }

                        if (FILA.size() == 1) {
                            System.out.println("Iniciando consumidor");
                            consumindo = true;
                        }
                        LOCK.unlock();
                    }else{
                        System.out.println("??? Produtor dormindo!!!");
                    }
                }

            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        });

        Thread consumidor = new Thread(() -> {
            try{
                while (true) {
                    simulaProcessamento();
                    if (consumindo) {
                        LOCK.lock();
                        System.out.println("Consumindo");
                        Optional<Integer> numero = FILA.stream().findFirst();
                        numero.ifPresent(n -> {
                            FILA.remove(n);
                        });
                        if (FILA.size() == 0) {
                            System.out.println("Parando consumidor");
                            consumindo = false;
                        }

                        if (FILA.size() == 4) {
                            System.out.println("Iniciando produtor");
                            produzindo = true;
                        }
                        LOCK.unlock();

                    }else{
                        System.out.println("Consumidor dormindo!!!");
                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        });

        produtor.start();
        consumidor.start();
    }

    private static final void simulaProcessamento(){
        int tempo = new Random().nextInt(10);
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

}
