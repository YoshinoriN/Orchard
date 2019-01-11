package net.yoshinorin.selfouettie.utils

import java.io.{File, FileNotFoundException}
import java.nio.charset.Charset
import java.nio.file
import java.nio.file.{Files, Paths}
import java.util.stream.Collectors

import scala.util.{Failure, Success, Try}

object File extends Logger {

  /**
   * Get file list in directory
   *
   * @param path directory path
   * @return list of files in specify directory
   */
  def get(path: String): Option[File] = {
    Some(new File(path))
  }

  /**
   * Get file list in directory
   *
   * TODO: use nio2
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
   * @param path file path
   * @return text
   */
  def readAll(path: String): String = {
    file.Files
      .lines(Paths.get(path), Charset.forName("UTF-8"))
      .collect(Collectors.joining(System.lineSeparator()))
      .stripMargin
  }

  /**
   * Create file
   *
   * @param path file path
   * @return scala.util.Try
   */
  def create(path: String): Try[Unit] = Try {
    Files.createFile(Paths.get(path))
  }

  /**
   * Write content to file
   *
   * @param path file path
   * @param content content
   * @return scala.util.Try
   */
  def write(path: String, content: String): Try[Unit] = Try {
    Files.exists(Paths.get(path)) match {
      case true => {
        val writer = Files.newBufferedWriter(Paths.get(path))
        try {
          writer.write(content)
        } finally {
          writer.close()
        }
        Success()
      }
      case false => {
        logger.error(s"$path does not exists.")
        Failure(new FileNotFoundException)
      }
    }
  }

}
