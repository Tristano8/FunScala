package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val copys4 = singletonSet(4)
    val setPositiveNum = union(singletonSet(5), singletonSet(950))
    val setPositiveBounds = union(singletonSet(1),singletonSet(1000))
    val setNegativeNum = union(singletonSet(-5), singletonSet(-950))
    val setNegativeBounds = union(singletonSet(-1000),singletonSet(-1))
    val setPositiveAndNegative = union(setPositiveNum, setNegativeNum)
    val setOddNum = union(singletonSet(5),singletonSet(121))
    val setEvenNum = union(singletonSet(0), singletonSet(-20))
    val setOddAndEven = union(setOddNum, setEvenNum)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union contains 1")
      assert(contains(s, 2), "Union contains 2")
      assert(!contains(s, 3), "Union doesn't contain 3")
    }
  }

  test("intersect contains elemints in both sets") {
    new TestSets {
      val s = intersect(s1, s2)
      val t = intersect(s4, copys4)
      assert(!contains(s, 1), "Intersect of s does not contain 1")
      assert(!contains(s, 2), "Intersect of s does not contain 2")
      assert(contains(t, 4), "Intersect of t contains 4")
      assert(!contains(t, 2), "Intersect of t does not contain 2")
    }
  }

  test("diff contains elements in set a not in b") {
    new TestSets {
      val s = diff(s1, s2)
      val t = diff(s4, copys4)
      assert(contains(s, 1), "Diff contains 1")
      assert(!contains(s, 2), "Diff contains 2")
      assert(!contains(t, 4), "Diff contains 3")
    }
  }

  test("filter returns the subset of a set for which a parameter function holds") {
    new TestSets {
      val s = filter(s1, (x: Int) => x != 1)
      val t = filter(s4, (x: Int) => x % 2 == 0)
      assert(!contains(s, 1), "Filter of s does not contain 1")
      assert(contains(t, 4), "Filter of t contains 4")
    }
  }

  test("forall returns true if condition is true for all elements of set") {
    new TestSets {

      assert(forall(setPositiveNum, (x: Int) => x > 0))
      assert(!forall(setPositiveNum, (x: Int) => x % 2 == 0))

      assert(forall(setNegativeNum, (x: Int) => x < 0))
      assert(!forall(setNegativeNum, (x: Int) => x == -5))

      assert(forall(setPositiveBounds, (x: Int) => x > 0 && x <= bound))
      assert(!forall(setPositiveBounds, (x: Int) => x == bound))

      assert(forall(setNegativeBounds, (x: Int) => x >= -bound && x < 0))
      assert(!forall(setNegativeBounds, (x: Int) => x == -bound))

      assert(forall(setEvenNum, (x: Int) => x % 2 == 0))

      assert(forall(setOddNum, (x: Int) => x % 2 != 0))

      assert(!forall(setOddAndEven, (x: Int) => x % 2 == 0))
      assert(!forall(setOddAndEven, (x: Int) => x % 2 != 0))

    }
  }

  test("exists returns true if condition is true for any element in set") {
    new TestSets {

      assert(exists(s1, (x: Int) => x == 1))

      assert(exists(setPositiveNum, (x: Int) => x % 2 == 0))
      assert(!exists(setPositiveNum, (x: Int) => x < 0))

      assert(exists(setNegativeNum, (x: Int) => x % 2 == 0))
      assert(!exists(setNegativeNum, (x: Int) => x > 0))

      assert(exists(setNegativeBounds, (x: Int) => x == -bound))

      assert(exists(setPositiveBounds, (x: Int) => x == bound))

      assert(!exists(setOddNum, (x: Int) => x % 2 == 0))
      assert(!exists(setEvenNum, (x: Int) => x % 2 != 0))

      assert(exists(setOddAndEven, (x: Int) => x % 2 == 0))
    }
  }

  test("map transforms a set by applying a function to each member") {
    new TestSets {

      val mapSet1 = map(s2, (x: Int) => x + x)
      assert(contains(mapSet1, 4))

      val mapSet2 = map(setOddNum, (x: Int) => x + 1)
      assert(contains(mapSet2, 122))
      assert(exists(mapSet2, (x: Int) => x % 2 == 0))

      val mapSet3 = map(setEvenNum, (x: Int) => x + 1)
      assert(contains(mapSet3, 1))
      assert(!exists(mapSet3, (x: Int) => x % 2 == 0))

      val mapSet4 = map(s4, (x: Int) => x * 10)
      assert(contains(mapSet4, 40))

    }
  }

}
