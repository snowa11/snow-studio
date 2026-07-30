package com.example.snowstudio.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface ConversationDao {


    @Query(
        "SELECT * FROM conversations ORDER BY updatedAt DESC"
    )
    fun observeAll(): Flow<List<ConversationEntity>>


    @Query(
        "SELECT * FROM conversations WHERE id=:id"
    )
    suspend fun getById(id:Long):ConversationEntity?


    @Insert
    suspend fun insert(
        conversation: ConversationEntity
    ):Long


    @Update
    suspend fun update(
        conversation: ConversationEntity
    )


    @Delete
    suspend fun delete(
        conversation: ConversationEntity
    )


    @Query(
        """
        UPDATE conversations 
        SET updatedAt=:time
        WHERE id=:id
        """
    )
    suspend fun touch(
        id:Long,
        time:Long = System.currentTimeMillis()
    )

}