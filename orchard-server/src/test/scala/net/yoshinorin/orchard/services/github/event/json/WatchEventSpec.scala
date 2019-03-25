package net.yoshinorin.orchard.services.github.event.json

import io.circe.Json
import io.circe.parser.parse
import net.yoshinorin.orchard.models.db.WatchEvents
import net.yoshinorin.orchard.utils.File
import org.scalatest.FunSuite

// testOnly *WatchEventSpec
class WatchEventSpec extends FunSuite {

  val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/watchEvent.json")
  val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(json).getOrElse(Json.Null))
  val eventInstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance.repository.get, parse(json).getOrElse(Json.Null))
  val instance = net.yoshinorin.orchard.services.github.event.json.WatchEvent(eventInstance.event.get, parse(json).getOrElse(Json.Null))

  test("getConvertedCaseClass should return WatchEvents case class") {
    val repositoryCaseClass = Some(WatchEvents(0, "YoshinoriN", 94911145, "started", 1549524579))
    assert(instance.getConvertedCaseClass == repositoryCaseClass)
  }

  test("getConvertedCaseClass method should return none") {
    val json = """Not a JSON"""
    assert(PullRequestReviewEvent(eventInstance.event.get, parse(json).getOrElse(Json.Null)).getConvertedCaseClass.isEmpty)
  }

}
