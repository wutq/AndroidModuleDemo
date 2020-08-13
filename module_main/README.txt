项目主模块

为防止同名资源文件被覆盖，则采用如下方式来规避
1.该module的资源命名必须以[main_]打头
  如：main_icon_close，main_img_bg，main_shape_round_4

2.该module的布局文件命名必须以[main_]打头
  如：main_activity_main，main_fragment_main，main_item_of_user_list

3.涉及到跨module跳转的Activity、Fragment需要在common_base模块的ARouterConfig中配置跳转的Path

