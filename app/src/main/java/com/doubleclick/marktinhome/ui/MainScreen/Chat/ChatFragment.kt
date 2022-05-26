package com.doubleclick.marktinhome.ui.MainScreen.Chat

import android.Manifest
import android.animation.Animator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.app.DownloadManager
import android.app.ProgressDialog
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.provider.DocumentsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.webkit.URLUtil
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devlomi.record_view.OnRecordListener
import com.devlomi.record_view.RecordButton
import com.devlomi.record_view.RecordView
import com.doubleclick.Api.APIService
import com.doubleclick.OnMessageClick
import com.doubleclick.Util.KeyboardUtil
import com.doubleclick.ViewModel.ChatViewModel
import com.doubleclick.ViewModel.UserViewModel
import com.doubleclick.downloader.*
import com.doubleclick.marktinhome.Adapters.BaseMessageAdapter
import com.doubleclick.marktinhome.BaseFragment
import com.doubleclick.marktinhome.Database.ChatViewModelDatabase
import com.doubleclick.marktinhome.Model.*
import com.doubleclick.marktinhome.Model.Constantes.CHATS
import com.doubleclick.marktinhome.Model.Constantes.USER
import com.doubleclick.marktinhome.Notifications.Client
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.Repository.ChatReopsitory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.vanniktech.emoji.EmojiPopup
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.keyword_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.Error


class ChatFragment : BaseFragment(), OnMapReadyCallback, OnMessageClick, ChatReopsitory.StatusChat {

    private lateinit var sendText: ImageView
    private lateinit var et_text_message: EditText
    private lateinit var chatRecycler: RecyclerView;
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var chatAdapter: BaseMessageAdapter
    private lateinit var recordView: RecordView
    private lateinit var sendRecord: RecordButton
    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var googleMap: GoogleMap
    var mLocationRequest: LocationRequest? = null
    var client: FusedLocationProviderClient? = null
    private var PICK_CONTACT = 100
    private val REQUEST_CODE = 101;
    private lateinit var file: ImageView
    private lateinit var video: ImageView
    private lateinit var contact: ImageView
    private lateinit var image: ImageView
    private lateinit var location: ImageView
    private lateinit var emotion: ImageView
    private lateinit var attach_file: ImageView
    private lateinit var layout_text: ConstraintLayout
    private lateinit var profile_image: CircleImageView
    private lateinit var continer_attacht: ConstraintLayout;
    private lateinit var username: TextView;
    private lateinit var status: TextView;
    private lateinit var apiService: APIService
    private lateinit var toolbar: Toolbar
    private var sharePost: String = "null".toString()
    private lateinit var chatViewModelDatabase: ChatViewModelDatabase
    private lateinit var userViewModel: UserViewModel
    private lateinit var storageReference: StorageReference
    private lateinit var option: ImageView;
    var fileType: String? = null
    private var chats: ArrayList<Chat> = ArrayList();
    var audioPath: String? = null
    private var cklicked = true
    private var user: User? = null
    private var userId: String = "null"
    private var id: Long = 0
    private val uri: Uri? = null
    private var manager: DownloadManager? = null
    private var request: DownloadManager.Request? = null
    private lateinit var broadcastReceiver: BroadcastReceiver;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (!it.isEmpty) {
                sharePost = it.getString("sharePost").toString()
            }
            if (!it.isEmpty) {
                userId = it.getString("userId").toString()
            }
            if (it.isEmpty) {
                val User by navArgs<ChatFragmentArgs>()
                userId = User.userId.toString();
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged", "UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        sendText = view.findViewById(R.id.sendText);
        et_text_message = view.findViewById(R.id.et_text_message);
        continer_attacht = view.findViewById(R.id.continer_attacht);
        chatRecycler = view.findViewById(R.id.chatRecycler);
        chatRecycler.setHasFixedSize(true);
        sendRecord = view.findViewById(R.id.sendRecord);
        recordView = view.findViewById(R.id.recordView);
        emotion = view.findViewById(R.id.emotion);
        layout_text = view.findViewById(R.id.layout_text)
        attach_file = view.findViewById(R.id.attach_file);
        sendRecord.setRecordView(recordView)
        file = view.findViewById(R.id.file)
        option = view.findViewById(R.id.option);
        location = view.findViewById(R.id.location)
        image = view.findViewById(R.id.image)
        video = view.findViewById(R.id.video)
        contact = view.findViewById(R.id.contact)
        profile_image = view.findViewById(R.id.profile_image)
        username = view.findViewById(R.id.username)
        status = view.findViewById(R.id.status)
        toolbar = view.findViewById(R.id.toolbar)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.getUserById(userId)
        userViewModel.userInfo.observe(viewLifecycleOwner) {
            user = it
            Glide.with(requireContext()).load(user!!.image).into(profile_image)
            username.text = user!!.name;
            status.text = user!!.status;
        }
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService::class.java)
        client = LocationServices.getFusedLocationProviderClient(requireActivity())
        chatViewModelDatabase = ViewModelProvider(this)[ChatViewModelDatabase::class.java]
        // TODO get All chat from database and put it in arrayList
        chats.addAll(chatViewModelDatabase.getListData(myId, userId));
        chatAdapter = BaseMessageAdapter(chats, this, myId);
        chatRecycler.adapter = chatAdapter
        chatAdapter.notifyDataSetChanged()
        chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        chatViewModel.ChatById(userId, this)
        if (sharePost != "null") {
            et_text_message.setText(sharePost)
            sendRecord.visibility = View.GONE
            sendText.visibility = View.VISIBLE
        }

