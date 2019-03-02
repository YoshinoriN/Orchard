package net.yoshinorin.selfouettie.services

import java.time.ZonedDateTime

import io.circe.Json
import io.circe.parser.parse
import net.yoshinorin.orchard.models.EventObject
import net.yoshinorin.orchard.models.db.{PushEvents, ReleaseEvents, Repositories, WatchEvents}
import net.yoshinorin.orchard.types.EventType
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

  test("ConvertJson to should return EventObject with WatchEvent case class") {
    val result = GitHubEventJsonService.convertToEventObject(File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/watchEvent.json"))
    val eventObject = Some(
      List(EventObject(
        "9023498449".toLong,
        EventType.WatchEvent,
        "YoshinoriN",
        Repositories(94911145, "test/WatchEvent"),
        1549524579,
        Some(WatchEvents("9023498449".toLong, "YoshinoriN", 94911145, "started", 1549524579))
      )))
    assert(result == eventObject)
  }

}
