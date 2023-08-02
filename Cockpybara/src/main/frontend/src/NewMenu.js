import React from 'react';
import { Link } from 'react-router-dom';
import './NewMenu.css';

const NewMenu = () => { // 여기에 menuClassName 추가

    return (
        <div className="menu-container">
            <ul className="menuClassName"> {/* 여기에 menuClassName 사용 */}
                <li className='new_about'><Link to="/about">About</Link></li>
                <li className='new_recipe'><Link to="/recipe">Recipe</Link></li>
                <li className='new_community'><Link to="/community/{userId}">Community</Link></li>
                <li className='new_MyPage'><Link to="/user/{userId}/my-page">MyPage</Link></li>
            </ul>
        </div>
    );
};

export default NewMenu; 
