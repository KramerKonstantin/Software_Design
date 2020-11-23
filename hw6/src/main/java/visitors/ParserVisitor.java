package visitors;

import tokenizer.*;

import java.util.List;
import java.util.Stack;

public class ParserVisitor implements TokenVisitor {
    private final Stack<Token> tokens;
    private final Stack<Token> stack;

    public ParserVisitor() {
        tokens = new Stack<>();
        stack = new Stack<>();
    }

    public List<Token> RPNParse(List<Token> tokens) {
        tokens.forEach(token -> token.accept(this));

        while (!stack.isEmpty()) {
            Token token = stack.pop();
            this.tokens.push(token);
        }

        return List.copyOf(this.tokens);
    }

    @Override
    public void visit(NumberToken token) {
        tokens.push(token);
    }

    @Override
    public void visit(BracketToken token) {
        if (token.getTokenType() == TokenType.LEFT) {
            stack.push(token);
        } else {
            Token currToken = stack.pop();

            while (!stack.isEmpty() && currToken.getTokenType() != TokenType.LEFT) {
                tokens.push(currToken);
                currToken = stack.pop();
            }
        }
    }

    @Override
    public void visit(OperationToken token) {
        if (!stack.isEmpty()) {
            TokenType currType = token.getTokenType();
            Token currToken = stack.peek();

            while (!stack.isEmpty() && currType.getPriority() <= currToken.getTokenType().getPriority()) {
                currToken = stack.pop();
                tokens.push(currToken);

                if (!stack.isEmpty()) {
                    currToken = stack.peek();
                }
            }
        }

        stack.push(token);
    }
}