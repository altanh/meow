# meow
Meow is compiler that transforms a Kodkod problem (in Java) to the correspoding Colocolo/Ocelot
problem in Racket/Rosette.

## Building
To build Meow (the compiler and benchmarks), you will need the dependencies required for [Kodkod](https://github.com/emina/kodkod). Once you have the dependencies installed, in the top directory run `waf configure --prefix=. build install` (make sure that `waf` is in the PATH). This will download and build Kodkod, and build Meow. After this completes, `bin/` will contain `meow.jar`, `benchmarks.jar`, and `kodkod.jar`.

## Running
To compile all the benchmarks used for Colocolo, build Meow and then execute `./compile-benchmarks` in the top directory. The compiled benchmarks will be outputted under `bench/`. Alternatively, to compile a single benchmark (of the ones provided under benchmarks), run

  java -cp bin/kodkod.jar:bin/meow.jar:bin/benchmarks.jar -Djava.library.path=bin meow.benchmarks.<benchmark name>

For example, to compile the TPTP ALG195 benchmark, we would execute the above command with `<benchmark name> = tptp.AGL195`. Note that this requires the `bench/tptp` folder to be created previously.