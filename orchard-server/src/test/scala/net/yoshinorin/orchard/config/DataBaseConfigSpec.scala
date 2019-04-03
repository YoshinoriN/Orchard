package net.yoshinorin.orchard.config

import org.scalatest.FunSuite

class DataBaseConfigSpec extends FunSuite {

  test("get datasource url") {
    assert("jdbc:mariadb://127.0.0.1/orchard" == DataBaseConfig.url)
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

}
