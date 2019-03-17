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
  val eventEnstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance.repository.get, parse(issueJson).getOrElse(Json.Null))

  val deleteEventJson = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/deleteEvent.json")
  val instance = net.yoshinorin.orchard.services.github.event.json.DeleteEvent(eventEnstance.event.get, parse(deleteEventJson).getOrElse(Json.Null))

  test("getConvertedCaseClass should return DeleteEvents case class") {
    val deleteEventCaseClass = Some(
      DeleteEvents(
        eventEnstance.event.get.id,
        eventEnstance.event.get.userName,
        "branch",
        "YoshinoriN/test-delete",
        eventEnstance.event.get.createdAt
      )
    )
    assert(instance.getConvertedCaseClass == deleteEventCaseClass)
  }

  test("getConvertedCaseClass method should return None") {
    val json = """Not a JSON"""
    assert(DeleteEvent(eventEnstance.event.get, parse(json).getOrElse(Json.Null)).getConvertedCaseClass.isEmpty)
  }

  test("Should callable insert method") {
    val json = """Not a JSON"""
    assert(DeleteEvent(eventEnstance.event.get, parse(json).getOrElse(Json.Null)).insert.isInstanceOf[Unit])
  }

}
