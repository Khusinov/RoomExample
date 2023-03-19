package com.khusinov.roomexample


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.khusinov.roomexample.adapter.UserAdapter
import com.khusinov.roomexample.database.UserDatabase
import com.khusinov.roomexample.databinding.ActivityMainBinding
import com.khusinov.roomexample.entity.User


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"

    lateinit var userDatabase: UserDatabase
    lateinit var userAdapter: UserAdapter
    lateinit var list: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDatabase = UserDatabase.getInstance(this)

        list = ArrayList()
        list.addAll(userDatabase.userDao().getAll())
        Log.d(TAG, "onCreate: ${list.toString()}")


        userAdapter = UserAdapter(list, object : UserAdapter.OnItemClickListener {
            override fun onItemDelete(user: User, position: Int) {
                userDatabase.userDao().delete(user)
                list.remove(user)
                userAdapter.notifyItemRemoved(position)
                userAdapter.notifyItemRangeChanged(position, list.size)

            }

            override fun onItemEdit(user: User, position: Int) {

            }

        })




        binding.apply {

            rv.adapter = userAdapter


            click.setOnClickListener {
                val firstName = firstName.text
                val lastName = lastName.text
                val user = User()
                user.firstName = firstName.toString()
                user.lastName = lastName.toString()
                list.add(user)
                userAdapter.notifyItemInserted(list.size)

                userDatabase.userDao().insertAll(user)

            }


        }
    }
}