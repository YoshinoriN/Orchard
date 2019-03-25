package net.yoshinorin.orchard.services.github.event.json

import io.circe.Json
import io.circe.parser.parse
import net.yoshinorin.orchard.models.db.PullRequestReviewCommentEvents
import net.yoshinorin.orchard.utils.File
import org.scalatest.FunSuite

// testOnly *PullRequestReviewCommentEventSpec
class PullRequestReviewCommentEventSpec extends FunSuite {

  val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/pullRequestReviewCommentEvent.json")
  val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(json).getOrElse(Json.Null))
  val eventInstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance.repository.get, parse(json).getOrElse(Json.Null))
  val instance = net.yoshinorin.orchard.services.github.event.json.PullRequestReviewCommentEvent(eventInstance.event.get, parse(json).getOrElse(Json.Null))

  test("getConvertedCaseClass should return PullRequestReviewCommentEvents case class") {
    val repositoryCaseClass = Some(PullRequestReviewCommentEvents(0, "YoshinoriN", 15822976, 807, "created", 1542188358))
    assert(instance.getConvertedCaseClass == repositoryCaseClass)
  }

  test("getConvertedCaseClass method should return none") {
    val json = """Not a JSON"""
    assert(PullRequestReviewCommentEvent(eventInstance.event.get, parse(json).getOrElse(Json.Null)).getConvertedCaseClass.isEmpty)
  }

}
