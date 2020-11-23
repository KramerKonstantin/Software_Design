package tokenizer;

import visitors.TokenVisitor;

public class BracketToken implements Token {
    private final TokenType type;

    public BracketToken(TokenType type) {
        this.type = type;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public TokenType getTokenType() {
        return type;
    }

    @Override
    public String toVisitorString() {
        return type.toString();
    }
}
