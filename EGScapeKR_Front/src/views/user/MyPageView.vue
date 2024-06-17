<script setup>
import { onMounted, ref } from "vue";
import { useTokenStore } from "@/stores/auth";
import {
  verifyPass,
  updateUserDetails,
  changePassword,
  changeEmail,
  getVerifyCode,
  getUserData,
} from "@/api/user";

const authStore = useTokenStore();

const password = ref("");
const isVerified = ref(false);

const userId = ref("")
const nickname = ref("")
const email = ref("")
const newPassword = ref("");
const confirmPassword = ref("");
const newEmail = ref("");
const verifyCode = ref("");
const verifyModalVisible = ref(false); // Modal 표시 여부

onMounted(()=>{
  setTimeout(()=>{
    userId.value = authStore.userid;
    nickname.value = authStore.nickname;
    email.value = authStore.useremail;
  }, 1000);
  
})

function verifyPassword() {
  verifyPass(
    password.value,
    (data) => {
      isVerified.value = true;
    },
    (error) => {
      if (error.response.status === 403) {
        alert("비밀번호를 확인 후 다시 입력해 주세요.");
        return;
      }
      if (error.response.status === 409) {
        alert("로그인이 만료되었습니다.");
        return;
      }
    }
  );
}

function updateUser() {
  updateUserDetails(
    nickname.value,
    (data) => {
      alert("정보가 업데이트되었습니다.");
      console.log(authStore.nickname);
      console.log(nickname);
      authStore.setNickname(nickname);
    },
    (error) => {
      alert("업데이트 중 오류가 발생했습니다.");
    }
  );
}

function updatePassword() {
  if (newPassword.value !== confirmPassword.value) {
    alert("비밀번호가 일치하지 않습니다.");
    return;
  }
  changePassword(
    { newPassword: newPassword.value },
    (data) => {
      alert("비밀번호가 변경되었습니다.");
    },
    (error) => {
      alert("비밀번호 변경 중 오류가 발생했습니다.");
    }
  );
}

function sendVerificationCode() {
  getVerifyCode(
    newEmail.value,
    (data) => {
      alert("인증코드가 발송되었습니다.");
      verifyModalVisible.value = true;
    },
    (error) => {
      alert("인증코드 발송 중 오류가 발생했습니다.");
    }
  );
}

function verifyEmailCode() {
  // 여기에 인증코드를 검증하는 로직을 추가하세요.
  // 인증이 성공하면 updateEmail을 호출하도록 할 수 있습니다.
  // 예시:
  // if (verifyCode.value === 'expectedCode') {
  //     updateEmail();
  // } else {
  //     alert('인증코드가 올바르지 않습니다.');
  // }

  // 임시로 updateEmail을 바로 호출하는 예시
  updateEmail();
  verifyModalVisible.value = false;
}

function updateEmail() {
  changeEmail(
    { newEmail: newEmail.value },
    (data) => {
      alert("이메일이 변경되었습니다.");
    },
    (error) => {
      alert("이메일 변경 중 오류가 발생했습니다.");
    }
  );
}
</script>

