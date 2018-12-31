package net.yoshinorin.selfouettie.models

case class Issues(
  userName: String,
  repositoryId: Long,
  issueNumber: Int,
  title: String
)
