/*

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static int constantCodeCounter = 600;
    private static int identifierCodeCounter = 401;
    private static Map<String, Integer> identifierLineMap = new HashMap<>();
    private static Map<String, Integer> constantLineMap = new HashMap<>();
    private static Map<String, Integer> identifierCodeMap = new HashMap<>();
    private static Map<String, Integer> constantCodeMap = new HashMap<>();

    public static void main(String[] args) {
        String input = "SELECT ANOMBRE\n" +
                "FROM ALUMNOS A,INSCRITOS I,CARRERAS C\n" +
                "WHERE A.A#=I.A# AND A.C#=C.C# AND I.SEMESTRE='2010I'\n" +
                "AND C.CNOMBRE='ISC' AND A.GENERACION='2010'";

        List<Token> tokens = parse(input);

        int lineNumber = 1;
        int tokenStart = 0;
        System.out.println("------------------------");
        System.out.println("Tabla Principal");
        System.out.println("------------------------\n");
        for (Token token : tokens) {
            int lineCount = countOccurrences(input.substring(tokenStart, tokenStart + token.getValue().length()), "\n");

            lineNumber += lineCount;

            System.out.printf("No. (%d), Linea (%d), TOKEN [%s], Tipo (%s), Código (%s)\n",
                    token.getTokenNumber(), lineNumber, token.getValue(), token.getType(), token.getCode());

            tokenStart += token.getValue().length();

            System.out.println();
        }

        printIdentifierTable();
        printConstantTable();

    }


    public static List<Token> parse(String input) {
        List<Token> tokens = new ArrayList<>();
        int tokenCounter = 1;

        // Definir patrón de expresión regular para tokens SQL
        Pattern pattern = Pattern.compile("\\b(SELECT|FROM|WHERE|IN|AND|OR|INSERT|INTO|VALUES|CREATE|TABLE|CHAR|NUMERIC|NOT|NULL|CONSTRAINT|KEY|PRIMARY|FOREIGN|REFERENCES)\\b|\\b[a-zA-Z]\\w*\\b|'[^']*'|<=?|>=?|==?|,|\\S");
        Matcher matcher = pattern.matcher(input);

        // Tokenizar la cadena de entrada
        int lineNumber = 1;
        while (matcher.find()) {
            String value = matcher.group();
            TokenType type = getTokenType(value);
            int code = getCode(value, type);
            tokens.add(new Token(value, type, code, lineNumber, tokenCounter++));
            lineNumber += countOccurrences(value, "\n");
        }

        return tokens;
    }

    public static void printIdentifierTable(){
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(identifierCodeMap.entrySet());
        sortedEntries.sort(Comparator.comparing(Map.Entry::getValue));

        System.out.println("------------------------");
        System.out.println("Tabla de identificadores");
        System.out.println("------------------------\n");
        for (Map.Entry<String, Integer> entry : sortedEntries) {

            String identifier = entry.getKey();
            int code = entry.getValue();
            List<Integer> lines = Collections.singletonList(identifierLineMap.get(identifier));
            System.out.printf("Identificador (%s) Valor (%d)\n", identifier, code);
            System.out.println();
        }
    }

    public static void printConstantTable() {
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(constantCodeMap.entrySet());
        sortedEntries.sort(Comparator.comparing(Map.Entry::getValue));

        System.out.println("------------------------");
        System.out.println("Tabla de constantes");
        System.out.println("------------------------\n");
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            String constant = entry.getKey();
            int code = entry.getValue();
            List<Integer> lines = Collections.singletonList(constantLineMap.get(constant));
            System.out.printf("Constante (%s) Valor (%d)\n", constant, code);
            System.out.println();
        }
    }


    public static TokenType getTokenType(String token) {
        if (Arrays.asList("SELECT", "FROM", "WHERE", "AND", "OR", "INSERT", "INTO", "VALUES", "CREATE", "TABLE", "CHAR", "NUMERIC", "NOT", "NULL","IN").contains(token.toUpperCase())) {
            return TokenType.KEYWORD;
        } else if (token.matches("\\b[a-zA-Z]\\w*\\b")) {
            return TokenType.IDENTIFIER;
        } else if (token.matches("'.*'")) {
            return TokenType.CONSTANT;
        } else if (Arrays.asList("=", ">=", "<=", "<", ">", "==").contains(token)) {
            return TokenType.LOGIC;
        } else if (Arrays.asList("," , "." ,"(" ,")" ,"'").contains(token)) {
            return TokenType.DELIMITER;
        } else if (Arrays.asList("+" , "-" ,"*" ,"/").contains(token)) {
            return TokenType.OPERATOR;
        } else {
            return TokenType.UNKNOWN;
        }
    }

    public static int getCode(String token, TokenType type) {
        Map<String, Integer> codeMap = new HashMap<>();
        codeMap.put("SELECT", 10);
        codeMap.put("FROM", 11);
        codeMap.put("WHERE", 12);
        codeMap.put("IN", 13);
        codeMap.put("AND", 14);
        codeMap.put("OR", 15);
        codeMap.put("INSERT", 27);
        codeMap.put("INTO", 28);
        codeMap.put("VALUES", 29);
        codeMap.put("CREATE", 16);
        codeMap.put("TABLE", 17);
        codeMap.put("CHAR", 18);
        codeMap.put("NUMERIC", 19);
        codeMap.put("NOT", 20);
        codeMap.put("NULL", 21);
        codeMap.put("CONSTRAINT", 22);
        codeMap.put("KEY", 23);
        codeMap.put("PRIMARY", 24);
        codeMap.put("FOREIGN", 25);
        codeMap.put("REFERENCES", 26);

        Map<String, Integer> codeMapLogic = new HashMap<>();
        codeMapLogic.put(">", 81);
        codeMapLogic.put("<", 82);
        codeMapLogic.put("=", 83);
        codeMapLogic.put(">=", 84);
        codeMapLogic.put("<=", 85);

        Map<String, Integer> codeMapDelimiter = new HashMap<>();
        codeMapDelimiter.put(",", 50);
        codeMapDelimiter.put(".", 51);
        codeMapDelimiter.put("(", 52);
        codeMapDelimiter.put(")", 53);
        codeMapDelimiter.put("'", 54);

        Map<String, Integer> codeMapOperator = new HashMap<>();
        codeMapOperator.put("+", 70);
        codeMapOperator.put("-", 71);
        codeMapOperator.put("*", 72);
        codeMapOperator.put("/", 73);



        if (type == TokenType.KEYWORD) {
            return codeMap.getOrDefault(token.toUpperCase(), -1);
        } else if (type == TokenType.IDENTIFIER) {
            return getIdentifierCode(token);
        } else if (type == TokenType.CONSTANT) {
            return getConstantCode(token);
        } else if (type == TokenType.LOGIC) {
            return codeMapLogic.getOrDefault(token.toUpperCase(), -1);
        } else if (type == TokenType.DELIMITER) {
            return codeMapDelimiter.getOrDefault(token.toUpperCase(), -1);
        } else if (type == TokenType.OPERATOR) {
            return codeMapOperator.getOrDefault(token.toUpperCase(), -1);
        } else {
            return -1; // Código para otros tokens
        }
    }

    public static int getConstantCode(String constant) {
        if (!constantCodeMap.containsKey(constant)) {
            constantCodeMap.put(constant, constantCodeCounter++);
        }
        return constantCodeMap.get(constant);
    }

    public static int getIdentifierCode(String identifier) {
        if (!identifierCodeMap.containsKey(identifier)) {
            identifierCodeMap.put(identifier, identifierCodeCounter++);
        }
        return identifierCodeMap.get(identifier);
    }


    public static int countOccurrences(String input, String target) {
        int count = 0;
        int lastIndex = 0;
        while (lastIndex != -1) {
            lastIndex = input.indexOf(target, lastIndex);
            if (lastIndex != -1) {
                count++;
                lastIndex += target.length();
            }
        }
        return count;
    }

    public static class Token {
        private String value;
        private TokenType type;
        private int code;
        private int lineNumber;
        private int tokenNumber;

        public Token(String value, TokenType type, int code, int lineNumber, int tokenNumber) {
            this.value = value;
            this.type = type;
            this.code = code;
            this.lineNumber = lineNumber;
            this.tokenNumber = tokenNumber;
        }

        public String getValue() {
            return value;
        }

        public TokenType getType() {
            return type;
        }

        public int getCode() {
            return code;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public int getTokenNumber() {
            return tokenNumber;
        }
    }

    public enum TokenType {
        KEYWORD,
        IDENTIFIER,
        CONSTANT,
        OPERATOR,
        LOGIC,
        DELIMITER,
        UNKNOWN
    }
}



*/
