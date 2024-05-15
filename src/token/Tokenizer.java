package token;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private int constantCodeCounter = 600;
    private int identifierCodeCounter = 401;
    private int lineNumber = 1;

    public List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        int tokenCounter = 1;
        int tokenStart = 0;
        int startIndex = 0;

        Pattern pattern = Pattern.compile("\\b(SELECT|FROM|WHERE|IN|AND|OR|INSERT|INTO|VALUES|CREATE|TABLE|CHAR|NUMERIC|NOT|NULL|CONSTRAINT|KEY|PRIMARY|FOREIGN|REFERENCES)\\b|\\b[a-zA-Z]\\w*(?:\\.\\w+)?#?|\\b[a-zA-Z]\\w*(?:\\s[a-zA-Z]\\w*)*(?:\\.\\w+)?#?\\b|'[^']*'|\\b\\d+\\b|<=?|>=?|==?|,|\\S");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String value = matcher.group();
            TokenType type = getTokenType(value);
            int code = getCode(value, type);

            lineNumber += countOccurrences(value, "\n");
            tokenStart += value.length();
            int tokenStartIndex = matcher.start(); // Obtener la posición de inicio del token en el texto original
            int tokenLineNumber = calculateLineNumber(input, tokenStartIndex);
            tokens.add(new Token(value, type, code, tokenLineNumber, tokenCounter++, tokenStartIndex));
        }

        return tokens;
    }

    private TokenType getTokenType(String token) {
        if (Arrays.asList("SELECT", "FROM", "WHERE", "AND", "OR", "INSERT", "INTO", "VALUES", "CREATE", "TABLE", "CHAR", "NUMERIC", "NOT", "NULL","IN", "CONSTRAINT", "PRIMARY", "KEY", "FOREIGN", "REFERENCES").contains(token.toUpperCase())) {
            return TokenType.RESERVADAS;
        } else if (token.matches("[a-zA-Z]\\w*(?:\\.[a-zA-Z]\\w*)*(?:#)?(?:\\s+[a-zA-Z]\\w*(?:\\.[a-zA-Z]\\w*)*(?:#)?)*")) {
            return TokenType.IDENTIFICADOR;
        } else if (token.matches("^'.*'$")) {
            return TokenType.CONSTANTE;
        } else if (Arrays.asList("=", ">=", "<=", "<", ">", "==").contains(token)) {
            return TokenType.RELACIONALES;
        } else if (Arrays.asList("," , "." ,"(" ,")" ,"'", ";").contains(token)) {
            return TokenType.DELIMITADORES;
        } else if (Arrays.asList("+" , "-" ,"*" ,"/").contains(token)) {
            return TokenType.OPERADORES;
        } else if (token.matches("^\\d+$")) {
            return TokenType.NUMERO; // El token consiste solo en números del 0 al 9
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
        } else if (type == TokenType.NUMERO) {
            return getConstantCode(token);
        }else {
            return -1; // Código para otros tokens
        }
    }

    private int getIdentifierCode(String identifier) {
        return identifierCodeCounter++;
    }

    private int getConstantCode(String constant) {
        return constantCodeCounter++;
    }

    private int calculateLineNumber(String input, int tokenStartIndex) {
        int lineNumber = 1;
        for (int i = 0; i < tokenStartIndex; i++) {
            if (input.charAt(i) == '\n') {
                lineNumber++;
            }
        }
        return lineNumber;
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
