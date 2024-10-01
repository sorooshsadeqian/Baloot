import React, {useEffect, useState} from 'react';
import logo from "../assets/images/logo.png"
import arrow from "../assets/images/arrow.png"
import {Link, Outlet} from "react-router-dom";

const Layout = ()=> {

    return (<>
        <header className="header">
        <div className="logo">
            <Link to="/"> <img src={logo} alt="Baloot Logo" className="logo__image"/> </Link>
            <p className="logo__title">Baloot</p>
        </div>

        <div className="searchbar">
            <div className="dropdown">
                <p className="dropdown__label" role="button">
                    <span className="dropdown__label-text">name</span>
                    <img className="dropdown__label-arrow" src={arrow} alt=""/>
                </p>
                <ul className="dropdown__content">
                    <li className="dropdown__item">name</li>
                    <li className="dropdown__item">category</li>
                </ul>
            </div>
            <input className="searchbar__controller" type="text" placeholder="search your product ..."/>
            <img className="searchbar__icon" src={require("../assets/images/search.png")} alt=""/>
        </div>

        <nav className="header__actions">
            <button className="button">Register</button>
            <button className="button">Login</button>
        </nav>
    </header>
    <Outlet />
        </>
    )

}

export default Layout
