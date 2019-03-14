package net.yoshinorin.orchard.definitions.db.manipulation

import org.scalatest.FunSuite

class ManipulationSpec extends FunSuite {

  test("Less than default value") {
    val l: Limit = Limit(49)
    assert(l.limit == 49)
  }

  test("Omit argument") {
    val l: Limit = Limit()
    assert(l.limit == 50)
  }

  test("Argument is one") {
    val l: Limit = Limit(1)

    assert(l.limit == 1)
  }

  test("Less than one") {
    val l: Limit = Limit(0)
    assert(l.limit == 50)
  }

  test("Exceed default value") {
    val l: Limit = Limit(51)
    assert(l.limit == 50)
  }

}
