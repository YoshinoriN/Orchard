package net.yoshinorin.orchard.services.github.event.json

import net.yoshinorin.orchard.models.db.Issues
import net.yoshinorin.orchard.utils.File
import org.scalatest.FunSuite

// testOnly *IssueSpec
class IssueSpec extends FunSuite {

  val repositoryJson = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/repository.json")
  val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(repositoryJson)

  val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/issue.json")
  val instance = net.yoshinorin.orchard.services.github.event.json.Issue(repositoryInstance, json)

  test("ConvertJson to Repositories case class") {
    val repositoryCaseClass = Some(Issues(94911145, 9999, "test issue"))
    assert(instance.getConvertedCaseClass == repositoryCaseClass)
  }

}
