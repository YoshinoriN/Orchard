# Server-side

## Commands

### Server

#### Run

```sh
> sbt
> runMain net.yoshinorin.orchard.BootStrap

or

sbt "runMain net.yoshinorin.orchard.BootStrap"
```

##### Options

|value|description|
|---|---|
|`-d`, `--dev`|Development mode. You can stop server by press `Enter` key.|

#### Stop

* Development mode: Press `Enter` key
* Production mode: `Ctrl + Enter` key

### Restructure

Restracture command is for develop. This command is execute below procedure.

* DROP schema
* CREATE schema
* CREATE TABLES (using by flyway)
* Import data from JSON files

Execute command is below

```sh
runMain net.yoshinorin.orchard.commands.db.Restructure
```

## Code Format

On IntelliJ `Ctrl + shift + l`

## Test

Run test.

```
$ sbt test
```

Generate coverage report.

```
$ sbt coverageReport

or

$ sbt clean coverage test coverageReport
```
