package nl.infowijs.codeassignment.verticle

import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.vertx.core.*
import io.vertx.core.json.jackson.DatabindCodec

class MainVerticle : AbstractVerticle() {
  override fun start(startPromise: Promise<Void>) {
    // Allow jackson to get along with Kotlin
    DatabindCodec.mapper().registerKotlinModule()
    deployMigrationVerticle(vertx)
      .flatMap { deployApiVerticle(vertx) }
      .onSuccess { println("Started successfully") }
      .onFailure { println("Error ${it.message}") }
  }

  private fun deployMigrationVerticle(vertx: Vertx): Future<Void> {
    val options: DeploymentOptions =
      DeploymentOptions()
        .setThreadingModel(ThreadingModel.WORKER)
        .setWorkerPoolName("migrations-worker-pool")
        .setInstances(1)
        .setWorkerPoolSize(1)

    return vertx
      .deployVerticle(MigrationVerticle::class.java.name, options)
      .flatMap(vertx::undeploy)
  }

  private fun deployApiVerticle(vertx: Vertx): Future<String> {
    return vertx.deployVerticle(ApiVerticle::class.java.name)
  }
}
