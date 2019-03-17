package net.yoshinorin.orchard.commands.github

import net.yoshinorin.orchard.services.github.event.EventApiService

// runMain net.yoshinorin.orchard.commands.github.ExecuteEventApi
object ExecuteEventApi extends App {

  EventApiService.save

}
