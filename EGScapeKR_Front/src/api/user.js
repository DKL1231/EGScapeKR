import myaxios from "@/utils/axios-common.js";

function login(username, password, success, fail) {
  myaxios
    .post(`/login`, {
      username: username,
      password: password,
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

export { login, signup, getVerifyCode, resetPassword };
