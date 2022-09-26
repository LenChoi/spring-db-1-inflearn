package com.example.springdb.jdbc.repository

import com.example.springdb.jdbc.domain.Member
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.NoSuchElementException

internal class MemberRepositoryVOTest {
    val repository = MemberRepositoryVO()

    @Test
    fun crud() {
        // save
        val member = Member("memberV7", 10000)

        repository.save(member)

        // findById
        val findMember = repository.findById(member.memberId!!)

        assertThat(findMember).isEqualTo(member)

        // update
        repository.update(member.memberId!!, 20000)

        val updatedMember = repository.findById(member.memberId!!)

        assertThat(updatedMember.money).isEqualTo(20000)

        // delete
        repository.delete(member.memberId!!)

        assertThatThrownBy { repository.findById(member.memberId!!) }.isInstanceOf(NoSuchElementException::class.java)
    }
}