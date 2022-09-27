package com.example.springdb.jdbc.repository

import com.example.springdb.jdbc.connection.getConnection
import com.example.springdb.jdbc.domain.Member
import org.springframework.jdbc.support.JdbcUtils
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.NoSuchElementException
import javax.sql.DataSource

/**
 * JDBC - DataSource 사용, JdbcUtils 사용
 */
class MemberRepositoryV1(
    private val dataSource: DataSource
) {

    fun save(member: Member) {
        val sql = "insert into member(member_id, money) values (?, ?)"

        var con: Connection? = null
        var pstmt: PreparedStatement? = null

        con = getConnection()

        try {
            pstmt = con.prepareStatement(sql)
            pstmt.setString(1, member.memberId)
            pstmt.setInt(2, member.money!!)
            pstmt.executeUpdate()
        } catch (e: SQLException) {
            e.printStackTrace()
            throw e
        } finally {
            JdbcUtils.closeStatement(pstmt)
            JdbcUtils.closeConnection(con)
        }
    }

    fun findById(memberId: String): Member {
        val sql = "select * from member where member_id = ?"

        var con: Connection? = null
        var pstmt: PreparedStatement? = null
        var rs: ResultSet? = null

        return try {
            con = getConnection()
            pstmt = con.prepareStatement(sql)
            pstmt.setString(1, memberId)

            rs = pstmt.executeQuery()

            if (rs.next()) {
                val member = Member()

                member.memberId = rs.getString("member_id")
                member.money = rs.getInt("money")

                member
            } else {
                throw NoSuchElementException("member not found")
            }
        } catch (e: SQLException) {
            throw e
        } finally {
            JdbcUtils.closeResultSet(rs)
            JdbcUtils.closeStatement(pstmt)
            JdbcUtils.closeConnection(con)
        }
    }

    fun update(memberId: String, money: Int) {
        val sql = "update member set money=? where member_id=?"

        var con: Connection? = null
        var pstmt: PreparedStatement? = null

        try {
            con = getConnection()
            pstmt = con.prepareStatement(sql)
            pstmt.setInt(1, money)
            pstmt.setString(2, memberId)

            val resultSize = pstmt.executeUpdate()

            println("resultSize = $resultSize")
        } catch (e: SQLException) {
            throw e
        } finally {
            JdbcUtils.closeStatement(pstmt)
            JdbcUtils.closeConnection(con)
        }
    }

    fun delete(memberId: String) {
        val sql = "delete from member where member_id=?"

        var con: Connection? = null
        var pstmt: PreparedStatement? = null

        try {
            con = getConnection()
            pstmt = con.prepareStatement(sql)
            pstmt.setString(1, memberId)
            pstmt.executeUpdate()
        } catch (e: SQLException) {
            throw e
        } finally {
            pstmt!!.close()
            con!!.close()
        }
    }

    private fun getConnection(): Connection {
        val con = dataSource.connection

        println("get connection: $con, class: ${con.javaClass}")

        return con
    }
}