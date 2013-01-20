#! /usr/bin/python

import sys
from union_find import UnionFind

def hamming(x, y):
    dist = 0
    val = x ^ y
    while val != 0:
        dist += 1
        val &= val - 1
    return dist

if __name__ == '__main__':
    num_of_node, num_of_bit = map(int, sys.stdin.readline().split())
    desired_distance = 3
    node_coll = []
    count = 0
    for line in sys.stdin.readlines():
        node = int(''.join(line.split()), 2)
        node_coll.append( (count, node))
        count += 1

    union_find = UnionFind(num_of_node)

    for x in node_coll:
        print x[0]
        for y in node_coll:
            if hamming(x[1], y[1]) < desired_distance:
                union_find.union(x[0], y[0])
    print union_find.num_of_cluster
