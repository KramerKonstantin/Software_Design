package states;

import tokenizer.OperationToken;
import tokenizer.Token;
import tokenizer.TokenType;
import tokenizer.Tokenizer;

public class OperationState extends State {
    public OperationState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public Token createToken() {
        final char symbol = tokenizer.currentSymbol();
        tokenizer.nextSymbol();

        TokenType type = switch (symbol) {
            case '+' -> TokenType.PLUS;
            case '-' -> TokenType.MINUS;
            case '*' -> TokenType.MUL;
            case '/' -> TokenType.DIV;
            default -> throw new IllegalArgumentException("Unknown character '" + symbol + "'");
        };

        return new OperationToken(type);
    }
}