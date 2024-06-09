<script setup>
import { ref } from "vue";
import { resetPassword } from "@/api/user.js";
import * as bootstrap from "bootstrap";

const id = ref("");
const email = ref("");

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

function resetPasswordProcess() {
  showModal("loadingModal");

  resetPassword(
    id.value,
    email.value,
    (data) => {
      hideModal("loadingModal");
      showModal("successModal");
      console.log("success");
      console.log(data);
    },
    (error) => {
      setTimeout(() => {
        hideModal("loadingModal");
      }, 500);
      setTimeout(() => {
        showModal("errorModal");
      }, 500);
      console.log("error");
      console.log(error);
    }
  );
}
</script>

<template>
  <div>
    <div class="loginForm row justify-content-center">
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
            type="email"
            id="floatingEmail"
            class="form-control"
            placeholder="이메일"
            v-model="email"
          />
          <label for="floatingEmail">이메일</label>
        </div>

        <div class="form-outline mb-4">
          <button
            type="button"
            data-mdb-button-init
            data-mdb-ripple-init
            class="btn btn-danger btn-block col-12"
            @click="resetPasswordProcess"
          >
            비밀번호 초기화
          </button>
        </div>
      </div>
    </div>

    <!-- Loading Modal -->
    <div
      class="modal fade"
      id="loadingModal"
      tabindex="-1"
      aria-labelledby="loadingModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="loadingModalLabel">로딩 중</h5>
          </div>
          <div class="modal-body">처리 중입니다. 잠시만 기다려 주세요...</div>
        </div>
      </div>
    </div>

    <!-- Success Modal -->
    <div
      class="modal fade"
      id="successModal"
      tabindex="-1"
      aria-labelledby="successModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="successModalLabel">성공</h5>
          </div>
          <div class="modal-body">비밀번호가 성공적으로 초기화되었습니다. 이메일을 확인해 주세요</div>
        </div>
      </div>
    </div>

    <!-- Error Modal -->
    <div
      class="modal fade"
      id="errorModal"
      tabindex="-1"
      aria-labelledby="errorModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="errorModalLabel">오류</h5>
          </div>
          <div class="modal-body">
            존재하지 않는 아이디 또는 이메일입니다. 다시 시도해 주세요.
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
