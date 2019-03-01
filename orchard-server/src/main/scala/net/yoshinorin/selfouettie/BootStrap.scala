package net.yoshinorin.orchard

import net.yoshinorin.orchard.commands.db.Migrate
import net.yoshinorin.orchard.http.HttpServer
import net.yoshinorin.orchard.utils.Logger

object BootStrap extends App with Logger {

  Migrate.run
  HttpServer.run(args.toList)

}
