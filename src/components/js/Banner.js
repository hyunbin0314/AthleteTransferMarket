import React from 'react';
import '../css/Banner.css'; // Footer에 대한 CSS 파일을 임포트합니다.

const Banner = () => {
    return (
        <div className="Banner-container">
            <div className="Banner-top">
                <div className="Banner-Link">
                    <a href="/">회사 소개</a>
                    <a href="/">Investor Relations</a>
                    <a href="/">입점/제휴문의</a>
                    <a href="/">공지사항</a>
                    <a href="/">고객의 소리</a>
                    <a href="/">이용약관</a>
                    <a href="/">개인정보처리방침</a>
                </div>
            </div>
        </div>
    );
}

export default Banner;
