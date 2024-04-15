package token;

public class Token {

    private String value;
    private TokenType type;
    private int code;
    private int lineNumber;
    private int tokenNumber;

    public Token(){

    }

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

    public void setTokenNumber(int tokenNumber) {
        this.tokenNumber = tokenNumber;
    }
}
