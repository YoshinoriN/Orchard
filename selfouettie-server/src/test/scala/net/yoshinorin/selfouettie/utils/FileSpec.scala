package net.yoshinorin.selfouettie.utils

import org.scalatest.FunSuite

class FileSpec extends FunSuite {

  test("get file") {
    val result = File.get(System.getProperty("user.dir") + "/src/test/resources/robots.txt")
    assert(result.get.getAbsoluteFile.getName == "robots.txt")
  }

  test("exists files") {
    val result = File.getFiles(System.getProperty("user.dir") + "/src/test/resources/data/import")
    assert(result.get.size == 3)
  }

  test("file not exists") {
    val result = File.getFiles(System.getProperty("user.dir") + "/src/test/resources/data/import/example/")
    assert(result.isEmpty)
  }

  test("filter by extension") {
    val result = File.getFiles(System.getProperty("user.dir") + "/src/test/resources/data/import")
    assert(File.filterByExtension(result.get, "json").get.size == 2)
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
    assert(File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/import/example.json") == examplejson)
  }
 */

}
