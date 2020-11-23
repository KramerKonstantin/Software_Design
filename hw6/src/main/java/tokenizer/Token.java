package tokenizer;

import visitors.TokenVisitor;

public interface Token {

    void accept(TokenVisitor visitor);

    TokenType getTokenType();

    String toVisitorString();
}