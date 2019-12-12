import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    public static void main(String[] args) throws IOException {
        String input = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt")).get(0);
        int width = 25;
        int height = 6;

        ArrayList<String> layers = new ArrayList<>();


        for (int i = 0; i < input.length(); i += width * height) {
            layers.add(input.substring(i, i + width * height));
        }

        int maxZeroCount = Integer.MAX_VALUE;
        int finalValue = 0;
        for (String layer : layers) {
            int zeroCount = layer.length() - layer.replace("0", "").length();
            if (zeroCount < maxZeroCount) {
                maxZeroCount = zeroCount;
                int oneCount = layer.length() - layer.replace("1", "").length();
                int twoCount = layer.length() - layer.replace("2", "").length();
                finalValue = oneCount * twoCount;
            }
        }

        System.out.println(finalValue);
        System.out.println("Done!");
    }


}
