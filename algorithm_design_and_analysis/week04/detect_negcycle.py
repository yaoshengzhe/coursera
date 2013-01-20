#! /usr/bin/python

import sys

if __name__ == '__main__':
    V, E = map(int, sys.stdin.readline().split())
    graph = [[] for i in range(V+1)]
    d = [None for i in range(V+1)]
    d[0] = 0
    graph[0] = [ (i, 0) for i in range(1, V+1)]

    for line in sys.stdin.readlines():
        tail, head, weight = map(int, line.split())
        graph[head].append((tail, weight))

    queue = [0]
    s = set([0])

    count = {0:1}

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
                count[v] = count.get(v, 0) + 1
                if count[v] >= V:
                    print 'detected negtive cycle'
                    exit(-1)
