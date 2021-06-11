package com.digihome.library.api.database.entity

import org.springframework.data.jpa.repository.JpaRepository
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

    var numberOfCopies: Int = 0
)

interface BooksRepository : JpaRepository<BooksEntity, String>