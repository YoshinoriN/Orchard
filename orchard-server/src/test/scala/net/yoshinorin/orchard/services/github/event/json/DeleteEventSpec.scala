package net.yoshinorin.orchard.services.github.event.json

import io.circe.Json
import io.circe.parser.parse
import net.yoshinorin.orchard.models.db.DeleteEvents
import net.yoshinorin.orchard.utils.File
import org.scalatest.FunSuite

// testOnly *DeleteEventSpec
class DeleteEventSpec extends FunSuite {

  val repositoryJson = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/repository.json")
  val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(repositoryJson).getOrElse(Json.Null))

  val issueJson = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/issue.json")
  val eventInstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance.repository.get, parse(issueJson).getOrElse(Json.Null))

  val deleteEventJson = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/deleteEvent.json")
  val instance = net.yoshinorin.orchard.services.github.event.json.DeleteEvent(eventInstance.event.get, parse(deleteEventJson).getOrElse(Json.Null))

  test("getConvertedCaseClass should return DeleteEvents case class") {
    val deleteEventCaseClass = Some(
      DeleteEvents(
        eventInstance.event.get.id,
        eventInstance.event.get.userName,
        "branch",
        "YoshinoriN/test-delete",
        eventInstance.event.get.createdAt
      )
    )
    assert(instance.getConvertedCaseClass == deleteEventCaseClass)
  }

  test("getConvertedCaseClass method should return None") {
    val json = """Not a JSON"""
    assert(DeleteEvent(eventInstance.event.get, parse(json).getOrElse(Json.Null)).getConvertedCaseClass.isEmpty)
  }

  test("insert method is callable") {
    val json = """Not a JSON"""
    assert(DeleteEvent(eventInstance.event.get, parse(json).getOrElse(Json.Null)).insert.isInstanceOf[Unit])
  }

}
