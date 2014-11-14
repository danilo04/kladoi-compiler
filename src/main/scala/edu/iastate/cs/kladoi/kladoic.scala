package edu.iastate.cs.kladoi

import java.io.File

import scopt.OptionParser

/**
 * @author Danilo Dominguez Perez
 */
object kladoic {
  case class Config(var src: File = new File("."))


  def main(args: Array[String]) = {
    println("Starting kladoic...")
    val configOption = parseArgs(args)
    if (!configOption.isDefined) {
      System.exit(1)
    }
    
    val config = configOption.get
    Compiler.compile(config.src)
  }

  def parseArgs(args: Array[String]) : Option[Config] = {
    val parser = new OptionParser[Config]("kladoic") {
      head("kladoic", "0.1")
      opt[File]('s', "src") required() valueName ("<source>") action { (x, c) =>
        c.copy(src = x)
      } text ("Source file")
    }

    parser.parse(args, Config()) map { config =>
      Some(config)
    } getOrElse {
      None
    }
  }
}
