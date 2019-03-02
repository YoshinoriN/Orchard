package net.yoshinorin.orchard.actor

import akka.actor.Props
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension

object GitHubEventActorService extends ActorService {

  private val gitHubEventActor = actorSystem.actorOf(Props(classOf[GitHubEventJsonActor]))
  private val scheduler = QuartzSchedulerExtension(actorSystem)

  def initialize(): Unit = {
    scheduler.schedule("GetGitHubEventsJson", gitHubEventActor, GitHubEventJsonActor.GetEventsJson)
  }

}
