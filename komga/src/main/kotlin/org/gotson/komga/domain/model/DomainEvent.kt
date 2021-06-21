package org.gotson.komga.domain.model

import java.io.Serializable
import java.net.URL

sealed class DomainEvent : Serializable {

  data class LibraryAdded(val library: Library) : DomainEvent()
  data class LibraryUpdated(val library: Library) : DomainEvent()
  data class LibraryDeleted(val library: Library) : DomainEvent()

  data class SeriesAdded(val series: Series) : DomainEvent()
  data class SeriesUpdated(val series: Series) : DomainEvent()
  data class SeriesDeleted(val series: Series) : DomainEvent()

  data class BookAdded(val book: Book) : DomainEvent()
  data class BookUpdated(val book: Book) : DomainEvent()
  data class BookDeleted(val book: Book) : DomainEvent()
  data class BookImported(val book: Book?, val sourceFile: URL, val success: Boolean, val message: String? = null) : DomainEvent()

  data class CollectionAdded(val collection: SeriesCollection) : DomainEvent()
  data class CollectionUpdated(val collection: SeriesCollection) : DomainEvent()
  data class CollectionDeleted(val collection: SeriesCollection) : DomainEvent()

  data class ReadListAdded(val readList: ReadList) : DomainEvent()
  data class ReadListUpdated(val readList: ReadList) : DomainEvent()
  data class ReadListDeleted(val readList: ReadList) : DomainEvent()

  data class ReadProgressChanged(val progress: ReadProgress) : DomainEvent()
  data class ReadProgressDeleted(val progress: ReadProgress) : DomainEvent()

  data class ThumbnailBookAdded(val thumbnail: ThumbnailBook) : DomainEvent()
  data class ThumbnailSeriesAdded(val thumbnail: ThumbnailSeries) : DomainEvent()
}
