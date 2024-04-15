package token;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private int constantCodeCounter = 600;
    private int identifierCodeCounter = 401;

    public List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        int tokenCounter = 1;
        int lineNumber = 1;
        int tokenStart = 0;

        Pattern pattern = Pattern.compile("\\b(SELECT|FROM|WHERE|IN|AND|OR|INSERT|INTO|VALUES|CREATE|TABLE|CHAR|NUMERIC|NOT|NULL|CONSTRAINT|KEY|PRIMARY|FOREIGN|REFERENCES)\\b|[a-zA-Z]\\w*(?:\\.\\w+)*(?:#)?|'[^']*'|<=?|>=?|==?|,|\\S");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String value = matcher.group();
            TokenType type = getTokenType(value);
            int code = getCode(value, type);
            tokens.add(new Token(value, type, code, lineNumber, tokenCounter++));
            lineNumber += countOccurrences(value, "\n");
            tokenStart += value.length();
        }

        return tokens;
    }

    private TokenType getTokenType(String token) {
        if (Arrays.asList("SELECT", "FROM", "WHERE", "AND", "OR", "INSERT", "INTO", "VALUES", "CREATE", "TABLE", "CHAR", "NUMERIC", "NOT", "NULL","IN").contains(token.toUpperCase())) {
            return TokenType.RESERVADAS;
        } else if (token.matches("[a-zA-Z]\\w*(?:\\.[a-zA-Z]\\w*)*(?:#)?")) {
            return TokenType.IDENTIFICADOR;
        } else if (token.matches("'.*'")) {
            return TokenType.CONSTANTE;
        } else if (Arrays.asList("=", ">=", "<=", "<", ">", "==").contains(token)) {
            return TokenType.RELACIONALES;
        } else if (Arrays.asList("," , "." ,"(" ,")" ,"'").contains(token)) {
            return TokenType.DELIMITADORES;
        } else if (Arrays.asList("+" , "-" ,"*" ,"/").contains(token)) {
            return TokenType.OPERADORES;
        } else {
            return TokenType.DESCONOCIDO;
        }
    }

    private int getCode(String token, TokenType type) {
        if (type == TokenType.RESERVADAS) {
            return getCodeForKeyword(token);
        } else if (type == TokenType.IDENTIFICADOR) {
            return getIdentifierCode(token);
        } else if (type == TokenType.CONSTANTE) {
            return getConstantCode(token);
        } else if (type == TokenType.RELACIONALES) {
            return getCodeForLogic(token);
        } else if (type == TokenType.DELIMITADORES) {
            return getCodeForDelimiter(token);
        } else if (type == TokenType.OPERADORES) {
            return getCodeForOperator(token);
        } else {
            return -1; // Código para otros tokens
        }
    }

    private int getCodeForKeyword(String token) {
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

        return codeMap.getOrDefault(token.toUpperCase(), -1);
    }

    private int getIdentifierCode(String identifier) {
        return identifierCodeCounter++;
    }

    private int getConstantCode(String constant) {
        return constantCodeCounter++;
    }

    private int getCodeForLogic(String token) {
        Map<String, Integer> codeMapLogic = new HashMap<>();
        codeMapLogic.put(">", 81);
        codeMapLogic.put("<", 82);
        codeMapLogic.put("=", 83);
        codeMapLogic.put(">=", 84);
        codeMapLogic.put("<=", 85);

        return codeMapLogic.getOrDefault(token.toUpperCase(), -1);
    }

    private int getCodeForDelimiter(String token) {
        Map<String, Integer> codeMapDelimiter = new HashMap<>();
        codeMapDelimiter.put(",", 50);
        codeMapDelimiter.put(".", 51);
        codeMapDelimiter.put("(", 52);
        codeMapDelimiter.put(")", 53);
        codeMapDelimiter.put("'", 54);

        return codeMapDelimiter.getOrDefault(token.toUpperCase(), -1);
    }

    private int getCodeForOperator(String token) {
        Map<String, Integer> codeMapOperator = new HashMap<>();
        codeMapOperator.put("+", 70);
        codeMapOperator.put("-", 71);
        codeMapOperator.put("*", 72);
        codeMapOperator.put("/", 73);

        return codeMapOperator.getOrDefault(token.toUpperCase(), -1);
    }

    private int countOccurrences(String input, String target) {
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
}
