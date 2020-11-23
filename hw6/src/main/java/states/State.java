package states;

import tokenizer.Token;
import tokenizer.Tokenizer;

public abstract class State {
    protected final Tokenizer tokenizer;

    public State(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public abstract Token createToken();
}
