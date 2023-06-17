package tw.edu.ncyu.netlab.lab_mvvm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import tw.edu.ncyu.netlab.lab_mvvm.databinding.ActivityCreateContactBinding

class CreateContactActivity : AppCompatActivity() {
    // private variable to inflate the layout for the activity
    private lateinit var binding : ActivityCreateContactBinding

    // variable to access the ViewModel class
    val viewModel : ContactViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inflate the layout
        binding = ActivityCreateContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set onClickListener for save button
        binding.save.setOnClickListener{
            createContact(it)
        }
    }

    // function to create new contact and call
    // the addContacts function from the ViewModel class
    private fun createContact(it: View?) {
        // read name and number from EditTexts
        val name = binding.etName.text.toString()
        val number = binding.etNumber.text.toString()

        // create new contact object
        val data = Contacts(null,name = name , number = number)

        // call addContacts function from the ViewModel class
        viewModel.addContacts(data)

        // display a Toast message to confirm the save
        Toast.makeText(this@CreateContactActivity, "Saved", Toast.LENGTH_SHORT).show()

        // start MainActivity
        startActivity(Intent(this@CreateContactActivity,MainActivity::class.java))
    }
}
