package net.yoshinorin.orchard.services

import org.flywaydb.core.Flyway
import net.yoshinorin.orchard.config.DataBaseConfig
import org.flywaydb.core.api.configuration.FluentConfiguration

object FlywayService {

  private val flywayConfig: FluentConfiguration = Flyway.configure().dataSource(DataBaseConfig.url, DataBaseConfig.user, DataBaseConfig.password)
  private val flyway: Flyway = new Flyway(flywayConfig)

  /**
   * Database migrate
   */
  def migrate(): Unit = flyway.migrate()

  /**
   * Drop tables
   * NOTE: Only for development
   */
  def clean(): Unit = flyway.clean()

  /**
   * Drop tables & migrate database
   * NOTE: Only for development
   */
  def recrate(): Unit = {
    this.clean()
    this.migrate()
  }

}
