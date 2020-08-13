玩android模块

为防止同名资源文件被覆盖，则采用如下方式来规避
1.该module的资源命名必须以[wan_]打头
  如：wan_icon_close，wan_img_bg，wan_shape_round_4

2.该module的布局文件命名必须以[wan_]打头
  如：wan_activity_main，wan_fragment_main，wan_item_of_user_list

3.涉及到跨module跳转的Activity、Fragment需要在common_base模块的ARouterConfig中配置跳转的Path

