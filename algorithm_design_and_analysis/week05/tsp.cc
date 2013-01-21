#include <iostream>
#include <cmath>
#include <vector>
#include <map>
using namespace std;

double euler_dist(double x1, double y1, double x2, double y2) {
    double x = x1-x2;
    double y = y1-y2;
    return sqrt(x*x+y*y);
}

double euler_dist(pair<double, double> a, pair<double, double> b) {
    return euler_dist(a.first, a.second, b.first, b.second);
}

int count_bits(long n) {
    int c = 0; // c accumulates the total bits set in v
    for (; n; c++)
        n &= (n - 1); // clear the least significant bit set
    return c;
}
void combinations_helper(map<int, vector<long> > &sets, int set_size, long data, int start) {
    if (set_size == start) {
        int len = count_bits(data);
        sets[len].push_back(data);
    } else {
        combinations_helper(sets, set_size, data, start + 1);
        data |= (1 << start);
        combinations_helper(sets, set_size, data, start + 1);
    }
}

void gen_combinations(map<int, vector<long> > &sets, int set_size) {
    combinations_helper(sets, set_size, 0l, 0);
}

int main(int argc, char *argv[]) {
    int num_cities;
    cin >> num_cities;
    cin.get();
    const long MAX_INT = 9999999999999;

    vector<pair<double, double> > cities;
    for (int i=0; i < num_cities; ++i) {
        double x, y;
        cin >> x >> y;
        cin.get();
        cities.push_back(make_pair(x, y));
    }

    vector<vector<double> > dist(num_cities+1);
    for (int i=1; i < dist.size(); ++i) {
        dist[i].resize(num_cities+1);
        for (int j=1; j < dist[i].size(); ++j) {
            dist[i][j] = euler_dist(cities[i-1], cities[j-1]);
        }
    }

    map<int, vector<long> > sets;
    gen_combinations(sets, num_cities);

    map<int, map<long, vector<double> > > table;
    for (int i=0; i < num_cities; ++i) {
        long s = 1 << i;
        for (int j=0; j < num_cities+1; ++j) {
            table[1][s].push_back(MAX_INT);
        }
        if (s == 1) {
            table[1][s][s] = 0;
        }
    }

    // start from set with number of "i" 1s
    for (int i=2; i < num_cities+1; ++i) {
        cout << i << endl;
        // get all sets with number of "i" 1s
        for (int j=0; j < sets[i].size(); ++j) {
            long current_set = sets[i][j];
            // init table[current_set]
            for (int p=0; p < num_cities+1; ++p) {
                table[i][current_set].push_back(MAX_INT);
            }

            for (int end=1; end < num_cities+1; ++end) {
                if (current_set & (1 << (end -1))) {
                    long subset = current_set & ~(1 << (end -1));
                    double result = MAX_INT;
                    for (int k=1; k < num_cities + 1; ++k) {
                        if (subset & (1 << (k -1))) {
                            if (result > (table[i-1][subset][k] + dist[end][k])) {
                                result = table[i-1][subset][k] + dist[end][k];
                            }
                        }
                    }
                    table[i][current_set][end] = result;
                }
            }
        }
        table.erase(i-1);
    }

    long key = (1 << num_cities) - 1;
    double result = MAX_INT;
    for (int i=1; i < num_cities+1; ++i) {
        double val = table[num_cities][key][i] + dist[i][1];

        if (result > val) {
            result = val;
        }
    }
    cout << result << endl;
    return 0;
}
