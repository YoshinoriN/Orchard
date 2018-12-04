# Drop tables

```sql
DROP TABLE flyway_schema_history;
DROP TABLE event_types;
DROP TABLE users;
DROP TABLE events;
```

# Full event list

```sql
/* https://developer.github.com/v3/activity/events/types/ */
/* INSERT INTO event_types VALUES ('CheckRunEvent'); */
/* INSERT INTO event_types VALUES ('CheckSuiteEvent'); */
INSERT INTO event_types VALUES ('CommitCommentEvent');                       /* Issue commit event */
INSERT INTO event_types VALUES ('CreateEvent');                              /* create repository & branch & tag event */
INSERT INTO event_types VALUES ('DeleteEvent');                              /* Delete branch or tag event */
/* INSERT INTO event_types VALUES ('DeploymentEvent'); */                    /* Not visible */
/* INSERT INTO event_types VALUES ('DeploymentStatusEvent'); */              /* Not visible */
/* INSERT INTO event_types VALUES ('DownloadEvent'); */                      /* No longer deliverd */
/* INSERT INTO event_types VALUES ('FollowEvent'); */                        /* No longer deliverd */
INSERT INTO event_types VALUES ('ForkEvent');                                /* fork event */
/* INSERT INTO event_types VALUES ('ForkApplyEvent'); */                     /* No longer deliverd */
/* INSERT INTO event_types VALUES ('GitHubAppAuthorizationEvent'); */        /* Not available in the EventAPI */
/* INSERT INTO event_types VALUES ('GistEvent'); */                          /* No longer deliverd */
INSERT INTO event_types VALUES ('GollumEvent');                              /* Create or update wiki page */
/* INSERT INTO event_types VALUES ('InstallationEvent'); */                  /* Install or Uninstall GitHubApplication */
/* INSERT INTO event_types VALUES ('InstallationRepositoriesEvent'); */      /* Add or Removeed from Installation */
INSERT INTO event_types VALUES ('IssueCommentEvent');                        /* Create, edit, delete on issue */
INSERT INTO event_types VALUES ('IssuesEvent');                              /* opened, edited, deleted, transferred, closed, reopened, assigned, unassigned, labeled, unlabeled, milestoned, or demilestoned */
/* INSERT INTO event_types VALUES ('LabelEvent'); */                         /* Create, edited, or deleted label */
/* INSERT INTO event_types VALUES ('MarketplacePurchaseEvent'); */           /* purchases a GitHub Marketplace plan */
/* INSERT INTO event_types VALUES ('MemberEvent'); */                        /* invitation or is removed as a collaborator to a repository */
/* INSERT INTO event_types VALUES ('MembershipEvent'); */                    /* user is added or removed from a team */
/* INSERT INTO event_types VALUES ('MilestoneEvent'); */                     /* not visible in timelines. milestone is created, closed, opened, edited, or deleted */
/* INSERT INTO event_types VALUES ('OrganizationEvent'); */                  /* added, removed, or invited to an Organization */
/* INSERT INTO event_types VALUES ('OrgBlockEvent'); */                      /* organization blocks or unblocks a user */
/* INSERT INTO event_types VALUES ('PageBuildEvent'); */                     /* build of a GitHub Pages site, whether successful or not */
/* INSERT INTO event_types VALUES ('ProjectCardEvent'); */                   /* project card is created, updated, moved, converted to an issue, or deleted */
/* INSERT INTO event_types VALUES ('ProjectColumnEvent'); */                 /* project column is created, updated, moved, or deleted */
/* INSERT INTO event_types VALUES ('ProjectEvent'); */                       /* project is created, updated, closed, reopened, or deleted */
/* INSERT INTO event_types VALUES ('PublicEvent'); */                        /* a repository private to public */
INSERT INTO event_types VALUES ('PullRequestEvent');                         /* a pull request is assigned, unassigned, labeled, unlabeled, opened, edited, closed, reopened, or synchronized */
INSERT INTO event_types VALUES ('PullRequestReviewEvent');                   /* a pull request review is submitted into a non-pending state, the body is edited, or the review is dismissed */
INSERT INTO event_types VALUES ('PullRequestReviewCommentEvent');            /* a comment on a pull request unified diff is created, edited, or deleted */
INSERT INTO event_types VALUES ('PushEvent');                                /* push event */
INSERT INTO event_types VALUES ('ReleaseEvent');                             /* publish release */
/* INSERT INTO event_types VALUES ('RepositoryEvent'); */                    /* not visible in timelines. a repository is created, archived, unarchived, made public, or made private */
INSERT INTO event_types VALUES ('RepositoryImportEvent');                    /* using either the GitHub Importer or the Source imports API */
/* INSERT INTO event_types VALUES ('RepositoryVulnerabilityAlertEvent'); */  /* a security alert is created, dismissed, or resolved */
/* INSERT INTO event_types VALUES ('SecurityAdvisoryEvent'); */              /* a new security advisory is published, updated, or withdrawn */
/* INSERT INTO event_types VALUES ('StatusEvent'); */                        /* not visible in timelines */
/* INSERT INTO event_types VALUES ('TeamEvent'); */                          /* not visible in timelines */
/* INSERT INTO event_types VALUES ('TeamAddEvent'); */                       /* not visible in timelines */
INSERT INTO event_types VALUES ('WatchEvent');                               /* starring a repository, not watching */
```
