package com.digihome.library.api.database.entity

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * Created by saurabhbilakhia on 2021-03-14
 */

@Entity
@Table(name = "Books")
class BooksEntity (
    @Id
    var id: String = "",

    var bookName: String = "",

    var author: String = "",

    var publication: String = "",

    var language: String = "",

    var location: String = "",

    var numberOfCopies: Int = 0,

    var createdDateTime: LocalDateTime = LocalDateTime.now(),

    var lastUpdatedDateTime: LocalDateTime = LocalDateTime.now()
)

interface BooksRepository : JpaRepository<BooksEntity, String> {
    fun findAllByOrderByCreatedDateTimeAsc() : List<BooksEntity>?
    fun findByLanguageOrderByBookNameAsc(language: String) : List<BooksEntity>?
}