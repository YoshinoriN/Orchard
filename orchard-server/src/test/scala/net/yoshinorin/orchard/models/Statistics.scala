package net.yoshinorin.orchard.models.db

import net.yoshinorin.orchard.models.ContributedRepository
import org.scalatest.FunSuite

// testOnly *Statistics
class Statistics extends FunSuite {
  test("ContributedRepository's url should return with github url prefix") {
    val contributedRepository: ContributedRepository = ContributedRepository(123456, "testRepo", "YoshinoriN/testRepo", 987)
    assert("https://github.com/YoshinoriN/testRepo" == contributedRepository.url)
  }
}
