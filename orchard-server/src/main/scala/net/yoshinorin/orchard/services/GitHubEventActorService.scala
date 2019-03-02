package net.yoshinorin.orchard.services

import akka.actor.Props
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension
import net.yoshinorin.orchard.actor.GitHubEventActor

trait GitHubEventActorService extends ActorService {

  private val gitHubEventActor = actorSystem.actorOf(Props(classOf[GitHubEventActor]))
  private val scheduler = QuartzSchedulerExtension(actorSystem)

  def initializeGitHubEventActor(): Unit = {
    scheduler.schedule("GetGitHubEventsJson", gitHubEventActor, GitHubEventActor.GetEventsJson)
  }

}
