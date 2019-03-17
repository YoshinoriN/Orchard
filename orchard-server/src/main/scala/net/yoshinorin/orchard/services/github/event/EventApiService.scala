package net.yoshinorin.orchard.services.github.event

import scala.util.{Failure, Success}
import akka.http.scaladsl.unmarshalling.Unmarshal
import net.yoshinorin.orchard.config.ConfigProvider
import net.yoshinorin.orchard.services.github.ApiService
import net.yoshinorin.orchard.utils.json.JsonUtil

/**
 * GitHub Event API Service for get JSON & save to storage & insert
 *
 * @param api call API
 * @param jsonPrefix JSON file name prefix when save to storage
 */
class EventApiService(api: String, jsonPrefix: String) extends ApiService(api: String, jsonPrefix: String) {

  override def save(): Unit = {
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

}

object EventApiService extends ConfigProvider {

  private val api = configuration.getString("github.api.eventByUser")
  private val instance = new EventApiService(api, "event_")

  def save: Unit = instance.save()

}
