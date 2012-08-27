import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Subset {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        List<String> in = new ArrayList<String>();

        while (!StdIn.isEmpty()) {
            in.add(StdIn.readString());
        }

        String[] out = new String[k];

        for (int i = 0; i < k; ++i) {
            out[i] = in.get(i);
        }

        Random rand = new Random();
        for (int i = k; i < in.size(); ++i) {
            int index = rand.nextInt(i+1);
            if (index < k) {
                out[index] = in.get(i);
            }
        }

        for (int i = 0; i < k; ++i) {
            System.out.println(out[i]);
        }
    }
}
