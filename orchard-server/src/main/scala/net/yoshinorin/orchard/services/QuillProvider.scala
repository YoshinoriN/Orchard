package net.yoshinorin.orchard.services

import io.getquill.{MysqlJdbcContext, SnakeCase}

/**
 * Provide quill context
 */
class QuillProvider {

  lazy val ctx = new MysqlJdbcContext[SnakeCase](SnakeCase, "db.ctx")

}
