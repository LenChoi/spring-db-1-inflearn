package com.example.springdb.connection

import com.example.springdb.jdbc.connection.ConnectionConst
import com.example.springdb.jdbc.connection.getConnection
import com.zaxxer.hikari.HikariDataSource
import org.junit.jupiter.api.Test
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

class ConnectionTest {

    @Test
    fun driverManager() {
        val con1 = getConnection()
        val con2 = getConnection()

        println("con1 = ${con1}, class= ${con1.javaClass}")
        println("con2 = ${con2}, class= ${con1.javaClass}")
    }

    @Test
    fun dataSourceDriverManager() {
        // DriverManagerDataSource - 항상 새로운 커넥션을 생성
        val dataSource =
            DriverManagerDataSource(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD)

        useDataSource(dataSource)
    }

    @Test
    fun dataSourceConnectionPool() {
        // 커넥션 풀링
        val dataSource = HikariDataSource()

        dataSource.jdbcUrl = ConnectionConst.URL
        dataSource.username = ConnectionConst.USERNAME
        dataSource.password = ConnectionConst.PASSWORD
        dataSource.maximumPoolSize = 10
        dataSource.poolName = "MyPool"

        useDataSource(dataSource)
        Thread.sleep(1000)
    }

    private fun useDataSource(dataSource: DataSource) {
        val con1 = dataSource.connection
        val con2 = dataSource.connection

        println("con1 = ${con1}, class= ${con1.javaClass}")
        println("con2 = ${con2}, class= ${con1.javaClass}")
    }
}