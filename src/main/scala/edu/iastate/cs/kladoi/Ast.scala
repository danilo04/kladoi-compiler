package edu.iastate.cs.kladoi

object Ast {

  sealed abstract class Prog
  case object Empty extends Prog {
    override def toString() = "Empty"
  }
  case class Seq(val stmt: Stmt, val prog: Prog) extends Prog {
    override def toString() = "Seq(" + stmt.toString + ", " + prog.toString  + ")"
  }

  sealed abstract class Stmt
  case class AssignmentStmt(val varr: Varr, val expr: Expr)  extends Stmt  {
    override def toString() = "Assignment(" + varr.toString + ", " + expr.toString  + ")"
  }
  case class IfStmt(val cond: Expr, val trueBranch: Prog, val falseBranch: Prog)  extends Stmt  {
    override def toString() = "If(" + cond.toString + ", " + trueBranch.toString  + ", " + falseBranch.toString + ")"
  }
  case class WhileStmt(val cond: Expr, val body: Prog) extends Stmt  {
    override def toString() = "While(" + cond.toString + ", " + body.toString  + ")"
  }
  case object NoOpStmt extends Stmt {
    override def toString() = "Pass"
  }

  sealed abstract class Expr
  case class Literal(val value: Int) extends Expr {
    override def toString() = value.toString
  }
  case class Variable(val varr: Varr) extends Expr  {
    override def toString() = varr.toString
  }
  case class BinExp(val op: String, val left: Expr, val right: Expr) extends Expr {
    override def toString() = "(" + op + ", " + left.toString + ", " + right.toString + ")"
  }

  case class Varr(val name: String) {
    override def toString() = name
  }

}
