package net.yoshinorin.selfouettie.config

import org.scalatest.FunSuite

class HttpServerConfigSpec extends FunSuite {

  test("get http server host") {
    assert("127.0.0.1" == HttpServerConfig.host)
  }

  test("get http server port") {
    assert(9001 == HttpServerConfig.port)
  }

}
