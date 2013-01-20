import heapq

class Heap:
    def __init__(self, comparator):
        self.buf = []
        self.comparator = comparator

    def push(self, elem):
        heapq.heappush(self.buf, (self.comparator(elem), elem))

    def pop(self):
        return heapq.heappop(self.buf)[1]
