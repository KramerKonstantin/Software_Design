package visitors;

import tokenizer.BracketToken;
import tokenizer.NumberToken;
import tokenizer.OperationToken;
import tokenizer.Token;

import java.util.List;

public class PrintVisitor implements TokenVisitor {
    private final StringBuilder answer;

    public PrintVisitor() {
        this.answer = new StringBuilder();
    }

    public String walk(List<Token> tokens) {
        tokens.forEach(token -> token.accept(this));
        return answer.toString();
    }

    @Override
    public void visit(NumberToken token) {
        addToken(token);
    }

    @Override
    public void visit(BracketToken token) {
        addToken(token);
    }

    @Override
    public void visit(OperationToken token) {
        addToken(token);
    }

    private void addToken(Token token) {
        answer.append(token.toVisitorString()).append(" ");
    }
}
