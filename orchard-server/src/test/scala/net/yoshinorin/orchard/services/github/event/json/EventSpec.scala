package net.yoshinorin.orchard.services.github.event.json

import net.yoshinorin.orchard.definitions.action.ActionType
import net.yoshinorin.orchard.models.db.{Events, Repositories}
import net.yoshinorin.orchard.utils.File
import org.scalatest.FunSuite

// testOnly *EventSpec
class EventSpec extends FunSuite {

  val repositoryJson = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/repository.json")
  val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(repositoryJson)

  val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/issue.json")
  val instance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance, json)

  test("ConvertJson to Events case class") {
    val eventsCaseClass = Some(
      Events(
        1234567890.toLong,
        "IssuesEvent",
        "YoshinoriN",
        repositoryInstance.repository.get.id,
        ActionType.Created.toString,
        repositoryInstance.repository.get.name,
        1543926608
      )
    )
    assert(instance.getConvertedCaseClass == eventsCaseClass)
  }

}
