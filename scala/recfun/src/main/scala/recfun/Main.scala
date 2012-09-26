package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) 1
    else nchoosek(r, c)
  }

  def nchoosek(n: Int, k: Int): Int = {
    def factorial(start: Int, end: Int): Int = {
      if (start > end) 1
      else start * factorial(start + 1, end)
    }

    if (k <= n / 2) {
      factorial(n - k + 1, n) / factorial(1, k)
    } else nchoosek(n, n - k)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def balance(chars: List[Char], numOfLeftParen: Int): Boolean = {
      if (numOfLeftParen < 0) {
        false
      } else if (chars.isEmpty) {
        numOfLeftParen == 0
      } else if (chars.head == '(') {
        balance(chars.tail, numOfLeftParen + 1)
      } else if (chars.head == ')') {
        balance(chars.tail, numOfLeftParen - 1)
      } else {
        balance(chars.tail, numOfLeftParen)
      }
    }
    balance(chars, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {

    if (money <= 0 || coins.isEmpty) 0
    else {
      val index = (0 to money / coins.head).toList.map(i => money - i * coins.head)
      index.map(x => if (x == 0) 1 else countChange(x, coins.tail)).reduce((a, b) => a + b)
    }
  }
}
