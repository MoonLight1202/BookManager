package com.example.sqlitemvvm.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqlitemvvm.R;
import com.example.sqlitemvvm.model.BookInfo;
import com.example.sqlitemvvm.views.MainActivity;

import java.util.List;

public class BookInfoAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<BookInfo> bookInfoList;

    public BookInfoAdapter(MainActivity context, int layout, List<BookInfo> bookInfoList) {
        this.context = context;
        this.layout = layout;
        this.bookInfoList = bookInfoList;
    }

    @Override
    public int getCount() {
        return bookInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout,null);
           // viewHolder.txtInfo = view.findViewById(R.id.txtInfo);
            viewHolder.imgViewEdit = view.findViewById(R.id.edit);
            viewHolder.imgViewDelete = view.findViewById(R.id.delete);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //gán giá trị
        BookInfo bookInfo = bookInfoList.get(i);
        viewHolder.txtInfo.setText(bookInfo.getName());
        viewHolder.imgViewDelete.setImageResource(R.drawable.close);
        viewHolder.imgViewEdit.setImageResource(R.drawable.edit);

       // bắt sự kiện cho xoá và sửa
        viewHolder.imgViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              context.DialogUpdate(bookInfo.getId());
            }
        });
        viewHolder.imgViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Cảnh báo!");
                alert.setMessage("Bạn có muốn xoá "+ bookInfo.getName()+ " không?");
                alert.setNegativeButton("Không", null);
                alert.setPositiveButton("Có", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context,"Đã xoá sản phẩm "+bookInfo.getName(),Toast.LENGTH_SHORT).show();
                        context.Delete(bookInfo.getId());
                    }});
                alert.show();
            }
        });
        return view;
    }
    class ViewHolder{
        TextView txtInfo;
        ImageView imgViewDelete;
        ImageView imgViewEdit;
    }
}
