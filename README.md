# Orchard

**This project is under construction.**

A GitHub events collecting application.

## Server side

|Build|Quality|Coverage|
|---|---|---|
|[![](https://travis-ci.org/YoshinoriN/Orchard.svg?branch=master)](https://travis-ci.org/YoshinoriN/Orchard)|[![Codacy Badge](https://api.codacy.com/project/badge/Grade/837c3a3046454c2da2b035d60ba30bea)](https://www.codacy.com/app/YoshinoriN/Selfouettellia?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=YoshinoriN/Selfouettellia&amp;utm_campaign=Badge_Grade)|[![Coverage Status](https://coveralls.io/repos/github/YoshinoriN/Orchard/badge.svg?branch=master)](https://coveralls.io/github/YoshinoriN/Orchard?branch=master)|

* [Documentation](./orchard-server)

## Front end

* [Documentation](./orchard-frontend)

## Setup

#### 1. Create database

Please execute create database DDL. For example...

```sql
CREATE DATABASE orchard;
```

#### 2. Set database configulation

Please change `./resources/db/db.conf` following your database configulation.

## Stacks

|Layer|Stack|Description|
|---|---|---|
|Server side|Scala|Server side main language|
|Server side|Akka HTTP|HTTP-based services|
|Server side|Akka Scheduler|Job Scheduler|
|Server side|Flyway|DataBase migration|
|Server side|circe|JSON encode, decode|
|Server side|LogBack|logger|
|Front end|React.js|-|
|Front end|Next.js|React.js framework|
|-|MariaDB|DataBase|
|-|Docker|-|

## LICENSE

Not yet decided
