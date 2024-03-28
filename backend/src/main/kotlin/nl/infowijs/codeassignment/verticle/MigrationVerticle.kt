package nl.infowijs.codeassignment.verticle

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import nl.infowijs.codeassignment.utils.DbUtils
import org.flywaydb.core.Flyway

class MigrationVerticle : AbstractVerticle() {
  override fun start(startPromise: Promise<Void>) {
    val config = DbUtils.buildMigrationsConfiguration()
    val flyway = Flyway(config)

    flyway.migrate()

    startPromise.complete()
  }
}
