package nl.infowijs.codeassignment.controllers

import io.vertx.core.json.JsonArray
import io.vertx.ext.web.RoutingContext
import java.time.Instant
import nl.infowijs.codeassignment.models.Message
import nl.infowijs.codeassignment.models.Person
import nl.infowijs.codeassignment.modules.WebResponse

class MessagesController {

  constructor() {
    print("MessagesController created")
  }

  val messages: MutableList<Message> =
      mutableListOf(
          Message(
              "Wie staat er dubbel geparkeerd in vak 14/15 met kenteken ZS-234-GA?\nIk kom mijn auto niet meer in...\n\nGraag je auto verplaatsen vóór 16:00 zodat ik weer naar huis kan!!!",
              Instant.now().minusSeconds(1500),
              Person(
                  "Karen Monster",
                  "https://images.unsplash.com/photo-1517841905240-472988babdf9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=3&w=256&h=256&q=80"
              )
          ),
          Message(
              "Hallo collega's,\n\nzouden jullie in week 35 de hele week een parkeerplaats voor mij willen reserveren?\n\nDank!",
              Instant.now().minusSeconds(43200),
              Person(
                  "Melanie de Vries",
                  "https://images.unsplash.com/photo-1517841905240-472988babdf9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=3&w=256&h=256&q=80"
              )
          )
      )

  fun listMessages(routingContext: RoutingContext) {
    val messages = JsonArray(messages.stream().sorted().map(Message::toJsonObject).toList())
    WebResponse(routingContext).end(messages)
  }

  fun createMessage(routingContext: RoutingContext) {
    val json = routingContext.body().asJsonObject()
    val message = Message.fromJsonObject(json)
    messages.add(message)
    WebResponse(routingContext).end(message.toJsonObject())
  }
}
