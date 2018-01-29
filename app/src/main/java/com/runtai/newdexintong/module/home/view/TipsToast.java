package com.runtai.newdexintong.module.home.view;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.runtai.newdexintong.R;

public class TipsToast extends Toast {
    public TipsToast(Context paramContext) {
        super(paramContext);
    }

    public static TipsToast makeText(Context paramContext, int paramInt1, int paramInt2)
            throws NotFoundException {
        return makeText(paramContext, paramContext.getResources().getText(paramInt1), paramInt2);
    }

    public static TipsToast makeText(Context paramContext, CharSequence paramCharSequence, int paramInt) {
        TipsToast localTipsToast = new TipsToast(paramContext);
        View localView = ((LayoutInflater) paramContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_tips, null);
        ((TextView) localView.findViewById(R.id.tips_msg)).setText(paramCharSequence);
        localTipsToast.setView(localView);
        localTipsToast.setGravity(16, 0, 0);
        localTipsToast.setDuration(paramInt);
        return localTipsToast;
    }

    public void setIcon(int paramInt) {
        if (getView() == null)
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        ImageView localImageView = (ImageView) getView().findViewById(R.id.tips_icon);
        if (localImageView == null)
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        localImageView.setImageResource(paramInt);
    }

    public void setText(CharSequence paramCharSequence) {
        if (getView() == null)
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        TextView localTextView = (TextView) getView().findViewById(R.id.tips_msg);
        if (localTextView == null)
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        localTextView.setText(paramCharSequence);
    }
}

