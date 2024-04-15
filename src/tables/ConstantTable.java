package tables;

import java.util.*;

public class ConstantTable {
    private Map<String, Integer> constantCodeMap = new HashMap<>();
    private Map<String, Integer> constantLineMap = new HashMap<>();
    private int constantCodeCounter = 600;

    public int getConstantCode(String constant) {
        if (!constantCodeMap.containsKey(constant)) {
            constantCodeMap.put(constant, constantCodeCounter++);
        }
        return constantCodeMap.get(constant);
    }

    public void addToConstantTable(String constant, int code, int lineNumber) {
        if (!constantCodeMap.containsKey(constant)) {
            constantCodeMap.put(constant, code);
        }
        if (!constantLineMap.containsKey(constant)) {
            constantLineMap.put(constant, lineNumber);
        }
    }

    public void printConstantTable() {
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(constantCodeMap.entrySet());
        sortedEntries.sort(Comparator.comparing(Map.Entry::getValue));

        System.out.println("------------------------");
        System.out.println("Constant Table");
        System.out.println("------------------------\n");
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            String constant = entry.getKey();
            int code = entry.getValue();
            int lineNumber = constantLineMap.get(constant);
            System.out.printf("Constant (%s) Value (%d) Line (%d)\n", constant, code, lineNumber);
            System.out.println();
        }
    }
}
