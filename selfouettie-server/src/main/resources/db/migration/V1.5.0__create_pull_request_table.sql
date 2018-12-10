CREATE TABLE pull_requests (
  repository_id BIGINT UNSIGNED NOT NULL,
  pull_request_number BIGINT UNSIGNED NOT NULL,
  title TEXT NOT NULL,
  merged BOOLEAN DEFAULT false,
  UNIQUE (repository_id, pull_request_number),
  FOREIGN KEY(repository_id) REFERENCES repositories(id)
) DEFAULT CHARSET=utf8mb4;
