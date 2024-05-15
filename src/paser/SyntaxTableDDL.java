    package paser;

    import tables.IdentifierTable;
    import token.Token;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    public class SyntaxTableDDL {

        private final Map<Integer, Map<Integer, Integer>> table;
        private final IdentifierTable identifierTable;
        private int contInt = 600;
        private int contStr = 401;

        public SyntaxTableDDL() {
            this.table = new HashMap<>();
            this.identifierTable = new IdentifierTable();
            initializeTable();
        }

        private void initializeTable() {

            // Reglas para SELECT
            addEntry(200, 16, 200);
            addEntry(201, 16, 201);
            addEntry(201, 27, 201);
            addEntry(203, 18, 203);
            addEntry(203, 19, 203);
            addEntry(204, 20, 204);
            addEntry(204, 50, 204);
            addEntry(205, 50, 205);
            addEntry(205, 53, 205);


            addEntry(206, 22, 206);

            addEntry(207, 22, 207);

            addEntry(208, 24, 208);
            addEntry(208, 25, 208);

            addEntry(209, 26, 209);
            addEntry(209, 50, 209);
            addEntry(209, 53, 209);

            addEntry(210, 50, 210);
            addEntry(210, 53, 210);

            addEntry(211, 27, 211);

            addEntry(212, 54, 212);

            addEntry(213, 54, 213);

            addEntry(214, 50, 214);
            addEntry(214, 53, 214);

            addEntry(215, 27, 215);

            /* *
             * Reglas que involucran identificadores
             * Los identificadores empiezan por 401.
             * En la tabla sintactica se representan como 4
             * */

            for (int i = 401; i <= 500; i++) {
                addEntry(202, i, 202);
                addEntry(206, i, 206);
            }

            /* *
             * Reglas que involucran Constantes
             * Las Constantes empiezan por 600.
             * En la tabla sintactica se representan como 4
             * */
            for (int i = 600; i <= 700; i++) {
                addEntry(212, i, 212);
                addEntry(213, i, 213);
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
            return symbol >= 200 && symbol <= 215;
        }

        public boolean isTerminal(int symbol) {
            return symbol >= 10 && symbol <= 85 || symbol >= 401 && symbol <= 800;
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
                case 200:
                    if (currentToken.getCode() == 16) {
                        symbols.add(16);
                        symbols.add(17);
                        symbols.add(contStr);
                        symbols.add(52);
                        symbols.add(202);
                        symbols.add(53);
                        symbols.add(55);
                        symbols.add(201);
                        contStr++;
                    }else{
                        symbols.add(-1);
                    }
                    break;
                case 201:
                    if(currentToken.getCode() == 16){
                        symbols.add(200);
                    } else if (currentToken.getCode() == 27) {
                        symbols.add(211);
                    }
                    break;
                case 202:
                        symbols.add(currentToken.getCode());
                        contStr++;
                        symbols.add(203);
                        symbols.add(52);
                        symbols.add(contInt);
                        symbols.add(53);
                        symbols.add(204);
                        symbols.add(205);
                        contInt++;
                    break;
                case 203:
                    if(currentToken.getCode() == 18){
                        symbols.add(18);
                    }else if (currentToken.getCode() == 19){
                        symbols.add(19);
                    }
                    break;
                case 204:
                    if(currentToken.getCode() == 20){
                        symbols.add(20);
                        symbols.add(21);
                    }
                    break;
                case 205:
                    if(currentToken.getCode() == 50){
                        symbols.add(50);
                        symbols.add(206);
                    }
                    break;
                case 206:
                    if(currentToken.getCode() >= 401){
                        symbols.add(202);
                    }else if(currentToken.getCode() == 22){
                        symbols.add(207);
                    }
                    break;
                case 207:
                    symbols.add(22);
                    symbols.add(contStr);
                    contStr++;
                    symbols.add(208);
                    symbols.add(52);
                    symbols.add(contStr);
                    symbols.add(53);
                    symbols.add(209);
                    contStr++;
                    break;
                case 208:
                    if(currentToken.getCode() == 24){
                        symbols.add(24);
                        symbols.add(23);
                    }else if(currentToken.getCode() == 25){
                        symbols.add(25);
                        symbols.add(23);
                    }
                    break;
                case 209:
                    if(currentToken.getCode() == 26){
                        symbols.add(26);
                        symbols.add(contStr);
                        contStr++;
                        symbols.add(52);
                        symbols.add(contStr);
                        symbols.add(53);
                        symbols.add(210);
                        contStr++;
                    }else if(currentToken.getCode() == 50){
                        symbols.add(50);
                        symbols.add(207);
                    }
                    break;
                case 210:
                    if(currentToken.getCode() == 50){
                        symbols.add(50);
                        symbols.add(207);
                    }
                    break;
                case 211:
                        symbols.add(27);
                        symbols.add(28);
                        symbols.add(contStr);
                        symbols.add(29);
                        symbols.add(52);
                        symbols.add(212);
                        symbols.add(53);
                        symbols.add(55);
                        symbols.add(215);
                        contStr++;
                    break;
                case 212:
                    symbols.add(213);
                    symbols.add(214);
                    break;
                case 213:
                    if(currentToken.getCode() == 54){
                        symbols.add(54);
                        symbols.add(contStr);
                        symbols.add(54);
                        contStr++;
                    }else if(currentToken.getCode() >= 600){
                        symbols.add(contInt);
                        contInt++;
                    }
                    break;
                case 214:
                    if(currentToken.getCode() == 50){
                        symbols.add(50);
                        symbols.add(212);
                    }
                    break;
                case 215:
                    if(currentToken.getCode() == 16){
                        symbols.add(200);
                    }else if(currentToken.getCode() == 27){
                        symbols.add(211);
                    }
                    break;
                default:
                    break;
            }
            return symbols;
        }


    }
