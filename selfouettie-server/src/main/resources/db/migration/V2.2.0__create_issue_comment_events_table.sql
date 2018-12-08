CREATE TABLE issue_comment_events (
  event_id BIGINT UNSIGNED PRIMARY KEY,
  repository_id BIGINT UNSIGNED NOT NULL,
  issue_number BIGINT UNSIGNED NOT NULL,
  action ENUM ('created','edited','deleted'),
  created_at DATETIME DEFAULT NULL,
  FOREIGN KEY(event_id) REFERENCES events(id),
  FOREIGN KEY(repository_id) REFERENCES repositories(id)
  #FOREIGN KEY(issue_number) REFERENCES issues(issue_number) Why this line faild ??
) DEFAULT CHARSET=utf8mb4;
