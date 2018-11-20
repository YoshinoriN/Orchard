package net.yoshinorin.selfouettellia.bootstraps

import net.yoshinorin.selfouettellia.config.DataBaseConfig
import net.yoshinorin.selfouettellia.utils.Logger
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
