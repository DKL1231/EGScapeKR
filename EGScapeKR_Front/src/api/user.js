import myaxios from "@/utils/axios-common.js";
import { useTokenStore } from "@/stores/auth";
import Cookies from "js-cookie";


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

function unameDupCheck(username, success, fail){
  myaxios
  .post(`/user/unamecheck`, {
    username: username
  })
  .then(success)
  .catch(fail);
}

function emailDupCheck(email, success, fail){
  myaxios
  .post(`/user/emailcheck`, {
    email: email
  })
  .then(success)
  .catch(fail);
}

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

function getVerifyCode(username, email, success, fail) {
    myaxios
      .post(`/join/email`, {
        username: username,
        email: email
      })
      .then(success)
      .catch(fail)
}

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

function getUserName(fail){
  const authStore = useTokenStore();
  myaxios
  .post(`/user/getnickname`)
  .then((data)=>{
    authStore.nickname = data.data;
    authStore.setNickname(data.data);
  })
  .catch(fail)
}

export { login, signup, getVerifyCode, resetPassword, unameDupCheck, emailDupCheck, getUserName };
