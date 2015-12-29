# Play API

[![Build Status](https://travis-ci.org/jtescher/play-api.svg)](https://travis-ci.org/jtescher/play-api)
[![Codacy Badge](https://api.codacy.com/project/badge/grade/0833151a4327414eb7c01c9c7e7c4826)](https://www.codacy.com/app/jatescher/play-api)

This is an example of how to build a [Play Framework](https://www.playframework.com/) application with specific
conventions for quick API development. The conventions are intended to make onboarding new developers as easy as possible
while keeping the code as expressive and free of boilerplate as possible.

## Conventions

1. [Routes](#routes)
1. [Controllers](#controllers)
1. [Serializers](#serializers)
1. [Services](#services)
1. [Data Access Objects](#data-access-objects)
1. [Database Migrations](#database-migrations)
1. [Additional Files](#additional-files)

## Routes

+ Use URL based API versioning. E.g. `/v1/posts`.
+ Use 5 standard endoints with appropriate HTTP verb:

```
GET       /v1/posts        # => Index
POST      /v1/posts        # => Create
GET       /v1/posts/:id    # => Show
PUT       /v1/posts/:id    # => Update
DELETE    /v1/posts/:id    # => Destroy
```

## Controllers

+ Keep your controllers as simple as possible.
+ Namespace your controller's package with company and version. E.g. `com.example.api.controllers.v1`.
+ Use pluralized resource name with `Controller` suffix. E.g. `PostsController`.
+ Controllers should be a `class` instead of a global `object`.
+ Use the [ControllerConventions](app/com/example/api/controllers/utils/ControllerConventions.scala) file to abstract
common controller code like model parsing.
+ Move repetitive error handling logic into [ErrorHandler](app/com/example/api/ErrorHandler.scala).
+ Import your model's JSON writes function from your corresponding serializer.
+ Include your model's JSON reads function in your controller for versioning, custom validations, and API clarity:

```scala
implicit val postJsonReads = (
  (__ \ "id").read[UUID] and
  (__ \ "title").read[String] and
  ...
)(Post.apply _)
```

## Serializers

+ Create one serializer per model.
+ Namespace your serializers package with company and version. E.g. `com.example.api.serializers.v1`.
+ User the singularized resource name with `Serializer` suffix. E.g. `PostSerializer`.
+ Prefer creating a `new Writes` instance over using the play-json `Json.format` macro:

```scala
implicit val postJsonWrites = new Writes[Post] {
  def writes(post: Post): JsObject = Json.obj(
    "id" -> post.id,
    "title" -> post.title,
    ...
  )
}
```

## Services

+ User the singularized resource name with `Service` suffix. E.g. `PostService`.
+ Keep business logic in the service layer as much as possible.
+ Use dependency injection to keep services decoupled.

## Data Access Objects

+ User the singularized resource name with `DAO` suffix. E.g. `PostDAO`.
+ Create one DAO per database table or remote resource.
+ Move repetitive create, read, update, delete functions into shared [DAOConventions](app/com/example/api/daos/DAOConventions.scala).
+ Keep database or remote resource specifics in the DAO layer as much as possible.

## Database Migrations

+ Use environment variables for database connection configuration in [application.conf](conf/application.conf).
+ Use [Liquibase](http://www.liquibase.org) to perform migrations and [Play Liquibase](https://github.com/Ticketfly/play-liquibase)
to have migrations run in dev / test mode.
+ Use `YYYMMDDHHMMSS_database_migration_description.xml` as changelog names.
+ Tag your database after making each significant change for easy rollback.

## Plugins

+ Use [Scoverage](https://github.com/scoverage/sbt-scoverage) with `coverageMinimum := 100` and `coverageFailOnMinimum := true`
in [build.sbt](build.sbt).
+ Use [Scalastyle](http://www.scalastyle.org/sbt.html) with `level="error"` in [scalastyle-config.xml](scalastyle-config.xml).
+ Use [Scalariform](https://github.com/daniel-trinh/scalariform) for standard style enforcement.
+ Use a server monitoring tool like New Relic with [sbt-newrelic](https://github.com/gilt/sbt-newrelic).
+ Enforce UTC timezone in JVM with [sbt-utc](https://github.com/tim-group/sbt-utc).

## Additional Files

+ Include a [.java-version](.java-version) file with the expected Java version.
+ Include a [activator](activator) wrapper file that downloads all necessary dependencies
(serving the app and testing the app should be as simple as: `$ ./activator run` and `$ ./activator test`).
+ Include a [resetdb](resetdb) script that drops and re-creates the database for testing and new developers.
