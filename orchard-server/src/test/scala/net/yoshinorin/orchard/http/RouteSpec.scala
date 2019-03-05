package net.yoshinorin.orchard.http

import scala.concurrent.duration._
import org.scalatest.{Matchers, WordSpec}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.{RouteTestTimeout, ScalatestRouteTest}
import akka.http.scaladsl.server._
import Directives._
import akka.http.scaladsl.testkit.RouteTestTimeout

// testOnly *RouteSpec
class RouteSpec extends WordSpec with Matchers with ScalatestRouteTest with Route {

  implicit val timeout = RouteTestTimeout(10.seconds)

  "The service" should {
    "return a API ready JSON for GET requests to the root path" in {
      // tests:
      Get() ~> route ~> check {
        responseAs[String] shouldEqual "{\"message\": \"API is ready\"}"
      }
    }

    "return a robots.txt" in {
      // tests:
      Get("/robots.txt") ~> route ~> check {
        responseAs[String].startsWith("User-agent")
      }
    }

  }

}
