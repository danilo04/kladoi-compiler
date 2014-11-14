package edu.iastate.cs.kladoi

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by danilo04 on 11/13/14.
 */
class ParserTest extends FlatSpec with Matchers {
  val test1 = "begin x := 45 end"
  val test2 =
    """
      begin
      	x := 45
      end
    """

  val test3 =
  """
    begin
        sum := 0
        i := sum
        while i begin
            if - i 5
                pass
                sum := + sum * i i
            i := - i 1
        end
    end
  """

  val test4 = ""

  val test5 = "begin while 5 begin x := 43 end end"

  val test6 = "begin while 5 x := 43 end"

  val test7 = "begin while 5 x := 43"

  val expectTest3 = "Seq(Assignment(sum, 0), Seq(Assignment(i, sum), Seq(While(i, Seq(If((-, i, 5), Seq(Pass, Empty), " +
    "Seq(Assignment(sum, (+, sum, (*, i, i))), Empty)), Seq(Assignment(i, (-, i, 1)), Empty))), Empty)))"

  "test1" should "have a Seq(Assignment(x, 45), Empty)" in {
    val parser = new Parser()
    val parserResult = parser.parseAll(parser.prog, test1)
    val result = parserResult.getOrElse(fail("Fail parsing"))
    result.toString should be ("Seq(Assignment(x, 45), Empty)")
  }

  "test2" should "is test1 with spaces" in {
    val parser = new Parser()
    val parserResult = parser.parseAll(parser.prog, test2)
    val result = parserResult.getOrElse(fail("Fail parsing"))
    result.toString should be ("Seq(Assignment(x, 45), Empty)")
  }

  "test3" should "parse while loop correctly" in {
    val parser = new Parser()
    val parserResult = parser.parseAll(parser.prog, test3)
    val result = parserResult.getOrElse(fail("Fail parsing"))
    result.toString should be (expectTest3)
  }

  "test4" should "be empty" in {
    val parser = new Parser()
    val parserResult = parser.parseAll(parser.prog, test4)
    val result = parserResult.getOrElse(fail("Fail parsing"))
    result.toString should be ("Empty")
  }

  "test5" should "have two Seq" in {
    val parser = new Parser()
    val parserResult = parser.parseAll(parser.prog, test5)
    val result = parserResult.getOrElse(fail("Fail parsing"))
    result.toString should be ("Seq(While(5, Seq(Assignment(x, 43), Empty)), Empty)")
  }

  "test6" should "have same result as test5" in {
    val parser = new Parser()
    val parserResult = parser.parseAll(parser.prog, test6)
    val result = parserResult.getOrElse(fail("Fail parsing"))
    result.toString should be ("Seq(While(5, Seq(Assignment(x, 43), Empty)), Empty)")
  }

  "test7" should "have failed" in {
    val parser = new Parser()
    val parserResult = parser.parseAll(parser.prog, test7)
    parserResult.isEmpty should be (true)
  }
}
