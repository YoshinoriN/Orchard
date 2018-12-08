CREATE TABLE fork_events (
  event_id BIGINT UNSIGNED PRIMARY KEY,
  forked_repository_id BIGINT UNSIGNED NOT NULL,
  created_at DATETIME DEFAULT NULL,
  FOREIGN KEY(event_id) REFERENCES events(id),
  FOREIGN KEY(forked_repository_id) REFERENCES repositories(id)
) DEFAULT CHARSET=utf8mb4;
