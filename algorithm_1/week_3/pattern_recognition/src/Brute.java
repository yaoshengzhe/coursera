import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Brute {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new FileReader(args[0]));
        int n = Integer.parseInt(in.readLine());

        List<Point> pointColl = new ArrayList<Point>();
        for (int i = 0; i < n; ++i) {
            String[] point = in.readLine().split(" ");
            pointColl.add(new Point(Integer.parseInt(point[0]), Integer.parseInt(point[1])));
        }
    }
}
