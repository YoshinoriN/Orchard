package net.yoshinorin.orchard.services.github.event.json

import io.circe.Json
import io.circe.parser.parse
import net.yoshinorin.orchard.models.db.IssueEvents
import net.yoshinorin.orchard.utils.File
import org.scalatest.FunSuite

// testOnly *IssuesEventSpec
class IssuesEventSpec extends FunSuite {

  val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/issuesEvent.json")
  val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(json).getOrElse(Json.Null))
  val eventInstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance.repository.get, parse(json).getOrElse(Json.Null))
  val instance = net.yoshinorin.orchard.services.github.event.json.IssuesEvent(eventInstance.event.get, parse(json).getOrElse(Json.Null))

  test("getConvertedCaseClass should return IssueEvents case class") {
    val repositoryCaseClass = Some(IssueEvents(0, "YoshinoriN", 15822976, 808, "opened", 1542189028))
    assert(instance.getConvertedCaseClass == repositoryCaseClass)
  }

  test("getConvertedCaseClass method should return none") {
    val json = """Not a JSON"""
    assert(IssuesEvent(eventInstance.event.get, parse(json).getOrElse(Json.Null)).getConvertedCaseClass.isEmpty)
  }

}
