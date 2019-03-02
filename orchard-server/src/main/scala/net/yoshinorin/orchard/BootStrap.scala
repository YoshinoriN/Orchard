package net.yoshinorin.orchard

import net.yoshinorin.orchard.actor.GitHubEventActorService
import net.yoshinorin.orchard.commands.db.Migrate
import net.yoshinorin.orchard.http.HttpServer
import net.yoshinorin.orchard.utils.Logger

object BootStrap extends App with Logger {

  Migrate.run

  if (!args.contains("dev") && !args.contains("-d")) {
    GitHubEventActorService.initialize()
  }

  HttpServer.run(args.toList)

}
