package com.example.sqlitemvvm.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqlitemvvm.R;
import com.example.sqlitemvvm.adapter.BookInfoAdapter;
import com.example.sqlitemvvm.database.Database;
import com.example.sqlitemvvm.model.BookInfo;
import com.example.sqlitemvvm.viewmodel.BookVM;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Database database;
    BookVM bookVM = new BookVM();
    ArrayList<BookInfo> bookInfoArrayList;
    BookInfoAdapter bookInfoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
        bookInfoArrayList = new ArrayList<>();
        database = new Database(this);
        bookInfoArrayList = bookVM.getALl();
        bookInfoAdapter = new BookInfoAdapter(this, R.layout.dong_layout,bookInfoArrayList);
        listView.setAdapter(bookInfoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuadd,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuAdd){
            DialogAdd();
        }
        return super.onOptionsItemSelected(item);
    }
    public void DialogAdd(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add);
        dialog.setCanceledOnTouchOutside(false);
        EditText edtBookName,edtBookPage, edtBookPrice,edtBookDesc;
        Button btnAdd, btnHuy;


        edtBookName = dialog.findViewById(R.id.txtBookName);
        edtBookPage = dialog.findViewById(R.id.txtBookPage);
        edtBookPrice = dialog.findViewById(R.id.txtBookPrice);
        edtBookDesc = dialog.findViewById(R.id.txtBookDesc);

        btnAdd = dialog.findViewById(R.id.btnAdd);
        btnHuy = dialog.findViewById(R.id.btnHuy);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtBookName.getText().toString();
                int page = Integer.valueOf(edtBookPage.getText().toString().trim());
                int price = Integer.valueOf(edtBookPrice.getText().toString().trim());
                String desc = edtBookDesc.getText().toString();
                byte[] avt= null;
                BookInfo b = new BookInfo(0, name, page,price,desc,avt );
                if (bookVM.insert(b) > -1){
                    Toast.makeText(MainActivity.this, "Thêm mới sách thành công "+ name, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Thêm mới sách thất bại", Toast.LENGTH_LONG).show();
                }
                bookInfoArrayList.clear();
                GetDataToListView();
                bookInfoAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void DialogUpdate(int idUpdate) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update);
        dialog.setCanceledOnTouchOutside(false);

        EditText edtBookNameUpdate, edtBookPageUpdate, edtBookPriceUpdate, edtBookDescUpdate;
        Button btnUpdate, btnHuy;


        edtBookNameUpdate = dialog.findViewById(R.id.txtBookNameUpdate);
        edtBookPageUpdate = dialog.findViewById(R.id.txtBookPageUpdate);
        edtBookPriceUpdate = dialog.findViewById(R.id.txtBookPriceUpdate);
        edtBookDescUpdate = dialog.findViewById(R.id.txtBookDescUpdate);

        btnUpdate = dialog.findViewById(R.id.btnUpdate);
        btnHuy = dialog.findViewById(R.id.btnHuy);

        Cursor dataBook = database.selectSQL("SELECT * FROM BOOKS where Id=" + idUpdate + "");
        while (dataBook.moveToNext()) {
            edtBookNameUpdate.setText(dataBook.getString(1));
            edtBookPageUpdate.setText(String.valueOf(dataBook.getInt(2)));
            edtBookPriceUpdate.setText(String.valueOf(dataBook.getInt(3)));
            edtBookDescUpdate.setText(dataBook.getString(4));
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = edtBookNameUpdate.getText().toString();
                    int page = Integer.valueOf(edtBookPageUpdate.getText().toString().trim());
                    int price = Integer.valueOf(edtBookPriceUpdate.getText().toString().trim());
                    String desc = edtBookDescUpdate.getText().toString();
                    byte[] avt= null;
                    BookInfo b = new BookInfo(idUpdate, name, page,price, desc,avt );
                    if (bookVM.update(b) > -1){
                        Toast.makeText(MainActivity.this, "Cập nhật sách thành công "+ name, Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Cập nhật sách thất bại", Toast.LENGTH_LONG).show();
                    }
                    bookInfoArrayList.clear();
                    GetDataToListView();
                    bookInfoAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
    public void Delete(int id){
        bookVM.delete(id);
        bookInfoArrayList.clear();
        GetDataToListView();
        bookInfoAdapter.notifyDataSetChanged();
    }
    public void GetDataToListView(){
        Cursor dataBook = database.selectSQL("SELECT * FROM BOOKS");
        while(dataBook.moveToNext()){
            int id = dataBook.getInt(0);
            String ten = dataBook.getString(1);
            int page = dataBook.getInt(2);
            int price = dataBook.getInt(3);
            String desc = dataBook.getString(4);
            byte[] avt = dataBook.getBlob(5);
            bookInfoArrayList.add(new BookInfo(id,ten,page,price,desc,avt));
        }
    }
    private void requestPermissions(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openImagePicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Quyền truy cập bị từ chối\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("Nếu bạn từ chối quyền, bạn không thể sử dụng dịch vụ này\n\nVui lòng bật quyền tại [Setting]> [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }
    private void openImagePicker() {
        TedBottomPicker.with(MainActivity.this)
                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        try {
                            bitmap_transfer = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            imageView.setImageBitmap(bitmap_transfer);
                            imageView.buildDrawingCache();
                            setBitmap_transfer(imageView.getDrawingCache());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}