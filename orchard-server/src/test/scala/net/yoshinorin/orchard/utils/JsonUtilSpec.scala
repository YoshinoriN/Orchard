package net.yoshinorin.orchard.utils.json

import org.scalatest.FunSuite
import net.yoshinorin.orchard.utils.json.Converter.jsonConverter

// testOnly *JsonUtilSpec
class JsonUtilSpec extends FunSuite {

  val json =
    """
      |[
      |  {
      |    "id": 123,
      |    "name": "test"
      |  },
      |  {
      |    "id": 456,
      |    "name": "test2"
      |  },
      |  {
      |    "id": 789,
      |    "name": "test3"
      |  }
      |]
      |
    """.stripMargin

  test("should return JSON List") {
    assert("{\n  \"id\" : 123,\n  \"name\" : \"test\"\n}" == json.toJson.get.head.toString)
    assert("{\n  \"id\" : 789,\n  \"name\" : \"test3\"\n}" == json.toJson.get.last.toString)
  }

  test("toJson should return None") {
    assert("This is not a JSON".toJson.isEmpty)
  }

}
