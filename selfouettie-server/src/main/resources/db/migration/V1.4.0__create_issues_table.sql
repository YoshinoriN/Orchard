CREATE TABLE issues (
  user_name VARCHAR(255) NOT NULL,
  repository_id BIGINT UNSIGNED NOT NULL,
  issue_number BIGINT UNSIGNED NOT NULL,
  title TEXT NOT NULL,
  UNIQUE (repository_id, issue_number),
  FOREIGN KEY(repository_id) REFERENCES repositories(id)
) DEFAULT CHARSET=utf8mb4;
