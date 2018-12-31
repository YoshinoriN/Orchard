package net.yoshinorin.selfouettie.models.db

case class Issues(
  userName: String,
  repositoryId: Long,
  issueNumber: Int,
  title: String
)
