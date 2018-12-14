CREATE TABLE push_events (
  event_id BIGINT UNSIGNED PRIMARY KEY,
  repository_id BIGINT UNSIGNED NOT NULL,
  ref VARCHAR(255) NOT NULL,
  size INT UNSIGNED NOT NULL,
  created_at DATETIME DEFAULT NULL,
  FOREIGN KEY(event_id) REFERENCES events(id),
  FOREIGN KEY(repository_id) REFERENCES repositories(id)
) DEFAULT CHARSET=utf8mb4;
