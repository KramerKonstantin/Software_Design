package tokenizer;

public enum TokenType {
    LEFT,
    RIGHT,
    PLUS,
    MINUS,
    DIV,
    MUL,
    NUMBER;

    public int getPriority() {
        return switch (this) {
            case LEFT, RIGHT -> 0;
            case PLUS, MINUS -> 1;
            case DIV, MUL -> 2;
            default -> -1;
        };
    }
}
