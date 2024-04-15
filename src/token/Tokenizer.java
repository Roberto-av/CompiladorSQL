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
            return TokenCodes.getKeywordCode(token);
        } else if (type == TokenType.IDENTIFICADOR) {
            return getIdentifierCode(token);
        } else if (type == TokenType.CONSTANTE) {
            return getConstantCode(token);
        } else if (type == TokenType.RELACIONALES) {
            return TokenCodes.getLogicCode(token);
        } else if (type == TokenType.DELIMITADORES) {
            return TokenCodes.getDelimiterCode(token);
        } else if (type == TokenType.OPERADORES) {
            return TokenCodes.getOperatorCode(token);
        } else {
            return -1; // Código para otros tokens
        }
    }

    private int getIdentifierCode(String identifier) {
        return identifierCodeCounter++;
    }

    private int getConstantCode(String constant) {
        return constantCodeCounter++;
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
