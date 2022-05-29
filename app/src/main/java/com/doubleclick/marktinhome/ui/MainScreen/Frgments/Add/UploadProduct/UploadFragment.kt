package com.doubleclick.marktinhome.ui.MainScreen.Frgments.Add.UploadProduct


import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatSpinner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.ViewModel.TradmarkViewModel
import com.doubleclick.marktinhome.Adapters.KeywordAdapter
import com.doubleclick.marktinhome.BaseFragment
import com.doubleclick.marktinhome.Model.Product
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.ui.MainScreen.Frgments.Add.KeywordBottomSheet
import com.google.android.material.textfield.TextInputEditText
import com.iceteck.silicompressorr.SiliCompressor
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup
import com.nex3z.togglebuttongroup.button.CircularToggle
import id.zelory.compressor.Compressor
import top.defaults.colorpicker.ColorPickerView
import java.io.File
import java.util.*


class UploadFragment : BaseFragment(), KeywordAdapter.OnDelete, KeywordBottomSheet.Keywords {


    lateinit var marke: String
    lateinit var usedOrnew: String
    private lateinit var productName: EditText;
    private lateinit var productPrice: EditText;
    private lateinit var productLastPrice: EditText;
    private lateinit var trademark: AppCompatSpinner;
    private lateinit var type: AppCompatSpinner
    private lateinit var Upload: Button;
    private lateinit var tradmarkViewModel: TradmarkViewModel
    private lateinit var ratingSeller: RatingBar
    private var Sizes: ArrayList<String> = ArrayList()
    var rate: Float = 0f
    private lateinit var groupSize: SingleSelectToggleGroup
    private lateinit var addSizes: ImageView
    private lateinit var addkeyword: ImageView
    private lateinit var builder: AlertDialog.Builder
    private var colorToggle: Int = 0
    private lateinit var keyword: RecyclerView
    private lateinit var keywordAdapter: KeywordAdapter;
    private lateinit var groupColor: SingleSelectToggleGroup
    private lateinit var addColor: ImageView
    private var colors: ArrayList<Int> = ArrayList();
    private var colorsName: ArrayList<String> = ArrayList();
    private var itemsKeys: ArrayList<String> = ArrayList()
    val parent_child by navArgs<UploadFragmentArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_upload, container, false)
        productName = view.findViewById(R.id.productName);
        productPrice = view.findViewById(R.id.productPrice);
        productLastPrice = view.findViewById(R.id.productLastPrice);
        groupColor = view.findViewById(R.id.groupColor);
        addColor = view.findViewById(R.id.addColor);
        trademark = view.findViewById(R.id.trademark);
        Upload = view.findViewById(R.id.Upload);
        type = view.findViewById(R.id.type);
        ratingSeller = view.findViewById(R.id.ratingSeller);
        groupSize = view.findViewById(R.id.groupSize);
        addSizes = view.findViewById(R.id.addSizes);
        keyword = view.findViewById(R.id.keyword);
        addkeyword = view.findViewById(R.id.addkeyword);
        tradmarkViewModel = TradmarkViewModel()
        tradmarkViewModel.namesMark.observe(viewLifecycleOwner, Observer {
            trademark.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    marke = it[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                    marke = it[0]

                }
            }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it)
            trademark.adapter = adapter
        })

        ratingSeller.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            rate = rating;
        }
        Upload.setOnClickListener {
            if (productName.text.toString() == "") {
                productName.error = "input name of product"
            } else if (productPrice.text.toString() == "") {
                productPrice.error = "input price of product"
            } else if (productLastPrice.text.toString() == "") {
                productLastPrice.error = "input last of product"
            } else {
                try {
                    SendData(
                        productName.text.toString(),
                        productPrice.text.toString().toDouble(),
                        productLastPrice.text.toString().toDouble(),
                        marke,
                        parent_child.parent!!.pushId,
                        parent_child.child!!.pushId,
                        parent_child.parent!!.name,
                        parent_child.child!!.name
                    )
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "" + resources.getString(R.string.cantUploadEmpty),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


        addkeyword.setOnClickListener {
            val keywordBottomSheet = KeywordBottomSheet(this);
            keywordBottomSheet.show(requireActivity().supportFragmentManager, "keywords")
        }

        addSizes.setOnClickListener {
            builder = AlertDialog.Builder(requireContext())
            val radio = CircularToggle(requireContext())
            val view = LayoutInflater.from(context).inflate(R.layout.add_toggal, null, false)
            val editorder: TextInputEditText = view.findViewById(R.id.editname)
            val colorPickerView: ColorPickerView = view.findViewById(R.id.colorPicker);
            colorPickerView.visibility = View.GONE
//            val color_seek_bar: ColorSeekBar = view.findViewById(R.id.color_seek_bar);
//            color_seek_bar.visibility = View.GONE
            builder.setTitle("Add Sizes")
            builder.setPositiveButton("ok") { dialog, which ->
                radio.text = "" + editorder.text.toString().trim()
                Sizes.add(editorder.text.toString().trim());
                groupSize.addView(radio)
                dialog.dismiss()
            }
            builder.setNegativeButton("cancel") { dialog, which ->
                dialog.dismiss()
            }
            builder.setView(view)
            builder.show()
        }

        // todo add color
        addColor.setOnClickListener {
            builder = AlertDialog.Builder(requireContext())
            val circuleToggle = CircularToggle(requireContext())
            val view = LayoutInflater.from(context).inflate(R.layout.add_toggal, null, false)
//            val cardView: CardView = view.findViewById(R.id.cardView);
            val editorder: TextInputEditText = view.findViewById(R.id.editname)
            val colorPickerView: ColorPickerView = view.findViewById(R.id.colorPicker);
            colorPickerView.subscribe { color: Int, fromUser: Boolean, shouldPropagate: Boolean ->
                colorPickerView.setBackgroundColor(color)
                Log.e("COLOR", color.toString());
                colorToggle = color;
//                editorder.setText(colorHex(color))
                requireActivity().window.statusBarColor = color
            }
            // to the same work
            /*ColorPickerPopup.Builder(requireContext())
                .initialColor(colorPickerView.color) // Set initial color
                .enableBrightness(true) // Enable brightness slider or not
                .enableAlpha(true) // Enable alpha slider or not
                .okTitle("Choose")
                .cancelTitle("Cancel")
                .showIndicator(true)
                .showValue(true)
                .build()
                .show(object : ColorPickerPopup.ColorPickerObserver() {
                    override fun onColorPicked(color: Int) {
                        colorPickerView.setBackgroundColor(color);
//                        colorToggle = color;
                    }

                    fun onColor(color: Int, fromUser: Boolean) {

                    }
                })*/
//            color_seek_bar.visibility = View.VISIBLE
//            color_seek_bar.setOnColorChangeListener(object : ColorSeekBar.OnColorChangeListener {
//                override fun onColorChangeListener(color: Int) {
//                    //gives the selected color
//                    colorToggle = color;
//                    cardView.setBackgroundColor(color)
//                }
//            })
            builder.setTitle("Add Colors")
            builder.setPositiveButton("ok", DialogInterface.OnClickListener { dialog, which ->
                circuleToggle.text = "" + editorder.text.toString().trim()
                circuleToggle.markerColor = colorToggle
                colors.add(colorToggle)
                colorsName.add(editorder.text.toString().trim())
                groupColor.addView(circuleToggle)
                dialog.dismiss()
            })
            builder.setNegativeButton("cancel", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            builder.setView(view)
            builder.show()
        }
        Used()
        return view;
    }

    /*
    * to transrafar color to hex color
    * */
    private fun colorHex(color: Int): String? {
        val a = Color.alpha(color)
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)
        return String.format(Locale.getDefault(), "0x%02X%02X%02X%02X", a, r, g, b)
    }

    // Compressor Image
    suspend fun compress(){
        val compressedImageFile = Compressor.compress(requireContext(), File(""))
    }

    fun SendData(
        name: String,
        price: Double,
        LastPrice: Double,
        trademark: String,
        ParentId: String,
        ChildId: String,
        ParentName: String,
        ChildName: String,
    ) {
        var lastMoney = 0.0
        var money = 0.0
        if (price > LastPrice) {
            val helper = LastPrice;
            lastMoney = price
            money = helper;
        } else {
            money = price
            lastMoney = LastPrice
        }
        val discount = (-1 * (100 - ((price / LastPrice) * 100.0)))

        val product = Product(
            "",
            money,
            "",
            0,
            myId.toString(),
            name.toString(),
            lastMoney,
            trademark.toString(),
            ParentName.toString(),
            ChildName.toString(),
            ParentId.toString(),
            ChildId.toString(),
            0,
            discount,
            itemsKeys.toString(),
            "",
            Sizes.toString(),
            colors.toString(),
            colorsName.toString(),
            rate,
            usedOrnew
        );
        findNavController().navigate(
            UploadFragmentDirections.actionUploadFragmentToUploadStep2Fragment(
                product
            )
        )
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onItemDelete(pos: Int) {
        itemsKeys.removeAt(pos);
        keywordAdapter.notifyItemRemoved(pos)
        keywordAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun ItemsKeyword(item: String) {
        itemsKeys.add(item);
        keywordAdapter = KeywordAdapter(itemsKeys, this);
        keyword.adapter = keywordAdapter
        keywordAdapter.notifyItemInserted(itemsKeys.size - 1)
        keywordAdapter.notifyDataSetChanged()
    }

    private fun Used() {
        var used_new: ArrayList<String> = ArrayList()
        used_new.add("used")
        used_new.add("new")
        type.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                usedOrnew = used_new[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
                usedOrnew = used_new[0]

            }
        }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, used_new)
        type.adapter = adapter

    }

}

