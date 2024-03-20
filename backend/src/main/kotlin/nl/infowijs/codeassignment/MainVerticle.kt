package nl.infowijs.codeassignment

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.Vertx
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.CorsHandler
import nl.infowijs.codeassignment.controllers.HealthController
import nl.infowijs.codeassignment.controllers.MessagesController

class MainVerticle : AbstractVerticle() {
  fun createCorsHandler() =
      CorsHandler.create("*")
          .allowedMethod(HttpMethod.GET)
          .allowedMethod(HttpMethod.POST)
          .allowedMethod(HttpMethod.OPTIONS)
          .allowedHeader("Access-Control-Request-Method")
          .allowedHeader("Access-Control-Allow-Credentials")
          .allowedHeader("Access-Control-Allow-Origin")
          .allowedHeader("Access-Control-Allow-Headers")
          .allowedHeader("Content-Type")

  fun createRouter(vertx: Vertx) =
      Router.router(vertx).apply {
        get("/messages").handler(MessagesController()::listMessages)
        get("/healthz").handler(HealthController.healthCheck)
        // TODO: Implement this API later!
        //    get("/chat").handler(messagesController::listMessages)
        route().handler(createCorsHandler())
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
