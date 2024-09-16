import React from 'react'
import './Header.css'
import { Link } from "react-router-dom";

const Header = () => {
  return (
    <div className='header'>
      <div className="header-contents">
        <h2>Đặt hàng ngay</h2>
        <p>Đặt đồ ăn chưa bao giờ đơn giản đến vậy! Chỉ cần vài bước nhanh chóng, bạn đã có thể chọn món yêu thích và nhận hàng ngay tại cửa. Thoải mái và tiện lợi, chúng tôi luôn sẵn sàng phục vụ bạn.</p>
        <Link to="/" className="btn order_now btn_red">
          Xem thêm
        </Link>
      </div>

    </div>
  )
}

export default Header
