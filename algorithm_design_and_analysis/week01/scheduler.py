#! /usr/bin/python

import sys

def diff_comp(x, y):
    diff = (x[0] - x[1]) - (y[0] - y[1])
    if diff == 0:
        return -(x[0] - y[0])
    else:
        return -diff

def ratio_comp(x, y):
    diff = x[0] / float(x[1]) - y[0] / float(y[1])
    if diff > 0:
        return -1
    elif diff < 0:
        return 1
    else:
        return 0

def completion_time(coll):
    return 0

if __name__ == '__main__':
    sys.stdin.readline()
    coll = []
    for line in sys.stdin.readlines():
        weight, length = line.split()
        coll.append((int(weight), int(length)))

    coll.sort(ratio_comp)
    acc_length = 0
    completion_time = 0
    for i in coll:
        weight, length = i
        acc_length += length
        completion_time += weight * acc_length

    print completion_time
