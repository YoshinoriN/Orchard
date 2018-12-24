CREATE TABLE fork_events (
  event_id BIGINT UNSIGNED PRIMARY KEY,
  user_name VARCHAR(255) UNIQUE NOT NULL,
  forked_repository_id BIGINT UNSIGNED NOT NULL,
  created_at BIGINT UNSIGNED,
  FOREIGN KEY(event_id) REFERENCES events(id),
  FOREIGN KEY(forked_repository_id) REFERENCES repositories(id)
) DEFAULT CHARSET=utf8mb4;
