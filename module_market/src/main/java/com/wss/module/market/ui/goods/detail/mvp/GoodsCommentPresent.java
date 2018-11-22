package com.wss.module.market.ui.goods.detail.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.module.market.bean.GoodsComment;
import com.wss.module.market.ui.goods.detail.mvp.contract.GoodsCommentContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：商品评论
 * Created by 吴天强 on 2018/11/5.
 */

public class GoodsCommentPresent extends BasePresenter<GoodsCommentContract.Model, GoodsCommentContract.View>
        implements GoodsCommentContract.Presenter {


    @Override
    public void getGoodsCommentList() {
        if (isViewAttached()) {
            List<GoodsComment> commentList = new ArrayList<>();
            GoodsComment comment = new GoodsComment();
            comment.setComment("手机到了刚弄好，非常流畅");
            comment.setStar(5);
            comment.setUserHead("http://i10.hoopchina.com.cn/hupuapp/bbs/966/16313966/thread_16313966_20180726164538_s_65949_o_w1024_h1024_62044.jpg?x-oss-process=image/resize,w_800/format,jpg");
            comment.setUserName("噶***蛋");
            commentList.add(comment);

            comment = new GoodsComment();
            comment.setComment("最重要的还是手机，各种操作都很流畅信号各方面都没有问题，谢谢！");
            comment.setStar(2);
            comment.setUserHead("https://img4.duitang.com/uploads/item/201602/01/20160201111345_4kvQA.jpeg");
            comment.setUserName("王***想");
            commentList.add(comment);

            comment = new GoodsComment();
            comment.setComment("物流速度很给力，同时店家也很有耐心客服也很有责任心各种操作都很流畅信号各方面都没有问题，谢谢！");
            comment.setStar(3);
            comment.setUserName("阿***蛋");
            comment.setUserHead("https://img4.duitang.com/uploads/item/201601/16/20160116161347_svH5y.thumb.224_0.jpeg");
            commentList.add(comment);

            comment = new GoodsComment();
            comment.setComment("雷总果然强大，谢谢！");
            comment.setStar(1);
            comment.setUserName("P***去");
            comment.setUserHead("http://img1.touxiang.cn/uploads/20130927/27-020608_93.jpg");
            commentList.add(comment);

            comment = new GoodsComment();
            comment.setComment("雷总果然强大，家里好多电器都是小米的");
            comment.setStar(3);
            comment.setUserHead("http://imgsrc.baidu.com/imgad/pic/item/ca1349540923dd54afaa0d4bdb09b3de9c82483c.jpg");
            comment.setUserName("补***萨");
            commentList.add(comment);

            comment = new GoodsComment();
            comment.setComment("手机到了刚弄好，非常流畅，家里好多电器都是小米的 谢谢！");
            comment.setStar(5);
            comment.setUserHead("http://img1.touxiang.cn/uploads/20131017/17-090235_674.jpg");
            comment.setUserName("b***y");
            commentList.add(comment);


            comment = new GoodsComment();
            comment.setComment("雷总果然强大，谢谢！");
            comment.setStar(1);
            comment.setUserName("P***去");
            comment.setUserHead("http://img1.touxiang.cn/uploads/20130927/27-020608_93.jpg");
            commentList.add(comment);

            comment = new GoodsComment();
            comment.setComment("物流速度很给力，同时店家也很有耐心客服也很有责任心各种操作都很流畅信号各方面都没有问题，谢谢！");
            comment.setStar(3);
            comment.setUserName("阿***蛋");
            comment.setUserHead("https://img4.duitang.com/uploads/item/201601/16/20160116161347_svH5y.thumb.224_0.jpeg");
            commentList.add(comment);

            comment = new GoodsComment();
            comment.setComment("雷总果然强大，家里好多电器都是小米的");
            comment.setStar(3);
            comment.setUserHead("http://imgsrc.baidu.com/imgad/pic/item/ca1349540923dd54afaa0d4bdb09b3de9c82483c.jpg");
            comment.setUserName("补***萨");
            commentList.add(comment);

            comment = new GoodsComment();
            comment.setComment("雷总果然强大，谢谢！");
            comment.setStar(1);
            comment.setUserName("P***去");
            comment.setUserHead("http://img1.touxiang.cn/uploads/20130927/27-020608_93.jpg");
            commentList.add(comment);

            comment = new GoodsComment();
            comment.setComment("最重要的还是手机，各种操作都很流畅信号各方面都没有问题，谢谢！");
            comment.setStar(2);
            comment.setUserHead("https://img4.duitang.com/uploads/item/201602/01/20160201111345_4kvQA.jpeg");
            comment.setUserName("王***想");
            commentList.add(comment);
            getView().commentList(commentList);
        }
    }


    @Override
    protected GoodsCommentContract.Model createModule() {
        return null;
    }

    @Override
    public void start() {
        getGoodsCommentList();
    }
}
