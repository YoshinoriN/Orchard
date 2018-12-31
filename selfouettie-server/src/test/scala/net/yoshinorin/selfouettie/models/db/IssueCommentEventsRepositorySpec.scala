package net.yoshinorin.selfouettie.models.db

import org.mockito.Mockito._
import org.scalatest.FunSuite

class IssueCommentEventsRepositorySpec extends FunSuite {

  val mockEvent: IssueCommentEvents = mock(classOf[IssueCommentEvents])
  val mockRepository: IssueCommentEventsRepository = mock(classOf[IssueCommentEventsRepository])
  val event: IssueCommentEvents = IssueCommentEvents(1, "YoshinoriN", 987, 1234, "created", 98765)

  when(mockRepository.findById(1))
    .thenReturn(Some(event))

  when(mockRepository.findById(2))
    .thenReturn(None)

  test("found") {
    assert(mockRepository.findById(1) == Some(event))
  }

  test("not found") {
    assert(mockRepository.findById(2) == None)
  }

}
