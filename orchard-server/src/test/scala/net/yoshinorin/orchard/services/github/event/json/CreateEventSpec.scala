package net.yoshinorin.orchard.services.github.event.json

import io.circe.Json
import io.circe.parser.parse
import net.yoshinorin.orchard.models.db.{CreateEvents, Events, Repositories}
import net.yoshinorin.orchard.utils.File
import org.scalatest.FunSuite

// testOnly *CreateEventSpec
class CreateEventSpec extends FunSuite {

  val repositoryJson = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/repository.json")
  val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(repositoryJson).getOrElse(Json.Null))

  val issueJson = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/issue.json")
  val eventInstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance.repository.get, parse(issueJson).getOrElse(Json.Null))

  val createEventJson = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/createEvent.json")
  val instance = net.yoshinorin.orchard.services.github.event.json.CreateEvent(eventInstance.event.get, parse(createEventJson).getOrElse(Json.Null))

  test("getConvertedCaseClass should return converted CrateEvents case class") {
    val createEventCaseClass = Some(
      CreateEvents(
        eventInstance.event.get.id,
        eventInstance.event.get.userName,
        "tag",
        "test-tag",
        eventInstance.event.get.createdAt
      )
    )
    assert(instance.getConvertedCaseClass == createEventCaseClass)
  }

  test("getConvertedCaseClass method should return none") {
    val json = """Not a JSON"""
    assert(CreateEvent(eventInstance.event.get, parse(json).getOrElse(Json.Null)).getConvertedCaseClass.isEmpty)
  }

  test("insert method is callable") {
    val json = """Not a JSON"""
    assert(CreateEvent(eventInstance.event.get, parse(json).getOrElse(Json.Null)).insert.isInstanceOf[Unit])
  }

}
