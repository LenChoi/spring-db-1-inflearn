package com.example.springdb.jdbc.repository

import com.example.springdb.jdbc.connection.getConnection
import com.example.springdb.jdbc.domain.Member
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.NoSuchElementException

class MemberRepositoryVO {

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
            pstmt!!.close()
            con.close()
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
            pstmt!!.close()
            con!!.close()
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
            pstmt!!.close()
            con!!.close()
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
}