        sendText.setOnClickListener {
            sentMessage(et_text_message.text.toString().trim(), "text")
        }
        chatViewModel.newInsertChat().observe(viewLifecycleOwner) {
            try {
                /*
                * work when i closed connection and open again
                * it's see what message is deleted and updated it in database and in ArrayList
                * and if message sent and deleted before stored in database -> exception
                * */
                if (it.receiver.equals(myId) && it.message.contains("@$@this@message@deleted")) {
                    Log.e("deleted", it.toString())
                    try {
                        chats[chats.indexOf(it)] = it;
                        chatAdapter.notifyItemChanged(chats.indexOf(it))
                        chatAdapter.notifyDataSetChanged()
                        chatViewModelDatabase.update(it);
                    } catch (e: IOException) {
                        Log.e("DatabaseExption216", e.message.toString());
                    } catch (e: IndexOutOfBoundsException) {
                        Log.e("ArrayListExp218", e.message.toString());
                    } catch (e: Exception) {
                        Log.e("Exception220", e.message.toString());
                    }

                }
                if (it.sender.equals(myId) && !it.isSeen && !it.message.contains("@$@this@message@deleted")) {
                    if (!chats.contains(it)) {
                        chats.add(it)
                        chatAdapter.notifyItemInserted(chats.size - 1)
                        chatAdapter.notifyDataSetChanged()
                        chatRecycler.scrollToPosition(chats.size - 1)
                        chatRecycler.smoothScrollToPosition(chats.size - 1)
                    }
                }
                /*
                * if I'm receiver -> update that i see this message
                * */
                if (it.receiver.equals(myId)) {
                    // TODO update status massage to been seen
                    val map: HashMap<String, Any> = HashMap();
                    map["StatusMessage"] = "beenSeen" //"beenSeen" , "Uploaded"
                    map["seen"] = true
                    reference.child(CHATS).child(myId).child(userId).child(it.id)
                        .updateChildren(map);
                    reference.child(CHATS).child(userId).child(myId).child(it.id)
                        .updateChildren(map);
                }
            } catch (e: Exception) {

            }


        };


