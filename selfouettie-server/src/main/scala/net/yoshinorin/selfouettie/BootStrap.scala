package net.yoshinorin.selfouettie

import org.flywaydb.core.Flyway
import net.yoshinorin.selfouettie.config.DataBaseConfig
import net.yoshinorin.selfouettie.http.HttpServer
import net.yoshinorin.selfouettie.utils.Logger

object BootStrap extends App with Logger {

  if (DataBaseConfig.migration) {
    val flyway = Flyway
      .configure()
      .dataSource(DataBaseConfig.url, DataBaseConfig.user, DataBaseConfig.password)
      .load()

    flyway.migrate
  }

  HttpServer.start

}
