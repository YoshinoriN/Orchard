package net.yoshinorin.selfouettie.services

import akka.actor.Props
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension
import net.yoshinorin.selfouettie.actor.GitHubEventActor

trait GitHubEventActorService extends ActorService {

  private val gitHubEventActor = actorSystem.actorOf(Props(classOf[GitHubEventActor]))
  private val scheduler = QuartzSchedulerExtension(actorSystem)

  def initializeGitHubEventActor(): Unit = {
    scheduler.schedule("GetGitHubEvent", gitHubEventActor, GitHubEventActor.GetEvents)
  }

}
