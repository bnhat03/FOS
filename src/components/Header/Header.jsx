import React from 'react'
import './Header.scss'
import { NavLink } from "react-router-dom";
import { useDispatch, useSelector } from 'react-redux';

const Header = () => {
  // fetch category
  const listCategories = useSelector((state) => {
    return state.category.listCategories;
  })
  return (
    <div className='header'>
      <div className="header-contents">
        <h2>Đặt hàng ngay</h2>
        <p>Đặt đồ ăn chưa bao giờ đơn giản đến vậy! Chỉ cần vài bước nhanh chóng, bạn đã có thể chọn món yêu thích và nhận hàng ngay tại cửa. Thoải mái và tiện lợi, chúng tôi luôn sẵn sàng phục vụ bạn.</p>
        <NavLink
          to={listCategories.length > 0 ? `/category/${listCategories[0].categoryId}` : "/category"}
          className={({ isActive }) => (isActive ? "active btn order_now btn_red" : "btn order_now btn_red")}
        >
          Xem thêm
        </NavLink>
      </div>
    </div>
  )
}

export default Header
