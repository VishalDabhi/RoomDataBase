package com.app.mydemo.RoomData

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.mydemo.R
import com.app.mydemo.databinding.ActivityPhoneBookBinding
import com.app.mydemo.databinding.DialogPhoneBookUpdateBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhoneBookActivity : AppCompatActivity(), View.OnClickListener, OnClickListener {

    private val TAG = "PhoneBookActivity"
    private lateinit var binding: ActivityPhoneBookBinding
    private var phoneBookList: ArrayList<PhoneBook> = arrayListOf()
    private lateinit var phoneBookAdapter: PhoneBookAdapter
    lateinit var phoneBookDatabase: PhoneBookDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_book)
        phoneBookDatabase = PhoneBookDatabase.getInstance(this)

        init()
    }

    private fun init() {
        Log.e(TAG, "init()")

        binding.rvPhoneBook.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        phoneBookAdapter = PhoneBookAdapter(this, phoneBookList, this)
        binding.rvPhoneBook.adapter = phoneBookAdapter

        getAllContact();
    }

    override fun onClick(view: View?) {
        when (view?.id) {

        }
    }

    private fun getAllContact()
    {
        Log.e(TAG, "getAllContact()")

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                phoneBookList.clear()
                phoneBookList.addAll(phoneBookDatabase.phoneBookDao().getNotes())
            }
            withContext(Dispatchers.Main) {
                phoneBookAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onItemClickPhoneBook(phoneBook: PhoneBook)
    {
        Log.e(TAG, "onItemClickPhoneBook()")

        val dialogBottom = BottomSheetDialog(this)
        dialogBottom.setCancelable(false)
        val binding = DialogPhoneBookUpdateBinding.inflate(LayoutInflater.from(this))
        dialogBottom.setContentView(binding.root)

        if (phoneBook.name.isNotEmpty())
        {
            binding.etName.setText(phoneBook.name)
        }
        if (phoneBook.number.isNotEmpty())
        {
            binding.etPhone.setText(phoneBook.number)
        }
        if (phoneBook.address.isNotEmpty())
        {
            binding.etAddress.setText(phoneBook.address)
        }

        binding.btnUpdate.setOnClickListener {
            if (isValid(binding))
            {
                dialogBottom.dismiss()
                callUpdatePhoneBook(binding, phoneBook.id)
            }
        }

        binding.btnCancel.setOnClickListener {
            dialogBottom.dismiss()
        }

        dialogBottom.show()
    }

    private fun isValid(binding: DialogPhoneBookUpdateBinding) : Boolean
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

    private fun callUpdatePhoneBook(binding: DialogPhoneBookUpdateBinding, id: Int?)
    {
        Log.e(TAG, "callUpdatePhoneBook()")

        lifecycleScope.launch {
            withContext(Dispatchers.IO)
            {
                phoneBookDatabase.phoneBookDao().update(PhoneBook(id,
                    binding.etName.text.toString().trim(),
                    binding.etPhone.text.toString().trim(),
                    binding.etAddress.text.toString().trim()))
            }

            withContext(Dispatchers.Main)
            {
                Log.e(TAG, "onClick() inserted")
                getAllContact()
            }
        }
    }
    override fun onItemRemovePhoneBook(phoneBook: PhoneBook)
    {
        Log.e(TAG, "onItemRemovePhoneBook()")

        lifecycleScope.launch {
            withContext(Dispatchers.IO)
            {
                phoneBookDatabase.phoneBookDao().delete(phoneBook)
            }

            withContext(Dispatchers.Main)
            {
                Log.e(TAG, "onClick() inserted")
                getAllContact()
            }
        }
    }
}