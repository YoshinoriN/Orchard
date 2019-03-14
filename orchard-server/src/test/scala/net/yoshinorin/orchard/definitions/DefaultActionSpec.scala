package net.yoshinorin.orchard.definitions.DefaultAction

import net.yoshinorin.orchard.definitions.action._
import org.scalatest.FunSuite

// testOnly *DefaultActionSpec
class DefaultActionSpec extends FunSuite {

  test("get method should return ActionType.Created if eventType is (String)CreateEvent") {
    assert(ActionType.Created == DefaultAction.get("CreateEvent"))
  }

  test("get method should return ActionType.Created if eventType is (String)DeleteEvent") {
    assert(ActionType.Created == DefaultAction.get("DeleteEvent"))
  }

  test("get method should return ActionType.Fork if eventType is (String)ForkEvent") {
    assert(ActionType.Fork == DefaultAction.get("ForkEvent"))
  }

  test("get method should return ActionType.Commented if eventType is (String)IssueCommentEvent") {
    assert(ActionType.Commented == DefaultAction.get("IssueCommentEvent"))
  }

  test("get method should return ActionType.Created if eventType is (String)IssuesEvent") {
    assert(ActionType.Created == DefaultAction.get("IssuesEvent"))
  }

  test("get method should return ActionType.Created if eventType is (String)PullRequestEvent") {
    assert(ActionType.Created == DefaultAction.get("PullRequestEvent"))
  }

  test("get method should return ActionType.Created if eventType is (String)PullRequestReviewEvent") {
    assert(ActionType.Created == DefaultAction.get("PullRequestReviewEvent"))
  }

  test("get method should return ActionType.Created if eventType is (String)PullRequestReviewCommentEvent") {
    assert(ActionType.Created == DefaultAction.get("PullRequestReviewCommentEvent"))
  }

  test("get method should return ActionType.Created if eventType is (String)PushEvent") {
    assert(ActionType.Created == DefaultAction.get("PushEvent"))
  }

  test("get method should return ActionType.Created if eventType is (String)ReleaseEvent") {
    assert(ActionType.Created == DefaultAction.get("ReleaseEvent"))
  }

  test("get method should return ActionType.Created if eventType is (String)WatchEvent") {
    assert(ActionType.Created == DefaultAction.get("WatchEvent"))
  }

}
