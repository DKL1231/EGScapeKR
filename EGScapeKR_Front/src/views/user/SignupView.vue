<script setup>
import { ref, watch } from "vue";

const id = ref("");
const email = ref("");
const verifyCode = ref("");
const userName = ref("");
const password = ref("");
const passwordCheck = ref("");
const checkpasstext = ref("비밀번호를 동일하게 입력해 주세요");

watch([password, passwordCheck], ([a, b], [c, d]) => {
  console.log(password.value);
  if (password.value == passwordCheck.value && password.value.length !== 0) {
    checkpasstext.value = "비밀번호가 동일합니다.";
  } else {
    checkpasstext.value = "비밀번호를 동일하게 입력해 주세요";
  }
});
</script>

<template>
  <div>
    <div class="loginForm row justify-content-center">
      <h1>회원가입</h1>
      <div class="col col-6">
        <!-- Email input -->
        <div data-mdb-input-init class="mb-4 row">
          <div class="col-12 mr-auto">
            <div class="form-floating">
              <input
                type="text"
                id="floatingId"
                class="form-control"
                placeholder="아이디"
                required
                v-model="id"
              />
              <label for="floatingId">아이디</label>
            </div>
          </div>
        </div>

        <div data-mdb-input-init class="form-floating mb-4">
          <input
            type="email"
            id="floatingEmail"
            class="form-control"
            placeholder="이메일"
            required
            v-model="email"
          />
          <label for="floatingEmail">이메일</label>
        </div>
        <div data-mdb-input-init class="form-floating mb-4">
          <input
            type="email"
            id="floatingCode"
            class="form-control"
            placeholder="인증코드"
            required
            v-model="verifyCode"
          />
          <label for="floatingCode">인증코드</label>
        </div>
        <div data-mdb-input-init class="form-floating mb-4">
          <input
            type="text"
            id="floatingName"
            class="form-control"
            placeholder="닉네임"
            required
            v-model="userName"
          />
          <label for="floatingName">닉네임</label>
        </div>

        <!-- Password input -->
        <div>
          <div data-mdb-input-init class="form-floating mb-4">
            <input
              type="password"
              id="floatingPw"
              class="form-control"
              placeholder="비밀번호"
              v-model="password"
              required
            />
            <label for="floatingPw">비밀번호</label>
          </div>
          <div data-mdb-input-init class="form-floating mb-4">
            <input
              type="password"
              id="floatingPwCheck"
              class="form-control"
              placeholder="비밀번호 확인"
              v-model="passwordCheck"
              required
            />
            <label for="floatingPwCheck">비밀번호 확인</label>
            <span>{{ checkpasstext }}</span>
          </div>
        </div>

        <div class="form-outline mb-4">
          <button
            type="button"
            data-mdb-button-init
            data-mdb-ripple-init
            class="btn btn-outline-success btn-block col-12"
          >
            회원가입
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
