商城模块

为防止同名资源文件被覆盖，则采用如下方式来规避
1.该module的资源命名必须以[market_]打头
  如：market_icon_close，market_img_bg，market_shape_round_4

2.该module的布局文件命名必须以[market_]打头
  如：market_activity_main，market_fragment_main，market_item_of_user_list

3.涉及到跨module跳转的Activity、Fragment需要在common_base模块的ARouterConfig中配置跳转的Path

