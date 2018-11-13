#!/bin/bash
# create test set
ls Data/Covtype > testSet.txt
echo launch tests
./test.sh < testSet.txt | tee output.txt | grep -E "^Temps|^Test|^testing" > result.txt |  awk '/^testing file/{line=$3","}/^Temps moyen apprentissage/{line=line""$5}/^Temps moyen analyse/{line=line","$5;}/^Test/{print line","$4;}'

#spark-shell -i <(echo val fileName = \"Data/Covtype/covtype32\" ; cat test.scala)
