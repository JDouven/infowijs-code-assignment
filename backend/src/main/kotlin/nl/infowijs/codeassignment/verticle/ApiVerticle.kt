package nl.infowijs.codeassignment.verticle

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CorsHandler
import nl.infowijs.codeassignment.controllers.ChatController
import nl.infowijs.codeassignment.controllers.HealthController
import nl.infowijs.codeassignment.controllers.MessageController
import nl.infowijs.codeassignment.repository.ChatRepository
import nl.infowijs.codeassignment.repository.MessageRepository
import nl.infowijs.codeassignment.service.ChatService
import nl.infowijs.codeassignment.service.MessageService
import nl.infowijs.codeassignment.utils.DbUtils.buildDbClient


class ApiVerticle : AbstractVerticle() {
  override fun start(startPromise: Promise<Void>) {
    val dbClient = buildDbClient(vertx)

    val chatRepository = ChatRepository()
    val chatService = ChatService(dbClient, chatRepository)
    val chatController = ChatController(chatService)

    val messageRepository = MessageRepository()
    val messageService = MessageService(dbClient, messageRepository)
    val messageController = MessageController(messageService)

    val router = Router.router(vertx).apply {
      route().handler(CorsHandler.create())
      route().handler(BodyHandler.create())
      get("/healthz").handler(HealthController::healthCheck)
      get("/chats").handler(chatController::readAll)
      post("/chats").handler(chatController::create)
      get("/chats/:chatId").handler(messageController::readAll)
      post("/chats/:chatId").handler(messageController::create)
    }

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
