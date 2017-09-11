## Minimal example for wrong behavior of javassist

### Run

`./run.sh`

or

```
mvn install
javac example/Example.java
java -javaagent:target/javassist-bugreport-1.0-SNAPSHOT-agent.jar example.Example
```

### Output

```
before main
before Example
before exception
after exception
catch exception
after main
catch main
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

### Environment

```
$ java -version
openjdk version "1.8.0_131"
OpenJDK Runtime Environment (build 1.8.0_131-8u131-b11-2ubuntu1.16.04.3-b11)
OpenJDK 64-Bit Server VM (build 25.131-b11, mixed mode)
```

Javassist version: `3.22.0-CR2`.
(I tried different ones, same result).