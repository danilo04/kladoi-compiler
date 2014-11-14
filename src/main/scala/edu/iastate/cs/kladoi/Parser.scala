package edu.iastate.cs.kladoi


import scala.util.parsing.combinator.JavaTokenParsers

class Parser extends JavaTokenParsers {
  //Helper parsers
  def id : Parser[String] =
    """[a-zA-Z=*+/<>!\?][a-zA-Z0-9=*+/<>!\?]*""".r ^^ { case s => s }

  def eof = "\\Z".r

  def makeSeqs(stmts: List[Ast.Stmt]): Ast.Prog = stmts match {
    case h :: t => Ast.Seq(h, makeSeqs(t))
    case _ => Ast.Empty
  }

  //Parse keywords of the language
  def begink: Parser[String] = "begin"
  def endk: Parser[String] = "end"
  def passk: Parser[String] = "pass"
  def ifk: Parser[String] = "if"
  def whilek: Parser[String] = "while"
  def equalk: Parser[String] = ":="

  //Parse operators
  def operator: Parser[String] = "+" | "-" | "*" | "/"

  //Parse expressions
  def exprLiteral: Parser[Ast.Literal] = 
    wholeNumber  ^^ { case n => Ast.Literal(n.toInt) }

  def exprVariable: Parser[Ast.Variable] = id ^^ { case v => Ast.Variable(Ast.Varr(v)) }

  def exprBin: Parser[Ast.BinExp] =
    operator ~ expr ~ expr ^^ {
      case op ~ left ~ right => Ast.BinExp(op, left, right)
    }

  def expr: Parser[Ast.Expr] = exprBin | exprVariable | exprLiteral

  //Parse statements
  def whileStmt: Parser[Ast.WhileStmt] = 
    whilek ~> expr ~ prog ^^ {
      case e ~ b => Ast.WhileStmt(e, b)
    }

  def ifStmt: Parser[Ast.IfStmt] = 
    ifk ~> expr ~ prog ~ prog ^^ {
      case e ~ t ~ f => Ast.IfStmt(e, t, f)
    }

  def assignmentStmt: Parser[Ast.AssignmentStmt] =
    id ~ equalk ~ expr ^^ {
      case i ~ _ ~ e => Ast.AssignmentStmt(Ast.Varr(i), e)
    }

  def noOpStmt: Parser[Ast.Stmt] = passk ^^ { case _ => Ast.NoOpStmt }

  def stmt: Parser[Ast.Stmt] = whileStmt | ifStmt | assignmentStmt | noOpStmt

  def emptyProg: Parser[Ast.Prog] = eof ^^ { case _ => Ast.Empty }

  def seqProg: Parser[Ast.Prog] = stmt ^^ { case s => Ast.Seq(s, Ast.Empty) }

  def beginProg: Parser[Ast.Prog] = begink ~> rep1(stmt) <~ endk ^^ { case ss => makeSeqs(ss) }

  def prog: Parser[Ast.Prog] = emptyProg | seqProg | beginProg
}

