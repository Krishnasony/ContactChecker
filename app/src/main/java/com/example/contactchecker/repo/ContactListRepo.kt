package com.example.contactchecker.repo

import android.app.Application
import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import android.text.TextUtils
import android.util.Patterns
import com.example.contactchecker.model.ContactModel
import com.example.contactchecker.room.dao.ContactCheckerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactListRepo @Inject constructor(
    application: Application,
    val contactCheckerDao: ContactCheckerDao):ContactListRepoI {

    private val contentResolver: ContentResolver = application.contentResolver

    override suspend fun getContactList(): HashMap<String?, ContactModel> = withContext(Dispatchers.IO) {
        val map = HashMap<String?, ContactModel>()
        val cursor: Cursor? =
            contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null
            )
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // get the contact's information
                val id: String = cursor
                    .getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name: String? = cursor
                    .getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val hasPhone: Int = cursor
                    .getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                var image: String? = null
                image = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.Photo.PHOTO_THUMBNAIL_URI)
                )

                // get the user's email address
                var email: String? = null
                val cursorEmail: Cursor? = contentResolver.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", arrayOf(id),
                    null
                )
                if (cursorEmail != null && cursorEmail.moveToFirst()) {
                    email =
                        cursorEmail.getString(cursorEmail.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                    cursorEmail.close()
                }

                // get the user's phone number
                var phone: String? = null
                if (hasPhone > 0) {
                    val cursorPhone: Cursor? = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )
                    if (cursorPhone != null && cursorPhone.moveToFirst()) {
                        phone =
                            cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        cursorPhone.close()
                    }
                }

                // if the user has an email or phone then add it to contacts
                if ((!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                            && !email.equals(name, ignoreCase = true)) || !TextUtils.isEmpty(phone)
                ) {
                    val contactModel = ContactModel(0,name, email, phone!!, image, null)
                    if (!map.containsKey(phone)) map[phone] = contactModel
                }
            } while (cursor.moveToNext())
            cursor.close()
        }
         map
    }


    override suspend fun addNickName(contactModel: ContactModel) = withContext(Dispatchers.IO) {

    }
}