        val emojiPopup =
            EmojiPopup.Builder.fromRootView(view.findViewById(R.id.root_view)).build(
                et_text_message
            )
        emotion.setOnClickListener {
            if (cklicked) {
                emojiPopup.toggle()
                cklicked = false
                emotion.setImageDrawable(resources.getDrawable(R.drawable.keyboard))
            } else {
                emojiPopup.dismiss()
                cklicked = true
                emotion.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_insert_emoticon_24))
            }
        }
        attach_file.setOnClickListener {
            if (continer_attacht.visibility == View.GONE) {
                showLayout()
                KeyboardUtil.getInstance(requireActivity()).closeKeyboard();
            } else {
                hideLayout()
            }
        }
        et_text_message.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                sendRecord.isListenForRecord = true
                layout_text.visibility = View.VISIBLE
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isEmpty()) {
                    status("online")
                    sendRecord.visibility = View.VISIBLE
                    sendText.visibility = View.GONE
                    recordView.visibility = View.VISIBLE
                    sendRecord.isListenForRecord = true
                    layout_text.visibility = View.GONE
                    attach_file.visibility = View.VISIBLE
                } else {
                    layout_text.visibility = View.VISIBLE
                    sendRecord.isListenForRecord = true
                    //IMPORTANT
                    sendRecord.visibility = View.GONE
                    sendText.visibility = View.VISIBLE
                    recordView.visibility = View.GONE
                    attach_file.visibility = View.GONE
                    status("typing...")
                    if (sendRecord.isListenForRecord) {
                        sendRecord.isListenForRecord = false
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {
                sendRecord.isListenForRecord = true
                layout_text.visibility = View.VISIBLE
            }
        })
        //Cancel Bounds is when the Slide To Cancel text gets before the timer . default is 8
        //Cancel Bounds is when the Slide To Cancel text gets before the timer . default is 8
        recordView.cancelBounds = 8f
        recordView.setSmallMicColor(Color.parseColor("#FF4081"))
        //prevent recording under one Second
        //prevent recording under one Second
        recordView.setLessThanSecondAllowed(false)
        recordView.setSlideToCancelText("Slide To Cancel")
        recordView.setCustomSounds(R.raw.record_start, R.raw.record_finished, 0)
        recordView.setOnRecordListener(object : OnRecordListener {
            override fun onStart() {
                status("recording...")
                recordView.visibility = View.VISIBLE
                layout_text.visibility = View.INVISIBLE
                setUpRecording()
                try {
                    mediaRecorder.prepare()
                    mediaRecorder.start()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                sendText.visibility = View.GONE
                Log.d("RecordView", "onStart")
                Toast.makeText(requireContext(), "OnStartRecord", Toast.LENGTH_SHORT).show()
            }

            override fun onCancel() {
                mediaRecorder.reset()
                mediaRecorder.release()
                status("online")
                val file = audioPath?.let { File(it) }
                if (file != null) {
                    if (file.exists()) {
                        file.delete()
                    }
                }
                layout_text.visibility = View.VISIBLE
            }

            override fun onFinish(recordTime: Long) {
                layout_text.visibility = View.VISIBLE
                status("online")
                try {
                    mediaRecorder.stop()
                    mediaRecorder.release()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                sendText.visibility = View.VISIBLE
                sendRecodingMessage(audioPath)
            }

            override fun onLessThanSecond() {
                mediaRecorder.reset()
                mediaRecorder.release()
                val file = audioPath?.let { File(it) }
                if (file != null) {
                    if (file.exists()) {
                        file.delete()
                    }
                }
                layout_text.visibility = View.VISIBLE
                sendText.visibility = View.VISIBLE
                status("online")
            }
        })
        recordView.setOnBasketAnimationEndListener {
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
            Log.d("RecordView", "Basket Animation Finished")
        }
        image.setOnClickListener {
            fileType = "image"
            openFiles("image/*")
        }
        video.setOnClickListener {
            fileType = "video"
            openFiles("video/*")
        }
        file.setOnClickListener {
            fileType = "file"
            openFiles("application/*")
        }
        location.setOnClickListener {
            getMyLocation()
        }
        contact.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            startActivityForResult(intent, PICK_CONTACT)
        }
        option.setOnClickListener { v ->
            val pupMenu = PopupMenu(requireContext(), v);
            pupMenu.menuInflater.inflate(R.menu.delete_all_chat, pupMenu.menu);
            pupMenu.setOnMenuItemClickListener {
                if (it.itemId == R.id.delete_all) {
                    chatViewModelDatabase.deleteAll();
                    chats.clear()
                    Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show()
                }
                true;
            }
            pupMenu.show()
        }

        return view;
    }

    private fun sentMessage(text: String, type: String) {
        val map: HashMap<String, Any> = HashMap()
        val time = Date().time;
        val id = reference.push().key.toString() + time;
        map["sender"] = myId
        map["message"] = text
        map["type"] = type
        map["receiver"] = userId // Id of Friend
        map["date"] = time
        map["id"] = id
        map["StatusMessage"] = "Uploaded"
        val chat = Chat(text, "", type, myId, userId, time, id, "Uploaded", false);
        chatViewModelDatabase.insert(chat);
        upload(id, map);
        et_text_message.setText("")
        makeChatList();
        sendNotifiaction(text);


    }

    private fun upload(id: String, map: HashMap<String, Any>) {
        reference.child(CHATS).child(myId).child(userId.toString())
            .child(id).updateChildren(map);
        reference.child(CHATS).child(userId.toString()).child(myId)
            .child(id).updateChildren(map);
    }

    private fun makeChatList() {
        val map1: HashMap<String, Any> = HashMap();
        map1["id"] = userId
        map1["time"] = -1 * Date().time;
        reference.child(Constantes.CHAT_LIST).child(myId).child(userId).updateChildren(map1)
        val map2: HashMap<String, Any> = HashMap();
        map2["id"] = myId
        map2["time"] = -1 * Date().time;
        reference.child(Constantes.CHAT_LIST).child(userId).child(myId).updateChildren(map2)

    }

    private fun getMyLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val task = client!!.lastLocation
            task.addOnSuccessListener { location ->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    val uri = "[" + location.latitude + "," + location.longitude + "]"
                    sentMessage(uri, "location")
                } else {
                    Toast.makeText(requireContext(), "Open your location", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


    private fun getFilePath(): String {
        val contextWrapper = ContextWrapper(requireContext())
        val file = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val date = Date()
        val f = File(file, "chat" + date.time + ".mp3")
        return f.path
    }

    private fun setUpRecording() {
        mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        val file = File(
            Environment.getExternalStorageDirectory(),
            "Chat"
        ) // String f = "/storage/emulated/0/Download";
        if (!file.exists()) {
            file.mkdirs()
        }
        audioPath =
            getFilePath() //file.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".3gp";
        mediaRecorder.setOutputFile(audioPath)
    }


    private fun openFiles(mimeType: String) {
        val intent = Intent()
        intent.type = mimeType
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun status(status: String) {
        val hashMap = HashMap<String, Any>()
        hashMap["status"] = status
        reference.child(USER).child(myId).updateChildren(hashMap)
    }

    override fun onResume() {
        super.onResume()
        status("online")
        currentUser(userId)
    }

    override fun onPause() {
        super.onPause()
        status("offline")
        currentUser("none")
    }

    private fun currentUser(userid: String) {
        val editor: SharedPreferences.Editor =
            requireActivity().getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
        editor.putString("currentuser", userid)
        editor.apply()
    }


    fun sendRecodingMessage(audioPath: String?) {
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Uploading")
        progressDialog.show()
        if (audioPath != null) {
            val storageReference = FirebaseStorage.getInstance()
                .getReference("/ChatData/Recording/" + myId + ":" + userId.toString() + System.currentTimeMillis())
            Log.e("audio path", audioPath)
            val audioFile = Uri.fromFile(File(audioPath))
            Log.e("audioFile = ", audioFile.toString())
            storageReference.putFile(audioFile)
                .addOnSuccessListener { success: UploadTask.TaskSnapshot ->
                    val audioUrl = success.storage.downloadUrl
                    audioUrl.addOnCompleteListener { path: Task<Uri> ->
                        if (path.isSuccessful) {
                            val url = path.result.toString()
                            val map: HashMap<String, Any> = HashMap()
                            val time = Date().time
                            val id = reference.push().key.toString() + time;
                            map["sender"] = myId
                            map["receiver"] = userId.toString()
                            map["message"] = url
                            map["type"] = "voice"
                            map["id"] = id
                            map["date"] = time
                            map["StatusMessage"] = "Uploaded" // "Stored" , "beenSeen"
                            map["uri"] = audioPath;
                            val chat = Chat(
                                url,
                                audioPath,
                                "voice",
                                myId,
                                userId,
                                time,
                                id,
                                "Uploaded",
                                false
                            );
                            chatViewModelDatabase.insert(chat);
//                            reference.child(CHATS).child(id).setValue(map)
                            upload(id, map);
                            progressDialog.dismiss()
                            makeChatList()
                            sendNotifiaction("audio")
                        } else {
                            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                        }
                    }
                }.addOnProgressListener {
                    val p: Double =
                        100.0 * it.bytesTransferred / it.totalByteCount
                    progressDialog.setMessage("${p.toInt()} % Uploading...")
                }.addOnFailureListener {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }

        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 1000
        mLocationRequest!!.fastestInterval = 1000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun getFileExtension(uri: Uri?): String? {
        val contentResolver = requireContext().contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri!!))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            sendFileData(data.data!!)
        } else if (requestCode == PICK_CONTACT && resultCode == Activity.RESULT_OK) {
            PickConact(data!!.data)
        }
    }

    @SuppressLint("Range")
    fun PickConact(contactData: Uri?) {
        val c = requireActivity().managedQuery(contactData, null, null, null, null)
        if (c.moveToFirst()) {
            val id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
            val hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
            if (hasPhone.equals("1", ignoreCase = true)) {
                val phones = requireContext().contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                    null, null
                )
                phones!!.moveToFirst()
                val cNumber = phones.getString(phones.getColumnIndex("data1"))
                val name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                Log.e("NameContact", name + cNumber)
                val message = """
                    $name
                    $cNumber
                    """.trimIndent()
                sentMessage(message, "contact")
            }
        }
    }

    private fun showLayout() {
        val radius = Math.max(continer_attacht.width, continer_attacht.height).toFloat()
        val animator =
            ViewAnimationUtils.createCircularReveal(
                continer_attacht,
                continer_attacht.left,
                continer_attacht.top,
                0f,
                radius * 2
            )
        animator.duration = 500
        continer_attacht.visibility = View.VISIBLE
        animator.start()
    }


    private fun hideLayout() {
        val radius = Math.max(continer_attacht.width, continer_attacht.height).toFloat()
        val animator =
            ViewAnimationUtils.createCircularReveal(
                continer_attacht,
                continer_attacht.left,
                continer_attacht.top,
                radius * 2,
                0f
            )
        animator.duration = 500
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                continer_attacht.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animator.start()
    }

    fun sendFileData(uri: Uri) {
        val uploadTask: StorageTask<*>
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Uploading")
        progressDialog.show()
        if (uri.toString() != "") {
            storageReference =
                FirebaseStorage.getInstance().getReference("/ChatData/Files")
            val fileReference = storageReference.child(
                System.currentTimeMillis().toString() + "." + getFileExtension(uri)
            )
            uploadTask = fileReference.putFile(uri).addOnSuccessListener {
                val url = it.storage.downloadUrl
                url.addOnCompleteListener {
                    if (it.isSuccessful) {
                        val url = it.result.toString()
                        val map = HashMap<String, Any>()
                        val time = Date().time;
                        val id = reference.push().key.toString() + time
                        map["sender"] = myId
                        map["receiver"] = userId
                        map["message"] = url
                        map["type"] = fileType.toString()
                        map["id"] = id
                        map["date"] = time
                        map["StatusMessage"] = "Uploaded" // "Stored" , "beenSeen"
                        map["uri"] = uri.toString()
                        val chat =
                            Chat(
                                url,
                                uri.toString(),
                                fileType.toString(),
                                myId,
                                userId,
                                time,
                                id,
                                "Uploaded",
                                false
                            );
                        //Chat{id='-N30jeKZgiyyHFSSKP6H1653591678996', message='https://firebasestorage.googleapis.com/v0/b/marketinhome-99d25.appspot.com/o/ChatData%2FFiles%2F1653591666788.mp4?alt=media&token=bca7773d-f434-48ee-8a1f-e273593906ca', type='video', sender='WoWDlmZx7lUwRr9ZD2LAkHRwkoi1', receiver='FkyB9ppQAlQcPQZ3F8tN24kLzbg1', date=1653591678996, StatusMessage='Uploaded', seen=false, uri='content://com.android.providers.media.documents/document/video%3A380701'}
                        Log.e("FileSent", chat.toString());
                        chatViewModelDatabase.insert(chat);
                        upload(id, map);
                        progressDialog.dismiss()
                        makeChatList()
                        sendNotifiaction(fileType.toString())
                    } else {
                        Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                    }
                }
            }.addOnProgressListener {
                val p: Double =
                    100.0 * it.bytesTransferred / it.totalByteCount
                progressDialog.setMessage("${p.toInt()} % Uploading...")
            }.addOnFailureListener {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        } else {
            Toast.makeText(context, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendNotifiaction(message: String) {
        val data = Data(
            myId,
            R.drawable.shopping_cart,
            "${user!!.name.toString()}: $message",
            "New Message",
            userId.toString()
        )
        val sender = Sender(data, user!!.token.toString())
        apiService.sendNotification(sender)
            .enqueue(object : Callback<MyResponse> {
                override fun onResponse(
                    call: Call<MyResponse>,
                    response: Response<MyResponse>
                ) {
                    if (response.code() == 200) {
                        if (response.body()!!.success != 1) {
                            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

                override fun onFailure(call: Call<MyResponse>, t: Throwable) {}
            })
    }


    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Throws(java.lang.Exception::class)
    override fun download(chat: Chat, pos: Int) {
        try {
            Log.e("CHATTTTTTTT", chat.toString() + "    " + pos);
            PRDownloaderInit(chat, pos)
            //=============================another way to download=======================================
            /* request = DownloadManager.Request(Uri.parse(chat.message))
             request!!.setDestinationInExternalPublicDir(
                 Environment.DIRECTORY_DOWNLOADS,
                 "" + chat.type + Date().time
             )
             request!!.setTitle(requireContext().resources.getString(R.string.app_name))
             request!!.setDescription("Downloading..." + chat.type)
             request!!.allowScanningByMediaScanner() // if you want to be available from media players
             request!!.setVisibleInDownloadsUi(true)
             request!!.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // to notify when download is complete
             manager = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
             id = manager!!.enqueue(request)
             requireContext().registerReceiver(
                 broadcastReceiver,
                 IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
             )
             broadcastReceiver = object : BroadcastReceiver() {
                 override fun onReceive(context: Context, intent: Intent) {
                     val ID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                     if (id == ID) {
                         Log.e("LOOOP", "Loop");
                         val c = chat;
                         c.uri =
                             manager!!.getUriForDownloadedFile(manager!!.enqueue(request)).toString()
                         chatViewModelDatabase.update(c)
                         chats[pos] = c;
                         chatAdapter.notifyItemChanged(pos);
                         chatAdapter.notifyDataSetChanged()
                     }
                 }
             }*/
            //=============================another way to download=======================================


        } catch (e: IllegalStateException) {
            Log.e("ExceptionChat845", e.message!!)
        } catch (e: NullPointerException) {
            Log.e("ExceptionChat847", e.message!!)
        } catch (e: Exception) {
            Log.e("ExceptionChat849", e.message!!)
        }
    }

    //===============================PRDownloader========================
    //https://github.com/MindorksOpenSource/PRDownloader
    //https://www.youtube.com/watch?v=olOCFo3mCQs&ab_channel=MAKENIQAL
    private fun PRDownloaderInit(chat: Chat, pos: Int) {
        var downloadId = 0;
        val dialog: ProgressDialog = ProgressDialog(requireContext())
        dialog.setTitle("Downloading ...")
        dialog.setMessage("Preparing ...")
        dialog.setCancelable(false)
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setButton(
            DialogInterface.BUTTON_NEGATIVE,
            "Cancel",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                PRDownloader.cancel(downloadId);
            })

        dialog.show()

        // Enabling database for resume support even after the application is killed:
        val config = PRDownloaderConfig.newBuilder()
            .setDatabaseEnabled(true)
            .build()
        PRDownloader.initialize(requireContext(), config)
        downloadId = PRDownloader.download(
            chat.message,
            directory(),
            fileName(chat)
        )
            .build()
            .setOnStartOrResumeListener {
                dialog.setTitle("Download Started")
            }.setOnPauseListener {

            }.setOnCancelListener {
                Toast.makeText(requireContext(), "Download Canceled", Toast.LENGTH_SHORT).show()
            }.setOnProgressListener { progress ->
                val progressPercent: Long = progress.currentBytes * 100 / progress.totalBytes
                dialog.progress = progressPercent.toInt()
                dialog.setMessage(
                    getBytesToMB(progress.currentBytes) + "/" + getBytesToMB(progress.totalBytes)
                )
            }.start(object : OnDownloadListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDownloadComplete() {
                    Toast.makeText(requireContext(), "Download Completed", Toast.LENGTH_SHORT)
                        .show()
                    Log.e("fullPath = 1 ", Uri.parse(directory() + fileName(chat)).toString());
                    var file = requireContext().getExternalFilesDir(
                        Environment.getExternalStoragePublicDirectory(
                            Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_DOWNLOADS
                            ).absolutePath + "/MarketEslam"
                        ).toString()
                    )
                    Log.e("fullPath = 2 ", file.toString());
                    Log.e(
                        "Path3",
                        Environment.getExternalStorageDirectory().path + "/Market/"
                    )

                    val c = chat;
                    c.uri = directory() + fileName(chat)
                    chatViewModelDatabase.update(c)
                    chats[pos] = c;
                    chatAdapter.notifyItemChanged(pos);
                    chatAdapter.notifyDataSetChanged()
                }

                override fun onError(error: com.doubleclick.downloader.Error?) {
                    Toast.makeText(
                        requireContext(),
                        "Download Error " + error.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun directory(): String {
        var file =
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + "/MarketEslam/");
//        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + "/MarketEslam";
        return file.toString()
    }

    private fun fileName(chat: Chat): String {
        if (chat.type.equals("voice")) {
            return "/" + chat.type + chat.date;
        } else {
            return "/" + chat.type + URLUtil.guessFileName(
                chat.message,
                chat.message,
                requireContext().contentResolver.getType(Uri.parse(chat.message))
            );
        }
    }

    // private directory
    fun privateDirectory(): String {
        return requireActivity().filesDir.absolutePath + "/MarketEslam/";
    }

    private fun getBytesToMB(bytes: Long): String {
        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.90 * 1824.00))
    }

    //===============================PRDownloader========================


    /*
    *  TODO Store data when friend sent it to me
    *  لمه صاحبي يبعت رساله وانا اشوفها
    */
    @SuppressLint("NotifyDataSetChanged")
    override fun BeenSeenForFriend(chat: Chat?) {
        chatViewModelDatabase.insert(chat!!)
        chats.add(chat)
        chatAdapter.notifyItemInserted(chats.size - 1)
        chatAdapter.notifyDataSetChanged()
        chatRecycler.scrollToPosition(chats.size - 1)
        chatRecycler.smoothScrollToPosition(chats.size - 1)
    }


    /*
    * TODO when i send message and my friend see it
    *  بتتنفذ لمه انا ابعت الرساله و صاحبي يشوفها
    * */
    override fun BeenSeenForMe(chat: Chat?) {

        chatViewModelDatabase.update(chat!!)
        try {
            // TODO update in ArrayList
            chats[chats.indexOf(chat)] = chat
            // TODO update in adapter
            chatAdapter.notifyItemChanged(chats.indexOf(chat))
            chatRecycler.scrollToPosition(chats.size - 1)
            chatRecycler.smoothScrollToPosition(chats.size - 1)

        } catch (e: ArrayIndexOutOfBoundsException) {

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun deleteForMe(chat: Chat, pos: Int) {
        val map: HashMap<String, Any> = HashMap();
        map["type"] = "text";
        map["message"] = "${chat.message}  @$@this@message@deleted";
        map["StatusMessage"] = chat.statusMessage;
        map["date"] = chat.date;
        map["seen"] = chat.isSeen;
        map["id"] = chat.id;
        map["sender"] = chat.sender;
        map["receiver"] = chat.receiver;
        reference.child(CHATS).child(myId).child(userId).child(chat.id).updateChildren(map)
            .addOnCompleteListener {
                chatViewModelDatabase.delete(chat)
                chats.removeAt(pos)
                chatAdapter.notifyItemRemoved(pos)
                chatAdapter.notifyDataSetChanged()
            }

    }

    /*
    * this method implimented from OnClickListner
    * working when I make delete for all
    * delete in firebase and update for friend that is deleted
    * */
    @SuppressLint("NotifyDataSetChanged")
    override fun deleteForAll(chat: Chat, pos: Int) {
        try {
            val map: HashMap<String, Any> = HashMap();
            map["type"] = "text";
            map["message"] = "${chat.message}  @$@this@message@deleted";
            map["StatusMessage"] = chat.statusMessage;
            map["date"] = chat.date;
            map["seen"] = chat.isSeen;
            map["id"] = chat.id;
            map["sender"] = chat.sender;
            map["receiver"] = chat.receiver;

            reference.child(CHATS).child(myId).child(userId).child(chat.id).updateChildren(map)
                .addOnCompleteListener {
                    reference.child(CHATS).child(userId).child(myId).child(chat.id)
                        .updateChildren(map)
                        .addOnCompleteListener {
                            val c = chat;
                            c!!.message = "this message deleted";
                            c.type = "text"
                            // TODO update in database
                            chatViewModelDatabase.update(c!!)
                            // TODO update in ArrayList
                            chats[pos] = c
                            // TODO update in Adapter
                            chatAdapter.notifyItemChanged(pos)
                            chatAdapter.notifyDataSetChanged()
                        }
                }
        } catch (e: ArrayIndexOutOfBoundsException) {
            Log.e("ArrayIndexOutOfBound", e.message.toString())
        } catch (e: Exception) {
            Log.e("Exception", e.message.toString())
        }
    }

    /*
    *this method implimented from statusMessage
    * working when friend make delete for all
    * */
    @SuppressLint("NotifyDataSetChanged")
    override fun deleteForAll(chat: Chat?) {
        try {
            val c = chat;
            c!!.message = "this message deleted";
            c.type = "text"
            chatViewModelDatabase.update(c!!)
            chats[chats.indexOf(c)] = c
            chatAdapter.notifyItemChanged(
                chats.indexOf(c)
            )
            chatAdapter.notifyDataSetChanged()
        } catch (e: ArrayIndexOutOfBoundsException) {
            Log.e("ArrayIndexOutOfBound", e.message.toString())
        }
    }


}