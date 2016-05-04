#!/bin/bash

set -v

cd Application

mkdir -p Output

rm Output/*.class Output/*.jar

cp -n Libraries/* Output/

javac -g -d Output -classpath ./:Output/:Model/:Controllers/:Views/:Misc/ JogWireless.java

javac -g -d Output -classpath ./:Output/:Model/:Controllers/:Views/:Misc/ Misc/DBPopulator.java

cd Output/

jar cfm JogWireless.jar Manifest.txt ./*.class

cp JogWireless.jar ../../JogWireless.jar
cp ojdbc6.jar ../../ojdbc6.jar