package net.yoshinorin.orchard.commands.db

import net.yoshinorin.orchard.commands.Import
import net.yoshinorin.orchard.config.{ConfigProvider, DataBaseConfig}
import net.yoshinorin.orchard.services.QuillProvider
import org.flywaydb.core.Flyway

/**
 * HACK: This code is only for development. Please do not use this.
 * Drop scheme & re-create & import JSON files
 *
 */
object Restructure extends App with QuillProvider with ConfigProvider {

  import ctx._

  val schema: String = configuration.getString("db.restructure.schema")

  println(s"[INFO]: DROP SCHEMA $schema")
  probe(s"DROP SCHEMA $schema")
  println("[INFO]: DROP SCHEMA COMPLETE!!")

  println(s"[INFO]: CREATE SCHEMA $schema")
  probe(s"CREATE DATABASE $schema")
  println("[INFO]: CREATE SCHEMA COMPLETE!!")

  println("[INFO]: STARTING DB MIGRATION")
  val flyway = Flyway
    .configure()
    .dataSource(DataBaseConfig.url, DataBaseConfig.user, DataBaseConfig.password)
    .load()

  flyway.migrate
  println("[INFO]: FINISH DB MIGRATION")

  if (configuration.getBoolean("db.restructure.importData")) {
    println("[INFO]: IMPORT DATA FROM JSON")
    Import.main(Array())
    println("[INFO]: FINISH IMPORT DATA FROM JSON")
  }

}
