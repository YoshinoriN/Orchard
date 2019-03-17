package net.yoshinorin.orchard.services.github.event.json

import io.circe.Json
import io.circe.parser.parse
import net.yoshinorin.orchard.models.db.Repositories
import net.yoshinorin.orchard.utils.File
import org.scalatest.FunSuite

// testOnly RepositorySpec
class RepositorySpec extends FunSuite {

  val json = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/repository.json")
  val instance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(json).getOrElse(Json.Null))

  test("getConvertedCaseClass should return Repositories case class") {
    val repositoryCaseClass = Some(Repositories(94911145, "test/WatchEvent"))
    assert(instance.getConvertedCaseClass == repositoryCaseClass)
  }

  test("getConvertedCaseClass method should return None") {
    val json = """Not a JSON"""
    assert(Repository(parse(json).getOrElse(Json.Null)).getConvertedCaseClass.isEmpty)
  }

}
