package Magma;

import java.util.List;

abstract class Stmt {
    interface Visitor<R> {
        R visitBlockStmt(Block stmt);
        //R visitClassStmt(Class stmt);
        R visitExpressionStmt(Expression stmt);
        //R visitFunctionStmt(Function stmt);
        //R visitIfStmt(If stmt);
        R visitPrintStmt(Print stmt);
        //R visitReturnStmt(Return stmt);
        //R visitVarStmt(Var stmt);
        //R visitWhileStmt(While stmt);
    }

    // Nested Stmt classes here…

    abstract <R> R accept(Visitor<R> visitor);

    static class Block extends Stmt {
        Block(List<Stmt> stmts) {
            this.statements = stmts;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitBlockStmt(this);
        }

        final List<Stmt> statements;
    }

    static class Expression extends Stmt {
        Expression(Expr expression) {
            this.expression = expression;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitExpressionStmt(this);
        }

        final Expr expression;
    }

    static class Print extends Stmt {
        Print(Expr expression) {
            this.expression = expression;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitPrintStmt(this);
        }

        final Expr expression;
    }
}
