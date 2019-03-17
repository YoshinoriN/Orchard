package net.yoshinorin.orchard.services.github.event

import java.time.ZonedDateTime
import scala.concurrent.Future
import scala.util.{Failure, Success}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers.{Authorization, OAuth2BearerToken}
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import net.yoshinorin.orchard.actor.ActorService
import net.yoshinorin.orchard.config.ConfigProvider
import net.yoshinorin.orchard.utils.json.JsonUtil
import net.yoshinorin.orchard.utils.{File, Logger}

object EventApiService extends ActorService with ConfigProvider with Logger {

  private val api = configuration.getString("github.api")
  private val token = configuration.getString("github.token")
  private val doStoreToLocalStorage = configuration.getBoolean("github.storeToLocalStorage")
  private val baseStoragePath = System.getProperty("user.dir") + "/src/main/resources/data/store/"

  def get(): Future[HttpResponse] = {
    val request = HttpRequest(
      HttpMethods.GET,
      uri = Uri(api),
      headers = List(Authorization(OAuth2BearerToken(token)))
    )
    Http().singleRequest(request)
  }

  def save(): Unit = {
    this.get().onComplete {
      case Success(s) => {
        Unmarshal(s.entity).to[String].onComplete {
          case Success(json) => {
            if (this.doStoreToLocalStorage) {
              this.store(json)
            }
            JsonUtil.toJsonList(json) match {
              case Some(x) => {
                x.foreach(y => EventService.insert(y))
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

  private def store(json: String): Unit = {
    val path = baseStoragePath + ZonedDateTime.now.toEpochSecond.toString + ".json"
    File.create(path) match {
      case Success(_) => File.write(path, json)
      case Failure(f) => logger.error(f.getMessage)
    }
  }

}
