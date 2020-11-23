package tokenizer;

import states.*;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final List<Token> tokens;
    private final String expression;
    private int index;
    private State state;

    public Tokenizer(String expression) {
        this.expression = expression + '#';
        this.index = 0;
        this.state = new StartState(this);
        this.tokens = new ArrayList<>();
    }

    public List<Token> parse() {
        while (index < expression.length() && expression.charAt(index) != '#') {
            final char symbol = expression.charAt(index);

            if (Character.isWhitespace(symbol)) {
                index++;
                continue;
            } else if (Character.isDigit(symbol)) {
                state = new NumberState(this);
            } else if (symbol == '(' || symbol == ')') {
                state = new BracketState(this);
            } else if (symbol == '*' || symbol == '/' || symbol == '+' || symbol == '-') {
                state = new OperationState(this);
            }

            tokens.add(state.createToken());
        }

        return tokens;
    }

    public char currentSymbol() {
        assert index >= expression.length() : new IndexOutOfBoundsException(index);

        return expression.charAt(index);
    }

    public char nextSymbol() {
        index++;
        return currentSymbol();
    }
}