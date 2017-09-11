## Minimal example for wrong behavior of javassist

### Run

`./run.sh`

or

```
mvn install
javac example/Example.java
java -javaagent:target/javassist-bugreport-1.0-SNAPSHOT-agent.jar example.Example
```

### Expected output

```
before main
before Example
before exception
after exception
catch exception
> after Example
> catch Example
after main
catch main
```

The lines marked with `>` are missing.
