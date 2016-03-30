#!/bin/bash

set -x

mkdir -p Output

cp -n Libraries/* Output/

make clean

make