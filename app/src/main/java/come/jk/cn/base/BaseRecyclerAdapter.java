package come.jk.cn.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Master on 2019/4/17.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> mDatas = new ArrayList<>();
    private OnItemClickListener<T> mOnItemClickListener;
    private OnItemLongClickListener<T> mOnItemLongClickListener;
    protected Context mContext;

    /**
     * @param mDatas
     */
    public BaseRecyclerAdapter(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    public void setData(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    public void append(T item) {
        if (item != null) {
            mDatas.add(item);
            notifyDataSetChanged();
        }
    }

    public void append(List<T> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }


    public void replace(List<T> datas) {
        mDatas.clear();
        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    public void remove(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    public void removeAll() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> listener) {
        this.mOnItemLongClickListener = listener;
    }

    public interface OnItemClickListener<T> {

        void onItemClick(View view, int position, T model);
    }

    public interface OnItemLongClickListener<T> {

        boolean onItemLongClick(View view, int position, T model);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        onBindMyViewHolder(holder, position);
        initItemClickListener(holder, position);
    }

    private void initItemClickListener(RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("===", "===onClick===");
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(v, position, mDatas.get(position));
            }
        });
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mOnItemLongClickListener.onItemLongClick(v, position, mDatas.get(position));
                }
            });
        }
    }

    protected abstract void onBindMyViewHolder(RecyclerView.ViewHolder baseHolder, int position);

}
