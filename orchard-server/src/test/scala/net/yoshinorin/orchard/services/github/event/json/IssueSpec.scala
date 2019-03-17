package net.yoshinorin.orchard.services.github.event.json

import io.circe.Json
import io.circe.parser.parse
import net.yoshinorin.orchard.models.db.Issues
import net.yoshinorin.orchard.utils.File
import org.scalatest.FunSuite

// testOnly *IssueSpec
class IssueSpec extends FunSuite {

  val repositoryJson = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/repository.json")
  val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(repositoryJson).getOrElse(Json.Null))

  val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/issue.json")
  val instance = net.yoshinorin.orchard.services.github.event.json.Issue(repositoryInstance.repository.get, parse(json).getOrElse(Json.Null))

  test("getConvertedCaseClass should return Issues case class") {
    val repositoryCaseClass = Some(Issues(94911145, 9999, "test issue"))
    assert(instance.getConvertedCaseClass == repositoryCaseClass)
  }

  test("getConvertedCaseClass method should return none") {
    val json = """Not a JSON"""
    assert(Issue(repositoryInstance.repository.get, parse(json).getOrElse(Json.Null)).getConvertedCaseClass.isEmpty)
  }

}
