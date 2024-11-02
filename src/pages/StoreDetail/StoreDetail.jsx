import React, { useState, useEffect, useContext } from 'react'
import './StoreDetail.scss'
import test_product from "../../assets/food-yummy/product1.jpg";
import { assets } from '../../assets/assets'

import DownloadImage from "../../assets/shop/ggmap.jpg";
import FoodDisplay from '../../components/FoodDisplay/FoodDisplay';
import store7 from "../../assets/image_gg/introduce_7.png";
import store8 from "../../assets/image_gg/introduce_8.png";
import store9 from "../../assets/image_gg/introduce_9.png";
import store10 from "../../assets/image_gg/introduce_10.png";

import { useParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { fetchStoreById } from "../../redux/actions/storeActions";
import { fetchProductsByIdStore } from '../../redux/actions/productActions';
import ChatContext from '../../context/showChat';
import { GetAllStoresChat, PostSaveMess, searchOwnerForStore } from '../../services/chat';
import { fetchUpdate } from '../../redux/actions/chatStoreAction';


const StoreDetail = () => {
  const {setShowChat,setSelectedUser } = useContext(ChatContext);
  const { id } = useParams();
  const dispatch = useDispatch();
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
  const [owner, setOwner] = useState(null);
  const u = useSelector((state) => state.auth.account)
  const idU = u.id;
  const stores = useSelector((state) => state.stores.stores); // Lấy dữ liệu từ Redux store

  const storeDetail = useSelector((state) => {
    return state.store.storeDetail;
  })
  const listProductsByIdStore = useSelector((state) => {
    return state.product.listProductsByIdStore;
  })

  const handleClickChatStore = async() => {
    const on = await SearchOwner();
    console.log("Onn: ",on)
    const index = stores.findIndex(u =>u.id === on.id);
    if(index === -1){
      saveMessage(on.id);
      setTimeout(() => {
        loadStoreAgain();
      },1000);
      
    }
    
    if(on!=null){
      setSelectedUser(on);
      setShowChat(true);
    }
    
  }

  const loadStoreAgain = async () => {
    try {
        console.log("Loading stores again...");

        const previousOnlineUsers = stores
            .filter(user => user.online === true)
            .map(user => user.id);

        const res = await GetAllStoresChat();

        if (res.data.EC === 0 && res.data.DT) {
            const updatedStores = res.data.DT.map(store => ({
                ...store,
                online: previousOnlineUsers.includes(store.id) // Dựa trên danh sách online trước đó
            }));
            dispatch(fetchUpdate({ DT: updatedStores }));
        } else {
            console.error("Lỗi khi tải lại stores:", res);
        }
    } catch (error) {
        console.error("Lỗi khi thực hiện loadStoreAgain:", error);
    }
};

  const saveMessage = async(idOwner) =>{
    const sender = idOwner;
    const receiver = Number(idU);
    const isRead = false;
    const mess = `Xin chào mừng bạn đến với cửa hàng ${storeDetail.storeName},cửa hàng chúng tôi tự hào bán những sản phẩm chất lượng và an toàn. Bạn cần sự giúp đỡ gì từ chúng tôi đây?`;
    try {
      let res = await PostSaveMess(sender, receiver, isRead, mess);
      console.log(res);
  } catch (exception) {
      console.error('Error sending image:', exception);
  }
  }

  const SearchOwner = async () => {
    const res = await searchOwnerForStore(id);
    console.log("owner: ",res)
    if (res.data.EC === 0) {
      return res.data.DT;
    }
    return null;
  }

  useEffect(() => {
    window.scrollTo(0, 0);
    dispatch(fetchStoreById(id));
    dispatch(fetchProductsByIdStore(id));
  }, [id]);

  if (!storeDetail) {
    return <div>Không có thông tin cửa hàng.</div>;
  }
  else return (
    <div className="page-store-detail">
      <div className="container">
        <div className="store-detail-infor">
          <div className="infor-left">
            <div className="infor-left-img-container">
              <img src={'data:image/png;base64,' + storeDetail.image} alt="" />
            </div>
            <div className="infor-left-infor-container">
              <div className="infor-left-name">{storeDetail.storeName}</div>
              <div className="infor-left-contact-container">
                <span className="contact-location">
                  <i class="fa-solid fa-compass"></i>
                  {storeDetail.location}
                </span>
                <span className="contact-phone">
                  <i class="fa-solid fa-phone"></i>
                  {storeDetail.numberPhone}
                </span>
                <span className="contact-time">
                  <i class="fa-solid fa-clock"></i>
                  {storeDetail.openingTime} - {storeDetail.closingTime}
                </span>
                {
                  isAuthenticated === true
                  &&
                  <button onClick={()=>handleClickChatStore()} className='chat-store'>Chat với của hàng</button>
                }
              </div>
            </div>
          </div>
          <div className="infor-right">
            <div className="infor-right-ggmap-container">
              {/* <img src={DownloadImage} alt="" /> */}
              <iframe
                src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3833.842405348711!2d108.14729407579229!3d16.073665739317885!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x314218d68dff9545%3A0x714561e9f3a7292c!2zVHLGsOG7nW5nIMSQ4bqhaSBo4buNYyBCw6FjaCBLaG9hIC0gxJDhuqFpIGjhu41jIMSQw6AgTuG6tW5n!5e0!3m2!1svi!2s!4v1730165637898!5m2!1svi!2s"
                // width="600"
                // height="450"
                style={{ border: 0, width: '100%', height: '80vh' }}
                allowFullScreen
                loading="lazy"
                referrerPolicy="no-referrer-when-downgrade"
              ></iframe>
              {/* 
                  2d: longitude => Kinh độ, 
                  3d: latitude => Vĩ độ
              */}
            </div>
          </div>
        </div>
        <div className="list-products">
          <FoodDisplay listProducts={listProductsByIdStore} />
        </div>
      </div>
    </div>
  )
}
export default StoreDetail
