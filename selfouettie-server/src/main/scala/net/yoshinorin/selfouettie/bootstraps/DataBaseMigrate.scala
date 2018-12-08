package net.yoshinorin.selfouettie.bootstraps

import net.yoshinorin.selfouettie.config.DataBaseConfig
import net.yoshinorin.selfouettie.utils.Logger
import org.flywaydb.core.Flyway

object DataBaseMigrate extends Logger {

  def migrate: Unit = {

    val flyway = Flyway
      .configure()
      .dataSource(DataBaseConfig.url, DataBaseConfig.user, DataBaseConfig.password)
      .load()

    flyway.migrate
  }

}
