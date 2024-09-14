class AppRoute {
  static String LOGIN_PAGE = "/login";
  static String HOME_PAGE = "/";
  static String REGISTER_PAGE = "/register";
  static String FORGET_PASSWORD_PAGE = "/forget_password";
  static String PROFILE_PAGE = "/profile";
  static String PROFILE_SETTING_PAGE = "/profile_setting";
  static String CAMERA_PAGE = "/camera";
  static String CART_PAGE = "/cart";
  static String PRODUCT_DETAIL = "/product_detail";
  static String COMBO_DETAIL = "/combo_detail";
  static String STORE_PAGE = "/store";
  static String CATEGORY_PAGE = "/category";
  static String CATEGORY_DETAIL_PAGE = "/category_detail";
  static String ORDER_PAGE = "/order";
  static String PRODUCT_ALL_PAGE = "/product_all";
  static String SEARCH_PAGE = "/search";
  static String PROFILE_CAMERA_PAGE = "/profile-camera";
  static String PROFILE_SETUP_PAGE = "/profile-setup";
  static String PROMOTION_PAGE = "/promotion";
  static String ORDER_DETAIL_PAGE = "/order-detail";
  static String CHART_PAGE = "/chart";

  static String get_order_detail(String orderCode) => '$ORDER_DETAIL_PAGE?orderCode=$orderCode';
  static String get_combo_detail(int comboId) => '$COMBO_DETAIL?comboId=$comboId';
  static String get_product_detail(int productId) => '$PRODUCT_DETAIL?productId=$productId';
  static String get_product_bycategoryid_detail(int categoryid,String categoryname) => '$CATEGORY_DETAIL_PAGE?categoryid=$categoryid&categoryname=$categoryname';
}