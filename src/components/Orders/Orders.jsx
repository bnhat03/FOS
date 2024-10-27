import React, { useState, useEffect } from "react";
import './Orders.scss';
import OrderDetailModal from '../../components/OrderDetailModal/OrderDetailModal'; // Import Modal
import { useDispatch, useSelector } from 'react-redux';
import { fetchAllOrders, cancelOrder } from "../../redux/actions/userActions";
import { toast } from "react-toastify";
import ReviewModal from "../ReviewModal/ReviewModal";
// const _ = require('lodash'); // copy object
const Orders = () => {
  const dispatch = useDispatch();
  const orders = useSelector((state) => {
    return state.user.allOrders;
  })
  const [showModalViewDetail, setShowModalViewDetail] = useState(false);
  const [showModalReviewOrder, setShowModalReviewOrder] = useState(false);
  const [orderDetails, setOrderDetails] = useState(null);
  const [statusOrderInteger, setStatusOrderInteger] = useState(0);

  const handleShowDetails = (order) => {
    // let orderChangeStatus = _.cloneDeep(order);
    switch (order.status) {
      case 'Đơn hàng mới':
        setStatusOrderInteger(1);
        break;
      case 'Đơn hàng đã bị hủy':
        setStatusOrderInteger(2);
        break;
      case 'Đơn hàng đã được xác nhận':
        setStatusOrderInteger(3);
        break;
      case 'Đơn hàng đang giao':
        setStatusOrderInteger(4);
        break;
      case 'Đơn hàng đã hoàn thành':
        setStatusOrderInteger(5);
        break;
      default:
        setStatusOrderInteger(1);
    }
    setOrderDetails(order);
    setShowModalViewDetail(true);
  };
  const handleShowReviewOrder = (order) => {
    setOrderDetails(order);
    setShowModalReviewOrder(true);
  };

  const handleCloseViewDetail = () => {
    setShowModalViewDetail(false);
    // setOrderDetails(null); // Reset thông tin đơn hàng
  };
  const handleCloseReviewOrder = () => {
    setShowModalReviewOrder(false);
  };

  // Format Date
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const daysOfWeek = ['Chủ nhật', 'Thứ 2', 'Thứ 3', 'Thứ 4', 'Thứ 5', 'Thứ 6', 'Thứ 7'];
    const dayOfWeek = daysOfWeek[date.getDay()];
    const day = date.getDate();  // Lấy ngày
    const month = date.getMonth() + 1;  // Lấy tháng (tháng trong JS bắt đầu từ 0)
    const year = date.getFullYear();  // Lấy năm
    const hours = date.getHours().toString().padStart(2, '0');  // Lấy giờ (padStart để đảm bảo đủ 2 chữ số)
    const minutes = date.getMinutes().toString().padStart(2, '0');  // Lấy phút
    return `${day}/${month}/${year} lúc ${hours}:${minutes}`;
  };
  const handleCancelOrder = (orderCode) => {
    dispatch(cancelOrder(orderCode));
  }
  useEffect(() => {
    dispatch(fetchAllOrders());
  }, [dispatch]);

  return (
    <div className="orders-management">
      <div className="container">
        <div className="orders-management-icon">
          <i class="icon-i fa-solid fa-box-open"></i>
          <span className='icon-span'>các đơn hàng của bạn</span>
        </div>
        <table className="table table-striped table-orders table-bordered">
          <thead>
            <tr>
              <th scope="col">Mã đơn hàng</th>
              <th scope="col">Ngày đặt</th>
              <th scope="col">Tổng tiền</th>
              <th scope="col">Trạng thái</th>
              <th scope="col">Xem chi tiết đơn hàng</th>
              <th scope="col">Hành động</th>
            </tr>
          </thead>
          <tbody>
            {
              orders && orders.length > 0 ? (orders.map((order, index) => (
                <tr key={index}>
                  <td scope="row" className="text-center align-middle">{order.orderCode}</td>
                  <td className="text-center align-middle">{formatDate(order.orderDate)}</td>
                  <td className="text-center align-middle">{Number(order.totalAmount).toLocaleString('vi-VN')} đ</td>
                  <td className="text-center align-middle">{order.status}</td>
                  <td className="text-center align-middle">
                    <button
                      className="btn btn-primary"
                      onClick={() => handleShowDetails(order)}
                    >
                      Xem chi tiết
                    </button>
                  </td>

                  {/* status = 5 -> Hiện button 'Đánh giá' */}
                  {/* status = 1 -> Hiện button 'Hủy đơn hàng */}
                  {/* status = 2,3,4 -> Disable button 'Hủy đơn hàng' */}
                  {
                    order && order.status == 'Đơn hàng đã hoàn thành' ? (
                      <td className="text-center align-middle">
                        <button
                          className="btn btn-success"
                          onClick={() => handleShowReviewOrder(order)}
                        >
                          Đánh giá
                        </button>
                      </td>
                    )
                      : (
                        <td className="text-center align-middle">
                          <button
                            className="btn btn-warning"
                            disabled={order.status === 'Đơn hàng mới' ? false : true}
                            onClick={() => handleCancelOrder(order.orderCode)}
                          >
                            Hủy đơn hàng
                          </button>
                        </td>
                      )
                  }

                </tr>
              )))
                : (
                  <tr >
                    <td colSpan={6}>Không có đơn hàng nào</td>
                  </tr>
                )
            }
          </tbody>
        </table>

        <button className="btn btn-dark btn-return-homepage">TRỞ LẠI TRANG CHỦ</button>
        <OrderDetailModal
          showModal={showModalViewDetail}
          handleClose={handleCloseViewDetail}
          orderDetails={orderDetails}
          statusOrderInteger={statusOrderInteger}
        />
        <ReviewModal
          showModal={showModalReviewOrder}
          handleClose={handleCloseReviewOrder}
          orderDetails={orderDetails}
        />
      </div>
    </div>
  );
};

export default Orders;