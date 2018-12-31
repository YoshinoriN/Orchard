package net.yoshinorin.selfouettie.models.db

import org.mockito.Mockito._
import org.scalatest.FunSuite

class CreateEventsRepositorySpec extends FunSuite {

  val mockEvent: CreateEvents = mock(classOf[CreateEvents])
  val mockRepository: CreateEventsRepository = mock(classOf[CreateEventsRepository])
  val event: CreateEvents = CreateEvents(1, "YoshinoriN", "develop-branch", "branch", 12345)

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
