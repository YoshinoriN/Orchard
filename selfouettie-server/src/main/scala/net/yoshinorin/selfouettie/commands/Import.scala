package net.yoshinorin.selfouettie.commands

import net.yoshinorin.selfouettie.utils.File
import net.yoshinorin.selfouettie.utils.Logger

/**
 * Import GitHub events from JSON files
 */
object Import extends App with Logger {

  logger.info("Start import from JSON files.")

  File.getFiles(System.getProperty("user.dir") + "/src/main/resources/data/import") match {
    case Some(result) => {
      File.filterByExtension(result, "json") match {
        case Some(jsonFiles) => {
          jsonFiles.foreach(jsonFile => {
            //TODO: Parse and insert them to DataBase
          })
        }
        case None => logger.info("JSON file not found.")
      }
    }
    case None => logger.info("JSON file not found.")
  }

  logger.info("Finish import.")
}
