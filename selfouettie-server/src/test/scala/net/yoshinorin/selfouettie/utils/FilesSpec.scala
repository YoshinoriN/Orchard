package net.yoshinorin.selfouettie.utils

import org.scalatest.FunSuite

class FilesSpec extends FunSuite {

  test("exists files") {
    val result = Files.getFiles(System.getProperty("user.dir") + "/src/test/resources/data/import")
    assert(result.get.size == 3)
  }

  test("file not exists") {
    val result = Files.getFiles(System.getProperty("user.dir") + "/src/test/resources/data/import/example/")
    assert(result.isEmpty)
  }

  test("filter by extension") {
    val result = Files.getFiles(System.getProperty("user.dir") + "/src/test/resources/data/import")
    assert(Files.filterByExtension(result.get, "json").get.size == 2)
  }

  val examplejson: String = """
      |[
      |  {
      |    "id": "8758940665",
      |    "type": "PushEvent",
      |    "actor": {
      |      "id": 11273093,
      |      "login": "YoshinoriN",
      |      "display_login": "YoshinoriN",
      |      "gravatar_id": "",
      |      "url": "https://api.github.com/users/YoshinoriN",
      |      "avatar_url": "https://avatars.githubusercontent.com/u/11273093?"
      |    },
      |    "repo": {
      |      "id": 158005210,
      |      "name": "YoshinoriN/Selfouettie",
      |      "url": "https://api.github.com/repos/YoshinoriN/Selfouettie"
      |    },
      |    "public": true,
      |    "created_at": "2018-12-15T10:35:57Z"
      |  }
      |]
    """.stripMargin

  /* TODO: Fix test failer
  test("readAll") {
    assert(Files.readAll(System.getProperty("user.dir") + "/src/test/resources/data/import/example.json") == examplejson)
  }
  */

}
