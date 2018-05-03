package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.MyDiscountBean;
import com.wishland.www.wanhaohui2.utils.CodeUtils;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.activity.MyMessageActivity;

import java.util.List;

/**
 * Created by admin on 2017/10/13.
 */

public class MyDiscountAdapter extends SuperAdapter<MyDiscountBean.MyDiscountData.NewDiscountBean> {

    private Context mContext;
    private Activity ctx;
    private CodeUtils codeUtils;
    private String strRand = "";
    private OnItemClickListener mOnItemClickListener = null;

    public MyDiscountAdapter(Activity ctx, Context mContext) {
        super(ctx);
        this.mContext = mContext;
        this.ctx = ctx;
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_my_discount, parent, false);
    }

    @Override
    protected void onBindItemView(final View view, int viewType, final int position) {
        MyDiscountBean.MyDiscountData.NewDiscountBean bean = getItem(position);
        TextView title = (TextView) view.findViewById(R.id.tv_my_discount_game_title);
        TextView money = (TextView) view.findViewById(R.id.tv_my_discount_money);
        TextView tj = (TextView) view.findViewById(R.id.my_discount_tj);
        TextView game = (TextView) view.findViewById(R.id.my_discount_game);
        TextView shenqing = (TextView) view.findViewById(R.id.tv_my_discount_sq);
        TextView date = (TextView) view.findViewById(R.id.tv_my_discount_date);
        RelativeLayout rl_my_discount_bg = (RelativeLayout) view.findViewById(R.id.rl_my_discount_bg);

        title.setText(bean.getTitle());
        money.setText(bean.getVictitle());
        tj.setText(bean.getDm() + "倍流水");
        game.setText(bean.getGame());
        date.setText(bean.getEndtime().substring(0, 10));

        if ("discount".equals(bean.getType())) {
            shenqing.setText("申请领取");
            date.setVisibility(View.GONE);
            rl_my_discount_bg.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.my_discount_bg_right));
            rl_my_discount_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (mOnItemClickListener != null) {
//                        mOnItemClickListener.onItemClick(view, position);
//                    }
                    showDiacountDialog();
                }
            });
        } else if ("apply".equals(bean.getType())) {
            date.setVisibility(View.VISIBLE);
            shenqing.setText("审核中");
            rl_my_discount_bg.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.my_discount_bg_right_pure));
        } else if ("pass".equals(bean.getType())) {
            date.setVisibility(View.VISIBLE);
            shenqing.setText("已领取");
            rl_my_discount_bg.setBackgroundDrawable(mContext.getResources().getDrawable(R.color.text_color2));
        } else if ("reject".equals(bean.getType())) {
            date.setVisibility(View.VISIBLE);
            shenqing.setText("审核未通过");
            rl_my_discount_bg.setBackgroundDrawable(mContext.getResources().getDrawable(R.color.text_color2));
        } else {
            date.setVisibility(View.VISIBLE);
            shenqing.setText("已过期");
            rl_my_discount_bg.setBackgroundDrawable(mContext.getResources().getDrawable(R.color.text_color2));
        }
    }

    public  interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    private void showDiacountDialog() {
//        AlertDialog alertDialog=new AlertDialog.Builder(mContext).create();
//        alertDialog.show();
//        alertDialog.getWindow().setContentView(R.layout.my_discount_dialog);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext, R.style.AlertDialog);

        RelativeLayout loginDialog = (RelativeLayout) ctx.getLayoutInflater().inflate(R.layout.my_discount_dialog, null);
        Button submit = (Button) loginDialog.findViewById(R.id.bt_submit_discount);
        final EditText et_discount_pt = (EditText) loginDialog.findViewById(R.id.et_discount_pt);
        final EditText et_discount_game_num = (EditText) loginDialog.findViewById(R.id.et_discount_game_num);
        final EditText et_discount_money = (EditText) loginDialog.findViewById(R.id.et_discount_money);
        final EditText et_discount_yzm = (EditText) loginDialog.findViewById(R.id.et_discount_yzm);
        final ImageView iv_yzm = (ImageView) loginDialog.findViewById(R.id.iv_yzm);

        codeUtils = CodeUtils.getInstance();
        strRand = "";
        for (int i = 0; i < 4; i++) {
            strRand += String.valueOf((int) (Math.random() * 10));
        }
        Bitmap bitmap = codeUtils.createBitmap(strRand);
        iv_yzm.setImageBitmap(bitmap);

        alertDialogBuilder.setView(loginDialog);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(et_discount_pt.getText().toString())) {
                    ToastUtil.showShort(mContext, "平台不能为空");
                    return;
                }
                if ("".equals(et_discount_game_num.getText().toString())) {
                    ToastUtil.showShort(mContext, "注单号不能为空");
                    return;
                }
                if ("".equals(et_discount_money.getText().toString())) {
                    ToastUtil.showShort(mContext, "下注金额不能为空");
                    return;
                }
                if (!strRand.equals(et_discount_yzm.getText().toString())) {
                    ToastUtil.showShort(mContext, "验证码不正确");
                    return;
                }
                submitDiscount();
                alertDialog.dismiss();
            }
        });
        iv_yzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strRand = "";
                for (int i = 0; i < 4; i++) {
                    strRand += String.valueOf((int) (Math.random() * 10));
                }
                Bitmap bitmap = codeUtils.createBitmap(strRand);
                iv_yzm.setImageBitmap(bitmap);
            }
        });

    }

    private void submitDiscount() {

    }
}
