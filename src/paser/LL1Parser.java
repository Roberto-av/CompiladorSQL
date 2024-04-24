package paser;

import token.Token;
import token.Tokenizer;

import java.util.List;
import java.util.Stack;

public class LL1Parser {
    private final SyntaxTable syntaxTable;
    private final Tokenizer tokenizer;

    public LL1Parser() {
        this.syntaxTable = new SyntaxTable();
        this.tokenizer = new Tokenizer();
    }

    public boolean parse(String input) {
        List<Token> tokens = tokenizer.tokenize(input);
        Stack<Integer> stack = new Stack<>();
        stack.push(Token.END_OF_INPUT_CODE); // Inserta el símbolo de fin de entrada en la pila
        stack.push(300); // Inserta el símbolo inicial en la pila

        int tokenIndex = 0; // Índice para recorrer los tokens

        while (!stack.isEmpty()){
            int top = stack.pop();

            System.out.println("Contenido de la pila:");
            for (int i = stack.size() - 1; i >= 0; i--) {
                System.out.println("pila valor: "+stack.get(i));
            }

            if (top == Token.END_OF_INPUT_CODE) {
                System.out.println("Se acabo la consulta");
                return true;
            }

            System.out.println("Numero de tokens: "+ tokenIndex);
            if (tokenIndex >= tokens.size()){
                return true;
            }

            Token currentToken = tokens.get(tokenIndex);
            int currentTokenCode = currentToken.getCode();

            System.out.println("[ Token: " + currentToken.getValue() + " ( " + currentTokenCode + ") ]");
            System.out.println("Top: " + top);

            if (syntaxTable.isTerminal(top) || top == Token.END_OF_INPUT_CODE) {
                System.out.println("Entro al if de si es terminal");
                // Coincidencia entre el símbolo en la parte superior de la pila y el token actual
                if (top == currentTokenCode) {
                    System.out.println("Avanza al siguiente token");
                    tokenIndex++;
                }else {
                    // ERROR: X y K son Terminales, pero X != K
                    System.out.println("Error: Se esperaba el token: " + top);
                    return false;
                }
            } else {
                int production = syntaxTable.getProduction(top, currentTokenCode);
                System.out.println("Produccion, top: "+ top + " Code: " + currentTokenCode);

                if (production != -1) {

                    System.out.println("Entre al if de si produccion es diferende de -1");

                    // Si la producción no es lambda, insertar en la pila en forma inversa
                    if (production != Token.EMPTY_PRODUCTION) {

                        System.out.println("Entre al if de si produccion es diferende de Token.EMPTY_PRODUCTION");

                        List<Integer> symbols = syntaxTable.getProductionSymbols(production, currentToken);
                        System.out.println("Symbols: " + symbols);

                        for (int i = symbols.size() - 1; i >= 0; i--) {
                            stack.push(symbols.get(i));
                            System.out.println("Pushed symbol: " + symbols.get(i));
                        }
                    }
                } else {
                    System.out.println();
                    System.out.println("-----------------ERROR-----------------");
                    int errorLineNumber = tokens.get(tokenIndex).getLineNumber();
                    // ERROR: TS[X, K] = Celda Vacía
                    if (top == 314 && currentTokenCode != 8) {
                        System.out.println("ERROR: 2:208 Linea "+ errorLineNumber +". Se esperaba operador relacional o palabra reservada 'IN'");
                        return false;
                    }else  if (top == 312 && currentTokenCode != 14 || top == 312 && currentTokenCode != 15) {
                        System.out.println("ERROR: 2:201 Linea "+ errorLineNumber +". Se esperaba palabra reservada");
                        return false;
                    }else if (top == 306 && currentTokenCode < 401){
                        System.out.println("ERROR: 2:204 Linea "+ errorLineNumber +". Se esperaba un identificador pero se hay un: "+currentToken.getValue());
                        return false;
                    }else if(top == 316 && currentTokenCode == -1){
                        System.out.println("ERROR: 2:205 Linea "+ errorLineNumber +". Se esperaba delimitador ");
                        return false;
                    }else{
                        System.out.println("Error: No se encontró una producción para el no terminal " + top + " y el token " + currentTokenCode + ".");
                        return false;
                    }

                }
            }
        }
        return true;
    }
}
