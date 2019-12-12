import com.sun.org.apache.xml.internal.serialize.LineSeparator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Part2 {

    public static void main(String[] args) throws IOException {
        String input = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt")).get(0);
        int width = 25;
        int height = 6;
        int numberOfLayers = input.length() / (width * height);

        ArrayList<String> layers  = new ArrayList<>();

        for (int layerNumber = 0; layerNumber < numberOfLayers; layerNumber++) {
            int startingColumn = layerNumber * (width * height);
            int endingColumn = startingColumn + (width * height);
                layers.add(input.substring(startingColumn, endingColumn));
        }

        StringBuilder image = new StringBuilder();

        for (int pixelNumber = 0; pixelNumber < height * width; pixelNumber++) {
            String actualPixel = " ";
            for (String layer : layers) {
                String layerPixel = layer.split("")[pixelNumber];
                if (layerPixel.equals("0")) {
                    break;
                }else if (layerPixel.equals("1")) {
                    actualPixel = "*";
                    break;
                }
            }
            image.append(actualPixel);
            if ((pixelNumber + 1) % width == 0) {
                image.append("\n");
            }
        }

        System.out.println(image);
        System.out.println("Done!");
    }


}
