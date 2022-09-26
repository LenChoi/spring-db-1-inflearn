package com.example.springdb.jdbc.connection

import java.lang.IllegalStateException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

fun getConnection(): Connection {
    return try {
        DriverManager.getConnection(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD)
    } catch (e: SQLException) {
        throw IllegalStateException(e)
    }
}