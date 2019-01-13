package net.yoshinorin.selfouettie.services

import java.time.ZonedDateTime
import scala.concurrent.Future
import scala.util.{Failure, Success}
import akka.http.scaladsl.model.headers.{Authorization, OAuth2BearerToken}
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse, Uri}
import akka.http.scaladsl.Http
import akka.http.scaladsl.unmarshalling.Unmarshal
import net.yoshinorin.selfouettie.config.ConfigProvider
import net.yoshinorin.selfouettie.utils.{File, Logger}

object GitHubEventService extends ActorService with EventsConverter with EventService with ConfigProvider with Logger {

  private val api = configuration.getString("github.api")
  private val token = configuration.getString("github.token")
  private val doStoreToLocalStorage = configuration.getBoolean("github.storeToLocalStorage")
  private val storagePath = System.getProperty("user.dir") + "/src/main/resources/data/store/" + ZonedDateTime.now.toEpochSecond.toString + ".json"

  def getEvents(): Future[HttpResponse] = {
    val request = HttpRequest(
      HttpMethods.GET,
      uri = Uri(api),
      headers = List(Authorization(OAuth2BearerToken(token)))
    )
    Http().singleRequest(request)
  }

  def save(): Unit = {
    this.getEvents().onComplete {
      case Success(s) => {
        Unmarshal(s.entity).to[String].onComplete {
          case Success(json) => {
            if (this.doStoreToLocalStorage) {
              this.storeJson(json)
            }
            convert(json) match {
              case Some(x) => {
                x.foreach(y => create(y))
              }
              case None => logger.info("GitHub Events is nothing.")
            }
          }
          case Failure(throwable: Throwable) => logger.error(throwable.toString)
        }
      }
      case Failure(throwable: Throwable) => logger.error(throwable.toString)
    }
  }

  private[this] def storeJson(json: String): Unit = {
    File.create(storagePath) match {
      case Success(_) => File.write(storagePath, json)
      case Failure(f) => logger.error(f.getMessage)
    }
  }

}
