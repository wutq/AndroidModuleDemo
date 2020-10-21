package com.wss.common.constants;

/**
 * Describe：字典 存放Activity跳转传参key、缓存数据到SP中key等。
 * Created by 吴天强 on 2018/11/13.
 */
public interface Dic {

    /**
     * 跳转画廊参数-图片集合
     */
    String IMAGE_LIST = "image_list";
    /**
     * 跳转画廊参数-图片起始位置
     */
    String IMAGE_POSITION = "image_position";
    /**
     * 跳转画廊参数-是否本地图片
     */
    String IMAGE_LOCAL = "image_local";
    /**
     * 跳转画廊参数-是否全屏显示
     */
    String FULL_SCREEN = "full_screen";

    /**
     * 通过路由跳转的Fragment的路径
     */
    String TARGET_FRAGMENT_PATH = "target_fragment_path";

    /**
     * 进入类型
     */
    String FROM_TYPE = "from_type";

    /**
     * 链接
     */
    String URL = "url";

    /**
     * 需要actionBar
     */
    String IS_ACTION_BAR = "is_action_bar";
    /**
     * actionBar 文字
     */
    String TITLE_TEXT = "title_text";
    /**
     * bundle带入的数据
     */
    String BUNDLE_DATA = "bundle_data";

    /**
     * 可选文件的最大值
     */
    String MAX_SELECT_FILE = "max_select_file";
    /**
     * 选择的文件路径列表
     */
    String SELECT_FILE_PATHS = "select_file_paths";
    /**
     * 商品信息
     */
    String GOODS_INFO = "goods_info";
    /**
     * 登录用户
     */
    String LOGIN_USER_INFO = "login_user_info";
    /**
     * 系列子类
     */
    String CLASSIFICATION_CHILD = "classification_child";
    /**
     * HouseFragment中的ViewPager
     */
    String HOUSE_FRAGMENT_VIEWPAGER = "viewpager";
    /**
     * 供应商 商品 信息
     */
    String VENDOR_GOODS_INFO = "vendor_goods_info";


}
