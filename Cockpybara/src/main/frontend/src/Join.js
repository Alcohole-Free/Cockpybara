import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Join.css'
import image1 from './photo/capybaraIcon.png';

const Join = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [alias, setAlias] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [gender, setGender] = useState('');
  const [birth, setBirth] = useState('');
  const [isJoinSuccess, setIsJoinSuccess] = useState(false);
  const [isNextClicked, setIsNextClicked] = useState(false);

  const [isIdAvailable, setIsIdAvailable] = useState(true); // 상태 변수 추가 (회원가입 아이디 중복확인)

  const handleNextClick = async () => {
    // 아이디 중복 확인을 위한 API 요청 코드
    try {
      const response = await fetch(`/check-id/${email}`, { // 이 부분은 백엔드의 실제 엔드포인트에 맞게 수정해야 합니다.
        method: 'GET',
      });

      if (response.ok) {
        const responseData = await response.json();
        setIsIdAvailable(responseData.isAvailable); // 백엔드에서 넘어온 데이터에 따라 아이디 중복 여부를 업데이트합니다.
        setIsNextClicked(true);
      } else {
        console.error('아이디 중복 확인 실패');
      }
    } catch (error) {
      console.error('API 요청 에러:', error);
    }
  };

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleConfirmPasswordChange = (e) => {
    setConfirmPassword(e.target.value);
  };

  const handleAliasChange = (e) => {
    setAlias(e.target.value);
  };

  const handlePhoneNumberChange = (e) => {
    setPhoneNumber(e.target.value);
  };

  const handleGenderChange = (e) => {
    setGender(e.target.value);
  };

  const handleBirthChange = (e) => {
    setBirth(e.target.value);
  };

  const handleSubmit = async (e) => {
    {/* 백엔드 API 요청 코드 */ }
    e.preventDefault();

    const formData = {
      email: email,
      password: password,
      alias: alias,
      phoneNumber: phoneNumber,
      gender: gender,
      birth: birth
    };

    try {
      const response = await fetch('/join', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
      });

      if (response.ok) {
        const responseData = await response.json();
        console.log('회원가입 성공:', responseData);
        setIsJoinSuccess(true);
      } else {
        console.error('회원가입 실패');
      }
    } catch (error) {
      console.error('API 요청 에러:', error);
    }
  }; {/* 백엔드 API 요청 코드 */ }


  const handleLoginButtonClick = () => {
    navigate('/login');
  };

  return (
    <div className='join-container'>
      <h1 className="join-title" onClick={() => navigate('/')}>cockpybara</h1>
      {isJoinSuccess ? (
        <div>
          <p>카피바라 님,</p>
          <p>가입을 축하합니다.</p>
          <div style={{ display: 'flex', justifyContent: 'flex-end' }}>
            <img src={image1} alt="Image 1" style={{ marginRight: '70px', marginTop: '-130px' }} />
          </div>
          <button className="join-button-login" onClick={handleLoginButtonClick}>로그인</button>
        </div>
      ) : (
        <form onSubmit={handleSubmit}>
          {!isNextClicked ? (
            <div>
              <button className="join-button" type="button" onClick={() => navigate('/login')}>
                &lt; BACK
              </button>
              <br />
              <input className="join-input-id" placeholder="아이디" type="email" value={email} onChange={handleEmailChange} />
              <br />
              <input className="join-input-pw" placeholder="비밀번호" type="password" value={password} onChange={handlePasswordChange} />
              <br />
              <input className="join-input-pw" placeholder="비밀번호 확인" type="password" value={confirmPassword} onChange={handleConfirmPasswordChange} />
              {confirmPassword !== '' && password !== confirmPassword && (
                <p style={{ color: 'red' }}>비밀번호와 비밀번호 확인이 일치하지 않습니다.</p>
              )}
              <br />

              <button className={`join-next-button ${password !== confirmPassword ? 'disabled' : ''}`} type="button" onClick={handleNextClick}>
                다음으로
              </button>
              {isIdAvailable === false && (
                <p style={{ color: 'red' }}>이미 사용 중인 아이디입니다.</p>
              )}

            </div>
          ) : (
            <div>
              <button className="join-button" type="button" onClick={() => navigate('/login')}>
                &lt; BACK
              </button>
              <br />
              <input className="join-input-alias" placeholder="별명" type="text" value={alias} onChange={handleAliasChange} />
              <br />
              <input className="join-input-phone" placeholder="전화번호" type="text" value={phoneNumber} onChange={handlePhoneNumberChange} />
              <br />
              <div className="join-select-container">
                <select className="join-select" value={gender} onChange={handleGenderChange}>
                  <option value="MALE">남성</option>
                  <option value="FEMALE">여성</option>
                </select>
              </div>
              <br />
              <input className="join-input-birth" type="date" value={birth} onChange={handleBirthChange} />
              <br />
              <button className="join-button-final" type="submit">
                가입하기
              </button>
            </div>
          )}
        </form>
      )}

    </div>
  );
};

export default Join;