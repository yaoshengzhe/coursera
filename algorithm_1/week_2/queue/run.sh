#! /bin/bash

rm -rf bin

ant

cd bin

echo A B C D E F G H I | java -cp ../../../week_1/percolation/stdlib.jar:. Subset 3

cd ..