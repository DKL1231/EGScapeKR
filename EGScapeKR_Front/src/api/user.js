import myaxios from "@/utils/axios-common.js";
import { useTokenStore } from "@/stores/auth";
import Cookies from "js-cookie";

// 로그인을 진행하고 토큰을 저장하는 함수
function login(username, password, success, fail) {
  const tokenStore = useTokenStore();
  myaxios
    .post(`/login`, {
      username: username,
      password: password,
    }, { withCredentials: true })
    .then(response => {
      // JWT 토큰을 응답 헤더에서 가져오기
      const token = response.headers['authorization'];
      if (token) {
        tokenStore.setTokens(token, Cookies.get('refreshToken'));
      }
      getUserName(null);
      // 성공 콜백 호출
      success(response);
    }
    )
    .catch(fail);
}

// 회원가입 시 사용하려는 아이디가 이미 가입된 아이디인지 확인하는 함수
function unameDupCheck(username, success, fail){
  myaxios
  .post(`/user/unamecheck`, {
    username: username
  })
  .then(success)
  .catch(fail);
}

// 회원가입 시 사용하려는 이메일이 이미 가입된 이메일인지 확인하는 함수
function emailDupCheck(email, success, fail){
  myaxios
  .post(`/user/emailcheck`, {
    email: email
  })
  .then(success)
  .catch(fail);
}

// 회원가입을 진행하는 함수
function signup(username, password, nickname, email, verifyCode, success, fail) {
  myaxios
    .post(`/join`, {
      username: username,
      password: password,
      nickname: nickname,
      email: email,
      verifyCode: verifyCode,
    })
    .then(success)
    .catch(fail);
}

// 회원가입 시 메일주소로 인증코드를 전송하는 함수
function getVerifyCode(username, email, success, fail) {
    myaxios
      .post(`/join/email`, {
        username: username,
        email: email
      })
      .then(success)
      .catch(fail)
}

// 비밀번호 초기화 메일을 전송할 때 사용하는 함수
function resetPassword(username, email, success, fail) {
    console.log(username)
    console.log(email)
    myaxios
      .post(`/user/resetpw`, {
        "username": username,
        "email": email
      })
      .then(success)
      .catch(fail)
}

// 헤더에 닉네임을 표시할 때 사용하는 함수
function getUserName(fail){
  const authStore = useTokenStore();
  myaxios
  .post(`/user/getnickname`)
  .then((data)=>{
    authStore.setNickname(data.data);
  })
  .catch(fail)
}

// 마이페이지에 접근할 때 비밀번호를 재확인하는 함수
function verifyPass(password, success, fail){
  myaxios
  .post(`/user/verifypass`, {password:password})
  .then(success)
  .catch(fail)
}


export { login, signup, getVerifyCode, resetPassword, unameDupCheck, emailDupCheck, getUserName, verifyPass };
