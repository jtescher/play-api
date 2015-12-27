package utils

import daos.PostDAO
import play.api.Application
import play.api.db.slick.DatabaseConfigProvider
import slick.dbio.DBIO
import slick.driver.JdbcProfile

class DatabaseCleaner {

  def cleanDatabase(implicit app: Application) = {
    val dbConfigProvider = app.injector.instanceOf[DatabaseConfigProvider]
    val db = dbConfigProvider.get[JdbcProfile].db
    val postDAO = app.injector.instanceOf[PostDAO]

    DatabaseHelper.exec(db.run(DBIO.seq(
      postDAO.deleteAction
    )))
  }

}
