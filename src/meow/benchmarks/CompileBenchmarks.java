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

    private static final Class[] fiveSevenBenchmarks = {
            LatinSquare.class,
            ALG212.class,
            COM008.class,
            GEO091.class,
            GEO092.class,
            GEO115.class,
            GEO158.class,
            GEO159.class,
            LAT258.class,
            MED007.class,
            MED009.class,
            MGT066.class,
            NUM374.class,
            SET943.class,
            SET948.class,
            SET967.class,
            TOP020.class
    };

    public static void main(String[] args) {
        try {
            Files.createDirectories(Paths.get("bench/alloy"));
            Files.createDirectories(Paths.get("bench/csp"));
            Files.createDirectories(Paths.get("bench/tptp"));

            DiffEg.main(new String[]{"40"});
            for (Class bench : emptyBenchmarks) {
                bench.getMethod("main", String[].class).invoke(null, (Object) new String[0]);
            }
            for (Class bench : fiveSevenBenchmarks) {
                bench.getMethod("main", String[].class).invoke(null, (Object) new String[] {"5"});
                bench.getMethod("main", String[].class).invoke(null, (Object) new String[] {"7"});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
