import React from 'react';
import '../css/Banner.css'; // Footer에 대한 CSS 파일을 임포트합니다.

const Banner = () => {
    return (
        <div className="Banner-container">
            <div className="Banner-top">
                <div className="Banner-Link">
                    <a href="/">News</a>
                    <a href="/">이적통계</a>
                    <a href="/">경매</a>
                    <a href="/">대회일정</a>
                    <a href="/">커뮤티니</a>
                    <a href="/">내 계약</a>
                    <a href="/">이용약관</a>
                </div>
            </div>
        </div>
    );
}

export default Banner;
