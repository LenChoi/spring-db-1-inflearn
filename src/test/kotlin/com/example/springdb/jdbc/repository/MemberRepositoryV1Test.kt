package com.example.springdb.jdbc.repository

import com.example.springdb.jdbc.connection.ConnectionConst
import com.example.springdb.jdbc.domain.Member
import com.zaxxer.hikari.HikariDataSource
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.jdbc.datasource.DriverManagerDataSource
import java.util.NoSuchElementException

internal class MemberRepositoryV1Test {

    lateinit var repository: MemberRepositoryV1

    @BeforeEach
    fun beforeEach() {
        // 기본 DriverManager - 항상 새로운 커넥션을 획득
//        val dataSource =
//            DriverManagerDataSource(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD)

        // 커넥션 풀링
        val dataSource = HikariDataSource()

        dataSource.jdbcUrl = ConnectionConst.URL
        dataSource.username = ConnectionConst.USERNAME
        dataSource.password = ConnectionConst.PASSWORD

        repository = MemberRepositoryV1(dataSource)
    }

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