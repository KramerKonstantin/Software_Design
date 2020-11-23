package states;

import tokenizer.Token;
import tokenizer.Tokenizer;

public class StartState extends State {
    public StartState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public Token createToken() {
        throw new UnsupportedOperationException();
    }
}
