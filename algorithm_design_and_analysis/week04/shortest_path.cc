#include <string>
#include <iostream>
#include <bitset>
#include <vector>
#include <queue>
using namespace std;

typedef vector<vector<pair<int, int> > > Graph;
typedef vector<int> IntArray;

const int MAX_INT = 99999999;

struct pair_comparator{
    bool operator()(const pair<int, int> &a, const pair<int, int> &b) const{
        return a.first > b.first;
    }
};

int dijkstra(int u, const Graph &graph, const vector<int> &d) {
    priority_queue<pair<int, int>, vector<pair<int, int> >, pair_comparator > h;
    h.push(make_pair(0, u));

    int openset_len = 1;
    bool *openset = new bool[graph.size()];
    for (int i=0; i < graph.size(); ++i) {
        openset[i] = 0;
    }
    openset[u] = 1;
    bool *closeset = new bool[graph.size()];
    for (int i=0; i < graph.size(); ++i) {
        closeset[i] = 0;
    }
    int *shortest_path = new int[graph.size()];
    for (int i=0; i < graph.size(); ++i) {
        shortest_path[i] = MAX_INT;
    }
    shortest_path[u] = 0;
    while (openset_len > 0) {
        pair<int, int> p = h.top();
        int v = p.second;
        h.pop();
        if (openset[v]) {
            --openset_len;
            openset[v] = 0;
        }
        closeset[v] = 1;
        for (int i=0; i < graph[v].size(); ++i) {
            int vi = graph[v][i].first;
            int wi = graph[v][i].second;
            if (!closeset[vi]) {
                if (!openset[vi]) {
                    ++openset_len;
                }
                openset[vi] = 1;
            }

            if (shortest_path[vi] > shortest_path[v] + wi) {
                shortest_path[vi] = shortest_path[v] + wi;
                h.push(make_pair(wi + d[u] - d[vi], vi));
            }
        }
    }

    shortest_path[u] = MAX_INT;
    delete[] closeset;
    delete[] openset;
    int shortest = MAX_INT;
    for (int i=0; i < graph.size(); ++i) {
        if (shortest > shortest_path[i]) {
            shortest = shortest_path[i];
        }
    }
    delete[] shortest_path;
    return shortest;
}

const IntArray spfa(const Graph &graph) {
    queue<int> q;
    q.push(0);
    bool *s = new bool[graph.size()];
    for (int i=0; i < graph.size(); ++i) {
        s[i] = 0;
    }
    s[0] = 1;
    IntArray d(graph.size());
    for (int i=0; i < graph.size(); ++i) {
        d[i] = MAX_INT;
    }

    d[0] = 0;
    while (q.size() > 0) {
        int u = q.front();
        q.pop();
        s[u] = 0;

        for (int i=0; i < graph[u].size(); ++i) {
            int v = graph[u][i].first;
            int w = graph[u][i].second;
            int old = d[v];
            if (d[v] > d[u] + w) {
                d[v] = d[u] + w;
            }
            if (old != d[v] && !s[v]) {
                q.push(v);
                s[v] = 1;
            }
        }
    }
    delete[] s;
    return d;
}


int main(int argc, char *argv[]) {
    int V, E;
    cin >> V >> E;
    cin.get();

    Graph graph(V+1);
    for (int i=0; i < V+1; ++i) {
        graph[i] = vector<pair<int, int> >();
    }

    for (int i=1; i < V+1; ++i) {
        graph[0].push_back(make_pair(i, 0));
    }

    for (int i=0; i < E; ++i) {
        int tail, head, weight;
        cin >> tail >> head >> weight;
        cin.get();
        graph[head].push_back(make_pair(tail, weight));
    }

    IntArray d = spfa(graph);

    int res = MAX_INT;
    cout << "V = " << V << endl;
    for (int i=1; i < V; ++i) {
        cout << i << endl;
        int val = dijkstra(i, graph, d);
        if (res > val) {
            res = val;
        }
    }


    cout << res << endl;
    return 0;
}
