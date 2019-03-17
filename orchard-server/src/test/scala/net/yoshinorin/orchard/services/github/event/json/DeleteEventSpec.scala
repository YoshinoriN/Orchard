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

  test("COnvertJson to CrateEvent case class") {
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

}
