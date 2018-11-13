#!/bin/bash
# create test set
ls Data/Covtype > testSet.txt
echo launch tests
./test.sh < testSet.txt | tee output.txt
#spark-shell -i <(echo val fileName = \"Data/Covtype/covtype32\" ; cat test.scala)
