import types from "../types";
import {
    updateProfileService,
    addProductToCartService,
    fetchProductsInCartService,
    placeOrderService,
    removeProductInCartService,
    increaseOneQuantityService,

} from "../../services/userService";
import { toast } from "react-toastify";

// Register New User
const updateProfileSuccess = () => {
    return {
        type: types.UPDATE_PROFILE_SUCCESS,
    };
};

const updateProfileError = (errorMessage) => {
    return {
        type: types.UPDATE_PROFILE_ERROR,
    };
};

const updateProfile = (fullName, avatar, email, address) => {
    return async (dispatch) => {
        try {
            const res = await updateProfileService(fullName, avatar, email, address);
            const isSuccess = res && res.data ? res.data.success : false;
            if (isSuccess) {
                dispatch(updateProfileSuccess());
                toast.success(res.data.message);
            } else {
                // Handle case where registration was unsuccessful but no error was thrown
                dispatch(updateProfileError());
                toast.error(res.data.message || "Update profile failed.");
            }
        } catch (error) {
            console.log(error);
            const errorMessage = error.response && error.response.data ? error.response.data.message : "An error occurred.";
            dispatch(updateProfileError());
            toast.error(errorMessage);
        }
    };
};

// Add product to cart
const addToCartSuccess = () => {
    return {
        type: types.ADD_TO_CART_SUCCESS,
    };
};
const addToCartError = () => {
    return {
        type: types.ADD_TO_CART_ERROR,
    };
};


const addToCart = (productId, quantity, storeId, size, status) => {
    return async (dispatch) => {
        try {
            const res = await addProductToCartService(productId, quantity, storeId, size, status);
            const isSuccess = res && res.data ? res.data.success : false;
            if (isSuccess) {
                dispatch(addToCartSuccess());
                dispatch(fetchProductsInCart());
                toast.success(res.data.message);
            } else {
                dispatch(addToCartError());
                toast.error(res.data.message || "Thêm vào giỏ hàng không thành công!");
            }
        } catch (error) {
            console.log(error);
            const errorMessage = error.response && error.response.data ? error.response.data.message : "An error occurred.";
            dispatch(addToCartError());
            toast.error(errorMessage);
        }
    };
}
// fetch list products in cart
const fetchProductsInCartSuccess = (data) => {
    return {
        type: types.FETCH_PRODUCT_CART_SUCCESS,
        dataProducts: data
    };
};
const fetchProductsInCart = () => {
    return async (dispatch, getState) => {
        try {
            const res = await fetchProductsInCartService();
            const data = res && res.data && res.data.data ? res.data.data : [];
            // Kiểm tra nếu giỏ hàng rỗng
            if (data.length === 0) {
                console.log("Giỏ hàng trống");
                dispatch(fetchProductsInCartSuccess([]));  // Gửi array rỗng đến redux
            }
            else {
                dispatch(fetchProductsInCartSuccess(data));
                console.log('>>> data cart: ', data);
            }
        } catch (error) {
            console.log(error);
        }
    }
};

// Nhấn MUA NGAY trong Modal -> Chuyển đến Check out
const placeOrderUsingBuyNow = (product, quantity, store, size) => {
    return {
        type: types.BUY_NOW_OPTION,
        productDetail: { product, quantity, store, size }
    };
};
// Nhấn THANH TOÁN trong Cart -> Chuyển đến Check out
const placeOrderUsingAddToCart = () => {
    return {
        type: types.ADD_TO_CART_OPTION,
    };
};

// Nhấn ĐẶT HÀNG trong Check out -> Chuyển đến Order Complete
const placeOrderSuccess = () => {
    return {
        type: types.PLACE_ORDER_SUCCESS,
    };
};
const placeOrderError = () => {
    return {
        type: types.PLACE_ORDER_ERROR,
    };
};

const placeOrder = () => {
    return async (dispatch) => {
        try {
            dispatch(placeOrderSuccess());

            // const res = await placeOrderService();
            // const isSuccess = res && res.data ? res.data.success : false;
            // if (isSuccess) {
            //     dispatch(placeOrderSuccess());
            //     toast.success(res.data.message);
            // } else {
            //     dispatch(placeOrderError());
            //     toast.error(res.data.message || "Đặt hàng không thành công!");
            // }
        } catch (error) {
            console.log(error);
            dispatch(placeOrderError());
            toast.error('Có lỗi ở Server');
        }
    };
}
const resetAllUser = () => {
    return {
        type: types.RESET_ALL_USER,
    };
}
// Xóa sp khỏi giỏ hàng
const removeProductInCartSuccess = () => {
    return {
        type: types.REMOVE_PRODUCT_IN_CART_SUCCESS,
    };
};
const removeProductInCart = (cartId) => {
    return async (dispatch, getState) => {
        try {
            const res = await removeProductInCartService(cartId);
            const isSuccess = res && res.data ? res.data.success : false;
            if (isSuccess) {
                dispatch(removeProductInCartSuccess());
                dispatch(fetchProductsInCart());
                toast.success(res.data.message);
            } else {
                toast.error(res.data.message || "Xóa sản phẩm khỏi giỏ hàng không thành công!");
            }
        } catch (error) {
            console.log(error);
            toast.error("Có lỗi ở Server!");
        }
    }
};

// Nhấn + => Tăng 1
const increaseOneQuantitySuccess = () => {
    return {
        type: types.INCREASE_ONE_QUANTITY_SUCCESS,
    };
};
const increaseOneQuantity = (cartId) => {
    return async (dispatch, getState) => {
        try {
            const res = await increaseOneQuantityService(cartId);
            const isSuccess = res && res.data ? res.data.success : false;
            if (isSuccess) {
                dispatch(increaseOneQuantitySuccess());
                dispatch(fetchProductsInCart());
                toast.success(res.data.message);
            } else {
                toast.error(res.data.message);
            }
        } catch (error) {
            console.log(error);
            toast.error("Có lỗi ở Server!");
        }
    }
};


export {
    updateProfile,
    addToCart,
    fetchProductsInCart,
    placeOrderUsingBuyNow,
    placeOrderUsingAddToCart,
    placeOrder,
    resetAllUser,
    removeProductInCart,
    increaseOneQuantity,

};
