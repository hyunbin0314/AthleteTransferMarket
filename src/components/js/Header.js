import React from 'react';
import '../css/Header.css'; // CSS 파일 import
import logo from '../../assets/Header/Logo.png' // 로고 이미지 import
import searchIcon from '../../assets/Header/Search.png'; // 검색 아이콘 이미지 import
import mypageIcon from '../../assets/Header/My.png'; // 마이페이지 아이콘 이미지 import
import detailedSearchIcon from '../../assets/Header/DetailSearch.png'; // 상세검색 아이콘 이미지 import

const Header = () => {
  return (
    <header className="Header-header">
      <div className="Header-container">
        <img src={logo} alt="My Website Logo" className="Header-logoImage" />
        <h1 className="Header-Link">.DW.co.kr</h1>
        <div className="Header-searchContainer">
          <input type="text" placeholder="검색어 입력 (예 : 선수, 감독...)" className="Header-searchInput" />
          <img src={searchIcon} alt="Search" className="Header-searchIcon" />
          <div className="Header-detailedSearchIconContainer">
            <img src={detailedSearchIcon} alt="Detailed Search" className="Header-detailedSearchIcon" />
          </div>
          <div className="Header-mypageIconContainer">
            <img src={mypageIcon} alt="My Page" className="Header-mypageIcon" />
            <span className="Header-tooltip">마이페이지</span>
          </div>
          <button className="Header-loginButton">로그인</button>
        </div>
      </div>
    </header>
  );
}

export default Header;
