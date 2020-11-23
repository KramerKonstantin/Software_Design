package visitors;

import tokenizer.BracketToken;
import tokenizer.NumberToken;
import tokenizer.OperationToken;
import tokenizer.Token;

import java.util.List;
import java.util.Stack;

public class CalcVisitor implements TokenVisitor {
    private final Stack<Long> stack;

    public CalcVisitor() {
        this.stack = new Stack<>();
    }

    public long calc(List<Token> tokens) {
        if (tokens.isEmpty()) {
            return 0L;
        }

        tokens.forEach(token -> token.accept(this));
        long result = stack.pop();

        assert !stack.isEmpty() : new RuntimeException("The wrong expression.");

        return result;
    }

    @Override
    public void visit(NumberToken token) {
        stack.push(token.getValue());
    }

    @Override
    public void visit(BracketToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(OperationToken token) {
        assert stack.size() < 2 : new IllegalArgumentException();

        final long b = stack.pop();
        final long a = stack.pop();
        stack.add(token.calculate(a, b));
    }
}