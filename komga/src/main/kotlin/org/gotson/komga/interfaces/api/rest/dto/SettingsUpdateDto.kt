package org.gotson.komga.interfaces.api.rest.dto

import jakarta.validation.constraints.Positive

data class SettingsUpdateDto(
  val deleteEmptyCollections: Boolean? = null,
  val deleteEmptyReadLists: Boolean? = null,
  @get:Positive
  val rememberMeDurationDays: Long? = null,
  val renewRememberMeKey: Boolean? = null,
  val thumbnailSize: ThumbnailSizeDto? = null,
  @get:Positive
  val taskPoolSize: Int? = null,
)
