CREATE TABLE issue_events (
  event_id BIGINT UNSIGNED PRIMARY KEY,
  user_name VARCHAR(255) UNIQUE NOT NULL,
  repository_id BIGINT UNSIGNED NOT NULL,
  issue_number BIGINT UNSIGNED NOT NULL,
  action ENUM ('opened','edited','deleted','transferred','closed','reopened','assigned','unassigned','labeled','unlabeled','milestoned','demilestoned'),
  created_at BIGINT UNSIGNED,
  FOREIGN KEY(event_id) REFERENCES events(id),
  FOREIGN KEY(repository_id) REFERENCES repositories(id)
  #FOREIGN KEY(issue_number) REFERENCES issues(issue_number) Why fail this line ??
) DEFAULT CHARSET=utf8mb4;
