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
@Table(name = "BookIssue")
data class BookIssueEntity (
    @Id
    var id: String = "",

    var userId: String = "",

    var bookId: String = "",

    var issueDate: LocalDateTime = LocalDateTime.now(),

    var returnDate: LocalDateTime? = null
)

interface BookIssueRepository : JpaRepository<BookIssueEntity, String> {
    fun findByUserIdAndBookIdAndReturnDateIsNull(userId: String, bookId: String) : BookIssueEntity?
}