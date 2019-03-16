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
  val eventEnstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance, parse(issueJson).getOrElse(Json.Null))

  val instance = net.yoshinorin.orchard.services.github.event.json.ForkEvent(eventEnstance.event.get)

  test("COnvertJson to CrateEvent case class") {
    val forkEventCaseClass = Some(
      ForkEvents(
        eventEnstance.event.get.id,
        eventEnstance.event.get.userName,
        eventEnstance.event.get.repositoryId,
        eventEnstance.event.get.createdAt
      )
    )
    assert(instance.getConvertedCaseClass == forkEventCaseClass)
  }

}
