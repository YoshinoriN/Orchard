package net.yoshinorin.orchard.models.db

case class Repositories(
  id: Long,
  name: String
){
  def insert: Unit = RepositoriesRepository.insert(this)
}
