package aula16;

import java.util.concurrent.CompletableFuture;

public class CompletableFuture_1 {

    /*
    * CompletableFuture - é uma forma de iniciar um processamento assincrono,
    * sera processado em algum momento no futuro e retornado
    *
    * */

    public static void main(String[] args) {
        CompletableFuture<String> processe = processe();
        CompletableFuture<String> thenApply = processe.thenApply(s -> s + " Curta o video!");
        CompletableFuture<Void> thenAccept = thenApply.thenAccept(s -> System.out.println(s));

        System.out.println("Apoie o canal pelo picpay ou apoia-se");

        sleep();
        sleep();
        sleep();
    }

    private static CompletableFuture<String> processe(){
        return CompletableFuture.supplyAsync(() -> {
            sleep();
            return "Inscreva-se no canal";
        });
    }

    private static void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
