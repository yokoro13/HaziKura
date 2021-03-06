package com.example.dev.hazikura.fragment.Management;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.database.Cursor;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.TextView;
import com.example.dev.hazikura.R;
import com.example.dev.hazikura.fragment.Household.DBAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yokoro
 **/

public class SelectListViewOutFragment extends Fragment{

    private DBAdapter dbAdapter;
    private SelectListViewOutFragment.MyBaseAdapter myBaseAdapter;
    private List<MyListItem> items;
    private ListView mListView;
    protected MyListItem myListItem;

    private TextView totalOutgo;

    private String[] columns = null;
    View rootView;


    public static SelectListViewOutFragment newInstance() {
        SelectListViewOutFragment fragment = new SelectListViewOutFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.select_out_listview, container, false);

        dbAdapter = new DBAdapter(getActivity());
        items = new ArrayList<>();

        myBaseAdapter = new SelectListViewOutFragment.MyBaseAdapter(getActivity(),items);

        totalOutgo =  (TextView) rootView.findViewById(R.id.display_TotalOutput);
        mListView = (ListView) rootView.findViewById(R.id.listView_out_management);

        loadMyList();

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("削除");
                builder.setMessage("削除しますか？");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myListItem = items.get(position);
                        int listID = myListItem.getId();

                        dbAdapter.openDB();
                        dbAdapter.selectDelete("outgo", String.valueOf(listID));
                        Log.d("Long Click:",String.valueOf(listID));
                        dbAdapter.closeDB();
                        loadMyList();
                    }
                });
                builder.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }});
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void loadMyList(){
        int ttl = 0;
        items.clear();
        dbAdapter.openDB();

        Cursor c = dbAdapter.getDB("outgo", columns);

        if (c.moveToFirst()){
            do {
                myListItem = new MyListItem(
                        c.getInt(0),//id
                        c.getString(1),//date
                        c.getString(2),//content
                        c.getString(3),//number
                        c.getString(4));//amount

                Log.d("取得ID:", String.valueOf(c.getInt(0)));
                Log.d("取得ID:", c.getString(1));
                Log.d("取得ID:", c.getString(2));
                Log.d("取得ID:", c.getString(3));
                Log.d("取得ID:", c.getString(4));

                ttl = ttl + Integer.parseInt(c.getString(4));

                Log.d("total",Integer.toString(ttl));
                items.add(myListItem);

            } while (c.moveToNext());
        }
        c.close();
        dbAdapter.closeDB();
        mListView.setAdapter(myBaseAdapter);
        myBaseAdapter.notifyDataSetChanged();
        totalOutgo.setText(Integer.toString(ttl));

        Log.d("notify:", "list更新");
    }

    public class MyBaseAdapter extends BaseAdapter {
        private Activity activity;
        private List<MyListItem> items;

        // 毎回findViewByIdをする事なく、高速化が出来るようするholderクラス
        private class ViewHolder {
            TextView text_date;
            TextView text_content;
            TextView text_number;
            TextView text_amount;
        }

        // コンストラクタの生成
        public MyBaseAdapter(Activity activity, List<MyListItem> items) {
            this.activity = activity;
            this.items = items;
            Log.d("contract","コントラクタ通過");
        }

        // Listの要素数を返す
        @Override
        public int getCount() {
            return items.size();
        }

        // indexやオブジェクトを返す
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        // IDを他のindexに返す
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 新しいデータが表示されるタイミングで呼び出される
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = convertView;
            SelectListViewOutFragment.MyBaseAdapter.ViewHolder holder;

            // データを取得
            myListItem = items.get(position);


            if (view == null) {
                LayoutInflater inflater =
                        (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.row_out_listview, parent, false);

                TextView text_date = (TextView) view.findViewById(R.id.text_outputDate);
                TextView text_content = (TextView) view.findViewById(R.id.text_outputContent);
                TextView text_number = (TextView) view.findViewById(R.id.text_outputNumber);
                TextView text_amount = (TextView) view.findViewById(R.id.text_outputAmount);

                // holderにviewを持たせておく
                holder = new SelectListViewOutFragment.MyBaseAdapter.ViewHolder();
                holder.text_date = text_date;
                holder.text_content = text_content;
                holder.text_number = text_number;
                holder.text_amount = text_amount;
                view.setTag(holder);

                Log.d("getView","view get");

            } else {
                // 初めて表示されるときにつけておいたtagを元にviewを取得する
                holder = (SelectListViewOutFragment.MyBaseAdapter.ViewHolder) view.getTag();
            }

            // 取得した各データを各TextViewにセット
            holder.text_date.setText(myListItem.getDate());
            holder.text_content.setText(myListItem.getContent());
            holder.text_number.setText(myListItem.getNumber());
            holder.text_amount.setText(myListItem.getAmount());

            Log.d("setView","view set");
            return view;
        }
    }

}
