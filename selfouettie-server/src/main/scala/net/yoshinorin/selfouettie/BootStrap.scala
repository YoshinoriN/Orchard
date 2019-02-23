package net.yoshinorin.selfouettie

import net.yoshinorin.selfouettie.commands.db.Migrate
import net.yoshinorin.selfouettie.http.HttpServer
import net.yoshinorin.selfouettie.utils.Logger

object BootStrap extends App with Logger {

  Migrate.run
  HttpServer.run(args.toList)

}
