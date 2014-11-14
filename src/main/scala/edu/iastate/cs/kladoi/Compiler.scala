package edu.iastate.cs.kladoi

import java.io.File
import scala.io.Source

object Compiler {
  def compile(src: File): Unit = {
    val srcReader = Source.fromFile(src).bufferedReader()
    val parser = new Parser()
    val parserResult = parser.parseAll(parser.prog, srcReader)
    val ast = parserResult.getOrElse(println("Error parsing"))
    println(ast)
  }
}
