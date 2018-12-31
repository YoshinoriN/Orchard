package net.yoshinorin.selfouettie.models.db

import org.mockito.Mockito._
import org.scalatest.FunSuite

class ForkEventsRepositorySpec extends FunSuite {

  val mockEvent: ForkEvents = mock(classOf[ForkEvents])
  val mockRepository: ForkEventsRepository = mock(classOf[ForkEventsRepository])
  val event: ForkEvents = ForkEvents(1, "YoshinoriN", 987, 12345)

  when(mockRepository.findById(1))
    .thenReturn(Some(event))

  when(mockRepository.findById(2))
    .thenReturn(None)

  test("user found") {
    assert(mockRepository.findById(1) == Some(event))
  }

  test("user not found") {
    assert(mockRepository.findById(2) == None)
  }

}
