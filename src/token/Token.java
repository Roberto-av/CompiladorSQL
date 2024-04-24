package token;

public class Token {

    private String value;
    private TokenType type;
    private int code;
    private int lineNumber;
    private int tokenNumber;
    private int startIndex;

    public static final int END_OF_INPUT_CODE = -1;
    public static final int EMPTY_PRODUCTION = -1;

    public Token(){

    }

    public Token(String value, TokenType type, int code, int lineNumber, int tokenNumber, int startIndex) {
        this.value = value;
        this.type = type;
        this.code = code;
        this.lineNumber = lineNumber;
        this.tokenNumber = tokenNumber;
        this.startIndex = startIndex;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getTokenNumber() {
        return tokenNumber;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setTokenNumber(int tokenNumber) {
        this.tokenNumber = tokenNumber;
    }
}
