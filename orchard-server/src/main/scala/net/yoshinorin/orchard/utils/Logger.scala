package net.yoshinorin.orchard.utils

import org.slf4j.{LoggerFactory, MarkerFactory}

trait Logger {
  val logger = LoggerFactory.getLogger(this.getClass)
  val securityMaker = MarkerFactory.getMarker("SECURITY")
}
