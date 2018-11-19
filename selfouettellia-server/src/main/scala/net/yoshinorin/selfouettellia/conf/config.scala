package net.yoshinorin.selfouettellia.config

import java.nio.file.Paths
import com.typesafe.config.{Config, ConfigFactory}

trait ConfigProvider {
  val config: Config = ConfigFactory.load()
}

object HttpServer extends ConfigProvider {
  val host = config.getString("http.host")
  val port = config.getInt("http.port")
}

object Data extends ConfigProvider {
  //TODO: Fix it
  val path = Paths.get(System.getProperty("user.dir"), "data", config.getString("data.fileName"))
}

object Token extends ConfigProvider {
  val reload = config.getString("token.reload")
}
