package kulturraum.org.calculator.utils;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ExpressionParser {
    public String eval(String expression) {
        try {
            Expression e = new ExpressionBuilder(expression).build();
            return String.format("%.1f", e.evaluate());
        } catch(Exception e) {
            return null;
        }
    }
}
