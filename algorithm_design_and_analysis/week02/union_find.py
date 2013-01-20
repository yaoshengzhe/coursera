class UnionFind:

    def __init__(self, n):
        self.coll = [i for i in range(n)]
        self.size = [1 for i in range(n)]
        self.num_of_cluster = n

    def union(self, x, y):
        if not self.connected(x, y):
            rx, ry = self.find(x), self.find(y)
            if self.size[rx] > self.size[ry]:
                self.coll[ry] = rx
                self.size[rx] += self.size[ry]
            else:
                self.coll[rx] = ry
                self.size[ry] += self.size[rx]

            self.num_of_cluster -= 1

    def find(self, x):
        while self.coll[x] != x:
            self.coll[x] = self.coll[self.coll[x]]
            x = self.coll[x]
        return x

    def connected(self, x, y):
        return self.find(x) == self.find(y)
