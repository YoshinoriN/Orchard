package net.yoshinorin.orchard.services.github.event.json

import net.yoshinorin.orchard.models.db.PullRequests
import net.yoshinorin.orchard.utils.File
import org.scalatest.FunSuite

// testOnly *PullRequestSpec
class PullRequestSpec extends FunSuite {

  val repositoryJson = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/repository.json")
  val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(repositoryJson)

  val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/pullRequest.json")
  val instance = net.yoshinorin.orchard.services.github.event.json.PullRequest(repositoryInstance, json)

  test("ConvertJson to Issues case class") {
    val repositoryCaseClass = Some(PullRequests(94911145, 22, "Test pull request", true))
    assert(instance.getConvertedCaseClass == repositoryCaseClass)
  }

}
