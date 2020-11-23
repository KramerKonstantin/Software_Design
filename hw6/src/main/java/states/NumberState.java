package states;

import tokenizer.NumberToken;
import tokenizer.Token;
import tokenizer.Tokenizer;

public class NumberState extends State {
    public NumberState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public Token createToken() {
        char symbol = tokenizer.currentSymbol();
        StringBuilder number = new StringBuilder();

        while (Character.isDigit(symbol)) {
            number.append(symbol);
            symbol = tokenizer.nextSymbol();
        }

        return new NumberToken(number.toString());
    }
}
