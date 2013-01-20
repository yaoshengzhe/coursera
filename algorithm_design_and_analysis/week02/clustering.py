#! /usr/bin/python

import sys
from union_find import UnionFind

if __name__ == '__main__':
    num_of_node = int(sys.stdin.readline())
    edge_coll = []
    for line in sys.stdin.readlines():
        a, b, cost = line.split()
        edge_coll.append((int(cost), int(a) - 1, int(b) - 1))

    edge_coll.sort(lambda x, y: x[0] - y[0])

    union_find = UnionFind(num_of_node)
    expected_cluster_size = 4
    for edge in edge_coll:
        cost, x, y = edge
        union_find.union(x, y)
        if union_find.num_of_cluster == expected_cluster_size:
            break

    max_clustering_map = {}
#a    print union_find.coll
    for edge in edge_coll:
        cost, x, y = edge
        if not union_find.connected(x, y):
            print cost
            break
