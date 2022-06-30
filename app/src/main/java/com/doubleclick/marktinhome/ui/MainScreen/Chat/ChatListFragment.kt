package com.doubleclick.marktinhome.ui.MainScreen.Chat

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleclick.UserInter
import com.doubleclick.ViewModel.ChatListViewModel
import com.doubleclick.ViewModel.StoryViewModel
import com.doubleclick.ViewModel.UserViewModel
import com.doubleclick.marktinhome.Adapters.MyUserChatListAdapter
import com.doubleclick.marktinhome.Adapters.StoriesAdapter
import com.doubleclick.marktinhome.BaseFragment
import com.doubleclick.marktinhome.Database.ChatListDatabase.ChatListData
import com.doubleclick.marktinhome.Database.ChatListDatabase.ChatListViewModelDatabase
import com.doubleclick.marktinhome.Model.User
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.Views.storyview.StoryView
import com.doubleclick.marktinhome.Views.storyview.callback.OnStoryChangedCallback
import com.doubleclick.marktinhome.Views.storyview.callback.StoryClickListeners
import com.doubleclick.marktinhome.Views.storyview.storyview.StoryModel
import com.doubleclick.marktinhome.Views.storyview.storyview.StoryViewCircle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.todkars.shimmer.ShimmerRecyclerView
import kotlinx.android.synthetic.main.layout_story_view.*
import java.text.ParseException
import java.text.SimpleDateFormat


class ChatListFragment : BaseFragment(), UserInter {

    lateinit var allUser: ShimmerRecyclerView;
    lateinit var chatListViewModel: ChatListViewModel
    lateinit var chatUser: FloatingActionButton
    private var sharePost: String = "null"
    private var currentAnimator: Animator? = null
    private var shortAnimationDuration: Int = 0
    private var allUsers: ArrayList<ChatListData> = ArrayList();
    private lateinit var allUserChatListAdapter: MyUserChatListAdapter
    private lateinit var chatListViewModelDatabase: ChatListViewModelDatabase
    private lateinit var userViewModel: UserViewModel
    private lateinit var rootView: View
    private lateinit var stories: RecyclerView;
    private lateinit var storyViewModel: StoryViewModel;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (!it.isEmpty) {
                sharePost = it.getString("sharePost").toString()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_chat_list, container, false)
        allUser = rootView.findViewById(R.id.allUser);
        allUser.showShimmer();
        chatUser = rootView.findViewById(R.id.chatUser);
        stories = rootView.findViewById(R.id.stories);
        chatListViewModel = ViewModelProvider(this)[ChatListViewModel::class.java];
        chatListViewModelDatabase = ViewModelProvider(this)[ChatListViewModelDatabase::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        storyViewModel = ViewModelProvider(this)[StoryViewModel::class.java];
        allUserChatListAdapter = MyUserChatListAdapter(this, allUsers);
        allUser.adapter = allUserChatListAdapter;
        // Retrieve and cache the system's default "short" animation time.
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)

        chatListViewModelDatabase.chatListData.observe(viewLifecycleOwner) {
            allUsers.clear()
            allUsers.addAll(it)
            Log.e("UPDATE", it.toString());
            allUserChatListAdapter.notifyDataSetChanged()
        }

        chatListViewModelDatabase.limitation.observe(viewLifecycleOwner) {

        }
        storyViewModel.Users(chatListViewModelDatabase.userList);
        storyViewModel.storiesLiveData.observe(viewLifecycleOwner) {
            Log.e("STORUMODEL", it.toString());
//            if (it.size != 0) {
                val storiesAdapter = StoriesAdapter(it);
                stories.adapter = storiesAdapter;
//            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////
        /**
         *if something happen in ChatList update , insert , delete
         * -> store in database
         * */
        chatListViewModel.ChatListInserted().observe(viewLifecycleOwner) {
            try {
                chatListViewModelDatabase.insertChatList(it)
            } catch (e: Exception) {

            }
            try {
                insertUser(it.id);
                updateUser(it.id);
            } catch (e: Exception) {

            }
        }
        chatListViewModel.ChatListUpdate().observe(viewLifecycleOwner) {
            chatListViewModelDatabase.updateChatList(it)
            updateUser(it.id);
        }

        chatListViewModel.ChatListDeleted().observe(viewLifecycleOwner) {
            chatListViewModelDatabase.deleteChatList(it)
            deleteUser(it.id);
        }
        ///////////////////////////////////////////////////////////////////////////////////


        chatUser.setOnClickListener {
            findNavController().navigate(ChatListFragmentDirections.actionChatListFragmentToAllUsersFragment())
        }

        return rootView;
    }

