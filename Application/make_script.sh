#!/bin/bash

set -x

mkdir -p Output

cp Libraries/* Output/

make clean

make