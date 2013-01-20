#! /usr/bin/python

import sys

if __name__ == '__main__':
    knapsack_size, _ = sys.stdin.readline().split()
    knapsack_size = int(knapsack_size)

    values = []
    weights = []

    for line in sys.stdin.readlines():
        v, w = line.split()
        values.append(int(v))
        weights.append(int(w))

    # 
