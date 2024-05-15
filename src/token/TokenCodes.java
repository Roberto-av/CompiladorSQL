package token;

import java.util.HashMap;
import java.util.Map;

public class TokenCodes {

    private static final Map<String, Integer> keywordCodes = new HashMap<>();

    private static final Map<String, Integer> codeMapLogic = new HashMap<>();

    private static final Map<String, Integer> codeMapDelimiter = new HashMap<>();

    private static final Map<String, Integer> codeMapOperator = new HashMap<>();

    static {
        keywordCodes.put("SELECT", 10);
        keywordCodes.put("FROM", 11);
        keywordCodes.put("WHERE", 12);
        keywordCodes.put("IN", 13);
        keywordCodes.put("AND", 14);
        keywordCodes.put("OR", 15);
        keywordCodes.put("CREATE", 16);
        keywordCodes.put("TABLE", 17);
        keywordCodes.put("CHAR", 18);
        keywordCodes.put("NUMERIC", 19);
        keywordCodes.put("NOT", 20);
        keywordCodes.put("NULL", 21);
        keywordCodes.put("CONSTRAINT", 22);
        keywordCodes.put("KEY", 23);
        keywordCodes.put("PRIMARY", 24);
        keywordCodes.put("FOREIGN", 25);
        keywordCodes.put("REFERENCES", 26);
        keywordCodes.put("INSERT", 27);
        keywordCodes.put("INTO", 28);
        keywordCodes.put("VALUES", 29);
    }

    static {
        codeMapLogic.put(">", 81);
        codeMapLogic.put("<", 82);
        codeMapLogic.put("=", 83);
        codeMapLogic.put(">=", 84);
        codeMapLogic.put("<=", 85);
    }

    static {
        codeMapDelimiter.put(",", 50);
        codeMapDelimiter.put(".", 51);
        codeMapDelimiter.put("(", 52);
        codeMapDelimiter.put(")", 53);
        codeMapDelimiter.put("'", 54);
        codeMapDelimiter.put(";", 55);
    }

    static {
        codeMapOperator.put("+", 70);
        codeMapOperator.put("-", 71);
        codeMapOperator.put("*", 72);
        codeMapOperator.put("/", 73);
    }

    public static int getKeywordCode(String keyword) {
        return keywordCodes.getOrDefault(keyword.toUpperCase(), -1);
    }

    public static int getLogicCode(String logic) {
        return codeMapLogic.getOrDefault(logic.toUpperCase(), -1);
    }

    public static int getDelimiterCode(String delimiter) {
        return codeMapDelimiter.getOrDefault(delimiter.toUpperCase(), -1);
    }

    public static int getOperatorCode(String operator) {
        return codeMapOperator.getOrDefault(operator.toUpperCase(), -1);
    }
}
