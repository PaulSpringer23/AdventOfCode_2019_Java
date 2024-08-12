import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Part1 {
    public static class MaterialQuantity {
        public String material;
        public int quantity;

        public MaterialQuantity(String material, int quantity) {
            this.material = material;
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return this.quantity + " " + this.material;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof MaterialQuantity) {
                MaterialQuantity other = (MaterialQuantity)obj;
                return this.material.equals(other.material) && this.quantity == other.quantity;
            }

            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(material, quantity);
        }
    }

    public static class Conversion {
        public List<MaterialQuantity> inputs;
        public MaterialQuantity output;

        public Conversion(List<MaterialQuantity> inputs, MaterialQuantity output) {
            this.inputs = inputs;
            this.output = output;
        }

        @Override
        public String toString() {
            String inputString = inputs.stream().map(mq -> mq.quantity + " " + mq.material).collect(Collectors.joining(", "));
            return inputString + " => " + this.output.quantity + " " + this.output.material;
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Paths.get(new File("Day14").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt"));
        Map<String, Conversion> conversions = new HashMap<>();
        Map<String, Integer> needs = new HashMap<>();
        Map<String, Integer> leftovers = new HashMap<>();

        for (String line : input) {
            String[] splitString = line.split(" => ");
            String[] inputMaterialQuantityStrings= splitString[0].split(", ");
            List<MaterialQuantity> inputMaterialQuantities = new ArrayList<>();
            for (String inputMaterialQuantityString : inputMaterialQuantityStrings) {
                String[] splitInputMaterialQuantityString = inputMaterialQuantityString.split(" ");
                int inputQuantity = Integer.parseInt(splitInputMaterialQuantityString[0]);
                inputMaterialQuantities.add(new MaterialQuantity(splitInputMaterialQuantityString[1], inputQuantity));
            }

            String outputMaterialQuantityString = splitString[1];
            String[] splitOutputMaterialQuantityString = outputMaterialQuantityString.split(" ");
            int outputQuantity = Integer.parseInt(splitOutputMaterialQuantityString[0]);
            MaterialQuantity outputMaterialQuantity = new MaterialQuantity(splitOutputMaterialQuantityString[1], outputQuantity);

            conversions.put(outputMaterialQuantity.material, new Conversion(inputMaterialQuantities, outputMaterialQuantity));
        }

        needs.put("FUEL", 1);

        int requiredOre = 0;

        while (!needs.isEmpty()) {
            Map.Entry<String, Integer> currentNeed = needs.entrySet().iterator().next();
            String currentNeedMaterial = currentNeed.getKey();
            int currentNeedQuantity = currentNeed.getValue();
            needs.remove(currentNeed.getKey());

            int amountOfCurrentNeedInLeftovers = leftovers.getOrDefault(currentNeedMaterial, 0);

            if (amountOfCurrentNeedInLeftovers > currentNeedQuantity) {
                amountOfCurrentNeedInLeftovers -= currentNeedQuantity;
                leftovers.put(currentNeed.getKey(), amountOfCurrentNeedInLeftovers);
                continue;
            }

            int howMuchWeNeed = currentNeedQuantity - amountOfCurrentNeedInLeftovers;
            leftovers.put(currentNeedMaterial, 0);

            Conversion conversion = conversions.get(currentNeedMaterial);
            int conversionCount = (int) Math.ceil((double) howMuchWeNeed / (double) conversion.output.quantity);

            int howMuchConversionOutputWeMake = conversion.output.quantity * conversionCount;
            int howMuchConversionOutputIsLeftover = howMuchConversionOutputWeMake - howMuchWeNeed;

            int howMuchConversionOutputWeHaveNow = leftovers.getOrDefault(conversion.output.material, 0) + howMuchConversionOutputIsLeftover;
            leftovers.put(conversion.output.material, howMuchConversionOutputWeHaveNow);

            for (MaterialQuantity conversionInput : conversion.inputs) {
                if (conversionInput.material.equals("ORE")) {
                    requiredOre += conversionInput.quantity * conversionCount;
                    continue;
                }

                int howMuchConversionInputWeHaveInLeftovers = leftovers.getOrDefault(conversionInput.material, 0);
                int howMuchConversionInputWeNeed = conversionInput.quantity * conversionCount;

                if (howMuchConversionInputWeHaveInLeftovers > howMuchConversionInputWeNeed) {
                    howMuchConversionInputWeHaveInLeftovers -= howMuchConversionInputWeNeed;
                    leftovers.put(conversionInput.material, howMuchConversionInputWeHaveInLeftovers);
                    continue;
                }

                howMuchConversionInputWeNeed -= howMuchConversionInputWeHaveInLeftovers;
                leftovers.put(conversionInput.material, 0);

                int howMuchConversionInputIsInTheQueue = needs.getOrDefault(conversionInput.material, 0);
                howMuchConversionInputIsInTheQueue += howMuchConversionInputWeNeed;
                needs.put(conversionInput.material, howMuchConversionInputIsInTheQueue);
            }
        }

        System.out.println("Required Ore: " + requiredOre);


    }
}
