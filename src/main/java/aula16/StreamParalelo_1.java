package aula16;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class StreamParalelo_1 {

    /*
    * Stream Paralelo - mais uma forma de trabalhar com processamento paralelo
    *
    * */

    public static void main(String[] args) {

        Instant inicio = Instant.now();
        ConcurrentHashMap<Double, Double> mapa = new ConcurrentHashMap<>();
        IntStream.range(1, 10000000)
                .parallel()
                .mapToDouble(numero -> Math.pow(numero, 5))
                .filter(numero -> numero % 2 == 0)
                .forEach(numero -> mapa.put(numero, Math.pow(numero, 5)));

        Instant fim = Instant.now();
        System.out.println(Duration.between(inicio, fim));
    }

}
