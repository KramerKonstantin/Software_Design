package visitors;

import tokenizer.BracketToken;
import tokenizer.NumberToken;
import tokenizer.OperationToken;

public interface TokenVisitor {
    void visit(NumberToken token);

    void visit(BracketToken token);

    void visit(OperationToken token);
}
