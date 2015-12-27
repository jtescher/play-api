package utils

import com.typesafe.config.ConfigFactory

object TestDatabaseConfig {
  private val conf = ConfigFactory.load
  val config = Map(
    "slick.dbs.default.db.url" -> conf.getString("slick.dbs.test.db.url"),
    "liquibase.url" -> conf.getString("slick.dbs.test.db.url")
  )

}
