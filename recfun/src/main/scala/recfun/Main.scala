package recfun

import java.util.NoSuchElementException

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }


  def pascal(c: Int, r: Int): Int = {
    def counter(acc: Int, c: Int, r: Int): Int =
      if (r == 0 && c == 0) 1
      else if (c == 0 || c==r) 1
        else acc + pascal((c - 1), (r - 1)) + pascal(c, (r - 1))
    counter(0, c, r)
  }
  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def isBalanced(chars: List[Char], stack: String): Boolean =
      if (chars.isEmpty) stack.isEmpty //Got to the end, see if any pars left over
      else if (chars.head == '(') isBalanced(chars.tail, chars.head + stack)
      else if (chars.head == ')') isBalanced(chars.tail, matchPar(stack))
      else isBalanced(chars.tail, stack)

    //Removes an open par from the accumulator if one exists
    def matchPar(stack: String): String =
      if (stack.isEmpty) "FALSE"   //
      else if (stack.head.toChar == '(') stack.tail else stack

    isBalanced(chars, "")
  }



    /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int =
    if (money == 0) 1
    else if (money > 0 && !coins.isEmpty)
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
    else 0
  }

