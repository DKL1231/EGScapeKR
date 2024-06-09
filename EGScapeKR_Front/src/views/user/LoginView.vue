<script setup>
import { ref } from "vue";
import { RouterLink, useRouter } from "vue-router";
import ResetPasswordView from "@/views/user/resetPasswordView.vue";
import { login } from "@/api/user.js";
import * as bootstrap from "bootstrap";

const id = ref("");
const pass = ref("");
const router = useRouter();

function showModal(modalId) {
  const modal = new bootstrap.Modal(document.getElementById(modalId));
  modal.show();
}

function hideModal(modalId) {
  const modalElement = document.getElementById(modalId);
  const modalInstance = bootstrap.Modal.getInstance(modalElement);
  if (modalInstance) {
    modalInstance.hide();
  }
}

function loginProcess() {
    login(id.value, pass.value, (success)=>{
        showModal("loginSuccessModal");
        setTimeout(() => {
           hideModal("loginSuccessModal");

           router.push("/");
        }, 1000);
    }, (fail)=>{
        console.log(fail);
        alert("로그인 실패!");
    })
}
</script>

<template>
  <div>
    <div class="loginForm row justify-content-center">
        <h1>로그인</h1>
      <div class="col col-6">
        <!-- Email input -->
        <div data-mdb-input-init class="form-floating mb-4">
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

        <!-- Password input -->
        <div data-mdb-input-init class="form-floating mb-4">
          <input
            type="password"
            id="floatingPw"
            class="form-control"
            placeholder="비밀번호"
            v-model="pass"
          />
          <label for="floatingPw">비밀번호</label>
        </div>

        <div class="form-outline mb-4">
          <button
            type="button"
            data-mdb-button-init
            data-mdb-ripple-init
            class="btn btn-outline-primary btn-block col-12"
            @click="loginProcess"
          >
            로그인
          </button>
        </div>

        <div class="form-outline mb-4">
          <RouterLink to="/signup">
            <button
              class="btn btn-outline-success btn-block col-12"
            >
              회원가입
            </button>
          </RouterLink>
        </div>

        <div class="form-outline mb-4">
          <button
            class="btn btn-outline-danger btn-block col-12"
            data-bs-target="#resetPasswordModal"
            data-bs-toggle="modal"
          >
            비밀번호를 까먹었어요
          </button>
        </div>
      </div>
    </div>
  </div>

  <!-- resetPasswordModal -->
  <div
    class="modal fade"
    id="resetPasswordModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="exampleModalCenterTitle"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalCenterTitle">
            비밀번호 초기화
          </h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <ResetPasswordView></ResetPasswordView>
        </div>
      </div>
    </div>
  </div>

  <!-- loginSuccessModal -->
  <div
    class="modal fade"
    id="loginSuccessModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="exampleModalCenterTitle"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalCenterTitle">
            로그인 성공!
          </h5>
        </div>
        <div class="modal-body">
           로그인 성공! 메인페이지로 이동합니다..
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