    override fun ItemUser(user: User) {}

    override fun ItemUserChanged(user: User) {}

    override fun ItemUserDeleted(user: User) {}

    override fun ItemUserInfoById(user: User?) {}

    override fun AllUser(user: ArrayList<User>?) {}

    override fun OnUserLisitner(user: User?) {
        try {
            if (sharePost != "null") {
                val chatFragment = ChatFragment();
                val bundle = Bundle();
                bundle.putString("sharePost", sharePost);
                bundle.putString("userId", user!!.id);
                chatFragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(com.doubleclick.marktinhome.R.id.main_fragment_Chat, chatFragment)
                    .commit()
            } else {
                findNavController().navigate(
                    ChatListFragmentDirections.actionChatListFragmentToChatFragment(
                        user!!.id
                    )
                )
            }
        } catch (e: Exception) {

        }
    }

    override fun OnImageListnerLoad(user: User, image: ImageView) {
        zoomImageFromThumb(image, user.image)
    }

    private fun insertUser(id: String) {
        userViewModel.getUserById(id)
        userViewModel.userInfo.observe(viewLifecycleOwner) {
            chatListViewModelDatabase.insertUser(it)
        }
    }

    private fun updateUser(id: String) {
        userViewModel.getUserById(id)
        userViewModel.userInfo.observe(viewLifecycleOwner) {
            chatListViewModelDatabase.updateUser(it)
        }
    }

    private fun deleteUser(id: String) {
        userViewModel.getUserById(id)
        userViewModel.userInfo.observe(viewLifecycleOwner) {
            chatListViewModelDatabase.deleteUser(it)
        }
    }

    private fun zoomImageFromThumb(thumbView: View, image: String) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        currentAnimator?.cancel()

        // Load the high-resolution "zoomed-in" image.
        val expandedImageView: ImageView =
            rootView.findViewById(com.doubleclick.marktinhome.R.id.expanded_image)
        Glide.with(requireContext()).load(image).into(expandedImageView)

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBoundsInt)
        rootView.findViewById<View>(com.doubleclick.marktinhome.R.id.container)
            .getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        val startScale: Float
        if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {
            // Extend start bounds horizontally
            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            // Extend start bounds vertically
            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
//        thumbView.alpha = 0f

        expandedImageView.visibility = View.VISIBLE

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.pivotX = 0f
        expandedImageView.pivotY = 0f

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        currentAnimator = AnimatorSet().apply {
            play(
                ObjectAnimator.ofFloat(
                    expandedImageView,
                    View.X,
                    startBounds.left,
                    finalBounds.left
                )
            ).apply {
                with(
                    ObjectAnimator.ofFloat(
                        expandedImageView,
                        View.Y,
                        startBounds.top,
                        finalBounds.top
                    )
                )
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f))
            }
            duration = shortAnimationDuration.toLong()
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    currentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    currentAnimator = null
                }
            })
            start()
        }

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        expandedImageView.setOnClickListener {
            currentAnimator?.cancel()

            // Animate the four positioning/sizing properties in parallel,
            // back to their original values.
            currentAnimator = AnimatorSet().apply {
                play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left)).apply {
                    with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                    with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale))
                    with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale))
                }
                duration = shortAnimationDuration.toLong()
                interpolator = DecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animation: Animator) {
                        thumbView.alpha = 1f
                        expandedImageView.visibility = View.GONE
                        currentAnimator = null
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        thumbView.alpha = 1f
                        expandedImageView.visibility = View.GONE
                        currentAnimator = null
                    }
                })
                start()
            }
        }
    }
}