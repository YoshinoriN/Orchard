package net.yoshinorin.selfouettie.utils

import java.io.File

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

}
