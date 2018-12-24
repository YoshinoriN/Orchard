package net.yoshinorin.selfouettie.utils

import org.scalatest.FunSuite
import net.yoshinorin.selfouettie.types.EventType
import net.yoshinorin.selfouettie.utils.Converter.eventTypeConverter

class ConverterSpec extends FunSuite {

  test("to CreateEvent") {
    assert("CreateEvent".toEventType == EventType.CreateEvent)
  }

  test("to ForkEvent") {
    assert("ForkEvent".toEventType == EventType.ForkEvent)
  }

  test("to IssueCommentEvent") {
    assert("IssueCommentEvent".toEventType == EventType.IssueCommentEvent)
  }

  test("to IssuesEvent") {
    assert("IssuesEvent".toEventType == EventType.IssuesEvent)
  }

  test("to PullRequestEvent") {
    assert("PullRequestEvent".toEventType == EventType.PullRequestEvent)
  }

  test("to PullRequestReviewEvent") {
    assert("PullRequestReviewEvent".toEventType == EventType.PullRequestReviewEvent)
  }

  test("to PullRequestReviewCommentEvent") {
    assert("PullRequestReviewCommentEvent".toEventType == EventType.PullRequestReviewCommentEvent)
  }

  test("to PushEvent") {
    assert("PushEvent".toEventType == EventType.PushEvent)
  }

  test("to ReleaseEvent") {
    assert("ReleaseEvent".toEventType == EventType.ReleaseEvent)
  }

  test("to WatchEvent") {
    assert("WatchEvent".toEventType == EventType.WatchEvent)
  }

}
