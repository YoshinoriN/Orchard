package net.yoshinorin.selfouettie.config

object HttpServerConfig extends ConfigProvider {
  val host: String = configuration.getString("http.host")
  val port: Int = configuration.getInt("http.port")
}
