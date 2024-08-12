import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Part2 {
    public static class MaterialQuantity {
        public String material;
        public long quantity;

        public MaterialQuantity(String material, long quantity) {
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

    public static long findRequiredOreForFuel(long fuel) throws IOException {
        List<String> input = Files.readAllLines(Paths.get(new File("Day14").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt"));
        Map<String, Conversion> conversions = new HashMap<>();
        Map<String, Long> needs = new HashMap<>();
        Map<String, Long> leftovers = new HashMap<>();

        for (String line : input) {
            String[] splitString = line.split(" => ");
            String[] inputMaterialQuantityStrings= splitString[0].split(", ");
            List<MaterialQuantity> inputMaterialQuantities = new ArrayList<>();
            for (String inputMaterialQuantityString : inputMaterialQuantityStrings) {
                String[] splitInputMaterialQuantityString = inputMaterialQuantityString.split(" ");
                long inputQuantity = Long.parseLong(splitInputMaterialQuantityString[0]);
                inputMaterialQuantities.add(new MaterialQuantity(splitInputMaterialQuantityString[1], inputQuantity));
            }

            String outputMaterialQuantityString = splitString[1];
            String[] splitOutputMaterialQuantityString = outputMaterialQuantityString.split(" ");
            long outputQuantity = Long.parseLong(splitOutputMaterialQuantityString[0]);
            MaterialQuantity outputMaterialQuantity = new MaterialQuantity(splitOutputMaterialQuantityString[1], outputQuantity);

            conversions.put(outputMaterialQuantity.material, new Conversion(inputMaterialQuantities, outputMaterialQuantity));
        }

        needs.put("FUEL", fuel);

        long requiredOre = 0;

        while (!needs.isEmpty()) {
            Map.Entry<String, Long> currentNeed = needs.entrySet().iterator().next();
            String currentNeedMaterial = currentNeed.getKey();
            long currentNeedQuantity = currentNeed.getValue();
            needs.remove(currentNeed.getKey());

            long amountOfCurrentNeedInLeftovers = leftovers.getOrDefault(currentNeedMaterial, 0L);

            if (amountOfCurrentNeedInLeftovers > currentNeedQuantity) {
                amountOfCurrentNeedInLeftovers -= currentNeedQuantity;
                leftovers.put(currentNeed.getKey(), amountOfCurrentNeedInLeftovers);
                continue;
            }

            long howMuchWeNeed = currentNeedQuantity - amountOfCurrentNeedInLeftovers;
            leftovers.put(currentNeedMaterial, 0L);

            Conversion conversion = conversions.get(currentNeedMaterial);
            long conversionCount = (long) Math.ceil((double) howMuchWeNeed / (double) conversion.output.quantity);

            long howMuchConversionOutputWeMake = conversion.output.quantity * conversionCount;
            long howMuchConversionOutputIsLeftover = howMuchConversionOutputWeMake - howMuchWeNeed;

            long howMuchConversionOutputWeHaveNow = leftovers.getOrDefault(conversion.output.material, 0L) + howMuchConversionOutputIsLeftover;
            leftovers.put(conversion.output.material, howMuchConversionOutputWeHaveNow);

            for (MaterialQuantity conversionInput : conversion.inputs) {
                if (conversionInput.material.equals("ORE")) {
                    requiredOre += (long) conversionInput.quantity * conversionCount;
                    continue;
                }

                long howMuchConversionInputWeHaveInLeftovers = leftovers.getOrDefault(conversionInput.material, 0L);
                long howMuchConversionInputWeNeed = conversionInput.quantity * conversionCount;

                if (howMuchConversionInputWeHaveInLeftovers > howMuchConversionInputWeNeed) {
                    howMuchConversionInputWeHaveInLeftovers -= howMuchConversionInputWeNeed;
                    leftovers.put(conversionInput.material, howMuchConversionInputWeHaveInLeftovers);
                    continue;
                }

                howMuchConversionInputWeNeed -= howMuchConversionInputWeHaveInLeftovers;
                leftovers.put(conversionInput.material, 0L);

                long howMuchConversionInputIsInTheQueue = needs.getOrDefault(conversionInput.material, 0L);
                howMuchConversionInputIsInTheQueue += howMuchConversionInputWeNeed;
                needs.put(conversionInput.material, howMuchConversionInputIsInTheQueue);
            }
        }

        return requiredOre;

    }

    public static void main(String[] args) throws IOException {
        long low = 1000000000000L / findRequiredOreForFuel(1);
        long high = low * 10;

        long oreRequiredForFuel = findRequiredOreForFuel(high);

        while (oreRequiredForFuel < 1000000000000L) {
            oreRequiredForFuel = findRequiredOreForFuel(high);
            low = high;
            high = 10 * low;
        }

        long mid = (low + high) / 2;

        int counter = 0;

        while (low < high - 1) {
            counter++;
            mid = (low + high) / 2;
            long ore = findRequiredOreForFuel(mid);
            if (ore < 1000000000000L){
                low = mid;
            } else if (ore > 1000000000000L) {
                high = mid;
            } else {
                break;
            }
        }

        System.out.println("Produced Fuel: " + mid);
    }
}
