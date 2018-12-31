CREATE TABLE create_events (
  event_id BIGINT UNSIGNED PRIMARY KEY,
  user_name VARCHAR(255) NOT NULL,
  ref_type ENUM ('repository','branch','tag'),
  ref VARCHAR(255),
  created_at BIGINT UNSIGNED,
  FOREIGN KEY(event_id) REFERENCES events(id)
) DEFAULT CHARSET=utf8mb4
comment='Events for created a repository, branch, or tag';
