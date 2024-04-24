package paser;

import tables.IdentifierTable;
import token.Token;

import java.util.*;

public class SyntaxTable {
    private final Map<Integer, Map<Integer, Integer>> table;
    private final IdentifierTable identifierTable;


    public SyntaxTable() {
        this.table = new HashMap<>();
        this.identifierTable = new IdentifierTable();
        initializeTable();
    }

    private void initializeTable() {

        // Reglas para SELECT
        addEntry(300, 10, 300);
        addEntry(300, 301, 300);
        addEntry(300, 11, 300);
        addEntry(300, 306, 300);
        addEntry(300, 310, 300);
        addEntry(301, 72, 301);
        addEntry(302, 303, 302);
        addEntry(303, 50, 303);
        addEntry(303, 302, 303);
        addEntry(303, 99, 303);
        addEntry(303, 11, 303);

        addEntry(305, 50, 305);
        addEntry(305, 51, 305);
        addEntry(305, 52, 305);
        addEntry(305, 53, 305);
        addEntry(305, 11, 305);
        addEntry(305, 81, 305);
        addEntry(305, 82, 305);
        addEntry(305, 83, 305);
        addEntry(305, 84, 305);
        addEntry(305, 14, 305);
        addEntry(305, 13, 305);
        addEntry(305, 15, 305);

        // Reglas para FROM
        addEntry(306, 308, 306);
        addEntry(306, 307, 307);
        addEntry(307, 50, 307);
        addEntry(307, 306, 306);
        addEntry(307, 12, 307);

        addEntry(308, 309, 308);
        addEntry(309, 401, 309);
        addEntry(309, 50, 309);
        addEntry(309, 12, 309);

        // Reglas para WHERE
        addEntry(310, 12, 310);
        addEntry(310, 99, 17);

        addEntry(311, 14, 311);

        addEntry(312, 14, 312);
        addEntry(312, 15, 312);
        addEntry(312, 53, 312);

        addEntry(314, 81, 314);
        addEntry(314, 82, 314);
        addEntry(314, 83, 314);
        addEntry(314, 84, 314);

        addEntry(314, 13, 314);

        addEntry(315, 81, 315);
        addEntry(315, 82, 315);
        addEntry(315, 83, 315);

        addEntry(315, 8, 29);
        addEntry(316, 304, 30);
        addEntry(316, 54, 31);
        addEntry(316, 318, 32);
        addEntry(316, 319, 33);
        addEntry(317, 14, 317);
        addEntry(317, 15, 317);
        addEntry(318, 62, 36);
        addEntry(319, 61, 37);

        /* *
         * Reglas que involucran identificadores
         * Los identificadores empiezan por 401.
         * En la tabla sintactica se representan como 4
         * */

        for (int i = 401; i <= 500; i++) {
            addEntry(304, i, 304);
            addEntry(301, i, 301);
            addEntry(302, i, 302);
            addEntry(308, i, 308);
            addEntry(309, i, 309);
            addEntry(306, i, 306);
            addEntry(305, i, 305);
            addEntry(311, i, 311);
            addEntry(313, i, 313);
            addEntry(316, i, 316);
        }

        /* *
         * Reglas que involucran Constantes
         * Las Constantes empiezan por 600.
         * En la tabla sintactica se representan como 4
         * */
        for (int i = 600; i <= 700; i++) {
            addEntry(304, i, 304);
            addEntry(301, i, 301);
            addEntry(302, i, 302);
            addEntry(308, i, 308);
            addEntry(316, i, 316);
            addEntry(306, i, 306);
            addEntry(305, i, 305);
            addEntry(311, i, 311);
            addEntry(313, i, 313);
            addEntry(319, i, 319);

        }
    }

    private void addEntry(int nonTerminal, int terminal, int production) {
        // Verifica si ya existe una entrada para el símbolo no terminal
        if (!table.containsKey(nonTerminal)) {
            // Si no existe, crea un nuevo mapa para el símbolo no terminal
            table.put(nonTerminal, new HashMap<>());
        }
        // Agrega la entrada al mapa correspondiente al símbolo no terminal
        table.get(nonTerminal).put(terminal, production);
    }

