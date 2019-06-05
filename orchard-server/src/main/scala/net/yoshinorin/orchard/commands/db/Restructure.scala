package net.yoshinorin.orchard.commands.db

import net.yoshinorin.orchard.services.FlywayService

/**
 * HACK: This code is only for development. Please do not use this.
 * Drop schema & re-create & import JSON files
 *
 */
object Restructure extends App {

  def restructure(): Unit = FlywayService.recrate()

}
