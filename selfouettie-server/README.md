# Development

### Run server

```sh
> sbt
> runMain net.yoshinorin.selfouettie.BootStrap

or 

sbt "runMain net.yoshinorin.selfouettie.BootStrap"
```

### Stop Server

Press `Enter` key.

# Code Format

On IntelliJ `Ctrl + shift + l`

# Test

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
