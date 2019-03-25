package net.yoshinorin.orchard.services.github.event.json

import io.circe.Json
import io.circe.parser.parse
import net.yoshinorin.orchard.models.db.ForkEvents
import net.yoshinorin.orchard.utils.File
import org.scalatest.FunSuite

// testOnly *ForkEventSpec
class ForkEventSpec extends FunSuite {

  val repositoryJson = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/repository.json")
  val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(repositoryJson).getOrElse(Json.Null))

  val issueJson = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/issue.json")
  val eventInstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance.repository.get, parse(issueJson).getOrElse(Json.Null))

  val instance = net.yoshinorin.orchard.services.github.event.json.ForkEvent(eventInstance.event.get)

  test("getConvertedCaseClass should return ForkEvents case class") {
    val forkEventCaseClass = Some(
      ForkEvents(
        eventInstance.event.get.id,
        eventInstance.event.get.userName,
        eventInstance.event.get.repositoryId,
        eventInstance.event.get.createdAt
      )
    )
    assert(instance.getConvertedCaseClass == forkEventCaseClass)
  }

  test("getConvertedCaseClass method should return none") {
    val json = """Not a JSON"""
    assert(Event(repositoryInstance.repository.get, parse(json).getOrElse(Json.Null)).getConvertedCaseClass.isEmpty)
  }

}
