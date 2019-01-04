package net.yoshinorin.selfouettie.commands

import net.yoshinorin.selfouettie.services.EventsConverter
import net.yoshinorin.selfouettie.services.EventService
import net.yoshinorin.selfouettie.utils.File
import net.yoshinorin.selfouettie.utils.Logger

/**
 * Import GitHub events from JSON files
 */
object Import extends App with EventsConverter with Logger with EventService {

  logger.info("Start import from JSON files.")

  File.getFiles(System.getProperty("user.dir") + "/src/main/resources/data/import") match {
    case Some(result) => {
      File.filterByExtension(result, "json") match {
        case Some(jsonFiles) => {
          jsonFiles.foreach(jsonFile => {
            val data = convert(File.readAll(jsonFile.getAbsolutePath))
            data match {
              case Some(x) => {
                x.foreach(y => create(y))
              }
              case None => logger.info("Import records are nothing.")
            }
          })
        }
        case None => logger.info("JSON file not found.")
      }
    }
    case None => logger.info("JSON file not found.")
  }

  logger.info("Finish import.")
}
