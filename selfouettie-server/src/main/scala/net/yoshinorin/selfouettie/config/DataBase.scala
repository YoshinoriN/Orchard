package net.yoshinorin.selfouettie.config

object DataBaseConfig extends ConfigProvider {
  val url = configuration.getString("db.ctx.dataSource.url")
  val user = configuration.getString("db.ctx.dataSource.user")
  val password = configuration.getString("db.ctx.dataSource.password")

  val migration = configuration.getBoolean("db.migration")
  val migrationSqlPath = configuration.getString("flyway.sqlfilePath")
}
