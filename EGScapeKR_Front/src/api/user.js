import myaxios from "@/utils/axios-common.js";

function login(username, password, success, fail) {
  myaxios
    .post(`/login`, {
      username: username,
      password: password,
    }, { withCredentials: true })
    .then(response => {
      // JWT 토큰을 응답 헤더에서 가져오기
      const token = response.headers['authorization'];
      console.log(token);
      if (token) {
        // 로컬스토리지에 JWT 토큰 저장
        localStorage.setItem('accessToken', token);
      }
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

export { login, signup, getVerifyCode, resetPassword, unameDupCheck, emailDupCheck };
