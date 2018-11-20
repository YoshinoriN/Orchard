package net.yoshinorin.selfouettellia.config

object DataBaseConfig extends ConfigProvider {
  val url = configuration.getString("db.ctx.dataSource.url")
  val user = configuration.getString("db.ctx.dataSource.user")
  val password = configuration.getString("db.ctx.dataSource.password")

  val migrationSqlPath = configuration.getString("flyway.sqlfilePath")
}
