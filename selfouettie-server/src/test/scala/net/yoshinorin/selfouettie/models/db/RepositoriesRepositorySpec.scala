package net.yoshinorin.selfouettie.models.db

import org.mockito.Mockito._
import org.scalatest.FunSuite

class RepositoriesRepositorySpec extends FunSuite {

  val mockRepositories: Repositories = mock(classOf[Repositories])
  val mockRepositoriesService: RepositoriesRepository = mock(classOf[RepositoriesRepository])
  val repo: Repositories = Repositories(1, "test")

  when(mockRepositoriesService.findById(1))
    .thenReturn(Some(repo))

  when(mockRepositoriesService.findById(2))
    .thenReturn(None)

  test("found") {
    assert(mockRepositoriesService.findById(1) == Some(repo))
  }

  test("not found") {
    assert(mockRepositoriesService.findById(2) == None)
  }

}
