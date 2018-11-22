package com.wss.module.market.ui.goods.detail.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.module.market.bean.GoodsComment;
import com.wss.module.market.bean.GoodsInfo;
import com.wss.module.market.ui.goods.detail.mvp.contract.GoodsDetailContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：商品详情Presenter
 * Created by 吴天强 on 2018/11/1.
 */

public class GoodsDetailPresenter extends BasePresenter<GoodsDetailContract.Model, GoodsDetailContract.View>
        implements GoodsDetailContract.Presenter {


    /**
     * 获取商品详情
     */
    @Override
    public void getGoodsInfo() {
        if (isViewAttached()) {
            //造伪数据
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setGoodsId("1");
            goodsInfo.setGoodsName("小米Mix3 6GB+128GB黑色 骁龙845 全网通4G 双卡双待 全面屏拍照游戏智能手机");
            goodsInfo.setVendorId("1");
            goodsInfo.setGoodsMasterImg("https://img14.360buyimg.com/n0/jfs/t1/1768/16/11748/360528/5bd072f8E06e4e532/b5d152da8a5dd0dc.jpg");
            List<String> goodsImgs = new ArrayList<>();
            goodsImgs.add("https://img14.360buyimg.com/n0/jfs/t1/1867/31/11716/401006/5bd072f8E6db292ab/f3610e2e816ade0f.jpg");
            goodsImgs.add("https://img14.360buyimg.com/n0/jfs/t1/2695/31/11550/73677/5bd072f8E7a7372dc/0a3da61ccc9e3762.jpg");
            goodsImgs.add("https://img14.360buyimg.com/n0/jfs/t1/708/33/12050/20042/5bd072f9E6702c378/3d2b5d137aea371a.jpg");
            goodsInfo.setGoodsHeadImg(goodsImgs);
            goodsInfo.setGoodsOldPrice("9999");
            goodsInfo.setGoodsPrice("3999");
            goodsInfo.setCommentCount("999");
            goodsInfo.setPraiseRate("97.8%");
            getView().goodsInfo(goodsInfo);
        }
    }

    /**
     * 推荐商品
     */
    @Override
    public void getRecommendList() {
        if (isViewAttached()) {
            List<List<GoodsInfo>> recommendList = new ArrayList<>();
            List<GoodsInfo> goodsInfoList1 = new ArrayList<>();
            GoodsInfo goodsInfo1 = new GoodsInfo();
            goodsInfo1.setGoodsId("1");
            goodsInfo1.setGoodsName("小米（MI） 小米mix3 手机 黑色 全网通6G RAM 128G ROM ");
            goodsInfo1.setGoodsMasterImg("https://img14.360buyimg.com/n0/jfs/t1/8925/38/4406/349935/5bdad9b1Eb8638a99/0c7c764b234e0c02.jpg");
            goodsInfo1.setGoodsOldPrice("777");
            goodsInfo1.setGoodsPrice("3899");
            goodsInfoList1.add(goodsInfo1);
            GoodsInfo goodsInfo2 = new GoodsInfo();
            goodsInfo2.setGoodsId("1");
            goodsInfo2.setGoodsName("小米（MI） 送豪礼 Mix3手机 全面屏 黑色 8G+256G ");
            goodsInfo2.setGoodsMasterImg("https://img14.360buyimg.com/n0/jfs/t1/8996/26/2402/124002/5bd27d42E7c3950db/f51f482b3c0a50e6.jpg");
            goodsInfo2.setGoodsOldPrice("8888");
            goodsInfo2.setGoodsPrice("4699");
            goodsInfoList1.add(goodsInfo2);

            List<GoodsInfo> goodsInfoList2 = new ArrayList<>();
            GoodsInfo goodsInfo3 = new GoodsInfo();
            goodsInfo3.setGoodsId("1");
            goodsInfo3.setGoodsName("小米（MI） MIX2S 全面屏手机 游戏手机 白 全网通(6G+128G)");
            goodsInfo3.setGoodsMasterImg("https://img14.360buyimg.com/n1/s546x546_jfs/t22591/58/2380284311/151588/1344c74b/5b7d2296N224823a9.jpg");
            goodsInfo3.setGoodsOldPrice("10001");
            goodsInfo3.setGoodsPrice("5688");
            goodsInfoList2.add(goodsInfo3);
            GoodsInfo goodsInfo4 = new GoodsInfo();
            goodsInfo4.setGoodsId("1");
            goodsInfo4.setGoodsName(" 小米（MI） 小米 MIX2 手机s 黑色 全网通（6G+64G）");
            goodsInfo4.setGoodsMasterImg("https://img14.360buyimg.com/n0/jfs/t26539/294/374934442/170031/37ad9714/5b8f9d4eN5455c30c.jpg");
            goodsInfo4.setGoodsOldPrice("3333");
            goodsInfo4.setGoodsPrice("2049");
            goodsInfoList2.add(goodsInfo4);

            recommendList.add(goodsInfoList1);
            recommendList.add(goodsInfoList2);
            getView().recommendList(recommendList);
        }
    }

    /**
     * 商品精彩评论
     */
    @Override
    public void getCommentList() {
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

            getView().commentList(commentList);
        }
    }

    @Override
    protected GoodsDetailContract.Model createModule() {
        return null;
    }

    @Override
    public void start() {
        getGoodsInfo();
        getRecommendList();
        getCommentList();
    }

}
