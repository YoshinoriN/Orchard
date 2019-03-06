package net.yoshinorin.orchard.services.github.event.json

import net.yoshinorin.orchard.models.db.Repositories
import net.yoshinorin.orchard.utils.File
import org.scalatest.FunSuite

// testOnly RepositorySpec
class RepositorySpec extends FunSuite {

  val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/repository.json")
  val instance = net.yoshinorin.orchard.services.github.event.json.Repository(json)

  test("ConvertJson to Repositories case class") {
    val repositoryCaseClass = Some(Repositories(94911145, "test/WatchEvent"))
    assert(instance.getConvertedCaseClass == repositoryCaseClass)
  }

}
