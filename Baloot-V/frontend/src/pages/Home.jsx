import React, {useEffect, useState} from 'react';
import axios from "axios";
import {Link} from "react-router-dom";

 const Home = ()=> {
     const [data, setData] = useState([])

     useEffect(() => {
         axios.get('http://localhost:8080/commodities')
             .then(function (response) {
                 setData(response.data.commoditiesList)
                 console.log(response);
             })
             .catch(function (error) {
                 console.log(error);
             })
             .finally(function () {
             });

     }, [])

     return <section className="content">
         <div className="filters">
             <div className="filters__left">
                 <p className="filters__title">Available Commodities</p>
                 <input id="available-commodities" type="checkbox" className="filters__controller"/>
                 <label className="filters__label" htmlFor="available-commodities">
                     <span className="filters__label-circle"/>
                 </label>
             </div>


             <div className="filters__right">
                 <p className="filters__sort">Sort by:</p>
                 <button className="button button-filter">name</button>
                 <button className="button button-filter button-filter--active">price</button>
             </div>
         </div>

         <div className="cards">
             {
                 data.map(el => (CommodityCard(el)))
             }
         </div>
     </section>

 }

const CommodityCard = (data)=> {
     return <div className="card">
         <h2 className="card__title">{data.name}</h2>
         <p className="card__count">{data.inStock} left in stock</p>
         <Link to={"/commodity/" + data.id}> <img src={data.image} alt="" className="card__image"/> </Link>
         <div className="card__actions">
             <p className="card__price">
                 {data.price}$
             </p>
             <button className="button card__action">
                 add to cart
             </button>
         </div>
     </div>
}
export default Home



