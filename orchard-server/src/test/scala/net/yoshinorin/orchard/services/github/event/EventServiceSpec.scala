package net.yoshinorin.orchard.services.github.event

import io.circe.Json
import io.circe.parser.parse
import net.yoshinorin.orchard.services.github.event.json._
import net.yoshinorin.orchard.utils.File
import org.scalatest.FunSuite

// testOnly *EventServiceSpec
class EventServiceSpec extends FunSuite {

  test("createEventInstance should return each CreateEvent instance") {
    val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/createEvent.json")
    val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(json).getOrElse(Json.Null))
    val eventInstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance.repository.get, parse(json).getOrElse(Json.Null))
    assert(EventService.createEventInstance(eventInstance.event.get, parse(json).getOrElse(Json.Null)).get.isInstanceOf[CreateEvent])
  }

  test("createEventInstance should return each DeleteEvent instance") {
    val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/deleteEvent.json")
    val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(json).getOrElse(Json.Null))
    val eventInstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance.repository.get, parse(json).getOrElse(Json.Null))
    assert(EventService.createEventInstance(eventInstance.event.get, parse(json).getOrElse(Json.Null)).get.isInstanceOf[DeleteEvent])
  }

  test("createEventInstance should return each IssueCommentEvent instance") {
    val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/issueCommentEvent.json")
    val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(json).getOrElse(Json.Null))
    val eventInstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance.repository.get, parse(json).getOrElse(Json.Null))
    assert(EventService.createEventInstance(eventInstance.event.get, parse(json).getOrElse(Json.Null)).get.isInstanceOf[IssueCommentEvent])
  }

  test("createEventInstance should return each IssuesEvent instance") {
    val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/issuesEvent.json")
    val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(json).getOrElse(Json.Null))
    val eventInstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance.repository.get, parse(json).getOrElse(Json.Null))
    assert(EventService.createEventInstance(eventInstance.event.get, parse(json).getOrElse(Json.Null)).get.isInstanceOf[IssuesEvent])
  }

  test("createEventInstance should return each PullRequestEvent instance") {
    val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/pullRequestEvent.json")
    val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(json).getOrElse(Json.Null))
    val eventInstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance.repository.get, parse(json).getOrElse(Json.Null))
    assert(EventService.createEventInstance(eventInstance.event.get, parse(json).getOrElse(Json.Null)).get.isInstanceOf[PullRequestEvent])
  }

  test("createEventInstance should return each PullRequestReviewEvent instance") {
    val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/pullRequestReviewEvent.json")
    val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(json).getOrElse(Json.Null))
    val eventInstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance.repository.get, parse(json).getOrElse(Json.Null))
    println(EventService.createEventInstance(eventInstance.event.get, parse(json).getOrElse(Json.Null)))
    assert(EventService.createEventInstance(eventInstance.event.get, parse(json).getOrElse(Json.Null)).get.isInstanceOf[PullRequestReviewEvent])
  }

  test("createEventInstance should return each PullRequestReviewCommentEvent instance") {
    val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/pullRequestReviewCommentEvent.json")
    val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(json).getOrElse(Json.Null))
    val eventInstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance.repository.get, parse(json).getOrElse(Json.Null))
    assert(EventService.createEventInstance(eventInstance.event.get, parse(json).getOrElse(Json.Null)).get.isInstanceOf[PullRequestReviewCommentEvent])
  }

  test("createEventInstance should return each PushEvent instance") {
    val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/pushEvent.json")
    val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(json).getOrElse(Json.Null))
    val eventInstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance.repository.get, parse(json).getOrElse(Json.Null))
    assert(EventService.createEventInstance(eventInstance.event.get, parse(json).getOrElse(Json.Null)).get.isInstanceOf[PushEvent])
  }

  test("createEventInstance should return each ReleaseEvent instance") {
    val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/releaseEvent.json")
    val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(json).getOrElse(Json.Null))
    val eventInstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance.repository.get, parse(json).getOrElse(Json.Null))
    assert(EventService.createEventInstance(eventInstance.event.get, parse(json).getOrElse(Json.Null)).get.isInstanceOf[ReleaseEvent])
  }

  test("createEventInstance should return each WatchEvent instance") {
    val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/watchEvent.json")
    val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(json).getOrElse(Json.Null))
    val eventInstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance.repository.get, parse(json).getOrElse(Json.Null))
    assert(EventService.createEventInstance(eventInstance.event.get, parse(json).getOrElse(Json.Null)).get.isInstanceOf[WatchEvent])
  }

}
