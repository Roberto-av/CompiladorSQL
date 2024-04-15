import tables.ConstantTable;
import tables.IdentifierTable;
import tables.MainTable;
import token.Token;
import token.TokenType;
import token.Tokenizer;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String input = "SELECT ANOMBRE\n" +
                "FROM ALUMNOS A,INSCRITOS I,CARRERAS C\n" +
                "WHERE A.A#=I.A# AND A.C#=C.C# AND I.SEMESTRE='2010I'\n" +
                "AND C.CNOMBRE='ISC' AND A.GENERACION='2010'";

        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(input);

        IdentifierTable tablaIdentificadores = new IdentifierTable();
        ConstantTable tablaConstantes = new ConstantTable();

        // Procesar tokens y llenar tablas
        for (Token token : tokens) {
            if (token.getType() == TokenType.IDENTIFICADOR) {
                int code = tablaIdentificadores.getIdentifierCode(token.getValue());
                tablaIdentificadores.addToIdentifierTable(token.getValue(), code, token.getLineNumber());
            } else if (token.getType() == TokenType.CONSTANTE) {
                int code = tablaConstantes.getConstantCode(token.getValue());
                tablaConstantes.addToConstantTable(token.getValue(), code, token.getLineNumber());
            }
        }

        MainTable.printMainTable(input);
        tablaIdentificadores.printIdentifierTable();
        tablaConstantes.printConstantTable();
    }
}
