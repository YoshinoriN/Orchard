package net.yoshinorin.selfouettie.utils

import java.io.File
import java.nio.charset.Charset
import java.nio.file
import java.nio.file.{Files, Path, Paths}
import java.util.stream.Collectors

object Files {

  /**
   * Get file list in directory
   *
   * @param dir directory path
   * @return list of files in specify directory
   */
  def getFiles(dir: String): Option[List[File]] = {
    val directory = new File(dir)

    if (directory.exists() && directory.isDirectory()) {
      Option(directory.listFiles().toList)
    } else {
      None
    }
  }

  /**
   * Get file list filter by extension
   *
   * @param files files
   * @param ext extension name
   * @return list of specify extension files
   */
  def filterByExtension(files: List[File], ext: String): Option[List[File]] = {
    Some(files.filter(_.getName.endsWith(ext)))
  }

  /**
   * Read all text from file
   *
   * @param String file path
   * @return text
   */
  def readAll(path: String): String = {
    file.Files
      .lines(Paths.get(path), Charset.forName("UTF-8"))
      .collect(Collectors.joining(System.lineSeparator()))
      .stripMargin
  }

}
