package tokenizer;

import visitors.TokenVisitor;

public class OperationToken implements Token {
    private final TokenType type;

    public OperationToken(TokenType type) {
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

    public long calculate(long a, long b) {
        return switch (type) {
            case PLUS -> a + b;
            case MINUS -> a - b;
            case MUL -> a * b;
            case DIV -> a / b;
            default -> throw new RuntimeException();
        };
    }

    @Override
    public String toVisitorString() {
        return type.toString();
    }
}
