import React, { useState, useEffect } from "react";
import "./Cart.scss";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from 'react-redux';
import {
  fetchProductsInCart,
  placeOrderUsingAddToCart,
  removeProductInCart,
  increaseOneQuantity,
  decreaseOneQuantity,

} from "../../redux/actions/userActions";
import { toast } from "react-toastify";
import { Link } from "react-router-dom";
const Cart = () => {

  const dispatch = useDispatch();
  const navigate = useNavigate();

  const listProductsInCart = useSelector((state) => {
    return state.user.listProductsInCart;
  })

  const listCombosInCart = useSelector((state) => {
    return state.user.listCombosInCart;
  })
  useEffect(() => {
    dispatch(fetchProductsInCart());
  }, [dispatch]);
  // Tăng/giảm slsp
  const handleIncreaseQuantity = (item) => {
    // note: BE thêm stockQuantity ở mỗi sản phẩm trong giỏ hàng khi trả về data
    // if (item.product.quantity < item.product.stockQuantity) {
    //   increaseOneQuantity(item.cartId);
    // }
    // else {
    //   toast.error('Sản phẩm vượt quá số lượng!')
    // }
    dispatch(increaseOneQuantity(item.cartId));
  };
  const handleDecreaseQuantity = (item) => {
    if ((item.product && item.product.quantity > 1) || (item.combo && item.combo.quantity > 1)) {
      dispatch(decreaseOneQuantity(item.cartId));
    }
  };
  // Xóa sản phẩm khỏi giỏ hàng
  const handleRemoveProductInCart = (cartId) => {
    dispatch(removeProductInCart(cartId));
  };
  const getTotalPriceInCart = () => {
    let total = 0;
    for (let i = 0; i < listProductsInCart?.length; i++) {
      total += (listProductsInCart[i].product.unitPrice * listProductsInCart[i].product.quantity);
    }
    for (let i = 0; i < listCombosInCart?.length; i++) {
      total += (listCombosInCart[i].combo.unitPrice * listCombosInCart[i].combo.quantity);
    }
    return total;
  }
  const handlePlaceOrder = () => {
    // console.log('listProductsInCart: ', listProductsInCart);
    // console.log('listCombosInCart: ', listCombosInCart);
    if (!listProductsInCart && !Array.isArray(listProductsInCart) && listProductsInCart.length === 0 && !listCombosInCart && !Array.isArray(listCombosInCart) && listCombosInCart.length === 0) { // -> Xử lý thêm trường hợp listProducts ko phải là Array
      toast.error('Không có sản phẩm trong giỏ hàng!');
    }
    else {
      dispatch(placeOrderUsingAddToCart());
      navigate('/checkout');
    }
  }

  // select -> filter
  const [selectedStore, setSelectedStore] = useState("all");
  const [searchTerm, setSearchTerm] = useState(""); // Thêm state lưu từ khóa tìm kiếm
  const handleStoreChange = (event) => {
    // console.log('storeId đang chọn: ', selectedStore);
    setSelectedStore(event.target.value);
    // console.log('storeId đang chọn: ', selectedStore);
  };

  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value); // Cập nhật từ khóa tìm kiếm khi nhập
  };

  const filteredProducts = Array.isArray(listProductsInCart) && Array.isArray(listCombosInCart)
    ? [
      ...listProductsInCart,
      ...listCombosInCart
    ].filter((item) => {
      const storeId = item.product ? item.product.storeId : item.combo.storeId;
      const name = item.product ? item.product.productName : item.combo.comboName;
      const isStoreMatch = selectedStore === "all" || +storeId === +selectedStore;
      const isSearchMatch = name.toLowerCase().includes(searchTerm.toLowerCase());
      return isStoreMatch && isSearchMatch;
    }) : [];

  // const filteredProducts = Array.isArray(listProductsInCart) ? listProductsInCart.filter((item) => {
  //   console.log('item.product.storeId: ', item.product.storeId);
  //   return selectedStore === "all" || +item.product.storeId === +selectedStore;
  // }) : [];
  return (
    <div className="page-cart">
      <div className="search-filter-container">
        <div className="search-container">
          <div className="row">
            <div className="col-md-12">
              <div className="input-group">
                <input
                  className="form-control border-end-0 border"
                  type="search"
                  value={searchTerm} // Hiển thị từ khóa trong ô input
                  onChange={handleSearchChange} // Xử lý khi người dùng nhập
                  placeholder="Tìm kiếm sản phẩm"
                />

                <span className="input-group-append">
                  <button className="btn btn-outline-secondary bg-white border ms-n5" type="button">
                    <i className="fa fa-search"></i>
                  </button>
                </span>
              </div>
            </div>
          </div>
        </div>
        <div className="filter-container">
          <select
            className="form-select"
            value={selectedStore}
            onChange={handleStoreChange}
            aria-label="Default select example"
          >
            <option value="all">Tất cả cửa hàng</option>
            {
              Array.isArray(listProductsInCart) && Array.isArray(listCombosInCart) &&
              listProductsInCart.concat(listCombosInCart)
                .map((item) => (item.product ? item.product.dataStore : item.combo.dataStore))
                .filter((value, index, self) => value && self.findIndex(v => v.storeId === value.storeId) === index)
                .map((store) => (
                  <option key={store.storeId} value={store.storeId}>
                    {store.storeName}
                  </option>
                ))
            }

          </select>
        </div>
      </div>
      <div className="cart-items">
        <div className="cart-items-title">
          <p>Sản phẩm</p>
          <p>Tên</p>
          <p>Kích cỡ</p>
          <p>Cửa hàng</p>
          <p>Giá</p>
          <p>Số lượng</p>
          <p>Tổng tiền</p>
          <p>Thao tác</p>
        </div>
        <br />
        <hr />
        {
          filteredProducts && filteredProducts.length > 0 ? (filteredProducts.map((item, index) => {
            const isProduct = item.product !== null;
            const data = isProduct ? item.product : item.combo;
            const itemName = isProduct ? data.productName : data.comboName;
            const itemImage = data.image; // Giả sử combo cũng có hình ảnh comboImage
            const itemSize = data.size; // Combo không có size
            const itemStoreName = data.dataStore ? data.dataStore.storeName : "N/A";
            const itemUnitPrice = data.unitPrice;
            const itemQuantity = data.quantity;
            const itemTotalPrice = itemUnitPrice * itemQuantity;
            return (
              <div key={index}>
                <div className="cart-items-title cart-items-item">
                  <Link to={isProduct ? `/product-detail/${data.productId}` : `/combo-detail/${data.comboId}`}>
                    <img src={`data:image/png;base64,${itemImage}`} alt="" />
                  </Link>
                  <p>{itemName}</p>
                  <p>{itemSize}</p>
                  <p>{itemStoreName}</p>
                  <p>{Number(itemUnitPrice).toLocaleString('vi-VN')} đ</p>
                  <div className="quantity-controls">
                    <button onClick={() => handleDecreaseQuantity(item)}> <i className="fa-solid fa-minus"></i></button>
                    <p>{itemQuantity}</p>
                    <button onClick={() => handleIncreaseQuantity(item)}><i className="fa-solid fa-plus"></i></button>
                  </div>
                  <p>{Number(itemTotalPrice).toLocaleString('vi-VN')} đ</p>
                  <p onClick={() => handleRemoveProductInCart(item.cartId)}>
                    <i className="fa-solid fa-trash action-delete"></i>
                  </p>
                </div>
                <hr />
              </div>
            );
          })
          ) : (
            <div className="no-product">
              <span>Không có sản phẩm trong giỏ hàng</span>
            </div>
          )}
        {
          filteredProducts && filteredProducts.length > 0 && (
            <div>
              <div className="cart-items-title cart-items-item">
                <span></span>
                <p></p>
                <p></p>
                <p></p>
                <p></p>
                <p>Tổng tiền giỏ hàng: </p>
                <p>
                  {Number(getTotalPriceInCart()).toLocaleString('vi-VN')} đ
                </p>
                <button className="btn-redirect-checkout"
                  onClick={handlePlaceOrder}
                >
                  <i className="fa-solid fa-file-invoice-dollar"></i>
                  Thanh toán
                </button>
              </div>
            </div>
          )
        }
      </div>

    </div>
  );
};

export default Cart;
