package com.example.dev.hazikura.fragment.Management;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.database.Cursor;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dev.hazikura.R;
import com.example.dev.hazikura.fragment.Household.DBAdapter;

public class SelectSheetTableFragment extends Fragment implements View.OnFocusChangeListener, SearchView.OnQueryTextListener{


    DBAdapter dbAdapter;

    private SearchView mSearchView03;           // 検索窓
    private TableLayout mTableLayout03List;     //データ表示用TableLayout

    private int colorFlg = 1;                   //背景切り替え用フラグ

    private final static int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final static int GCH = Gravity.CENTER_HORIZONTAL;
    private final static int GE = Gravity.END;         // Gravity.RIGHTでもよい

    View rootView;
    public static SelectSheetTableFragment newInstance() {
        SelectSheetTableFragment fragment = new SelectSheetTableFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        rootView = inflater.inflate(R.layout.select_table, container, false);
        dbAdapter = new DBAdapter(getActivity());

        findViews();        // 各部品の結び付け

        // 検索窓を開いた状態にする(設定していない場合はアイコンをクリックしないと入力箇所が開かない)
        mSearchView03.setIconified(false);
        // 検索窓のイベント処理
        mSearchView03.setOnQueryTextListener(this);

        return rootView;
    }

    /**
     * SearchViewの各イベント処理
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    /**
     * 各部品の結びつけ処理
     * findViews()
     */
    private void findViews() {

        mSearchView03 = (SearchView) rootView.findViewById(R.id.searchView);               // 検索窓
        if(mSearchView03 == null)
            Log.d("searchView","*********null*********");
        mTableLayout03List = (TableLayout) rootView.findViewById(R.id.tableLayout_outgo);    //データ表示用TableLayout
        if(mTableLayout03List == null)
            Log.d("LayoutTable","*********null*********");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {


        dbAdapter.readDB();                         // DBの読み込み(読み込みの方)

        mSearchView03.clearFocus();                 // 検索窓のフォーカスを外す(=キーボードを非表示)

        TableRow rowHeader = new TableRow(getActivity());    // 行を作成
        rowHeader.setPadding(16, 12, 16, 12);       // 行のパディングを指定(左, 上, 右, 下)

        // ヘッダー：日付
        TextView headerDate = setTextItem("日 付", GCH);            // TextViewのカスタマイズ処理
        TableRow.LayoutParams paramsDate = setParams(0.5f);       // LayoutParamsのカスタマイズ処理
        // ヘッダー：内容
        TextView headerContent = setTextItem("内 容", GCH);
        TableRow.LayoutParams paramsContent = setParams(0.4f);
        // ヘッダー：個数
        TextView headerNumber = setTextItem("個 数", GCH);
        TableRow.LayoutParams paramsNumber = setParams(0.2f);
        // ヘッダー：金額
        TextView headerAmount = setTextItem("金 額", GCH);
        TableRow.LayoutParams paramsAmount = setParams(0.3f);

        // rowHeaderにヘッダータイトルを追加
        rowHeader.addView(headerDate, paramsDate);          // ヘッダー：日付
        rowHeader.addView(headerContent, paramsContent);            // ヘッダー：内容
        rowHeader.addView(headerAmount, paramsAmount);            // ヘッダー：金額
        rowHeader.addView(headerNumber, paramsNumber);          // ヘッダー：個数
        rowHeader.setBackgroundResource(R.drawable.row_deco1);  // 背景

        // TableLayoutにrowHeaderを追加
        mTableLayout03List.addView(rowHeader);

        String column = "content";          //検索対象のカラム名
        String[] name = {query};            //検索対象の文字

        // DBの検索データを取得 入力した文字列を参照してDBの品名から検索
        Cursor c = dbAdapter.searchDB("outgo",null, column, name);

        if (c.moveToFirst()) {
            do {

                TableRow row = new TableRow(getActivity());          // 行を作成
                row.setPadding(16, 12, 16, 12);             // 行のパディングを指定(左, 上, 右, 下)

                // 日付
                TextView textDate = setTextItem(c.getString(2), GCH);     // TextViewのカスタマイズ処理
                // 内容
                TextView textContent = setTextItem(c.getString(3), GE);      // TextViewのカスタマイズ処理
                // 個数
                TextView textNumber = setTextItem(c.getString(4), GE);      // TextViewのカスタマイズ処理
                // 金額
                TextView textAmount = setTextItem(c.getString(5), GE);      // TextViewのカスタマイズ処理

                // rowHeaderに各項目(DBから取得した産地,内容,金額,個数)を追加
                row.addView(textDate, paramsDate);      // 産地
                row.addView(textContent, paramsContent);        // 内容
                row.addView(textNumber, paramsNumber);      // 個数
                row.addView(textAmount, paramsAmount);        // 金額

                mTableLayout03List.addView(row);            // TableLayoutにrowHeaderを追加

                // 交互に行の背景を変える
                if (colorFlg % 2 != 0) {
                    row.setBackgroundResource(R.drawable.row_deco2);
                } else {
                    row.setBackgroundResource(R.drawable.row_deco1);
                }
                colorFlg++;

            } while (c.moveToNext());
        } else {
            Toast.makeText(getActivity(), "検索結果 0件", Toast.LENGTH_SHORT).show();
        }
        c.close();
        dbAdapter.closeDB();        // DBを閉じる

        return false;
    }

        // 検索を始める時

        @Override
        public boolean onQueryTextChange(String s) {
            mTableLayout03List.removeAllViews();        // TableLayoutのViewを全て消す

            return false;
        }


    /**
     * 行の各項目のTextViewカスタマイズ処理
     * setTextItem()
     *
     * @param str     String
     * @param gravity int
     * @return title TextView タイトル
     */
    private TextView setTextItem(String str, int gravity) {
        TextView title = new TextView(getActivity());
        title.setTextSize(16.0f);           // テキストサイズ
        title.setTextColor(Color.BLACK);    // テキストカラー
        title.setGravity(gravity);          // テキストのGravity
        title.setText(str);                 // テキストのセット

        return title;
    }

    /**
     * 行の各項目のLayoutParamsカスタマイズ処理
     * setParams()
     */
    private TableRow.LayoutParams setParams(float f) {
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, WC);
        params.weight = f;      //weight(行内でのテキストごとの比率)

        return params;
    }

}
