package net.yoshinorin.orchard.models.db

case class Events(
  id: Long,
  githubEventId: Long,
  eventType: String,
  userName: String,
  repositoryId: Long,
  action: String,
  url: String,
  createdAt: Long
) {
  def insert: Option[Long] = EventsRepository.insert(this)

  /**
   * Insert case class to DataBase and get new instance with auto-incremented id
   *
   * @return
   */
  def insertAndGetInstance: Option[Events] = {
    this.insert match {
      case Some(id) => {
        Option(
          Events(
            id,
            this.githubEventId,
            this.eventType,
            this.userName,
            this.repositoryId,
            this.action,
            this.url,
            this.createdAt
          ))
      }
      case None => None
    }
  }
}
