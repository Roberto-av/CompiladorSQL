package tables;

import token.Token;
import token.Tokenizer;

import java.util.List;

public class MainTable {
    public static void printMainTable(String input) {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(input);

        int lineNumber = 1;
        int tokenStart = 0;
        System.out.println("------------------------");
        System.out.println("Main Table");
        System.out.println("------------------------\n");
        for (Token token : tokens) {
            int lineCount = countOccurrences(input.substring(tokenStart, tokenStart + token.getValue().length()), "\n");

            lineNumber += lineCount;

            System.out.printf("No. (%d), Line (%d), TOKEN [%s], Type (%s), Code (%s)\n",
                    token.getTokenNumber(), lineNumber, token.getValue(), token.getType(), token.getCode());

            tokenStart += token.getValue().length();

            System.out.println();
        }
    }

    private static int countOccurrences(String input, String target) {
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
