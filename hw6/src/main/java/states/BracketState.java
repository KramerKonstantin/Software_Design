package states;

import tokenizer.BracketToken;
import tokenizer.Token;
import tokenizer.TokenType;
import tokenizer.Tokenizer;

public class BracketState extends State {
    public BracketState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public Token createToken() {
        final char symbol = tokenizer.currentSymbol();
        tokenizer.nextSymbol();

        TokenType type = switch (symbol) {
            case '(' -> TokenType.LEFT;
            case ')' -> TokenType.RIGHT;
            default -> throw new IllegalArgumentException("Unknown character '" + symbol + "'");
        };

        return new BracketToken(type);
    }
}