<template>
  <div
    class="d-flex justify-content-center align-items-center min-vh-75 my-auto"
  >
    <div v-if="!isVerified" class="card p-4">
      <h2 class="mb-4 text-center">비밀번호 확인</h2>
      <form @submit.prevent="verifyPassword">
        <div class="form-floating mb-3">
          <input
            type="password"
            class="form-control"
            id="floatingPassword"
            v-model="password"
            placeholder="비밀번호"
            required
          />
          <label for="floatingPassword">비밀번호</label>
        </div>
        <div class="d-grid">
          <button type="submit" class="btn btn-primary">확인</button>
        </div>
      </form>
    </div>
    <div v-else class="container mt-5">
      <!-- 실제 마이페이지 내용 -->
      <div class="row">
        <div class="col-md-3">
          <ul class="nav flex-column nav-pills" id="myTab" role="tablist">
            <li class="nav-item" role="presentation">
              <button
                class="nav-link active"
                id="profile-tab"
                data-bs-toggle="tab"
                data-bs-target="#profiletab"
                type="button"
                role="tab"
                aria-controls="profile"
                aria-selected="true"
              >
                프로필
              </button>
            </li>
            <li class="nav-item" role="presentation">
              <button
                class="nav-link"
                id="password-tab"
                data-bs-toggle="tab"
                data-bs-target="#passwordtab"
                type="button"
                role="tab"
                aria-controls="password"
                aria-selected="false"
              >
                비밀번호 변경
              </button>
            </li>
            <li class="nav-item" role="presentation">
              <button
                class="nav-link"
                id="email-tab"
                data-bs-toggle="tab"
                data-bs-target="#emailtab"
                type="button"
                role="tab"
                aria-controls="email"
                aria-selected="false"
              >
                이메일 변경
              </button>
            </li>
          </ul>
        </div>
        <div class="col-md-9">
          <div class="tab-content" id="myTabContent">
            <div
              class="tab-pane fade show active"
              id="profiletab"
              role="tabpanel"
              aria-labelledby="profile-tab"
            >
              <form @submit.prevent="updateUser" class="mt-4">
                <div class="form-floating mb-3">
                  <input
                    type="text"
                    class="form-control"
                    id="userId"
                    v-model="userId"
                    placeholder="사용자 아이디"
                    disabled
                  />
                  <label for="userId">사용자 아이디</label>
                </div>
                <div class="form-floating mb-3">
                  <input
                    type="text"
                    class="form-control"
                    id="nickname"
                    v-model="nickname"
                    placeholder="닉네임"
                    required
                  />
                  <label for="nickname">닉네임</label>
                </div>
                <div class="form-floating mb-3">
                  <input
                    type="email"
                    class="form-control"
                    id="email"
                    v-model="email"
                    placeholder="이메일"
                    disabled
                  />
                  <label for="email">이메일</label>
                </div>
                <div class="d-grid">
                  <button type="submit" class="btn btn-primary">
                    업데이트
                  </button>
                </div>
              </form>
            </div>
            <div
              class="tab-pane fade"
              id="passwordtab"
              role="tabpanel"
              aria-labelledby="password-tab"
            >
              <form @submit.prevent="updatePassword" class="mt-4">
                <div class="form-floating mb-3">
                  <input
                    type="password"
                    class="form-control"
                    id="newPassword"
                    v-model="newPassword"
                    placeholder="새 비밀번호"
                    required
                  />
                  <label for="newPassword">새 비밀번호</label>
                </div>
                <div class="form-floating mb-3">
                  <input
                    type="password"
                    class="form-control"
                    id="confirmPassword"
                    v-model="confirmPassword"
                    placeholder="비밀번호 확인"
                    required
                  />
                  <label for="confirmPassword">비밀번호 확인</label>
                </div>
                <div class="d-grid">
                  <button type="submit" class="btn btn-primary">
                    비밀번호 변경
                  </button>
                </div>
              </form>
            </div>
            <div
              class="tab-pane fade"
              id="emailtab"
              role="tabpanel"
              aria-labelledby="email-tab"
            >
              <form @submit.prevent="sendVerificationCode" class="mt-4">
                <div class="form-floating mb-3 d-flex">
                  <input
                    type="email"
                    class="form-control"
                    id="newEmail"
                    v-model="newEmail"
                    placeholder="새 이메일"
                    required
                  />
                  <label for="newEmail">새 이메일</label>
                  <button
                    type="button"
                    class="btn btn-secondary ms-2"
                    @click="sendVerificationCode"
                    style="white-space: nowrap;"
                  >
                    인증코드 발송
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 인증코드 입력 Modal -->
  <div
    v-if="verifyModalVisible"
    class="modal show"
    tabindex="-1"
    style="display: block"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">인증코드 입력</h5>
          <button
            type="button"
            class="btn-close"
            @click="verifyModalVisible = false"
          ></button>
        </div>
        <div class="modal-body">
          <div class="form-floating mb-3">
            <input
              type="text"
              class="form-control"
              id="verifyCode"
              v-model="verifyCode"
              placeholder="인증코드"
              required
            />
            <label for="verifyCode">인증코드</label>
          </div>
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-secondary"
            @click="verifyModalVisible = false"
          >
            취소
          </button>
          <button
            type="button"
            class="btn btn-primary"
            @click="
              verifyEmail;
              Code;
            "
          >
            확인
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.min-vh-75 {
  min-height: 75vh;
}
.card {
  max-width: 400px;
  width: 100%;
}
</style>
