package net.yoshinorin.selfouettie.config

import org.scalatest.FunSuite

class DataBaseConfigSpec extends FunSuite {

  test("get datasource url") {
    assert("jdbc:mariadb://127.0.0.1/selfouettie" == DataBaseConfig.url)
  }

  test("get datasource user") {
    assert("root" == DataBaseConfig.user)
  }

  test("get datasource pass") {
    assert("pass" == DataBaseConfig.password)
  }

  test("get migration") {
    assert(!DataBaseConfig.migration)
  }

  test("get migration sql path") {
    assert("/db/migration" == DataBaseConfig.migrationSqlPath)
  }

}
