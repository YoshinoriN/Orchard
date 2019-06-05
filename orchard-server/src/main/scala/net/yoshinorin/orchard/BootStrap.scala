package net.yoshinorin.orchard

import net.yoshinorin.orchard.actor.GitHubEventActorService
import net.yoshinorin.orchard.utils.Logger
import net.yoshinorin.orchard.services.FlywayService

object BootStrap extends App with Logger {

  FlywayService.migrate()

  if (!args.contains("dev") && !args.contains("-d")) {
    GitHubEventActorService.initialize()
  }

  //HttpServer.run(args.toList)

}
