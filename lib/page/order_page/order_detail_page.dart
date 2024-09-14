import 'dart:convert';
import 'dart:typed_data';

import 'package:android_project/data/controller/Order_controller.dart';
import 'package:android_project/page/order_page/order_footer.dart';
import 'package:android_project/route/app_route.dart';
import 'package:android_project/theme/app_color.dart';
import 'package:android_project/theme/app_dimention.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
class OrderDetailPage extends StatefulWidget{
  final String orderCode;
   const OrderDetailPage({
       Key? key,
       required this.orderCode
   }): super(key:key);
   @override
   _OrderDetailPageState createState() => _OrderDetailPageState();
}
class _OrderDetailPageState extends State<OrderDetailPage>{
  @override
  void initState() {
    super.initState();
    Get.find<OrderController>().getorderbyOrdercode(widget.orderCode);
  }
   @override
   Widget build(BuildContext context) {
      return GetBuilder<OrderController>(builder: (orderController){
         
          return !orderController.isLoading ? Scaffold(
              resizeToAvoidBottomInset: false,
              body: Column(
                children: [
                  Container(
                    width: AppDimention.screenWidth,
                    height: AppDimention.size100,
                    padding: EdgeInsets.only(top: AppDimention.size40),
                    decoration: BoxDecoration(
                      color: AppColor.mainColor
                    ),
                    child: Center(
                      child: Text("Chi tiết đơn hàng",style: TextStyle(color: Colors.white,fontWeight: FontWeight.bold,fontSize: AppDimention.size25,)),
                    ),
                  ),
        
                  Expanded(
                    child: SingleChildScrollView(
                    child: Column(
                      children: [
                        
                       GridView.builder(
                          physics: NeverScrollableScrollPhysics(),
                          shrinkWrap: true, 
                          gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                            crossAxisCount: 2,  
                            childAspectRatio: 0.7,  
                          ),
                          itemCount: orderController.orderdetail?.orderDetails?.length,  
                          itemBuilder: (context, index) {
                            var productOrder = orderController.orderdetail?.orderDetails?[index].productDetail;
                            var comboOrder = orderController.orderdetail?.orderDetails?[index].comboDetail;
                            bool key = productOrder != null;

                            Uint8List decodeImage() {
                                if (productOrder != null ) {
                                  try {
                                    return base64Decode(productOrder.productImage!);
                                  } catch (e) {
                                    print("Error decoding product image: $e");
                                    return Uint8List(0);
                                  }
                                } 
                                else {
                                  try {
                                    return base64Decode(comboOrder?.combo?.image ?? '');

                                  } catch (e) {
                                    print("Error decoding combo image: $e");
                                    return Uint8List(0);
                                  }
                                }
                               
                              }
                            return GestureDetector(
                                onTap: (){
                                  key? Get.toNamed(AppRoute.get_product_detail(productOrder.productId!)) :   Get.toNamed(AppRoute.get_combo_detail(index));
                                },
                                child: Container(
                                  decoration: BoxDecoration(
                                    color: Colors.white,
                                    border: Border.all(width: 1, color: Color.fromRGBO(218, 218, 218, 0.494)),
                                  ),
                                  child: Column(
                                  children: [
                                    Container(
                                      width: 170, 
                                      height: 150,  
                                    
                                      decoration: BoxDecoration(
                                        image: DecorationImage(
                                          fit: BoxFit.cover,
                                         image: MemoryImage(
                                            decodeImage()
                                          ),

                                        ),
                                      ),
                                    ),
                                    Container(
                                      width: 170,
                    
                                      padding: EdgeInsets.only(left: AppDimention.size10),
                                      
                                      child: Column(
                                        crossAxisAlignment: CrossAxisAlignment.start,
                                        children: [
                                          SizedBox(height: AppDimention.size5,),
                                          Text("[ ${index + 1 } ] ${ key
                                              ? productOrder?.productName ?? "No name"
                                              : comboOrder?.combo?.comboName ?? "No name" }",style: TextStyle(fontWeight: FontWeight.bold,color: AppColor.mainColor),),
                                          Text("${key ? productOrder?.unitPrice : comboOrder?.unitPrice} vnđ",style: TextStyle(fontSize: 13),),
                                          Row(
                                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                            children: [
                                              Row(
                                                children: [
                                                    Wrap(children: List.generate(5, (index) => Icon(Icons.star,color: AppColor.mainColor,size: 8)),),
                                                    Text("(5)",style: TextStyle(fontSize: 12,color: AppColor.mainColor),),
                                                  ],
                                              ),
                                              Row(
                                                children: [
                                                    Text("1028",style: TextStyle(fontSize: 12),),
                                                    SizedBox(width: 5,),
                                                    Icon(Icons.chat_bubble_outline_rounded,size: 12,),
                                                    
                                                  ],
                                              ),
                                            ],
                                          ),
                                          SizedBox(height: AppDimention.size15),
                                          Row(
                                            children: [
                                              Icon(Icons.delivery_dining_sharp),
                                              Text("Miễn phí vận chuyển" ,style: TextStyle(fontSize: 10),overflow: TextOverflow.ellipsis,)
                                            ],
                                          )
                                          
                                          
                                        ],
                                      ),
                                    )
                                  ],
                                ),
                                )
                              );
                         }
                        ),
                        
                       ListView.builder(
                          physics: NeverScrollableScrollPhysics(),
                          shrinkWrap: true,
                          itemCount: orderController.orderdetail?.orderDetails?.length,
                          itemBuilder: (context , index){
                              var productOrder = orderController.orderdetail?.orderDetails?[index].productDetail;
                              var comboOrder = orderController.orderdetail?.orderDetails?[index].comboDetail;
                              bool key = productOrder != null;
                              return Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Container(
                                    padding: EdgeInsets.only(left: AppDimention.size20),
                                    child: Column(
                                      crossAxisAlignment: CrossAxisAlignment.start,
                                      children: [
                                        if(index != 0)
                                          SizedBox(height: AppDimention.size20,),
                                        Text("[ ${index + 1 } ] ${ key
                                              ? productOrder?.productName ?? "No name"
                                              : comboOrder?.combo?.comboName ?? "No name" }",style: TextStyle(fontSize: AppDimention.size20,fontWeight: FontWeight.bold),),
                                          SizedBox(height: AppDimention.size10,),
                                          Container(
                                            width: AppDimention.screenWidth  - AppDimention.size40,
                                            height: AppDimention.size40,
                                            padding:EdgeInsets.only(top:AppDimention.size10),
                                            decoration: BoxDecoration(
                                              border: Border(bottom: BorderSide(width: 1,color: Colors.black26))
                                            ),
                                            child: Text("Giá : ${key ? productOrder?.unitPrice : comboOrder?.unitPrice} vnđ"),
                                          ),
                                          Container(
                                            width: AppDimention.screenWidth  - AppDimention.size40,
                                            height: AppDimention.size40,
                                            padding:EdgeInsets.only(top:AppDimention.size10),
                                            decoration: BoxDecoration(
                                              border: Border(bottom: BorderSide(width: 1,color: Colors.black26))
                                            ),
                                            child: Text("Số lượng : ${key ? productOrder?.quantity : comboOrder?.quantity} "),
                                          ),
                                          Container(
                                            width: AppDimention.screenWidth  - AppDimention.size40,
                                            height: AppDimention.size40,
                                            padding:EdgeInsets.only(top:AppDimention.size10),
                                            decoration: BoxDecoration(
                                              border: Border(bottom: BorderSide(width: 1,color: Colors.black26))
                                            ),
                                            child: Text("Tổng giá : ${key ? productOrder?.totalPrice : comboOrder?.totalPrice} vnđ "),
                                          ),
                                          Container(
                                            width: AppDimention.screenWidth  - AppDimention.size40,
                                            height: AppDimention.size40,
                                            padding:EdgeInsets.only(top:AppDimention.size10),
                                            decoration: BoxDecoration(
                                              border: Border(bottom: BorderSide(width: 1,color: Colors.black26))
                                            ),
                                            child: Text("Size : ${key ? productOrder?.size : comboOrder?.size} "),
                                          ),
                                         
                                      ],
                                    )
                                  ),
                                
                                  
                                ],
                              );
                          }
                        ),
                        SizedBox(height: AppDimention.size30,),
                        Container(
                            padding:EdgeInsets.only(left:AppDimention.size20),
                            width: AppDimention.screenWidth,
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                    Text("Chi tiết",style: TextStyle(fontSize: AppDimention.size30,)),
                                    Container(
                                      width: AppDimention.screenWidth - AppDimention.size40,
                                      height: AppDimention.size150 *2,
                                      padding: EdgeInsets.all(AppDimention.size20),
                                      decoration: BoxDecoration(
                                        borderRadius: BorderRadius.circular(AppDimention.size10),
                                        border: Border.all(width: 1,color: Colors.black26)
                                      ),
                                      child: Column(
                                        crossAxisAlignment: CrossAxisAlignment.start,
                                        children: [
                                            Text("Mã đơn hàng : ${orderController.orderdetail?.orderCode}"),
                                            SizedBox(height: AppDimention.size10,),
                                            Text("Tổng giá đơn hàng : ${orderController.orderdetail?.totalAmount} vnđ"),
                                            SizedBox(height: AppDimention.size10,),
                                            Text("Ngày đặt đơn : ${orderController.orderdetail?.orderDate}"),
                                            SizedBox(height: AppDimention.size10,),
                                            Text("Địa chỉ giao hàng : ${orderController.orderdetail?.deliveryAddress}"),
                                            SizedBox(height: AppDimention.size10,),
                                            Text("Phương thức thanh toán : ${orderController.orderdetail?.paymentMethod}"),
                                            SizedBox(height: AppDimention.size10,),
                                            Text("Trạng thái đơn hàng : ${orderController.orderdetail?.status}"),
                                        ],
                                      ),
                                    )
                                ],
                            ),
                          )
                        
                        
                      ],
                    ),
                    
                  )
                  ),
                  OrderFooter()
                  
                ],
              ),
            ):CircularProgressIndicator();
      });
   }
}