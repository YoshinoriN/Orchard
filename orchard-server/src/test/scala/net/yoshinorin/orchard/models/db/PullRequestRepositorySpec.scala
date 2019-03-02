package net.yoshinorin.orchard.models.db

import org.mockito.Mockito._
import org.scalatest.FunSuite

class PullRequestRepositorySpec extends FunSuite {

  val mockEvent: PullRequests = mock(classOf[PullRequests])
  val mockRepository: PullRequestsRepository = mock(classOf[PullRequestsRepository])
  val event: PullRequests = PullRequests(1, 20, "Test issue", true)

  when(mockRepository.find(1, 20))
    .thenReturn(Some(event))

  when(mockRepository.find(2, 20))
    .thenReturn(None)

  test("found") {
    assert(mockRepository.find(1, 20) == Some(event))
  }

  test("not found") {
    assert(mockRepository.find(2, 20) == None)
  }

}
