package net.yoshinorin.orchard.commands

import net.yoshinorin.orchard.services.EventService
import net.yoshinorin.orchard.utils.File
import net.yoshinorin.orchard.utils.Logger

/**
 * Import GitHub events from JSON files
 */
object Import extends App with Logger with EventService {

  logger.info("Start import from JSON files.")

  File.getFiles(System.getProperty("user.dir") + "/src/main/resources/data/import") match {
    case Some(result) => {
      File.filterByExtension(result, "json") match {
        case Some(jsonFiles) => {
          jsonFiles.foreach(jsonFile => {
            convert(File.readAll(jsonFile.getAbsolutePath)) match {
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
