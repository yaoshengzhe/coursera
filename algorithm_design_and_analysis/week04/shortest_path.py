#! /usr/bin/python

import sys
from heap import Heap

class bitset:
    def __init__(self, capacity):
        self.buf = [0 for i in range(capacity)]
        self.len = 0
    def add(self, elem):
        if self.buf[elem] == 0: self.len += 1
        self.buf[elem] = 1

    def remove(self, elem):
        if self.buf[elem] == 1: self.len -= 1
        self.buf[elem] = 0

    def __len__(self):
        return self.len

    def __contains__(self, elem):
        return elem >= 0 and elem < len(self.buf) and self.buf[elem] == 1


# d is node reweight
def dijkstra(u, graph, d):
    h = Heap(lambda x: x[0] + d[u] - d[x[1]])
    h.push((0, u))

    openset = bitset(len(graph))
    openset.add(u)
    closeset = bitset(len(graph))

    shortest_path = [None for i in range(len(graph))]
    shortest_path[u] = 0

    while len(openset) > 0:
        w, v = h.pop()
        openset.remove(v)
        closeset.add(v)
        for vi, wi in graph[v]:
            if vi not in closeset:
                openset.add(vi)
            if shortest_path[vi] is None or shortest_path[vi] > shortest_path[v] + wi:
                shortest_path[vi] = shortest_path[v] + wi
                h.push((wi, vi))

    shortest_path[u] = None
    return min(filter(lambda x: x is not None, shortest_path))

def spfa(graph):
    queue = [0]
    s = set([0])
    d = [None for i in range(len(graph))]
    d[0] = 0
    while len(queue) > 0:
        u = queue.pop(0)
        s.discard(u)
        for v, w in graph[u]:
            old = d[v]
            if d[v] is None or d[v] > d[u] + w:
                d[v] = d[u] + w
            if old != d[v] and v not in s:
                queue.append(v)
                s.add(v)
    return d

if __name__ == '__main__':
    V, E = map(int, sys.stdin.readline().split())
    graph = [[] for i in range(V+1)]
    graph[0] = [ (i, 0) for i in range(1, V+1)]

    for line in sys.stdin.readlines():
        tail, head, weight = map(int, line.split())
        graph[head].append((tail, weight))

    d = spfa(graph)
    print min([dijkstra(u, graph, d) for u in range(1, V)])
