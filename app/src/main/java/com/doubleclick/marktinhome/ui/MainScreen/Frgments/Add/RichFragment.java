package com.doubleclick.marktinhome.ui.MainScreen.Frgments.Add;

import static com.doubleclick.marktinhome.Model.Constantes.PRODUCT;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.doubleclick.RichEditor.mricheditor.ActionType;
import com.doubleclick.RichEditor.mricheditor.RichEditorAction;
import com.doubleclick.RichEditor.mricheditor.RichEditorCallback;
import com.doubleclick.RichEditor.mricheditor.ui.ActionImageView;
import com.doubleclick.RichEditor.sample.GlideImageLoader;
import com.doubleclick.RichEditor.sample.fragment.EditHyperlinkFragment;
import com.doubleclick.RichEditor.sample.fragment.EditTableFragment;
import com.doubleclick.RichEditor.sample.fragment.EditorMenuFragment;
import com.doubleclick.RichEditor.sample.interfaces.OnActionPerformListener;
import com.doubleclick.marktinhome.BaseFragment;
import com.doubleclick.marktinhome.Model.Product;
import com.doubleclick.marktinhome.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.protobuf.Any;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RichFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RichFragment extends BaseFragment {

    FrameLayout flAction;
    LinearLayout llActionBarContainer;
    private ImageView iv_action_insert_link;
    private ImageView iv_get_html,
            iv_action_undo,
            iv_action_redo,
            iv_action,
            iv_action_txt_color,
            iv_action_txt_bg_color,
            iv_action_line_height,
            iv_action_insert_image,
            iv_action_table;
    private WebView webView;
    private FrameLayout rootFrame;
    private boolean isKeyboardShowing;
    private String htmlContent = "<p>" + "type here....." + "</p>";
    private RichEditorAction mRichEditorAction;
    private RichEditorCallback mRichEditorCallback;
    //    private ShareHTML shareHTML;
    private EditorMenuFragment mEditorMenuFragment;
    private Product product;
    private ArrayList<String> downloadUri = new ArrayList<>();
    private FloatingActionButton upload;
    private String HTMLText = "";


    private String begin = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "\n" +
            "<head>\n" +
            "    <style>\n" +
            "        table,\n" +
            "        th,\n" +
            "        td {\n" +
            "            border: 2px solid black;\n" +
            "            border-collapse: collapse;\n" +
            "        }\n" +
            "      table{\n" +
            "        width: 100%\n" +
            "      }\n" +
            "    </style>\n" +
            "</head>\n" +
            "\n" +
            "<body>";
    private String end = "</body>\n" +
            "\n" +
            "</html>";


    private List<ActionType> mActionTypeList =
            Arrays.asList(ActionType.BOLD,
                    ActionType.ITALIC,
                    ActionType.UNDERLINE,
                    ActionType.STRIKETHROUGH,
                    ActionType.SUBSCRIPT,
                    ActionType.SUPERSCRIPT,
                    ActionType.NORMAL,
                    ActionType.H1,
                    ActionType.H2,
                    ActionType.H3,
                    ActionType.H4,
                    ActionType.H5,
                    ActionType.H6,
                    ActionType.INDENT,
                    ActionType.OUTDENT,
                    ActionType.JUSTIFY_LEFT,
                    ActionType.JUSTIFY_CENTER,
                    ActionType.JUSTIFY_RIGHT,
                    ActionType.JUSTIFY_FULL,
                    ActionType.ORDERED,
                    ActionType.UNORDERED,
                    ActionType.LINE,
                    ActionType.BLOCK_CODE,
                    ActionType.BLOCK_QUOTE
                    // to view the html code
//                    ,ActionType.CODE_VIEW
            );

    private List<Integer> mActionTypeIconList =
            Arrays.asList(R.drawable.ic_format_bold,
                    R.drawable.ic_format_italic,
                    R.drawable.ic_format_underlined,
                    R.drawable.ic_format_strikethrough,
                    R.drawable.ic_format_subscript,
                    R.drawable.ic_format_superscript,
                    R.drawable.ic_format_para,
                    R.drawable.ic_format_h1,
                    R.drawable.ic_format_h2,
                    R.drawable.ic_format_h3,
                    R.drawable.ic_format_h4,
                    R.drawable.ic_format_h5,
                    R.drawable.ic_format_h6,
                    R.drawable.ic_format_indent_decrease,
                    R.drawable.ic_format_indent_increase,
                    R.drawable.ic_format_align_left,
                    R.drawable.ic_format_align_center,
                    R.drawable.ic_format_align_right,
                    R.drawable.ic_format_align_justify,
                    R.drawable.ic_format_list_numbered,
                    R.drawable.ic_format_list_bulleted,
                    R.drawable.ic_line,
                    R.drawable.ic_code_block,
                    R.drawable.ic_format_quote
                    // to view the html code
//                    ,R.drawable.ic_code_review
            );

    private static final int REQUEST_CODE_CHOOSE = 0;

    public RichFragment() {
        // Required empty public constructor
    }


    public static RichFragment newInstance(String param1, String param2) {
        RichFragment fragment = new RichFragment();
        Bundle args = new Bundle();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = RichFragmentArgs.fromBundle(getArguments()).getProduct();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rich, container, false);
        htmlContent = "<p>" + getResources().getString(R.string.typehere) + "</p>";
        rootFrame = requireActivity().findViewById(R.id.nav_host_fragment);
        llActionBarContainer = view.findViewById(R.id.ll_action_bar_container);
        flAction = view.findViewById(R.id.fl_action);
        iv_action_insert_link = view.findViewById(R.id.iv_action_insert_link);
        iv_get_html = view.findViewById(R.id.iv_get_html);
        iv_action_undo = view.findViewById(R.id.iv_action_undo);
        iv_action_redo = view.findViewById(R.id.iv_action_redo);
        iv_action = view.findViewById(R.id.iv_action);
        iv_action_txt_color = view.findViewById(R.id.iv_action_txt_color);
        iv_action_txt_bg_color = view.findViewById(R.id.iv_action_txt_bg_color);
        iv_action_line_height = view.findViewById(R.id.iv_action_line_height);
        iv_action_insert_image = view.findViewById(R.id.iv_action_insert_image);
        iv_action_table = view.findViewById(R.id.iv_action_table);
        webView = view.findViewById(R.id.wv_container);
        upload = view.findViewById(R.id.upload);
        webView.setNestedScrollingEnabled(true);
        webView.setVerticalScrollbarOverlay(true);
        webView.setVerticalScrollBarEnabled(true);
        initImageLoader();
        initView();
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9, getResources().getDisplayMetrics());
        for (int i = 0, size = mActionTypeList.size(); i < size; i++) {
            ActionImageView actionImageView = new ActionImageView(getContext());
            actionImageView.setLayoutParams(new LinearLayout.LayoutParams(width, width));
            actionImageView.setPadding(padding, padding, padding, padding);
            actionImageView.setActionType(mActionTypeList.get(i));
            actionImageView.setTag(mActionTypeList.get(i));
            actionImageView.setActivatedColor(R.color.blue);
            actionImageView.setDeactivatedColor(R.color.tintColor);
            actionImageView.setRichEditorAction(mRichEditorAction);
            actionImageView.setBackgroundResource(R.drawable.btn_colored_material);
            actionImageView.setImageResource(mActionTypeIconList.get(i));
            actionImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionImageView.command();
                }
            });
            llActionBarContainer.addView(actionImageView);
        }

        mEditorMenuFragment = new EditorMenuFragment();
        mEditorMenuFragment.setActionClickListener(new MOnActionPerformListener(mRichEditorAction));
        requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.fl_action, mEditorMenuFragment, EditorMenuFragment.class.getName()).commit();
        KeyboardUtils.registerSoftInputChangedListener(requireActivity(), height -> {
            isKeyboardShowing = height > 0;
            if (height > 0) {
                flAction.setVisibility(View.INVISIBLE);
                ViewGroup.LayoutParams params = flAction.getLayoutParams();
                params.height = height;
                flAction.setLayoutParams(params);
            } else if (flAction.getVisibility() != View.VISIBLE) {
                flAction.setVisibility(View.GONE);
            }
        });

        iv_action_table.setOnClickListener(v -> {
            onClickInsertTable();
        });
        iv_action_insert_link.setOnClickListener(v -> {
            onClickInsertLink();
        });
        // TODO get HTML as text
        iv_get_html.setOnClickListener(v -> {
            onClickGetHtml();
        });
        iv_action_redo.setOnClickListener(v -> {
            onClickRedo();
        });
        iv_action_undo.setOnClickListener(v -> {
            onClickUndo();
        });
        iv_action.setOnClickListener(v -> {
            onClickAction();
        });
        iv_action_txt_color.setOnClickListener(v -> {
            onClickTextColor();
        });
        iv_action_txt_bg_color.setOnClickListener(v -> {
            onClickHighlight();
        });
        iv_action_line_height.setOnClickListener(v -> {
            onClickLineHeight();
        });
        iv_action_insert_image.setOnClickListener(v -> {
            onClickInsertImage();
        });

        upload.setOnClickListener(v -> {
            onClickGetHtml();
            UploadData();
        });
        return view;
    }

    /**
     * ImageLoader for insert Image
     */
    private void initImageLoader() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setShowCamera(true);
        imagePicker.setCrop(false);
        imagePicker.setMultiMode(false);
        imagePicker.setSaveRectangle(true);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        imagePicker.setFocusWidth(800);
        imagePicker.setFocusHeight(800);
        imagePicker.setOutPutX(256);
        imagePicker.setOutPutY(256);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        webView.setWebChromeClient(new CustomWebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        mRichEditorCallback = new MRichEditorCallback();
        webView.addJavascriptInterface(mRichEditorCallback, "MRichEditor");
        webView.loadUrl("file:///android_asset/richEditor.html");
        mRichEditorAction = new RichEditorAction(webView);
    }

    private class CustomWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                if (!TextUtils.isEmpty(htmlContent)) {
                    mRichEditorAction.insertHtml(htmlContent);
                }
                KeyboardUtils.showSoftInput(requireActivity());
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }

    void onClickAction() {
        if (flAction.getVisibility() == View.VISIBLE) {
            flAction.setVisibility(View.GONE);
        } else {
            if (isKeyboardShowing) {
                KeyboardUtils.hideSoftInput(requireActivity());
            }
            flAction.setVisibility(View.VISIBLE);
        }
    }


    private RichEditorCallback.OnGetHtmlListener onGetHtmlListener = html -> {
        if (TextUtils.isEmpty(html)) {
            return;
        }
        HTMLText = html;
    };

    void onClickGetHtml() {
        mRichEditorAction.refreshHtml(mRichEditorCallback, onGetHtmlListener);
    }


    void onClickUndo() {
        mRichEditorAction.undo();
    }

    void onClickRedo() {
        mRichEditorAction.redo();
    }

    void onClickTextColor() {
        mRichEditorAction.foreColor("blue");
    }

    void onClickHighlight() {
        mRichEditorAction.backColor("red");
    }

    void onClickLineHeight() {
        mRichEditorAction.lineHeight(20);
    }

    void onClickInsertImage() {
        Intent intent = new Intent(getContext(), ImageGridActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CHOOSE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS
                && data != null
                && requestCode == REQUEST_CODE_CHOOSE) {
            ArrayList<ImageItem> images =
                    (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images != null && !images.isEmpty()) {

                //1.Insert the Base64 String (Base64.NO_WRAP)
                ImageItem imageItem = images.get(0);
                mRichEditorAction.insertImageData(imageItem.name,
                        encodeFileToBase64Binary(imageItem.path));

                //2.Insert the ImageUrl
                //mRichEditorAction.insertImageUrl(
                //    "https://avatars0.githubusercontent.com/u/5581118?v=4&u=b7ea903e397678b3675e2a15b0b6d0944f6f129e&s=400");
            }
        }
    }

    private static String encodeFileToBase64Binary(String filePath) {
        byte[] bytes = FileIOUtils.readFile2BytesByStream(filePath);
        byte[] encoded = Base64.encode(bytes, Base64.NO_WRAP);
        return new String(encoded);
    }

    void onClickInsertLink() {
        KeyboardUtils.hideSoftInput(requireActivity());
        EditHyperlinkFragment fragment = new EditHyperlinkFragment();
        fragment.setOnHyperlinkListener((address, text) -> mRichEditorAction.createLink(text, address));
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, fragment, EditHyperlinkFragment.class.getName())
                .setCustomAnimations(R.anim.lefttoright, R.anim.lefttoright)
                .commit();
    }

    void onClickInsertTable() {
        KeyboardUtils.hideSoftInput(requireActivity());
        EditTableFragment fragment = new EditTableFragment();
        fragment.setOnTableListener((rows, cols) -> mRichEditorAction.insertTable(rows, cols));
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, fragment, EditHyperlinkFragment.class.getName())
                .setCustomAnimations(R.anim.lefttoright, R.anim.lefttoright)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (flAction.getVisibility() == View.INVISIBLE) {
            flAction.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    class MRichEditorCallback extends RichEditorCallback {

        @Override
        public void notifyFontStyleChange(ActionType type, final String value) {
            ActionImageView actionImageView =
                    (ActionImageView) llActionBarContainer.findViewWithTag(type);
            if (actionImageView != null) {
                actionImageView.notifyFontStyleChange(type, value);
            }

            if (mEditorMenuFragment != null) {
                mEditorMenuFragment.updateActionStates(type, value);
            }
        }
    }

    public class MOnActionPerformListener implements OnActionPerformListener {
        private RichEditorAction mRichEditorAction;

        public MOnActionPerformListener(RichEditorAction mRichEditorAction) {
            this.mRichEditorAction = mRichEditorAction;
        }

        @Override
        public void onActionPerform(ActionType type, Object... values) {
            if (mRichEditorAction == null) {
                return;
            }

            String value = "";
            if (values != null && values.length > 0) {
                value = (String) values[0];
            }

            switch (type) {
                case SIZE:
                    mRichEditorAction.fontSize(Double.parseDouble(value));
                    break;
                case LINE_HEIGHT:
                    mRichEditorAction.lineHeight(Double.parseDouble(value));
                    break;
                case FORE_COLOR:
                    mRichEditorAction.foreColor(value);
                    break;
                case BACK_COLOR:
                    mRichEditorAction.backColor(value);
                    break;
                case FAMILY:
                    mRichEditorAction.fontName(value);
                    break;
                case IMAGE:
                    onClickInsertImage();
                    break;
                case LINK:
                    onClickInsertLink();
                    break;
                case TABLE:
                    onClickInsertTable();
                    break;
                case BOLD:
                case ITALIC:
                case UNDERLINE:
                case SUBSCRIPT:
                case SUPERSCRIPT:
                case STRIKETHROUGH:
                case JUSTIFY_LEFT:
                case JUSTIFY_CENTER:
                case JUSTIFY_RIGHT:
                case JUSTIFY_FULL:
                case CODE_VIEW:
                case ORDERED:
                case UNORDERED:
                case INDENT:
                case OUTDENT:
                case BLOCK_QUOTE:
                case BLOCK_CODE:
                case NORMAL:
                case H1:
                case H2:
                case H3:
                case H4:
                case H5:
                case H6:
                case LINE:
                    ActionImageView actionImageView = llActionBarContainer.findViewWithTag(type);
                    if (actionImageView != null) {
                        actionImageView.performClick();
                    }
                    break;
                default:
                    break;
            }
        }
    }


    private void UploadData() {
        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Uploading");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        List<String> uris = Arrays.asList(product.getImages().replace("[", "").replace("]", "").replace(" ", "").split(","));
        if (uris.size() != 0) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("Uploads");
            for (String uri : uris) {
                StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(Uri.parse(uri)));
                fileReference.putFile(Uri.parse(uri)).addOnSuccessListener(taskSnapshot -> {
                    Task<Uri> url = taskSnapshot.getStorage().getDownloadUrl();
                    url.addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            downloadUri.add(task.getResult().toString());
                            if (uris.size() == downloadUri.size()) {
                                HashMap<String, Object> map = new HashMap<>();
                                long time = new Date().getTime();
                                String push = reference.push().getKey() + time;
                                map.put("productId", push);
                                map.put("price", product.getPrice());
                                map.put("date", time);
                                map.put("adminId", myId.toString());
                                map.put("productName", product.getProductName());
                                map.put("lastPrice", product.getLastPrice());
                                map.put("tradeMark", product.getTradeMark());
                                map.put("parentCategoryId", product.getParentCategoryId());
                                map.put("childCategoryId", product.getChildCategoryId());
                                map.put("parentCategoryName", product.getParentCategoryName());
                                map.put("childCategoryName", product.getChildCategoryName());
                                map.put("totalRating", 0);
                                map.put("discount", product.getDiscount());
                                map.put("ratingSeller", product.getRatingSeller());
                                map.put("images", downloadUri.toString());
                                map.put("description", begin + HTMLText + end);
                                map.put("sizes", product.getSizes());
                                map.put("colors", product.getColors());
                                map.put("colorsName", product.getColorsName());
                                map.put("keywords", product.getKeywords());
                                reference.child(PRODUCT).child(push).updateChildren(map);
                                progressDialog.dismiss();
                            }
                        } else {
                            Toast.makeText(requireContext(), "Failed!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }).addOnProgressListener(snapshot -> {
                    double p = 100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount();
                    progressDialog.setMessage(p + " % Uploading...");
                }).addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                });
            }
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver contentResolver = requireContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}