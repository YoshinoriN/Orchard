package net.yoshinorin.orchard.models.db

import org.mockito.Mockito._
import org.scalatest.FunSuite

class EventsRepositorySpec extends FunSuite {

  val mockEvent: Events = mock(classOf[Events])
  val mockRepository: EventsRepository = mock(classOf[EventsRepository])
  val event: Events = Events(1, "TestEvent", "YoshinoriN", 999, "created", "yoshinorin.example.net", 12345)

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
