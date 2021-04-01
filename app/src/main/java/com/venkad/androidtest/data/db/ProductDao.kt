package com.venkad.androidtest.data.db

import androidx.room.*
import com.venkad.androidtest.data.network.model.Products

@Dao
interface ProductDao {

//    @Insert
//    suspend fun addNote(products: Products)

    /*@Query("SELECT * from products ORDER BY id DESC")
    suspend fun getProducts() : List<Products>*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(products: List<Products>)

    /*@Query("select * FROM products WHERE id>=:id ORDER BY id DESC LIMIT 5")
    fun getProducts(id: Int?): ArrayList<Products>?*/

    /*@Insert
    suspend fun addMultipleNotes(vararg note: Note)
*/
    @Update
    suspend fun updateNote(products: Products)

    /*@Delete
    suspend fun deleteNote(note: Note)*/
}