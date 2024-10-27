import React, { useState, useEffect } from "react";
import './Category.scss'
import styled from "styled-components";
import product1 from "../../assets/food-yummy/product1.jpg";
import product2 from "../../assets/food-yummy/product2.jpg";
import product3 from "../../assets/food-yummy/product3.jpg";
import product4 from "../../assets/food-yummy/product4.jpg";
import ProductItem from "../../components/ProductItem/ProductItem";
import { useParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { fetchProductsByIdCategory } from "../../redux/actions/productActions";

export default function Category() {
  const { id } = useParams();
  const dispatch = useDispatch();
  const listProducts = useSelector((state) => {
    return state.product.listProductsByIdCategory;
  })
  useEffect(() => {
    window.scrollTo(0, 0);
    dispatch(fetchProductsByIdCategory(id));
  }, [id]); // 'id' category thay đổi -> lấy lại list products


  return (
    <div
      className={`page-category ${listProducts && listProducts.length > 0 ? 'has-products' : ''}`}
    >
      <div className="category-list-products">
        {
          listProducts && listProducts.length > 0 ? (
            listProducts.map((product, index) => {
              return (
                <React.Fragment key={index}>
                  <ProductItem product={product} />
                  {
                    (index + 1) % 4 === 0 && (index + 1) !== listProducts.length && <hr className="hr-separate" />
                  }
                </React.Fragment>
              );
            })
          ) : (
            <div className="no-product">Không có sản phẩm</div>
          )
        }

      </div>
    </div>
  );
}