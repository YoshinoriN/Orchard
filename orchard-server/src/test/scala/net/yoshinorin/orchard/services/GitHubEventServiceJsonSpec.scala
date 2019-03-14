package net.yoshinorin.selfouettie.services

import java.time.ZonedDateTime
import net.yoshinorin.orchard.models.{DummyEvent, EventObject}
import net.yoshinorin.orchard.models.db._
import net.yoshinorin.orchard.definitions.event.EventType
import net.yoshinorin.orchard.utils.File
import net.yoshinorin.orchard.services.GitHubEventJsonService
import org.scalatest.FunSuite
//import org.scalatest.{FunSuite, PrivateMethodTester}

// testOnly *GitHubEventServiceJsonSpec
//class GitHubEventServiceJsonSpec extends FunSuite with PrivateMethodTester {
class GitHubEventServiceJsonSpec extends FunSuite {

  /* How to test private method at object???
  test("generateWatchEvent should return case class") {
    val jsonFile = File.get(System.getProperty("user.dir") + "/src/test/resources/data/json/watchEvent.json")
    val json = parse(jsonFile.toString).getOrElse(Json.Null)
    val generateWatchEventMethod = PrivateMethod[Option[WatchEvents]]('generateWatchEvent)
    val result = new GitHubEventJsonService.invokePrivate(generateWatchEventMethod(1, "YoshinoriN", 123, 123, json))
  }
   */

  /* TODO
  test("ConvertJson to should return EventObject with PushEvent case class") {
    val result = GitHubEventJsonService.convertToEventObject(File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/pushEvent.json"))
    val eventObject = List(
      EventObject(
        "9123030144".toLong,
        EventType.PushEvent,
        "YoshinoriN",
        Repositories(158005210, "YoshinoriN/Selfouettie"),
        1550930494,
        Some(PushEvents("9123030144".toLong, "YoshinoriN", 158005210, "refs/heads/master", 1, 1550930494))
      ))
    assert(result == eventObject)
  }

  test("ConvertJson to should return EventObject with ReleaseEvent case class") {
    val result = GitHubEventJsonService.convertToEventObject(File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/releaseEvent.json"))
    val eventObject = List(
      EventObject(
        "8857019681".toLong,
        EventType.ReleaseEvent,
        "YoshinoriN",
        Repositories(164438991, "YoshinoriN/hexo-tag-config"),
        1547033831,
        Some(ReleaseEvents("8857019681".toLong, "YoshinoriN", 164438991, "v1.0.0", "v1.0.0", "published", 1547033831))
      ))
    assert(result == eventObject)
  }
   */

  test("ConvertJson to should return None if json have not event id") {
    val result = GitHubEventJsonService.convertToEventObject(File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/noneEventId.json"))
    assert(result.isEmpty)
  }

  test("ConvertJson to should return None if json have not event type") {
    val result = GitHubEventJsonService.convertToEventObject(File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/noneEventType.json"))
    assert(result.isEmpty)
  }

  test("ConvertJson to should return None if json have not userName") {
    val result = GitHubEventJsonService.convertToEventObject(File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/noneUserName.json"))
    assert(result.isEmpty)
  }

  test("ConvertJson to should return None if json have not createdAt") {
    val result = GitHubEventJsonService.convertToEventObject(File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/noneCreatedAt.json"))
    assert(result.isEmpty)
  }

  test("ConvertJson to should return EventObject with CreateEvent case class") {
    val result = GitHubEventJsonService.convertToEventObject(File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/createEvent.json"))
    val eventObject = Some(
      List(EventObject(
        "9876543".toLong,
        EventType.CreateEvent,
        "YoshinoriN",
        Repositories("9999999999".toLong, "YoshinoriN/testCreateEvent"),
        None,
        None,
        1549524579,
        Some(CreateEvents("9876543".toLong, "YoshinoriN", "tag", "test-tag", 1549524579))
      )))
    assert(result == eventObject)
  }

  test("ConvertJson to should return EventObject with DeleteEvent case class") {
    val result = GitHubEventJsonService.convertToEventObject(File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/deleteEvent.json"))
    val eventObject = Some(
      List(EventObject(
        "99876543".toLong,
        EventType.DeleteEvent,
        "YoshinoriN",
        Repositories("9999999998".toLong, "YoshinoriN/testDeleteEvent"),
        None,
        None,
        1549524579,
        Some(DeleteEvents("99876543".toLong, "YoshinoriN", "branch", "YoshinoriN/test-delete", 1549524579))
      )))
    assert(result == eventObject)
  }

  test("ConvertJson to should return EventObject with WatchEvent case class") {
    val result = GitHubEventJsonService.convertToEventObject(File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/watchEvent.json"))
    val eventObject = Some(
      List(EventObject(
        "9023498449".toLong,
        EventType.WatchEvent,
        "YoshinoriN",
        Repositories(94911145, "test/WatchEvent"),
        None,
        None,
        1549524579,
        Some(WatchEvents("9023498449".toLong, "YoshinoriN", 94911145, "started", 1549524579))
      )))
    assert(result == eventObject)
  }

  test("ConvertJson to should return EventObject for undefined") {
    val result = GitHubEventJsonService.convertToEventObject(File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/dummyEvent.json"))
    val eventObject = Some(
      List(
        EventObject(
          "9023498449".toLong,
          EventType.Undefined,
          "YoshinoriN",
          Repositories(94911145, "test/WatchEvent"),
          None,
          None,
          1549524579,
          Some(DummyEvent())
        )))
    assert(result == eventObject)
  }

}
