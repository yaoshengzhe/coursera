#! /usr/bin/python

import sys
import math
import itertools

def euler_dist(a, b):
    return math.sqrt((a[0] - b[0])**2 + (a[1] - b[1])**2)

if __name__ == '__main__':

    num_cities = int(sys.stdin.readline())
    MAX_INT = 99999999

    cities = []
    for line in sys.stdin.readlines():
        x, y = map(float, line.split())
        cities.append((x, y))

    dist = [ [euler_dist(cities[i], cities[j]) for j in range(num_cities)] for i in range(num_cities)]
    table = {}
    indexes = range(1, num_cities+1)
    for i in itertools.combinations(indexes, 1):
        table[i] = table.get(i, {})
        if i[0] == 1:
            table[i][i[0]] = 0
        else:
            table[i][i[0]] = MAX_INT

    for i in range(2, num_cities+1):
        print i
        for j in itertools.combinations(indexes, i):
            table[j] = table.get(j, {})
            for end in j:
                s = set(j)
                s.discard(end)
                x = tuple(sorted(s))
                table[j][end] = min([table[x].get(k, MAX_INT)  + dist[k-1][end-1] for k in x])

#    print min([table[tuple(indexes)][j] + dist[j-1][0] for j in range(1, num_cities+1)])
    print [table[tuple(indexes)][j] + dist[j-1][0] for j in range(1, num_cities+1)]
