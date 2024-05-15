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

        String input = "CREATE TABLE PROFESORES(\n" +
                "P# CHAR(2) NOT NULL,\n" +
                "PNOMBRE CHAR(20) NOT NULL,\n" +
                "EDAD NUMERIC(2) NOT NULL,\n" +
                "SEXO CHAR(1)NOT NULL,\n" +
                "ESP CHAR(4) NOT NULL,\n" +
                "GRADO CHAR(3) NOT NULL,\n" +
                "D# CHAR(2) NOT NULL,\n" +
                "CONSTRAINT PK_PROFESORES PRIMARY KEY (P#),\n" +
                "CONSTRAINT FK_PROFESORES FOREIGN KEY (D#) REFERENCES DEPARTAMENTOS(D#));\n" +
                "INSERT INTO PROFESORES VALUES ('P1','DA VINCI LEONARDO',60,'M','PINT','LIC',\n" +
                "\n" +
                "'D2');\n" +
                "\n" +
                "INSERT INTO PROFESORES VALUES ('P2','ARQUIMIDES',65,'M','QUIM','MAE','D3');\n" +
                "INSERT INTO PROFESORES VALUES ('P3','TURING ALAN',43,'M','COMP','DOC','D1');\n" +
                "INSERT INTO PROFESORES VALUES ('P4','EINSTEIN ALBERT',58,'M','GENI','DOC','D1');\n" +
                "INSERT INTO PROFESORES VALUES ('P5','CURIE MARIE',45,'F','QUIM','LIC','D3');\n" +
                "INSERT INTO PROFESORES VALUES ('P6','HAWKING WILLIAM',52,'M','FISI','DOC','D4');\n" +
                "INSERT INTO PROFESORES VALUES ('P7','VON NEWMAN JOHN',47,'M','COMP','MAE','D1');\n" +
                "INSERT INTO PROFESORES VALUES ('P8','NEWTON ISAAC',36,'M','FISI','LIC','D3');\n" +
                "INSERT INTO PROFESORES VALUES ('P9','THATCHER MARGARET',64,'F','COMP','MAE',\n" +
                "\n" +
                "'D1');";

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
