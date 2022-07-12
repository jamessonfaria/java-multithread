package aula15;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ProdutorConsumidor_1 {

    /*
    * Produtor x Consumidor - tem um problema classico "condicao de corrida / condicao de concorrencia"
    * ao trabalhar com multithread, ai ocorre o deadlock uma thread espera pela outra
    * aonde uma thread pode pausar e a outra entrar no meio do caminho e sobrescrever
    * o valor da variavel
    * */

    private static final List<Integer> LISTA = new ArrayList<>(5);
    private static boolean produzindo = true;
    private static boolean consumindo = true;


    public static void main(String[] args) {

        Thread produtor = new Thread(() -> {
            try {
                while (true) {
                    simulaProcessamento();
                    if (produzindo) {
                        System.out.println("Produzindo");
                        int numero = new Random().nextInt(10000);
                        LISTA.add(numero);
                        if (LISTA.size() == 5) {
                            System.out.println("Pausando produtor");
                            produzindo = false;
                        }

                        if (LISTA.size() == 1) {
                            System.out.println("Iniciando consumidor");
                            consumindo = true;
                        }
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
                        System.out.println("Consumindo");
                        Optional<Integer> numero = LISTA.stream().findFirst();
                        numero.ifPresent(n -> {
                            LISTA.remove(n);
                        });
                        if (LISTA.size() == 0) {
                            System.out.println("Parando consumidor");
                            consumindo = false;
                        }

                        if (LISTA.size() == 4) {
                            System.out.println("Iniciando produtor");
                            produzindo = true;
                        }
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
