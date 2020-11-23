import tokenizer.Token;
import tokenizer.Tokenizer;
import visitors.CalcVisitor;
import visitors.ParserVisitor;
import visitors.PrintVisitor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            assert args.length != 1 : new IllegalArgumentException("There must be exactly one argument");

            String expression = args[0];

            Tokenizer tokenizer = new Tokenizer(expression);
            List<Token> tokens = tokenizer.parse();
            System.out.println("Expression: " + new PrintVisitor().walk(tokens));

            ParserVisitor parserVisitor = new ParserVisitor();
            tokens = parserVisitor.RPNParse(tokens);
            System.out.println("Polish notation: " + new PrintVisitor().walk(tokens));

            System.out.println("Value: " + new CalcVisitor().calc(tokens));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}