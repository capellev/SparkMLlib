#!/bin/bash
echo launching tests
while read line
do
	echo testing file $line
	spark-shell -i <(echo val fileName = \"Data/Covtype/$line\" ; cat test.scala)
done
