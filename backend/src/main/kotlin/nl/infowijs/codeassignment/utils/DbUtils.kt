package nl.infowijs.codeassignment.utils

import io.vertx.core.Vertx
import io.vertx.pgclient.PgBuilder
import io.vertx.pgclient.PgConnectOptions
import io.vertx.pgclient.SslMode
import io.vertx.sqlclient.Pool
import io.vertx.sqlclient.PoolOptions
import org.flywaydb.core.api.configuration.Configuration
import org.flywaydb.core.api.configuration.FluentConfiguration

private const val DB_PORT = 5432
private const val DB_HOST = "localhost"
private const val DB_DB = "infowijs"
private const val DB_USER = "user"
private const val DB_PASSWORD = "password"

object DbUtils {
  fun buildDbClient(vertx: Vertx): Pool {
    val connectOptions = PgConnectOptions()
      .setPort(DB_PORT)
      .setHost(DB_HOST)
      .setDatabase(DB_DB)
      .setUser(DB_USER)
      .setPassword(DB_PASSWORD)
      .setSsl(false)
      .setSslMode(SslMode.DISABLE)

    val poolOptions: PoolOptions = PoolOptions().setMaxSize(5)

    return PgBuilder
      .pool()
      .with(poolOptions)
      .connectingTo(connectOptions)
      .using(vertx)
      .build()
  }

  fun buildMigrationsConfiguration(): Configuration {
    val url = "jdbc:postgresql://$DB_HOST:$DB_PORT/$DB_DB"

    return FluentConfiguration()
      .dataSource(url, DB_USER, DB_PASSWORD)
  }
}
