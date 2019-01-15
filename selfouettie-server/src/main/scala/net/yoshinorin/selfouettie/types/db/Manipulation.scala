package net.yoshinorin.selfouettie.types.db

class SqlLimit(val limit: Int = 50) {}

object SqlLimit {
  def apply(limit: Int = 50): SqlLimit = {
    println(limit)
    if (limit < 1 || limit > 50) {
      new SqlLimit(50)
    } else {
      new SqlLimit(limit)
    }
  }
}
