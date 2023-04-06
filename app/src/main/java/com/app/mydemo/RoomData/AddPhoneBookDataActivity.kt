package com.app.mydemo.RoomData

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.app.mydemo.R
import com.app.mydemo.databinding.ActivityAddPhoneBookDataBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddPhoneBookDataActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = "AddPhoneBookDataActivity"
    lateinit var binding : ActivityAddPhoneBookDataBinding
    lateinit var phoneBookDatabase: PhoneBookDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_phone_book_data)
        phoneBookDatabase = PhoneBookDatabase.getInstance(this)

        init();
    }

    fun init()
    {
        Log.e(TAG, "init()")

        binding.btnSave.setOnClickListener(this)
        binding.btnPhoneBook.setOnClickListener(this)
    }

    override fun onClick(view: View?)
    {
        when(view?.id)
        {
            R.id.btnSave ->
            {
                if (isValid())
                {
                    Log.e(TAG, "onClick()")
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO)
                        {
                            phoneBookDatabase.phoneBookDao().insert(PhoneBook(id = null,
                                binding.etName.text.toString().trim(),
                                binding.etPhone.text.toString().trim(),
                                binding.etAddress.text.toString().trim()))
                        }

                        withContext(Dispatchers.Main)
                        {
                            Log.e(TAG, "onClick() inserted")
                            startActivity(Intent(this@AddPhoneBookDataActivity, PhoneBookActivity::class.java))
                        }
                    }
                }
            }

            R.id.btnPhoneBook -> {
                Log.e(TAG, "onClick()")
                startActivity(Intent(this, PhoneBookActivity::class.java))
            }
        }
    }

    private fun isValid() : Boolean
    {
        binding.tilName.error = null
        binding.tilPhone.error = null
        binding.tilAddress.error = null

        if (TextUtils.isEmpty(binding.etName.text.toString().trim()))
        {
            binding.tilName.error = "Please enter name"
            binding.etName.requestFocus()
            return false
        }
        else if (TextUtils.isEmpty(binding.etPhone.text.toString().trim()))
        {
            binding.tilPhone.error = "Please enter phone number"
            binding.etPhone.requestFocus()
            return false
        }
        else if (TextUtils.isEmpty(binding.etAddress.text.toString().trim()))
        {
            binding.tilAddress.error = "Please enter address"
            binding.etAddress.requestFocus()
            return false
        }
        return true
    }
}