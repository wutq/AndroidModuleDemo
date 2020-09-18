package com.wss.module.main.ui.main.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.bean.Template;
import com.wss.common.constants.ARouterConfig;
import com.wss.common.constants.Constants;
import com.wss.common.view.scan.ScanActivity;
import com.wss.module.main.ui.main.mvp.contract.CaseContract;
import com.wss.module.main.ui.main.mvp.model.CaseModel;
import com.wss.module.main.ui.page.CountViewActivity;
import com.wss.module.main.ui.page.CustomViewActivity;
import com.wss.module.main.ui.page.DialogActivity;
import com.wss.module.main.ui.page.FlowLayoutActivity;
import com.wss.module.main.ui.page.MultipleItemActivity;
import com.wss.module.main.ui.page.ObserverButtonActivity;
import com.wss.module.main.ui.page.RadioGroupActivity;
import com.wss.module.main.ui.page.SwitchButtonActivity;
import com.wss.module.main.ui.page.navigation.CityListActivity;
import com.wss.module.main.ui.page.selector.SelectorActivity;
import com.wss.module.main.ui.page.viscosity.ComplexViscositySlideActivity;
import com.wss.module.main.ui.page.viscosity.SimpleViscositySlideActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：案例控制器
 * Created by 吴天强 on 2018/11/20.
 */
public class CasePresenter extends BasePresenter<CaseModel, CaseContract.View> implements CaseContract.Presenter {

    @Override
    protected CaseModel createModule() {
        return new CaseModel(getLifecycleOwner());
    }

    @Override
    public void start() {
        initMenuBlock();
    }

    private void initMenuBlock() {
        //案例模板
        List<Template> list = new ArrayList<>();
        list.add(new Template("商城", ARouterConfig.MARKET_MAIN_ACTIVITY, Constants.TemplateType.AROUTER, "简单的一个商城模板，本地购物车，商品详情和TB、JD相似"));
        list.add(new Template("多功能选择器", SelectorActivity.class, "时间、日期、地址、自定义数据底部弹窗选择器"));
        list.add(new Template("多功能横向Item", MultipleItemActivity.class, "左右文字+Icon的Item  支持右输入框显示"));
        list.add(new Template("数量加减控件", CountViewActivity.class, "数量加减、支持手动输入"));
        list.add(new Template("观察者按钮", ObserverButtonActivity.class, "利用按钮对多个输入框监听，如：注册、from表单"));
        list.add(new Template("流式布局", FlowLayoutActivity.class, "流式布局，从左上角位置开始，根据ItemView的宽度自动换行"));
        list.add(new Template("多功能对话框", DialogActivity.class, "常用对话框，支持输入框、单按钮、个性化设置等"));
        list.add(new Template("自定义RadioGroup", RadioGroupActivity.class, "自定义RadioGroup，可以包含任意View内容，如首页Tab带消息角标"));
        list.add(new Template("扫一扫", ScanActivity.class, "扫描二维码，支持自动缩放距离"));
        list.add(new Template("字母导航List", CityListActivity.class, "根据首字母定位到具体的Item，如选择城市右侧导航条 "));
        list.add(new Template("简单黏性滑动", SimpleViscositySlideActivity.class, "页面滑动，某一个View滑动到顶部固定不动"));
        list.add(new Template("复杂黏性滑动", ComplexViscositySlideActivity.class, "诸如APP首页，既有上下滑动，也有左右滑动，还需要悬停某一项Item"));
        list.add(new Template("自定义View展览区", CustomViewActivity.class, "展示一些自定义的View,学习为主"));
        list.add(new Template("自定义开关", SwitchButtonActivity.class, "嗐~UI非要仿iOS风格的开关按钮"));

        getView().refreshCaseList(list);
    }

}
