package net.yoshinorin.orchard.services.github.event.json

import io.circe.Json
import io.circe.parser.parse
import net.yoshinorin.orchard.models.db.PullRequests
import net.yoshinorin.orchard.utils.File
import org.scalatest.FunSuite

// testOnly *PullRequestSpec
class PullRequestSpec extends FunSuite {

  val repositoryJson = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/repository.json")
  val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(repositoryJson).getOrElse(Json.Null))

  val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/pullRequest.json")
  val instance = net.yoshinorin.orchard.services.github.event.json.PullRequest(repositoryInstance.repository.get, parse(json).getOrElse(Json.Null))

  test("getConvertedCaseClass should return PullRequests case class") {
    val repositoryCaseClass = Some(PullRequests(94911145, 22, "Test pull request", true))
    assert(instance.getConvertedCaseClass == repositoryCaseClass)
  }

}
