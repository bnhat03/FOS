import 'package:android_project/custom/big_text.dart';
import 'package:android_project/custom/select_profile_custom.dart';
import 'package:android_project/custom/select_setting_custom.dart';
import 'package:android_project/route/app_route.dart';
import 'package:android_project/theme/app_color.dart';
import 'package:android_project/theme/app_dimention.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
class ProfileSetting extends StatefulWidget {
  const ProfileSetting({
    Key? key,
  }) : super(key: key);

  @override
  _ProfileSettingState createState() => _ProfileSettingState();
}

class _ProfileSettingState extends State<ProfileSetting> {
  Color _rowColor = Colors.transparent;
  Color _rowColor1 = Colors.transparent;

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        SizedBox(height: AppDimention.size20,),
        BigText(text: "Account",size: AppDimention.size40,),
        SizedBox(height: AppDimention.size5,),
        Container(
            width: 373,
            height: 277,
            padding: EdgeInsets.all(AppDimention.size10),
            decoration: BoxDecoration(
              color: Colors.white,
              borderRadius: BorderRadius.circular(AppDimention.size10),
              boxShadow: [
                BoxShadow(
                  color: Colors.grey.withOpacity(0.2),
                  spreadRadius: 2,
                  blurRadius: 5,
                  offset: Offset(0, 3),
                ),
              ],
            ),
            child: Column(
              
              children: [
                GestureDetector(
                  onTap: (){
                    
                  },
                  onTapDown: (_) {
                    setState(() {
                      _rowColor = Colors.grey.withOpacity(0.3); 
                    });
                  },
                  onTapUp: (_) {
                    setState(() {
                      _rowColor = Colors.transparent; 
                    });
                  },
                  child: Container(
                    decoration: BoxDecoration(
                        color: _rowColor, 
                        borderRadius: BorderRadius.circular(AppDimention.size10),
                    ),
                    
          
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        SelectSettingCustom(
                          icon: Icons.person,
                          title: "Hồ sơ",
                        ),
                        Icon(
                          Icons.arrow_right_rounded,
                          size: AppDimention.size80,
                          color: AppColor.mainColor,
                        ),
                      ],
                    ),
                  ),
                ),
                GestureDetector(
                  onTap: (){
                    
                  },
                  onTapDown: (_) {
                    setState(() {
                      _rowColor1 = Colors.grey.withOpacity(0.3); 
                    });
                  },
                  onTapUp: (_) {
                    setState(() {
                      _rowColor1 = Colors.transparent; 
                    });
                  },
                  child: Container(
                    decoration: BoxDecoration(
                        color: _rowColor1, 
                        borderRadius: BorderRadius.circular(AppDimention.size10),
                    ),
                    
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        SelectSettingCustom(
                          icon: Icons.settings,
                          title: "Cài đặt",
                        ),
                        Icon(
                          Icons.arrow_right_rounded,
                          size: AppDimention.size80,
                          color: AppColor.mainColor,
                        ),
                      ],
                    ),
                  ),
                ),
                GestureDetector(
                  onTap: (){
                    Get.toNamed(AppRoute.LOGIN_PAGE);
                  },
                  onTapDown: (_) {
                    setState(() {
                      _rowColor1 = Colors.grey.withOpacity(0.3); 
                    });
                  },
                  onTapUp: (_) {
                    setState(() {
                      _rowColor1 = Colors.transparent; 
                    });
                  },
                  child: Container(
                    decoration: BoxDecoration(
                        color: _rowColor1, 
                        borderRadius: BorderRadius.circular(AppDimention.size10),
                    ),
                    
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        SelectSettingCustom(
                          icon: Icons.login,
                          title: "Đăng xuất",
                        ),
                        Icon(
                          Icons.arrow_right_rounded,
                          size: AppDimention.size80,
                          color: AppColor.mainColor,
                        ),
                      ],
                    ),
                  ),
                ),
              
              ],
            ),
          )
      ],
    );
  }
}
