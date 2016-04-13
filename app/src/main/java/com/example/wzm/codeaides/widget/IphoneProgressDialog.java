package com.example.wzm.codeaides.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wzm.codeaides.R;

/**
 * Created by wzm on 2016/4/12.
 */
public class IphoneProgressDialog extends Dialog {
    private Context context;
    private ImageView img;
    private TextView txt;

    public IphoneProgressDialog(Context context) {
        super(context, R.style.iphone_progress_dialog);
        this.context = context;
        //加载布局文件
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.animation_loading, null);
//        img = (ImageView) view.findViewById(R.id.iphone_progress_dialog_img);
//        txt = (TextView) view.findViewById(R.id.iphone_progress_dialog_txt);
        //给图片添加动态效果
//        Animation anim = AnimationUtils.loadAnimation(context, R.anim.progressbar);
//        img.setAnimation(anim);
//        txt.setText("加载中");
        //dialog添加视图
        setContentView(view);

    }

    public void setMsg(String msg) {
        txt.setText(msg);
    }

    public void setMsg(int msgId) {
        txt.setText(msgId);
    }

}
