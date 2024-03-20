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
}
