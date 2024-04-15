package tables;

import java.util.*;

public class IdentifierTable {
    private Map<String, Integer> identifierCodeMap = new HashMap<>();
    private Map<String, Integer> identifierLineMap = new HashMap<>();
    private int identifierCodeCounter = 401;

    public int getIdentifierCode(String identifier) {
        if (!identifierCodeMap.containsKey(identifier)) {
            identifierCodeMap.put(identifier, identifierCodeCounter++);
        }
        return identifierCodeMap.get(identifier);
    }

    public void addToIdentifierTable(String identifier, int code, int lineNumber) {
        if (!identifierCodeMap.containsKey(identifier)) {
            identifierCodeMap.put(identifier, code);
        }
        if (!identifierLineMap.containsKey(identifier)) {
            identifierLineMap.put(identifier, lineNumber);
        }
    }

    public void printIdentifierTable() {
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(identifierCodeMap.entrySet());
        sortedEntries.sort(Comparator.comparing(Map.Entry::getValue));

        System.out.println("------------------------");
        System.out.println("Tabla de identificadores");
        System.out.println("------------------------\n");
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            String identifier = entry.getKey();
            int code = entry.getValue();
            int lineNumber = identifierLineMap.get(identifier);
            System.out.printf("Identificador (%s) Valor (%d) Linea (%d)\n", identifier, code, lineNumber);
            System.out.println();
        }
    }
}

