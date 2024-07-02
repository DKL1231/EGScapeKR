import myaxios from "@/utils/axios-common.js";
import { useTokenStore } from "@/stores/auth";

// 로그인을 진행하고 토큰을 저장하는 함수
function login(username, password, success, fail) {
  const tokenStore = useTokenStore();
  tokenStore.clearTokens();
  myaxios
    .post(`/login`, {
      username: username,
      password: password,
    }, { withCredentials: true })
    .then(response => {
      // JWT 토큰을 응답 헤더에서 가져오기
      const token = response.headers['authorization'];
      if (token) {
        tokenStore.setTokens(token);
      }
      getUserData(null);
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

// 유저 정보를 가져오는 함수
function getUserData(fail){
  const authStore = useTokenStore();
  myaxios
  .post(`/user/getuserdata`)
  .then((data)=>{
    const userdata = data.data;
    authStore.setNickname(userdata.nickname);
    authStore.setUserId(userdata.username);
    authStore.setUserEmail(userdata.email);
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

// 마이페이지에서 유저의 정보를 변경하는 함수
function updateUserDetails(newNickname, success, fail){
  myaxios
  .post(`/user/updatenickname`, {nickname:newNickname})
  .then(success)
  .catch(fail);
}

// 마이페이지에서 유저의 비밀번호를 변경하는 함수
function changePassword(newPw, success, fail){
  myaxios
  .post(`/user/updatepassword`, {password: newPw})
  .then(success)
  .catch(fail)
}

// 마이페이지에서 인증 메일을 발송하는 함수
function mypageVerifyCode(email, success, fail){
  myaxios
  .post(`/user/sendmail`, {email: email})
  .then(success)
  .catch(fail);
}

// 마이페이지에서 이메일을 변경하는 함수
function changeEmail(newEmail, verifyCode, success, fail){
  myaxios
  .post(`/user/updateemail`, {email: newEmail, verifyCode: verifyCode})
  .then(success)
  .catch(fail);
}

// 회원탈퇴를 진행하는 함수
function deleteUserAccount(success, fail){
  myaxios
  .post(`/user/withdraw`)
  .then(success)
  .catch(fail);
}

export { login, signup, getVerifyCode, resetPassword, unameDupCheck, emailDupCheck, getUserData, verifyPass, updateUserDetails, changePassword, mypageVerifyCode, changeEmail, deleteUserAccount };
