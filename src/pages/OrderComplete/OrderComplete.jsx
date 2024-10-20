import React from 'react';
import './OrderComplete.scss'

const OrderComplete = () => {
    return (
        <div className="order-complete-page">
            <div className="container">
                {/* <div className="breadcrumb">
                    <p>Giỏ hàng  &gt; </p>
                    <p>Thông tin đơn hàng </p>
                    <p>&gt; Đặt hàng thành công </p>
                </div> */}
                <div className="paragraph-complete">
                    <p>Cảm ơn bạn đã đặt hàng tại cửa hàng của chúng tôi. Đơn hàng sẽ sớm được giao</p>
                    <button>Quản lý đơn hàng</button>
                </div>

            </div>
        </div>
    )
}

export default OrderComplete;