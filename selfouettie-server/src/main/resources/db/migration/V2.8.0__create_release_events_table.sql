CREATE TABLE release_events (
  event_id BIGINT UNSIGNED PRIMARY KEY,
  user_name VARCHAR(255) UNIQUE NOT NULL,
  repository_id BIGINT UNSIGNED NOT NULL,
  tag_name VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  action ENUM ('published'),
  created_at BIGINT UNSIGNED,
  FOREIGN KEY(event_id) REFERENCES events(id),
  FOREIGN KEY(repository_id) REFERENCES repositories(id)
) DEFAULT CHARSET=utf8mb4;
