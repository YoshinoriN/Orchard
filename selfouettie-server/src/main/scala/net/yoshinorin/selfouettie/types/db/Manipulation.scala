package net.yoshinorin.selfouettie.types.db

class Limit(val limit: Int = 50) {}

object Limit {
  def apply(limit: Int = 50): Limit = {
    if (limit < 1 || limit > 50) {
      new Limit(50)
    } else {
      new Limit(limit)
    }
  }
}

case class Between(from: Option[Long] = None, to: Option[Long])
