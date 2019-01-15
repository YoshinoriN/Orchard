package net.yoshinorin.selfouettie.types.db

import org.scalatest.FunSuite

class ManipulationSpec extends FunSuite {

  test("Less than default value") {
    val l: SqlLimit = SqlLimit(49)
    assert(l.limit == 49)
  }

  test("Omit argument") {
    val l: SqlLimit = SqlLimit()
    assert(l.limit == 50)
  }

  test("Argument is one") {
    val l: SqlLimit = SqlLimit(1)

    assert(l.limit == 1)
  }

  test("Less than one") {
    val l: SqlLimit = SqlLimit(0)
    assert(l.limit == 50)
  }

  test("Exceed default value") {
    val l: SqlLimit = SqlLimit(51)
    assert(l.limit == 50)
  }

}
