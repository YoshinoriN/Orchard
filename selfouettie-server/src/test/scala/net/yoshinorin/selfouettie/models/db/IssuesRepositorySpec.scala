package net.yoshinorin.selfouettie.models.db

import org.mockito.Mockito._
import org.scalatest.FunSuite

class IssuesRepositorySpec extends FunSuite {

  val mockEvent: Issues = mock(classOf[Issues])
  val mockRepository: IssuesRepository = mock(classOf[IssuesRepository])
  val event: Issues = Issues(1, 20, "Test issue")

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
