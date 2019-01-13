package net.yoshinorin.selfouettie.commands.db

import org.flywaydb.core.Flyway
import net.yoshinorin.selfouettie.config.DataBaseConfig

object Migrate {

  /**
   * Migrate database
   */
  def run: Unit = {
    if (DataBaseConfig.migration) {
      val flyway = Flyway
        .configure()
        .dataSource(DataBaseConfig.url, DataBaseConfig.user, DataBaseConfig.password)
        .load()

      flyway.migrate
    }
  }

}
