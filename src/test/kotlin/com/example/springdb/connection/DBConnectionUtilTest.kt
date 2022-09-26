package com.example.springdb.connection

import com.example.springdb.jdbc.connection.getConnection
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DBConnectionUtilTest {
    @Test
    fun connection() {
        val connection = getConnection()

        assertThat(connection).isNotNull
    }
}