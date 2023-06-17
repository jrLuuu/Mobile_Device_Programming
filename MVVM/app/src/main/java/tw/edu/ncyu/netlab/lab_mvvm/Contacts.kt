package tw.edu.ncyu.netlab.lab_mvvm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contacts")
class Contacts (
    @PrimaryKey(autoGenerate = true)
    var id : Int?=null,
    var name : String,
    var number : String
)