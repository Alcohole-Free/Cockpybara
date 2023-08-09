// CocktailListSection.js
import React, { useState, useEffect } from 'react';
import './CocktailListSection.css';

const CocktailListSection = () => {
  const [cocktailList, setCocktailList] = useState([]);
  const [sortBy, setSortBy] = useState('recommended');
  const [selectedButton, setSelectedButton] = useState('recommended');

  useEffect(() => {
    // 더미 데이터를 사용하여 프론트에서 확인할 수 있도록 함
    const dummyData = [
      { id: 1, name: 'Mojito', description: 'Refreshing cocktail with mint and lime', taste: 'Sweet', recommended: true },
      { id: 2, name: 'Cosmopolitan', description: 'Classic cocktail with vodka and cranberry juice', taste: 'Sour', recommended: true },
      { id: 3, name: 'Martini', description: 'Sophisticated cocktail with gin and vermouth', taste: 'Dry', recommended: false },
      // 더 많은 칵테일들...
    ];
    setCocktailList(dummyData);
  }, []);

  // 추천 상태 변경 함수
  const toggleRecommendation = (id) => {
    setCocktailList((prevCocktails) =>
      prevCocktails.map((cocktail) =>
        cocktail.id === id ? { ...cocktail, recommended: !cocktail.recommended } : cocktail
      )
    );
  };

  const handleSortByClick = (sortType) => {
    setSortBy(sortType);
    setSelectedButton(sortType);
  };

  const filterCocktails = () => {
    let filteredCocktails = cocktailList;

    // 필터링 로직을 적용합니다.
    if (sortBy === 'popular') {
      filteredCocktails = filteredCocktails.filter(cocktail => cocktail.recommended);
    } else if (sortBy === 'latest') {
      // 최신순 필터링 로직 추가
      filteredCocktails = filteredCocktails.sort((a, b) => b.id - a.id); // 가장 높은 ID 값부터 정렬
    } else if (sortBy === 'alphabetical') {
      // 가나다순 필터링 로직 추가
      filteredCocktails = filteredCocktails.sort((a, b) => a.name.localeCompare(b.name)); // 이름순으로 정렬
    }

    // 최대 3개만 표시
    return filteredCocktails.slice(0, 3);
  };

  // API 호출로 데이터를 가져오는 부분을 주석처리해둡니다.
  // fetchCocktailData(); // 가정: API를 호출하여 데이터를 가져오는 함수

  // const fetchCocktailData = async () => {
  //   try {
  //     const response = await fetch('your-api-url-here'); // API의 실제 URL을 입력하세요
  //     if (!response.ok) {
  //       throw new Error('Network response was not ok');
  //     }
  //     const data = await response.json();
  //     setCocktailList(data); // 가져온 데이터를 상태로 업데이트
  //   } catch (error) {
  //     console.error('Error fetching cocktail data:', error);
  //   }
  // };


  return (
    <div className="cockList-box">
      <div className="selectList-box">
        <div className="selectList-contents">
          <button
            className={selectedButton === 'popular' ? 'selected' : ''}
            onClick={() => handleSortByClick('popular')}
          >
            인기순
          </button>
          <button
            className={selectedButton === 'latest' ? 'selected' : ''}
            onClick={() => handleSortByClick('latest')}
          >
            최신순
          </button>
          <button
            className={selectedButton === 'alphabetical' ? 'selected' : ''}
            onClick={() => handleSortByClick('alphabetical')}
          >
            가나다순
          </button>
        </div>
      </div>
      <div className="cockList-contents">
        <h2>칵테일 나열</h2>
        <ul>
          {filterCocktails().map((cocktail) => (
            <li key={cocktail.id}>
              <h3>{cocktail.name}</h3>
              <p>{cocktail.description}</p>
              <p>Taste: {cocktail.taste}</p>
              <p>Recommended: {cocktail.recommended ? 'Yes' : 'No'}</p>
              {/* 추천 유무에 따른 버튼 표시 */}
              <button onClick={() => toggleRecommendation(cocktail.id)}>
                {cocktail.recommended ? '추천 취소' : '추천하기'}
              </button>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default CocktailListSection;
