package net.yoshinorin.selfouettie.config

object HttpServerConfig extends ConfigProvider {
  val host = configuration.getString("http.host")
  val port = configuration.getInt("http.port")
}
