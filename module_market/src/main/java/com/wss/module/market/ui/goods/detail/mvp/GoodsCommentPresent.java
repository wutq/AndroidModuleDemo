package com.wss.module.market.ui.goods.detail.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.module.market.bean.GoodsComment;
import com.wss.module.market.ui.goods.detail.mvp.contract.GoodsCommentContract;
import com.wss.module.market.ui.goods.detail.mvp.model.GoodsCommentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：商品评论
 * Created by 吴天强 on 2018/11/5.
 */

public class GoodsCommentPresent extends BasePresenter<GoodsCommentModel, GoodsCommentContract.View>
        implements GoodsCommentContract.Presenter {


    @Override
    public void getGoodsCommentList() {
        List<GoodsComment> commentList = new ArrayList<>();
        GoodsComment comment = new GoodsComment();
        comment.setComment("手机到了刚弄好，非常流畅！就是电池不耐用，充一次用不到一天就没了 呸");
        comment.setStar(5);
        comment.setDate("2020年10月20日09:47:59");
        comment.setUserHead("http://i10.hoopchina.com.cn/hupuapp/bbs/966/16313966/thread_16313966_20180726164538_s_65949_o_w1024_h1024_62044.jpg?x-oss-process=image/resize,w_800/format,jpg");
        comment.setUserName("噶***蛋");
        commentList.add(comment);

        comment = new GoodsComment();
        comment.setComment("最重要的还是手机，各种操作都很流畅信号各方面都没有问题，谢谢！");
        comment.setStar(2);
        comment.setDate("2020年10月20日09:47:59");
        comment.setUserHead("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603168237890&di=e924574070032a4d98643123d7e11586&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201410%2F08%2F20141008210803_Kxjhf.thumb.700_0.jpeg");
        comment.setUserName("王***想");
        commentList.add(comment);

        comment = new GoodsComment();
        comment.setComment("物流速度很给力，同时店家也很有耐心客服也很有责任心各种操作都很流畅信号各方面都没有问题，谢谢！");
        comment.setStar(3);
        comment.setUserName("阿***蛋");
        comment.setDate("2020年10月20日09:47:59");
        comment.setUserHead("hhttps://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603168239065&di=e5b7ef4102677e10de3de3bfebf5491a&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201708%2F23%2F20170823102305_WeA2y.thumb.700_0.jpeg");
        commentList.add(comment);

        comment = new GoodsComment();
        comment.setComment("雷总果然强大，谢谢！");
        comment.setStar(1);
        comment.setUserName("P***去");
        comment.setUserHead("http://img1.touxiang.cn/uploads/20130927/27-020608_93.jpg");
        commentList.add(comment);
        comment.setDate("2020年10月20日09:47:59");

        comment = new GoodsComment();
        comment.setComment("雷总果然强大，家里好多电器都是小米的");
        comment.setStar(3);
        comment.setDate("2020年10月20日09:47:59");
        comment.setUserHead("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603168241013&di=6d4f6b4f0995325f4723460d0d956f1c&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171114%2F0fc43e9ad58f4a5cb41a018925b0e475.jpeg");
        comment.setUserName("补***萨");
        commentList.add(comment);

        comment = new GoodsComment();
        comment.setComment("手机到了刚弄好，非常流畅，家里好多电器都是小米的 谢谢！");
        comment.setStar(5);
        comment.setUserHead("http://img1.touxiang.cn/uploads/20131017/17-090235_674.jpg");
        comment.setUserName("b***y");
        comment.setDate("2020年10月20日09:47:59");
        commentList.add(comment);


        comment = new GoodsComment();
        comment.setComment("雷总果然强大，谢谢！");
        comment.setStar(1);
        comment.setDate("2020年10月20日09:47:59");
        comment.setUserName("P***去");
        comment.setUserHead("http://img1.touxiang.cn/uploads/20130927/27-020608_93.jpg");
        commentList.add(comment);

        comment = new GoodsComment();
        comment.setComment("物流速度很给力，同时店家也很有耐心客服也很有责任心各种操作都很流畅信号各方面都没有问题，谢谢！");
        comment.setStar(3);
        comment.setDate("2020年10月20日09:47:59");
        comment.setUserName("阿***蛋");
        comment.setUserHead("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603168242468&di=e62f8708f01cd7f85ef0a40bfa1fdee1&imgtype=0&src=http%3A%2F%2Fgaitaobao4.alicdn.com%2Ftfscom%2Fi4%2F2040947480%2FTB27GrsAgmTBuNjy1XbXXaMrVXa_%2521%25212040947480.jpg_300x300q90.jpg");
        commentList.add(comment);

        comment = new GoodsComment();
        comment.setComment("雷总果然强大，家里好多电器都是小米的");
        comment.setStar(3);
        comment.setDate("2020年10月20日09:47:59");
        comment.setUserHead("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2456975006,3192848039&fm=26&gp=0.jpg");
        comment.setUserName("补***萨");
        commentList.add(comment);

        comment = new GoodsComment();
        comment.setComment("雷总果然强大，谢谢！");
        comment.setStar(1);
        comment.setDate("2020年10月20日09:47:59");
        comment.setUserName("P***去");
        comment.setUserHead("http://img1.touxiang.cn/uploads/20130927/27-020608_93.jpg");
        commentList.add(comment);

        comment = new GoodsComment();
        comment.setComment("最重要的还是手机，各种操作都很流畅信号各方面都没有问题，谢谢！");
        comment.setStar(2);
        comment.setUserHead("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603168248736&di=7e59800fa7af4c3194872a78540af6f9&imgtype=0&src=http%3A%2F%2Ftupian.qqw21.com%2Farticle%2FUploadPic%2F2020-9%2F2020922033115276.jpg");
        comment.setUserName("王***想");
        commentList.add(comment);
        comment.setDate("2020年10月20日09:47:59");
        getView().refreshCommentList(commentList);
    }


    @Override
    protected GoodsCommentModel createModule() {
        return new GoodsCommentModel(getLifecycleOwner());
    }

    @Override
    public void start() {
        getGoodsCommentList();
    }
}
