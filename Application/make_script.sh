#!/bin/bash

set -v

mkdir -p Output

rm Output/*.class

cp -n Libraries/* Output/

javac -g -d Output -classpath ./:Output/:Model/:Misc/ JogWireless.java

javac -g -d Output -classpath ./:Output/:Model/:Misc/ Misc/DBPopulator.java