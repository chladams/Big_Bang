package com.longshihan.kuang.bigbang1;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ReceiverClip extends AppCompatActivity implements BigBangLayout.ActionListener {
    public static final String EXTRA_TEXT = "extra_text";
    private BigBangLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_clip);
        mLayout = (BigBangLayout) findViewById(R.id.bigbang);
        mLayout.setActionListener(this);
        String text = getIntent().getStringExtra(EXTRA_TEXT);
        if (!TextUtils.isEmpty(text)) {
            List<String> keywordList = new ArrayList<>();
            try {
                byte[] bt = text.getBytes();
                InputStream ip = new ByteArrayInputStream(bt);
                Reader read = new InputStreamReader(ip);
                IKSegmenter iks = new IKSegmenter(read, true);//true开启只能分词模式，如果不设置默认为false，也就是细粒度分割
                Lexeme t;
                while ((t = iks.next()) != null) {
                    keywordList.add(t.getLexemeText());
                    mLayout.addTextItem(t.getLexemeText());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSearch(String text) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu" +
                    ".com/s?wd=" + URLEncoder.encode(text, "utf-8")));
            startActivity(intent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onShare(String text) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(sharingIntent);
    }

    @Override
    public void onCopy(String text) {
        ClipboardManager service = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        service.setPrimaryClip(ClipData.newPlainText("BigBang", text));
        Toast.makeText(this, R.string.copy_clip, Toast.LENGTH_SHORT).show();
    }
}