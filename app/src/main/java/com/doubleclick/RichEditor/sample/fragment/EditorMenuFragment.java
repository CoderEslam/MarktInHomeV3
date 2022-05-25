package com.doubleclick.RichEditor.sample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.doubleclick.RichEditor.mricheditor.ActionType;
import com.doubleclick.RichEditor.sample.interfaces.OnActionPerformListener;
import com.doubleclick.RichEditor.sample.widget.ColorPaletteView;
import com.doubleclick.marktinhome.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EditorMenuFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    TextView tvFontSize;
    TextView tvFontName;
    TextView tvFontSpacing;
    ColorPaletteView cpvFontTextColor;
    ColorPaletteView cpvHighlightColor;
    private OnActionPerformListener mActionClickListener;

    private final static Pattern PATTERN_RGB =
            Pattern.compile("rgb *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)");

    private final Map<Integer, ActionType> mViewTypeMap = new HashMap<Integer, ActionType>() {
        {
            put(R.id.iv_action_bold, ActionType.BOLD);
            put(R.id.iv_action_italic, ActionType.ITALIC);
            put(R.id.iv_action_underline, ActionType.UNDERLINE);
            put(R.id.iv_action_strikethrough, ActionType.STRIKETHROUGH);
            put(R.id.iv_action_justify_left, ActionType.JUSTIFY_LEFT);
            put(R.id.iv_action_justify_center, ActionType.JUSTIFY_CENTER);
            put(R.id.iv_action_justify_right, ActionType.JUSTIFY_RIGHT);
            put(R.id.iv_action_justify_full, ActionType.JUSTIFY_FULL);
            put(R.id.iv_action_subscript, ActionType.SUBSCRIPT);
            put(R.id.iv_action_superscript, ActionType.SUPERSCRIPT);
            put(R.id.iv_action_insert_numbers, ActionType.ORDERED);
            put(R.id.iv_action_insert_bullets, ActionType.UNORDERED);
            put(R.id.iv_action_indent, ActionType.INDENT);
            put(R.id.iv_action_outdent, ActionType.OUTDENT);
            put(R.id.iv_action_code_view, ActionType.CODE_VIEW);
            put(R.id.iv_action_blockquote, ActionType.BLOCK_QUOTE);
            put(R.id.iv_action_code_block, ActionType.BLOCK_CODE);
            put(R.id.ll_normal, ActionType.NORMAL);
            put(R.id.ll_h1, ActionType.H1);
            put(R.id.ll_h2, ActionType.H2);
            put(R.id.ll_h3, ActionType.H3);
            put(R.id.ll_h4, ActionType.H4);
            put(R.id.ll_h5, ActionType.H5);
            put(R.id.ll_h6, ActionType.H6);
            put(R.id.iv_action_insert_image, ActionType.IMAGE);
            put(R.id.iv_action_insert_link, ActionType.LINK);
            put(R.id.iv_action_table, ActionType.TABLE);
            put(R.id.iv_action_line, ActionType.LINE);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_editor_menu, container, false);
        cpvFontTextColor = rootView.findViewById(R.id.cpv_font_text_color);
        cpvHighlightColor = rootView.findViewById(R.id.cpv_highlight_color);
        tvFontName = rootView.findViewById(R.id.tv_font_name);
        tvFontSpacing = rootView.findViewById(R.id.tv_font_spacing);
        tvFontSize = rootView.findViewById(R.id.tv_font_size);
        LinearLayout ll_font_size = rootView.findViewById(R.id.ll_font_size);
        LinearLayout ll_line_height = rootView.findViewById(R.id.ll_line_height);
        ImageView iv_action_bold = rootView.findViewById(R.id.iv_action_bold);
        ImageView iv_action_italic = rootView.findViewById(R.id.iv_action_italic);
        ImageView iv_action_underline = rootView.findViewById(R.id.iv_action_underline);
        ImageView iv_action_strikethrough = rootView.findViewById(R.id.iv_action_strikethrough);
        ImageView iv_action_justify_left = rootView.findViewById(R.id.iv_action_justify_left);
        ImageView iv_action_justify_center = rootView.findViewById(R.id.iv_action_justify_center);
        ImageView iv_action_justify_right = rootView.findViewById(R.id.iv_action_justify_right);
        ImageView iv_action_justify_full = rootView.findViewById(R.id.iv_action_justify_full);
        ImageView iv_action_subscript = rootView.findViewById(R.id.iv_action_subscript);
        ImageView iv_action_superscript = rootView.findViewById(R.id.iv_action_superscript);
        ImageView iv_action_insert_numbers = rootView.findViewById(R.id.iv_action_insert_numbers);
        ImageView iv_action_insert_bullets = rootView.findViewById(R.id.iv_action_insert_bullets);
        ImageView iv_action_indent = rootView.findViewById(R.id.iv_action_indent);
        ImageView iv_action_outdent = rootView.findViewById(R.id.iv_action_outdent);
        ImageView iv_action_code_view = rootView.findViewById(R.id.iv_action_code_view);
        ImageView iv_action_blockquote = rootView.findViewById(R.id.iv_action_blockquote);
        ImageView iv_action_code_block = rootView.findViewById(R.id.iv_action_code_block);
        LinearLayout ll_normal = rootView.findViewById(R.id.ll_normal);
        ImageView iv_action_insert_image = rootView.findViewById(R.id.iv_action_insert_image);
        ImageView iv_action_insert_link = rootView.findViewById(R.id.iv_action_insert_link);
        ImageView iv_action_table = rootView.findViewById(R.id.iv_action_table);
        ImageView iv_action_line = rootView.findViewById(R.id.iv_action_line);
        LinearLayout ll_h1 = rootView.findViewById(R.id.ll_h1);
        LinearLayout ll_h2 = rootView.findViewById(R.id.ll_h2);
        LinearLayout ll_h3 = rootView.findViewById(R.id.ll_h3);
        LinearLayout ll_h4 = rootView.findViewById(R.id.ll_h4);
        LinearLayout ll_h5 = rootView.findViewById(R.id.ll_h5);
        LinearLayout ll_h6 = rootView.findViewById(R.id.ll_h6);
        iv_action_bold.setOnClickListener(this);
        iv_action_italic.setOnClickListener(this);
        iv_action_underline.setOnClickListener(this);
        iv_action_strikethrough.setOnClickListener(this);
        iv_action_justify_left.setOnClickListener(this);
        iv_action_justify_center.setOnClickListener(this);
        iv_action_justify_right.setOnClickListener(this);
        iv_action_justify_full.setOnClickListener(this);
        iv_action_subscript.setOnClickListener(this);
        iv_action_superscript.setOnClickListener(this);
        iv_action_insert_numbers.setOnClickListener(this);
        iv_action_insert_bullets.setOnClickListener(this);
        iv_action_indent.setOnClickListener(this);
        iv_action_outdent.setOnClickListener(this);
        iv_action_code_view.setOnClickListener(this);
        iv_action_blockquote.setOnClickListener(this);
        iv_action_code_block.setOnClickListener(this);
        ll_normal.setOnClickListener(this);
        iv_action_insert_image.setOnClickListener(this);
        iv_action_insert_link.setOnClickListener(this);
        iv_action_table.setOnClickListener(this);
        iv_action_line.setOnClickListener(this);
        ll_h1.setOnClickListener(this);
        ll_h2.setOnClickListener(this);
        ll_h3.setOnClickListener(this);
        ll_h4.setOnClickListener(this);
        ll_h5.setOnClickListener(this);
        ll_h6.setOnClickListener(this);
        initView();

        ll_font_size.setOnClickListener(v -> onClickFontSize());
        ll_line_height.setOnClickListener(v -> onClickLineHeight());
        tvFontName.setOnClickListener(v -> onClickFontFamily());

        return rootView;
    }

    private void initView() {
        cpvFontTextColor.setOnColorChangeListener(color -> {
            if (mActionClickListener != null) {
                mActionClickListener.onActionPerform(ActionType.FORE_COLOR, color);
            }
        });

        cpvHighlightColor.setOnColorChangeListener(color -> {
            if (mActionClickListener != null) {
                mActionClickListener.onActionPerform(ActionType.BACK_COLOR, color);
            }
        });
    }

    void onClickFontSize() {
        openFontSettingFragment(FontSettingFragment.TYPE_SIZE);
    }

    void onClickLineHeight() {
        openFontSettingFragment(FontSettingFragment.TYPE_LINE_HEIGHT);
    }

    void onClickFontFamily() {
        openFontSettingFragment(FontSettingFragment.TYPE_FONT_FAMILY);
    }


    private void openFontSettingFragment(final int type) {
        FontSettingFragment fontSettingFragment = new FontSettingFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(FontSettingFragment.TYPE, type);
        fontSettingFragment.setArguments(bundle);

        fontSettingFragment.setOnResultListener(result -> {
            if (mActionClickListener != null) {
                switch (type) {
                    case FontSettingFragment.TYPE_SIZE:
                        tvFontSize.setText(result);
                        mActionClickListener.onActionPerform(ActionType.SIZE, result);
                        break;
                    case FontSettingFragment.TYPE_LINE_HEIGHT:
                        tvFontSpacing.setText(result);
                        mActionClickListener.onActionPerform(ActionType.LINE_HEIGHT, result);
                        break;
                    case FontSettingFragment.TYPE_FONT_FAMILY:
                        tvFontName.setText(result);
                        mActionClickListener.onActionPerform(ActionType.FAMILY, result);
                        break;
                    default:
                        break;
                }
            }
        });


        requireActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_action, fontSettingFragment, FontSettingFragment.class.getName())
                .hide(EditorMenuFragment.this)
                .commit();
    }

    public void updateActionStates(final ActionType type, final boolean isActive) {
        rootView.post(() -> {
            View view = null;
            for (Map.Entry<Integer, ActionType> e : mViewTypeMap.entrySet()) {
                Integer key = e.getKey();
                if (e.getValue() == type) {
                    view = rootView.findViewById(key);
                    break;
                }
            }

            if (view == null) {
                return;
            }

            switch (type) {
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
                case ORDERED:
                case CODE_VIEW:
                case UNORDERED:
                    if (isActive) {
                        ((ImageView) view).setColorFilter(
                                ContextCompat.getColor(requireContext(), R.color.blue));
                    } else {
                        ((ImageView) view).setColorFilter(
                                ContextCompat.getColor(requireContext(), R.color.tintColor));
                    }
                    break;
                case NORMAL:
                case H1:
                case H2:
                case H3:
                case H4:
                case H5:
                case H6:
                    if (isActive) {
                        view.setBackgroundResource(R.drawable.round_rectangle_blue);
                    } else {
                        view.setBackgroundResource(R.drawable.round_rectangle_white);
                    }
                    break;
                default:
                    break;
            }
        });
    }

    public void setActionClickListener(OnActionPerformListener mActionClickListener) {
        this.mActionClickListener = mActionClickListener;
    }

    public void updateActionStates(ActionType type, final String value) {
        switch (type) {
            case FAMILY:
                updateFontFamilyStates(value);
                break;
            case SIZE:
                updateFontStates(ActionType.SIZE, Double.parseDouble(value));
                break;
            case FORE_COLOR:
            case BACK_COLOR:
                updateFontColorStates(type, value);
                break;
            case LINE_HEIGHT:
                updateFontStates(ActionType.LINE_HEIGHT, Double.parseDouble(value));
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
            case NORMAL:
            case H1:
            case H2:
            case H3:
            case H4:
            case H5:
            case H6:
            case ORDERED:
            case UNORDERED:
                updateActionStates(type, Boolean.parseBoolean(value));
                break;
            default:
                break;
        }
    }

    private void updateFontFamilyStates(final String font) {
        rootView.post(() -> tvFontName.setText(font));
    }

    private void updateFontStates(final ActionType type, final double value) {
        rootView.post(() -> {
            switch (type) {
                case SIZE:
                    tvFontSize.setText(String.valueOf((int) value));
                    break;
                case LINE_HEIGHT:
                    tvFontSpacing.setText(String.valueOf(value));
                    break;
                default:
                    break;
            }
        });
    }

    private void updateFontColorStates(final ActionType type, final String color) {
        rootView.post(() -> {
            String selectedColor = rgbToHex(color);
            if (selectedColor != null) {
                if (type == ActionType.FORE_COLOR) {
                    cpvFontTextColor.setSelectedColor(selectedColor);
                } else if (type == ActionType.BACK_COLOR) {
                    cpvHighlightColor.setSelectedColor(selectedColor);
                }
            }
        });
    }

    public static String rgbToHex(String rgb) {
        Matcher m = PATTERN_RGB.matcher(rgb);
        if (m.matches()) {
            return String.format("#%02x%02x%02x", Integer.valueOf(Objects.requireNonNull(m.group(1))),
                    Integer.valueOf(Objects.requireNonNull(m.group(2))), Integer.valueOf(Objects.requireNonNull(m.group(3))));
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        if (mActionClickListener == null) {
            return;
        }

        ActionType type = mViewTypeMap.get(v.getId());
        mActionClickListener.onActionPerform(type);
    }
}
