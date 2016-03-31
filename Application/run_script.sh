#!/bin/bash

if [ $# -ge 1 ] && [ $1 == "populate" ]; then
	set -x
	java -cp Output/:Output/ojdbc6.jar DBPopulator
elif [ $# -ge 1 ]; then
	echo "parameter" "\""$1"\"" "not recognized"
else
    set -x
    java -cp Output/:Output/ojdbc6.jar JogWireless
fi