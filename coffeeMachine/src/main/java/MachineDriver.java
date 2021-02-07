import requestDtos.Beverage;
import operations.BeverageMaker;
import com.fasterxml.jackson.databind.ObjectMapper;
import contentManager.ContentManager;
import operations.AlertIndicator;
import contentManager.MachineRepository;
import requestDtos.Machine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class MachineDriver {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {

        /**
         * This is to read the given input
         */
        Machine machine  = objectMapper.readValue(getFileAsString("src/main/resources/input.json"), Machine.class);
        Map<String, Map<String, Integer>> beveragesinput  = machine.getBeverages();
        Map<String, Integer> itemsQuantity = machine.getTotalItemsQuantity();
        Integer outlets = machine.getOutlets();

        /**
         * This to add the initial ingredients quantity to the machine
         */
        for(Map.Entry<String, Integer> entry : itemsQuantity.entrySet()) {
            ContentManager.addIngredientToMachine(entry.getKey(), entry.getValue());
        }

        for(Map.Entry<String, Map<String, Integer>> entry : beveragesinput.entrySet()) {
            MachineRepository.addBeverage(entry.getKey(), new Beverage(entry.getKey(), entry.getValue()));
        }

        /**
         * This is to initiate a thread which will keep looking for the quantity of the ingredients and will print an
         * alert if low quantity and refill the quantity
         */
        AlertIndicator alertIndicator = new AlertIndicator(ContentManager.getIngredients());
        Thread alertIndicatorThread = new Thread(alertIndicator);
        alertIndicatorThread.setName("Alert_Thread");
        alertIndicatorThread.start();


        /**
         * This to provide the functionality for N outlets for a machine and N drinks can be taken up in parallel
         * by the machine
         */
        ForkJoinPool forkJoinPool = new ForkJoinPool(outlets);

        forkJoinPool.execute(new BeverageMaker("hot_coffee"));
        forkJoinPool.execute(new BeverageMaker("milk_tea"));
        forkJoinPool.execute(new BeverageMaker("cold_tea"));
        forkJoinPool.execute(new BeverageMaker("black_tea"));
        forkJoinPool.execute(new BeverageMaker("green_tea"));
        forkJoinPool.execute(new BeverageMaker("milk_tea"));
        //Thread.sleep(1000);
        forkJoinPool.execute(new BeverageMaker("green_tea"));
        Thread.sleep(5000);
        Map<String, Integer> hotTeaMap = new HashMap<>();
        hotTeaMap.put("hot_milk", 100);
        hotTeaMap.put("tea_leaves_syrup", 20);
        hotTeaMap.put("sugar_syrup", 10);
        Beverage hotTea = new Beverage("milk_tea", hotTeaMap);
        MachineRepository.addBeverage("milk_tea", hotTea);
        forkJoinPool.execute(new BeverageMaker("milk_tea"));
        forkJoinPool.execute(new BeverageMaker("black_tea"));
    }

    /**
     * Function to convert the given input file into string
     * @param path
     * @return
     */
    static String getFileAsString(String path) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            List<String> content = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine())!= null) {
                content.add(line);
            }
            return content.stream().collect(Collectors.joining("\n"));
        }catch (Exception e) {
            return "";
        }
    }

}
