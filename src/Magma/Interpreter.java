package Magma;

import java.util.List;

class Interpreter implements Expr.Visitor<Object>, Stmt.Visitor<Void> {

    @Override
    public Object visitLiteralExpr(Expr.Literal expr) {
        return expr.value;
    }

    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) {
        return evaluate(expr.expression);
    }

    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }

    private void execute(Stmt stmt) {
        stmt.accept(this);
    }

    @Override
    public Object visitBinaryExpr(Expr.Binary expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        switch(expr.operator.type) {
            case TokenType.MINUS:
                return (double)left - (double)right;
            case TokenType.SLASH:
                if((double)right == 0.0f) {
                    throw new RuntimeError(expr.operator, "Division by zero");
                }
                return (double)left / (double)right;
            case TokenType.STAR:
                return (double)left * (double)right;
            case TokenType.PLUS:
                if(left instanceof Double && right instanceof Double) {
                    return (double)left + (double)right;
                }
                if(left instanceof String && right instanceof String) {
                    return left + (String)right;
                }
                if(left instanceof String && right instanceof Double) {
                    return left + stringify(right);
                }
                if(left instanceof Double && right instanceof String) {
                    return stringify(left) + right;
                }
            case TokenType.GREATER:
                return (double)left > (double)right;
            case TokenType.GREATER_EQUAL:
                return (double)left >= (double)right;
            case TokenType.LESS:
                return (double)left < (double)right;
            case TokenType.LESS_EQUAL:
                return (double)left <= (double)right;
            case TokenType.BANG_EQUAL:
                return !isEqual(left,right);
            case TokenType.EQUAL_EQUAL:
                return isEqual(left, right);
        }

        return null;
    }


    private boolean isEqual(Object a, Object b) {
        if (a == null && b == null) return true;
        if (a == null) return false;
        return a.equals(b);
    }

    @Override
    public Object visitUnaryExpr(Expr.Unary expr) {
        Object right = evaluate(expr.right);

        switch(expr.operator.type) {
            case TokenType.MINUS:
                checkNumberOperand(expr.operator, right);
                return -(double)right;
            case TokenType.BANG:
                return !isTruthy(right);
        }

        // unreachable
        return null;
    }

    void interpret(List<Stmt> statements) {
        try{
            for(Stmt s : statements) {
                execute(s);
            }
        } catch (RuntimeError e) {
            Magma.runtimeError(e);
        }
    }

    private String stringify(Object object) {
        if (object == null) return "nil";
        if(object instanceof Double) {
            String text = object.toString();
            if(text.endsWith(".0")) {
                text = text.substring(0, text.length()-2);
            }
            return text;
        }

        return object.toString();
    }

    private void checkNumberOperand(Token op, Object operand) {
        if(operand instanceof Double) return;
        throw new RuntimeError(op, "Operand must be a number");
    }

    private void checkNumberOperands(Token op, Object left, Object right) {
        if(left instanceof Double && right instanceof Double) return;
        throw new RuntimeError(op, "Operands must be a numbers");
    }

    private boolean isTruthy(Object object) {
        if(object == null) return false;
        if(object instanceof Boolean) return (boolean)object;
        return true;
    }

    @Override
    public Void visitBlockStmt(Stmt.Block stmt) {
        return null;
    }

    @Override
    public Void visitExpressionStmt(Stmt.Expression stmt) {
        evaluate(stmt.expression);
        return null;
    }

    @Override
    public Void visitPrintStmt(Stmt.Print stmt) {
        Object value = evaluate(stmt.expression);
        System.out.println(stringify(value));
        return null;
    }
}