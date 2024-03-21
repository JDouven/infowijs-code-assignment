package nl.infowijs.codeassignment.models

import io.vertx.core.json.JsonObject
import java.time.Instant

data class Message(val id: Int, val message: String, val datetime: Instant, val person: Person) :
    Comparable<Message> {
  companion object {
    fun fromJsonObject(json: JsonObject): Message {
      return Message(json.getString("message"), Person.fromJsonObject(json.getJsonObject("person")))
    }
  }

  constructor(message: String, person: Person) : this(message, Instant.now(), person)
  constructor(
      message: String,
      datetime: Instant,
      person: Person
  ) : this((Math.random() * 100000).toInt(), message, datetime, person)

  fun toJsonObject(): JsonObject {
    return JsonObject()
        .put("id", id)
        .put("message", message)
        .put("datetime", datetime)
        .put("person", person.toJsonObject())
  }

  override fun compareTo(other: Message): Int {
    return other.datetime.compareTo(datetime)
  }
}
