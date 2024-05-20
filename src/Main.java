import paser.LL1Parser;
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
                "FROM ALUMNOS,INSCRITOS,CARRERAS\n" +
                "WHERE ALUMNOS.A#=INSCRITOS.A# AND ALUMNOS.C#=CARRERAS.C#\n" +
                "AND INSCRITOS.SEMESTRE='2010I'\n" +
                "AND CARRERAS.CNOMBRE='ISC\n" +
                "AND ALUMNOS.GENERACION='2010'";

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

        LL1Parser parser = new LL1Parser();
        boolean result = parser.parse(input);
        if(result){
            System.out.println();
            System.out.println("--------------SUCCESS---------------");
            System.out.println("200 sin error: La consulta es valida");
        }

    }
}
