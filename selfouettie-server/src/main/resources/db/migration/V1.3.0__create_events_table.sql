CREATE TABLE events (
  id BIGINT UNSIGNED PRIMARY KEY,
  event_type VARCHAR(255) NOT NULL,
  user_name VARCHAR(255) UNIQUE NOT NULL,
  repository_id BIGINT UNSIGNED NOT NULL,
  action ENUM ('opened','edited','deleted','transferred','closed','reopened','assigned','unassigned','labeled','unlabeled','milestoned','demilestoned','started','published','submitted','dismissed','review_requested','review_request_removed'),
  url VARCHAR(255) NOT NULL,
  created_at BIGINT UNSIGNED,
  FOREIGN KEY(event_type) REFERENCES event_types(name),
  FOREIGN KEY(repository_id) REFERENCES repositories(id)
) DEFAULT CHARSET=utf8mb4;
