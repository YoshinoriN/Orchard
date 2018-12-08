package net.yoshinorin.selfouettie.config

import com.typesafe.config.{Config, ConfigFactory}

trait ConfigProvider {
  val configuration: Config = ConfigFactory.load()
}
