package com.example.snowstudio.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface MessageDao {


    @Query(
        """
        SELECT * FROM messages
        WHERE conversationId=:conversationId
        ORDER BY timestamp ASC
        """
    )
    fun observeForConversation(
        conversationId:Long
    ):Flow<List<MessageEntity>>


    @Query(
        """
        SELECT * FROM messages
        WHERE conversationId=:conversationId
        ORDER BY timestamp ASC
        """
    )
    suspend fun getForConversation(
        conversationId:Long
    ):List<MessageEntity>


    @Insert
    suspend fun insert(
        message:MessageEntity
    ):Long


    @Query(
        """
        DELETE FROM messages
        WHERE conversationId=:conversationId
        """
    )
    suspend fun deleteForConversation(
        conversationId:Long
    )

}