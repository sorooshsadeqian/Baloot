import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import './assets/styles/reset.css'
import './assets/styles/button.css'
import './assets/styles/header.css'
import './assets/styles/searchbar.css'
import './assets/styles/content.css'
import './assets/styles/card.css'
import './assets/styles/dropdown.css'
import './assets/styles/comments.css'
import './assets/styles/user.css'
import './assets/styles/commodity.css'
import './assets/styles/suggestions.css'
import './assets/styles/footer.css'

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
