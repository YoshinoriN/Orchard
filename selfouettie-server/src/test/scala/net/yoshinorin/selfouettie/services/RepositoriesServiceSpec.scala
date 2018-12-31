package net.yoshinorin.selfouettie.services

import net.yoshinorin.selfouettie.models.Repositories
import org.mockito.Mockito._
import org.scalatest.FunSuite

class RepositoriesServiceSpec extends FunSuite {

  val mockRepositories: Repositories = mock(classOf[Repositories])
  val mockRepositoriesService: RepositoriesService = mock(classOf[RepositoriesService])
  val repo: Repositories = Repositories(1, "test")

  when(mockRepositoriesService.findById(1))
    .thenReturn(Some(repo))

  when(mockRepositoriesService.findById(2))
    .thenReturn(None)

  test("user found") {
    assert(mockRepositoriesService.findById(1) == Some(repo))
  }

  test("user not found") {
    assert(mockRepositoriesService.findById(2) == None)
  }

}
