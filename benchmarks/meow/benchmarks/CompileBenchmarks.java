package meow.benchmarks;

import meow.benchmarks.alloy.*;
import meow.benchmarks.csp.*;
import meow.benchmarks.tptp.*;

import java.nio.file.Files;
import java.nio.file.Paths;

public class CompileBenchmarks {
    private static final Class[] emptyBenchmarks = {
            ALG195.class,
            ALG195_1.class,
            ALG197.class,
            NUM378.class,
    };

    private static final Class[] fiveBenchmarks = {
            LatinSquare.class,
            ALG212.class,
            COM008.class,
            TOP020.class
    };

    public static void main(String[] args) {
        try {
            Files.createDirectories(Paths.get("bench/alloy"));
            Files.createDirectories(Paths.get("bench/csp"));
            Files.createDirectories(Paths.get("bench/tptp"));

            DiffEg.main(new String[]{"40"});
            LatinSquare.main(new String[]{"7"});
            for (Class bench : emptyBenchmarks) {
                bench.getMethod("main", String[].class).invoke(null, (Object) new String[0]);
            }
            for (Class bench : fiveBenchmarks) {
                bench.getMethod("main", String[].class).invoke(null, (Object) new String[] {"5"});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
