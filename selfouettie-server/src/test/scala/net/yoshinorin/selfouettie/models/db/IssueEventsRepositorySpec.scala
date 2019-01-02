package net.yoshinorin.selfouettie.models.db

import org.mockito.Mockito._
import org.scalatest.FunSuite

class IssueEventsRepositorySpec extends FunSuite {

  val mockEvent: IssueEvents = mock(classOf[IssueEvents])
  val mockRepository: IssuesEventsRepository = mock(classOf[IssuesEventsRepository])
  val event: IssueEvents = IssueEvents(1, "YoshinoriN", 987, 1234, "created", 98765)

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
