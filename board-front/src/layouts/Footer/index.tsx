import React from "react";
import "./style.css";

export default function Footer() {
  const onInstaIconButtonClickHandler = () => {
    window.open("https://www.instagram.com");
  };
  const onNaverBlogIconButtonClickHandler = () => {
    window.open("https://www.instagram.com");
  };

  return (
    <div id="footer">
      <div className="footer-container">
        <div className="footer-top">
          <div className="footer-logo-box">
            <div className="icon-box">
              <div className="icon logo-light-icon"></div>
            </div>
            <div className="footer-logo-text">{"Hoons Board"}</div>
          </div>
          <div className="footer-link-box">
            <div className="footer-email-link">{"qowjdxo00@gmail.com"}</div>
            <div className="icon-button">
              <div
                className="icon insta-icon"
                onClick={onInstaIconButtonClickHandler}
              ></div>
            </div>
            <div className="icon-button">
              <div
                className="icon naver-blog-icon"
                onClick={onNaverBlogIconButtonClickHandler}
              ></div>
            </div>
          </div>
        </div>
        <div className="footer-bottom">
          <div className="footer-copyright">
            ('Copyright 2022 JungTae. All Rights Reserved')
          </div>
        </div>
      </div>
    </div>
  );
}
