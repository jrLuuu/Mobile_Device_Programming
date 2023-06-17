package tw.edu.ncyu.netlab.lab_mvvm

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {
    @Query("Select * from Contacts")
    fun getAllContacts() : LiveData<List<Contacts>>

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun insertContact(contact : Contacts)

    @Delete
    fun delete(contact: Contacts)
}