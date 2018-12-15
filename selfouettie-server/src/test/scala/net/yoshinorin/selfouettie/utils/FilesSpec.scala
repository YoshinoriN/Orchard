package net.yoshinorin.selfouettie.utils

import org.scalatest.FunSuite

class FilesSpec extends FunSuite {

  test("exists files") {
    val result = Files.getFiles(System.getProperty("user.dir") + "/src/test/resources/data/import")
    assert(result.size == 1)
  }

  test("file not exists") {
    val result = Files.getFiles(System.getProperty("user.dir") + "/src/test/resources/data/import/example/")
    assert(result.isEmpty)
  }

  test("filter by extension") {
    val result = Files.getFiles(System.getProperty("user.dir") + "/src/test/resources/data/import")
    assert(Files.filterByExtension(result.get, "json").get.size == 2)
  }

}
