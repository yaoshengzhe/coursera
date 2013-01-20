#! /usr/bin/python

import sys
import copy
import heapq

def add_vertex(vertex_map, a, b, cost):
    if a not in vertex_map:
        vertex_map[a] = []
    heapq.heappush(vertex_map[a], (cost, a, b))

if __name__ == '__main__':
    num_node, num_edge = sys.stdin.readline().split()
    num_node = int(num_node)

    vertex_map = {}
    vertex = None
    for line in sys.stdin.readlines():
        a, b, cost = line.split()
        cost = int(cost)
        add_vertex(vertex_map, a, b, cost)
        add_vertex(vertex_map, b, a, cost)
        vertex = a

    used_vertex = set([vertex])
    edge_heap = copy.deepcopy(vertex_map[vertex])
    total_cost = 0

    while len(used_vertex) < num_node:
        cost, a, b = heapq.heappop(edge_heap)
        vertex_to_be_merge = None
        if a not in used_vertex and b in used_vertex:
            vertex_to_be_merge = a
        elif a in used_vertex and b not in used_vertex:
            vertex_to_be_merge = b

        if vertex_to_be_merge is not None:
            used_vertex.add(vertex_to_be_merge)
            total_cost += cost
            for item in vertex_map[vertex_to_be_merge]:
                heapq.heappush(edge_heap, item)


    print total_cost
