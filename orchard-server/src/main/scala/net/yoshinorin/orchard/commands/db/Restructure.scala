package net.yoshinorin.orchard.commands.db

import net.yoshinorin.orchard.commands.Import
import net.yoshinorin.orchard.config.{ConfigProvider, DataBaseConfig}
import net.yoshinorin.orchard.services.QuillProvider
import net.yoshinorin.orchard.utils.Logger
import org.flywaydb.core.Flyway

/**
 * HACK: This code is only for development. Please do not use this.
 * Drop scheme & re-create & import JSON files
 *
 */
object Restructure extends App with QuillProvider with ConfigProvider with Logger {

  import ctx._

  val schema: String = configuration.getString("db.restructure.schema")

  logger.debug("DROP SCHEMA")
  probe(s"DROP SCHEMA $schema")
  logger.debug("DROP SCHEMA COMPLETE!!")

  logger.debug("CREATE SCHEMA")
  probe(s"CREATE DATABASE $schema")
  logger.debug("CREATE SCHEMA COMPLETE!!")

  logger.debug("STARTING DB MIGRATION")
  val flyway = Flyway
    .configure()
    .dataSource(DataBaseConfig.url, DataBaseConfig.user, DataBaseConfig.password)
    .load()

  flyway.migrate
  logger.debug("FINISH DB MIGRATION")

  if (configuration.getBoolean("db.restructure.importData")) {
    logger.debug("IMPORT DATA FROM JSON")
    Import.main(Array())
    logger.debug("FINISH IMPORT DATA FROM JSON")
  }

}
