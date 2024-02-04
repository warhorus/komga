package org.gotson.komga.interfaces.api.rest

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.asSequence

@RestController
@RequestMapping("api/v1/filesystem", produces = [MediaType.APPLICATION_JSON_VALUE])
@PreAuthorize("hasRole('ADMIN')")
class FileSystemController {
  private val fs = FileSystems.getDefault()

  @PostMapping
  fun getDirectoryListing(
    @RequestBody(required = false) request: DirectoryRequestDto = DirectoryRequestDto(),
  ): DirectoryListingDto =
    if (request.path.isEmpty()) {
      DirectoryListingDto(
        directories = fs.rootDirectories.map { it.toDto() },
      )
    } else {
      val p = fs.getPath(request.path)
      if (!p.isAbsolute) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Path must be absolute")
      try {
        DirectoryListingDto(
          parent = (p.parent ?: "").toString(),
          directories =
            Files.list(p).use { dirStream ->
              dirStream.asSequence()
                .filter { Files.isDirectory(it) }
                .filter { !Files.isHidden(it) }
                .sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.toString() })
                .map { it.toDto() }
                .toList()
            },
        )
      } catch (e: Exception) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Path does not exist")
      }
    }
}

data class DirectoryRequestDto(
  val path: String = "",
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DirectoryListingDto(
  val parent: String? = null,
  val directories: List<PathDto>,
)

data class PathDto(
  val type: String,
  val name: String,
  val path: String,
)

fun Path.toDto(): PathDto =
  PathDto(
    type = if (Files.isDirectory(this)) "directory" else "file",
    name = (fileName ?: this).toString(),
    path = toString(),
  )
