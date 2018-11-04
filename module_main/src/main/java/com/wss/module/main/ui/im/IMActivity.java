package com.wss.module.main.ui.im;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.wss.common.base.ActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.widget.ObserverButton;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.bean.IMMessage;
import com.wss.module.main.ui.im.adapter.IMAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Describe：聊天
 * Created by 吴天强 on 2018/10/30.
 */

public class IMActivity extends ActionBarActivity {


    @BindView(R2.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R2.id.edt_input)
    EditText edtInput;
    @BindView(R2.id.btn_send)
    ObserverButton btnSend;

    private List<IMMessage> data = new ArrayList<>();
    private IMAdapter adapter;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_im;
    }

    @Override
    protected void initView() {
        setTitleText("与张三聊天中");
        adapter = new IMAdapter(mContext, data);
        recycleView.setLayoutManager(new LinearLayoutManager(mContext));
        recycleView.setAdapter(adapter);
        btnSend.observer(edtInput);
        getData();
        notifyData();
    }

    private void getData() {
        data.add(new IMMessage("大妹子", R.drawable.main_im_message_head2, 0, "您好"));
        data.add(new IMMessage("张三", R.drawable.main_im_message_head1, 1, "好个毛，一天天的你好你好你好的，干啥呢啊，没有个正事儿啊 真是的 哪凉快哪呆着去"));
        data.add(new IMMessage("张三", R.drawable.main_im_message_head1, 1, "从前，一对夫妇租着一块地。有一天，老婆对老公说:“老公，上个月房东来催款，咱们就没给。老公说了:瞧你这话唠的，合计着这个月我能给是的！"));
        data.add(new IMMessage("张三", R.drawable.main_im_message_head1, 1, "好笑不， 哈哈哈哈哈哈哈"));
        data.add(new IMMessage("大妹子", R.drawable.main_im_message_head2, 0, "SB"));
        data.add(new IMMessage("张三", R.drawable.main_im_message_head1, 1, "你嗦啥？"));
        data.add(new IMMessage("大妹子", R.drawable.main_im_message_head2, 0, "古时候,人们讲究事死如事生，尤其王室皇家，更是如此，去世后会陪葬众多金银宝贝，所以千百年来，从陵墓里传出的宝贝不计其数，其中既有金银宝物，还有比较罕见的书画圣旨等，而且很多宝物都是在无意间被普通民众发现，或是上交，或是被当成传家宝传承，留下一段段有趣的故事，比如接下来要说的这件，就是男子拿着家传圣旨去鉴定真假，专家要求他上交，不料男子说2个字，让专家很尴尬。"));
        data.add(new IMMessage("张三", R.drawable.main_im_message_head1, 1, "...."));
    }

    @OnClick(R2.id.btn_send)
    public void onClick() {
        data.add(new IMMessage("大妹子", R.drawable.main_im_message_head2, 0, edtInput.getText().toString()));
        edtInput.setText("");
        notifyData();
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            data.add(new IMMessage("张三", R.drawable.main_im_message_head1, 1, "您呼叫的用户不在线！"));
            notifyData();
        }
    };

    private void notifyData() {
        recycleView.scrollToPosition(adapter.getItemCount() - 1);
        adapter.notifyDataSetChanged();
    }

}
