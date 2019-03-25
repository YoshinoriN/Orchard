package net.yoshinorin.orchard.services.github.event.json

import io.circe.Json
import io.circe.parser.parse
import net.yoshinorin.orchard.models.db.PushEvents
import net.yoshinorin.orchard.utils.File
import org.scalatest.FunSuite

// testOnly *PushEventSpec
class PushEventSpec extends FunSuite {

  val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/pushEvent.json")
  val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(json).getOrElse(Json.Null))
  val eventInstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance.repository.get, parse(json).getOrElse(Json.Null))
  val instance = net.yoshinorin.orchard.services.github.event.json.PushEvent(eventInstance.event.get, parse(json).getOrElse(Json.Null))

  test("getConvertedCaseClass should return PushEvents case class") {
    val repositoryCaseClass = Some(PushEvents(0, "YoshinoriN", 158005210, "refs/heads/master", 1, 1552828782))
    assert(instance.getConvertedCaseClass == repositoryCaseClass)
  }

  test("getConvertedCaseClass method should return none") {
    val json = """Not a JSON"""
    assert(PullRequestReviewEvent(eventInstance.event.get, parse(json).getOrElse(Json.Null)).getConvertedCaseClass.isEmpty)
  }

}
