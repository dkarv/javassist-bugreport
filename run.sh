#!/bin/bash

mvn install

javac example/Example.java

java -javaagent:target/javassist-bugreport-1.0-SNAPSHOT-agent.jar example.Example