    public boolean isNonTerminal(int symbol) {
        return symbol >= 300 && symbol <= 319;
    }

    public boolean isTerminal(int symbol) {
        return symbol >= 10 && symbol <= 85 || symbol >= 401 && symbol <= 650;
    }


    public int getProduction(int nonTerminal, int terminal) {
        Map<Integer, Integer> productionMap = table.get(nonTerminal);
        if (productionMap != null && productionMap.containsKey(terminal)) {
            return productionMap.get(terminal);
        } else {
            return -1;
        }
    }

    public List<Integer> getProductionSymbols(int production, Token currentToken) {
        List<Integer> symbols = new ArrayList<>();
        switch (production) {
            case 300:
                if (currentToken.getCode() == 10) {
                    symbols.add(10);
                    symbols.add(301);
                    symbols.add(11);
                    symbols.add(306);
                    symbols.add(310);
                }else{
                    symbols.add(-1);
                }
                break;
            case 301:
                if (currentToken.getCode() == 72) {
                    symbols.add(72);
                } else {
                    symbols.add(302);
                }
                break;
            case 302:
                symbols.add(304);
                symbols.add(303);
                break;
            case 303:
                if (currentToken.getCode() == 50) {
                    symbols.add(50);
                    symbols.add(302);
                }
                break;
            case 304:
                symbols.add(currentToken.getCode());
                symbols.add(305);
                break;
            case 305:
                if (currentToken.getCode() == 51) {
                    symbols.add(51);
                    symbols.add(currentToken.getCode());
                }
                break;
            case 306:
                symbols.add(308);
                symbols.add(307);
                break;
            case 307:
                if (currentToken.getCode() == 50) {
                    symbols.add(50);
                    symbols.add(306);
                }
                break;
            case 308:
                System.out.println("el token actual: " + currentToken.getCode());
                symbols.add(currentToken.getCode());
                symbols.add(309);
                break;
            case 309:
                if (currentToken.getCode() >= 401) {
                    symbols.add(currentToken.getCode());
                }
                break;
            case 310:
                if (currentToken.getCode() == 12) {
                    symbols.add(12);
                    symbols.add(311);
                }
                break;
            case 311:
                symbols.add(313);
                symbols.add(312);
                break;
            case 312:
                if (currentToken.getCode() == 14 || currentToken.getCode() == 15) {
                    symbols.add(317);
                    symbols.add(311);
                } else if (currentToken.getCode() == 53) {
                    System.out.println("VACIO");
                }else {
                    symbols.add(-1);
                }
                break;
            case 313:
                symbols.add(304);
                symbols.add(314);
                break;
            case 314:
                if (currentToken.getCode() >= 81 && currentToken.getCode() <= 85) {
                    symbols.add(315);
                    symbols.add(316);
                }else if(currentToken.getCode() == 13){
                    symbols.add(13);
                    symbols.add(52);
                    symbols.add(300);
                    symbols.add(53);
                }else {
                    symbols.add(-1);
                }
                break;
            case 315:
                symbols.add(currentToken.getCode());
                break;
            case 316:
                if (currentToken.getCode() == 54) {
                    symbols.add(54);
                    symbols.add(318);
                    symbols.add(54);
                } else if (currentToken.getCode() >= 600) {
                    symbols.add(319);
                }else if(currentToken.getCode() >= 401) {
                    symbols.add(currentToken.getCode());
                }
                break;
            case 317:
                if (currentToken.getCode() == 14) {
                    symbols.add(14);
                } else if (currentToken.getCode() == 15) {
                    symbols.add(15);
                }
                break;
            case 318:
                if (currentToken.getCode() >= 600) {
                    symbols.add(currentToken.getCode());
                }
                break;
            case 319:
                if (currentToken.getCode() >= 600) {
                    symbols.add(currentToken.getCode());
                }
                break;
            default:
                break;
        }
        return symbols;
    }

}

