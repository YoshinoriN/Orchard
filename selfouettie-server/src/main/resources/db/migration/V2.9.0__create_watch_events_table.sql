CREATE TABLE watch_events (
  event_id BIGINT UNSIGNED PRIMARY KEY,
  repository_id BIGINT UNSIGNED NOT NULL,
  action ENUM ('started'),
  created_at DATETIME DEFAULT NULL,
  FOREIGN KEY(event_id) REFERENCES events(id),
  FOREIGN KEY(repository_id) REFERENCES repositories(id)
) DEFAULT CHARSET=utf8mb4;
