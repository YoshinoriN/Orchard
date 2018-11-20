package net.yoshinorin.selfouettellia.config

object HttpServer extends ConfigProvider {
  val host = configuration.getString("http.host")
  val port = configuration.getInt("http.port")
}
