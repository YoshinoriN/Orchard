package net.yoshinorin.orchard.services.github.event.json

import io.circe.Json
import io.circe.parser.parse
import net.yoshinorin.orchard.models.db.IssueCommentEvents
import net.yoshinorin.orchard.utils.File
import org.scalatest.FunSuite

// testOnly *IssueCommentEventSpec
class IssueCommentEventSpec extends FunSuite {

  val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/issueCommentEvent.json")
  val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(json).getOrElse(Json.Null))
  val eventInstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance.repository.get, parse(json).getOrElse(Json.Null))
  val instance = net.yoshinorin.orchard.services.github.event.json.IssueCommentEvent(eventInstance.event.get, parse(json).getOrElse(Json.Null))

  test("getConvertedCaseClass should return IssueCommentEvent case class") {
    val repositoryCaseClass = Some(IssueCommentEvents(0, "YoshinoriN", 27988627, 2, "created", 1541881164))
    assert(instance.getConvertedCaseClass == repositoryCaseClass)
  }

  test("getConvertedCaseClass method should return none") {
    val json = """Not a JSON"""
    assert(IssueCommentEvent(eventInstance.event.get, parse(json).getOrElse(Json.Null)).getConvertedCaseClass.isEmpty)
  }

  test("insert method is callable") {
    val json = """Not a JSON"""
    assert(IssueCommentEvent(eventInstance.event.get, parse(json).getOrElse(Json.Null)).insert.isInstanceOf[Unit])
  }

}
