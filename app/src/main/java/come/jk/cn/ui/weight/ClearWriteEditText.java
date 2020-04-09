package come.jk.cn.ui.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.IBinder;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import come.jk.cn.R;

/**
 * Created by Master on 2019/4/17.
 */

public class ClearWriteEditText extends RelativeLayout implements View.OnFocusChangeListener, TextWatcher {
    private TextView cwtTitle;
    private EditText cwtContent;
    private ImageView cwtClear;
    private View cwtUnderline;
    private View cwtUnderlineFocus;
    private TextWatcher textWatcher;
    private OnFocusChangeListener focusChangeListener;
    private OnTextClearListener textClearListener;
    private boolean underLineVisible;
    private int start;
    private int count;

    public ClearWriteEditText(Context context) {
        super(context);
        initView(null);
    }

    public ClearWriteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        inflate(getContext(), R.layout.rce_view_clear_write_edit_text, this);
        cwtTitle = (TextView) findViewById(R.id.cwt_title);
        cwtContent = (EditText) findViewById(R.id.cwt_content);
        cwtClear = (ImageView) findViewById(R.id.cwt_clear);
        cwtUnderline = findViewById(R.id.cwt_underline);
        cwtUnderlineFocus = findViewById(R.id.cwt_underline_focus);
        if (attrs != null) {
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.rce_cwt_edit_text);
            CharSequence title = attributes.getText(R.styleable.rce_cwt_edit_text_cwt_edit_text_title);
            if (!TextUtils.isEmpty(title)) {
                cwtTitle.setText(title);
            } else {
                cwtTitle.setVisibility(View.GONE);
            }
            int contentColor = attributes.getColor(R.styleable.rce_cwt_edit_text_cwt_edit_text_content_color, getResources().getColor(R.color.color_normal_text));
            cwtContent.setTextColor(contentColor);
            int contentHintColor = attributes.getColor(R.styleable.rce_cwt_edit_text_cwt_edit_text_content_hint_color, getResources().getColor(R.color.color_text_operation_disable));
            cwtContent.setHintTextColor(contentHintColor);
            float textSize = attributes.getDimension(R.styleable.rce_cwt_edit_text_cwt_edit_text_content_textSizes, -1);
            if (textSize != -1) {
                cwtContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }
            int maxLines = attributes.getInteger(R.styleable.rce_cwt_edit_text_cwt_edit_text_content_maxLines, -1);
            if (maxLines != -1) {
                cwtContent.setMaxLines(maxLines);
            }
            int maxLength = attributes.getInteger(R.styleable.rce_cwt_edit_text_cwt_edit_text_content_maxLength, -1);
            if (maxLength != -1) {
                cwtContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
            }
            CharSequence hint = attributes.getText(R.styleable.rce_cwt_edit_text_cwt_edit_text_content_hint);
            if (!TextUtils.isEmpty(hint)) {
                cwtContent.setHint(hint);
            }
            boolean singleLine = attributes.getBoolean(R.styleable.rce_cwt_edit_text_cwt_edit_text_content_singleLine, false);
            if (singleLine) {
                cwtContent.setSingleLine();
            }
            String inputType = attributes.getString(R.styleable.rce_cwt_edit_text_cwt_edit_text_content_inputType);
            if (!TextUtils.isEmpty(inputType)) {
                if (inputType.equals("textPassword")) {
                    cwtContent.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    cwtContent.setTypeface(Typeface.DEFAULT);
                    this.setTypeface();
                } else if (inputType.equals("number")) {
                    cwtContent.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                }
            }
            underLineVisible = attributes.getBoolean(R.styleable.rce_cwt_edit_text_cwt_edit_text_content_underLineVisible, true);
            if (!underLineVisible) {
                cwtUnderline.setVisibility(View.GONE);
            }
            float marginLeft = attributes.getDimension(R.styleable.rce_cwt_edit_text_cwt_edit_text_content_margin_left, -1);
            if (marginLeft != -1) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cwtContent.getLayoutParams();
                params.setMargins((int) marginLeft, 0, (int) getResources().getDimension(R.dimen.dp_14), 0);
                cwtContent.setLayoutParams(params);
            }
            attributes.recycle();
        }
        cwtContent.setOnFocusChangeListener(this);
        cwtContent.addTextChangedListener(this);
        cwtClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cwtContent.setText("");
                if (textClearListener != null) {
                    textClearListener.onTextClear(v);
                }
            }
        });
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (focusChangeListener != null) {
            focusChangeListener.onFocusChange(v, hasFocus);
        }
        if (hasFocus) {
            if (underLineVisible) {
                cwtUnderline.setVisibility(View.GONE);
                cwtUnderlineFocus.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(cwtContent.getText().toString())) {
                cwtClear.setVisibility(View.VISIBLE);
                return;
            }
        } else {
            if (underLineVisible) {
                cwtUnderline.setVisibility(View.VISIBLE);
                cwtUnderlineFocus.setVisibility(View.GONE);
            }
        }
        cwtClear.setVisibility(View.GONE);
    }

    /**
     * 禁止编辑
     */
    public void disableEdit() {
        cwtContent.setFocusable(false);
        cwtClear.setVisibility(View.GONE);
    }

    public String getText() {
        return cwtContent.getText().toString();
    }

    public void setText(String text) {
        cwtContent.setText(text, TextView.BufferType.SPANNABLE);
        cwtContent.setSelection(cwtContent.length());
        if (!TextUtils.isEmpty(text) && cwtContent.hasFocus()) {
            setClearButtonVisibility(View.VISIBLE);
        } else {
            setClearButtonVisibility(View.GONE);
        }

    }

    public void setInputType(int inputType) {
        cwtContent.setInputType(inputType);
    }

    public void setSelection(int position) {
        cwtContent.setSelection(position);
        this.setTypeface();
    }

    public void setClearButtonVisibility(int visibility) {
        cwtClear.setVisibility(visibility);
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }

    public void addOnFocusChangeListener(OnFocusChangeListener focusChangeListener) {
        this.focusChangeListener = focusChangeListener;
    }

    public void addTextClearListener(OnTextClearListener textClearListener) {
        this.textClearListener = textClearListener;
    }

    public void setOnEditorActionListener(TextView.OnEditorActionListener editorActionListener) {
        cwtContent.setOnEditorActionListener(editorActionListener);
    }

    public IBinder getWindowToken() {
        return cwtContent.getWindowToken();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (textWatcher != null) {
            textWatcher.beforeTextChanged(s, start, count, after);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.start = start;
        this.count = count;
        setClearButtonVisibility(cwtContent.hasFocus() && s.length() >= 1 ? View.VISIBLE : View.GONE);
        if (textWatcher != null) {
            textWatcher.onTextChanged(s, start, before, count);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (textWatcher != null) {
            textWatcher.afterTextChanged(s);
        }

            cwtContent.removeTextChangedListener(this);
            cwtContent.setText(s.toString(), TextView.BufferType.SPANNABLE);
            cwtContent.setSelection(start + count);
            cwtContent.addTextChangedListener(this);

    }

    public interface OnTextClearListener {
        void onTextClear(View v);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }

    /**
     * fix bug 16597
     * android 5.0 后EditText中的hint英文字体变化不统一
     */
    private void setTypeface() {
        cwtContent.setTypeface(Typeface.DEFAULT);
        cwtContent.setTransformationMethod(new PasswordTransformationMethod());
    }
}
