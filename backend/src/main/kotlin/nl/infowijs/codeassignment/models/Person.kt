package nl.infowijs.codeassignment.models

import io.vertx.core.json.JsonObject

data class Person(
    val name: String,
    val avatar: String,
    val email: String? = null,
    val phone: String? = null
) {
  fun toJsonObject(): JsonObject {
    return JsonObject()
        .put("name", name)
        .put("avatar", avatar)
        .apply {
          if (!email.isNullOrBlank()) {
            this.put("email", email)
          }
        }
        .put("phone", phone)
  }

  companion object {
    fun fromJsonObject(json: JsonObject): Person {
      return Person(
          json.getString("name"),
          json.getString("avatar"),
          json.getString("email"),
          json.getString("phone")
      )
    }
  }
}
