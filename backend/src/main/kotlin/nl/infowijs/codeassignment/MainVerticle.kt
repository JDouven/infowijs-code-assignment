package nl.infowijs.codeassignment

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CorsHandler
import nl.infowijs.codeassignment.controllers.HealthController
import nl.infowijs.codeassignment.controllers.MessagesController

class MainVerticle : AbstractVerticle() {
  val corsHandler = CorsHandler.create("*")
  val messagesController = MessagesController()

  fun createRouter(vertx: Vertx) =
      Router.router(vertx).apply {
        route().handler(corsHandler)
        route().handler(BodyHandler.create())
        get("/messages").handler(messagesController::listMessages)
        post("/messages").handler(messagesController::createMessage)
        get("/healthz").handler(HealthController.healthCheck)
      }

  override fun start(startPromise: Promise<Void>) {
    val router = createRouter(vertx)

    vertx.createHttpServer().requestHandler(router).listen(8888) { http ->
      if (http.succeeded()) {
        startPromise.complete()
        println("HTTP server started on port 8888")
      } else {
        startPromise.fail(http.cause())
      }
    }
  }
}
