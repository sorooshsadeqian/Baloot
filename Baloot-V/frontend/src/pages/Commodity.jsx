import React, {useEffect, useState} from 'react';
import { useParams } from 'react-router';
import axios from "axios";

 const Commodity = ()=> {
     const { id } = useParams();
     const [commodityData, setCommodityData] = useState([])


     useEffect(() => {
         axios.get('http://localhost:8080/commodity/' + id)
             .then(function (response) {
                 setCommodityData(response.data)
                 console.log(response);
             })
             .catch(function (error) {
                 console.log(error);
             })

     }, [])

     return <>
         <div className="commodity">
             {
                 CommodityInfo(commodityData)
             }
             {
                 Comments(commodityData)
             }
             {
                 SuggestedCommodities(commodityData)
             }
         </div>
    </>
 }

 const CommodityInfo = (data)=> {
     return <div className="commodity__info">
             <img className="commodity__image" src={data.image} alt=""/>
             <div className="commodity__details">
                 <h2 className="commodity__name">{data.name}</h2>
                 <div className="commodity__subdetails">
                     <p className="commodity__count">{data.inStock} left in stock</p>
                     <div className="commodity__rating">
                         <img className="commodity__rating-star" src={require("../assets/images/star.png")} alt=""/>
                         <p className="commodity__rating-number">{data.rating}</p>
                         <p className="commodity__rating-count">(12)</p>
                     </div>
                 </div>
                 <div>
                     <p>by <a href="">Huwaei</a></p>
                 </div>
                 <div className="commodity__categories">
                     <p className="commodity__categories-title">Category(s) </p>
                     <ul>
                         {data.categories?.map(cat => <li className="commodity__category">{cat}</li>)}
                     </ul>
                 </div>
                 <div className="commodity__actions">
                     <p className="commodity__price">
                         {data.price}$
                     </p>
                     <button className="button commodity__action">
                         add to cart
                     </button>
                 </div>
                 <div className="commodity__rate">
                     <div className="commodity__rate-stars">
                         <p className="commodity__rate-title">rate now</p>
                         <div className="commodity__stars">
                             <img className="commodity__stars-image" src={require("../assets/images/stars.png")} alt=""/>
                             <img className="commodity__stars-image" src={require("../assets/images/stars.png")} alt=""/>
                         </div>
                     </div>
                     <button className="button commodity__rate-button">
                         submit
                     </button>
                 </div>
             </div>
         </div>
 }

 const Comments = (data)=> {
     return <div className="comments">
         <div className="comments__title">
             <p className="comments__title-text">Comments</p>
             <p className="comments__title-count">({data.comments?.length})</p>
         </div>
         {
             data.comments?.map(comment => <div className="comments__row">
                 <p className="comments__text">{comment.text}</p>
                 <div className="comments__details">
                     <p className="comments__details-text">{comment.date}</p>
                     <p className="comments__details-text">.</p>
                     <p className="comments__details-text">#{comment.username}</p>
                 </div>
                 <div className="comments__likes">
                     <p className="comments__likes-text">Is this comment helpful?</p>
                     <p className="comments__likes-count">{comment.likes}</p>
                     <img className="comments-likes-image" src={require("../assets/images/like.png")}/>
                     <p className="comments__likes-count">{comment.dislikes}</p>
                     <img className="comments-likes-image" src={require("../assets/images/dislike.png")}/>
                 </div>
             </div>)
         }
         <div className="comments__add">
             <p className="comments__add-title">Submit your opinion</p>
             <div className="comments__add-submit">
                 <textarea className="comments__add-input" type="text"></textarea>
                 <button className="comments__add-action button">Post</button>
             </div>
         </div>
     </div>
 }


 const SuggestedCommodities = (data)=> {
     return <> <h3 className="suggestions__title">You also might like...</h3>
         <div className="cards">
             { data.suggestedCommodities?.map( c =>
                 <div className="card">
                     <h2 className="card__title">{c.name}</h2>
                     <p className="card__count">{c.inStock} left in stock</p>
                     <img src={c.image} alt="" className="card__image"/>
                     <div className="card__actions">
                         <p className="card__price">
                             {c.price}$
                         </p>
                         <button className="button card__action">
                             add to cart
                         </button>
                     </div>
                 </div>
             )}
         </div>
         </>
 }
export default Commodity;



