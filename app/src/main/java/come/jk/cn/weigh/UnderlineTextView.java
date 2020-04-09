package come.jk.cn.weigh;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import come.jk.cn.R;

public class UnderlineTextView extends LinearLayout {

    private View underline;
    private TextView category;

    public UnderlineTextView(Context context) {
        this(context, null);
    }

    public UnderlineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        inflate(getContext(), R.layout.rce_widget_layout_under_line_text_view, this);
        setOrientation(VERTICAL);
        category = findViewById(R.id.tv_category);
        underline = findViewById(R.id.rce_category_underline);
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.UnderlineTextView);
            CharSequence text = array.getText(R.styleable.UnderlineTextView_category_name);
            int length = (int) array.getDimension(R.styleable.UnderlineTextView_underline_length,
                    getContext().getResources().getDimension(R.dimen.dp_50));
            category.setText(text);
            ViewGroup.LayoutParams layoutParams =
                    underline.getLayoutParams();
            layoutParams.width = length;
            underline.setLayoutParams(layoutParams);
            array.recycle();
        }
    }

    public void setUnderlineVisibility(int visibility) {
//        underline.setVisibility(visibility);
        category.setBackgroundResource(R.drawable.backyazhengma);

    }
}
