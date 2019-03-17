package net.yoshinorin.orchard.services.github

import java.time.ZonedDateTime
import scala.concurrent.Future
import scala.util.{Failure, Success}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse, Uri}
import akka.http.scaladsl.model.headers.{Authorization, OAuth2BearerToken}
import net.yoshinorin.orchard.actor.ActorService
import net.yoshinorin.orchard.config.ConfigProvider
import net.yoshinorin.orchard.utils.{File, Logger}

/**
 * GitHub API Service for get JSON & save to storage & insert
 *
 * @param api call API
 * @param jsonPrefix JSON file name prefix when save to storage
 */
abstract class ApiService(api: String, jsonPrefix: String) extends ActorService with ConfigProvider with Logger {

  private val token: String = configuration.getString("github.token")
  private val baseStoragePath: String = System.getProperty("user.dir") + "/src/main/resources/data/store/"
  protected val doStoreToLocalStorage: Boolean = configuration.getBoolean("github.storeToLocalStorage")

  protected def get(): Future[HttpResponse] = {
    val request = HttpRequest(
      HttpMethods.GET,
      uri = Uri(api),
      headers = List(Authorization(OAuth2BearerToken(token)))
    )
    Http().singleRequest(request)
  }

  protected def save(): Unit

  protected def store(json: String): Unit = {
    val path = baseStoragePath + jsonPrefix + ZonedDateTime.now.toEpochSecond.toString + ".json"
    File.create(path) match {
      case Success(_) => File.write(path, json)
      case Failure(f) => logger.error(f.getMessage)
    }
  }

}
