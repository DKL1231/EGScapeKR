<script setup>
import { ref, watch } from "vue";
import { useRouter } from "vue-router";
import { unameDupCheck, emailDupCheck, getVerifyCode, signup } from "@/api/user";

const id = ref("");
const idCheck = ref(false);
const email = ref("");
const verifyCode = ref("");
const userName = ref("");
const password = ref("");
const passwordCheck = ref("");
const checkpasstext = ref("비밀번호는 6자 이상으로, 동일하게 입력해 주세요");
const passCheck = ref(false);

const router = useRouter();

watch([password, passwordCheck], ([a, b], [c, d]) => {
  console.log(password.value);
  if (password.value == passwordCheck.value && password.value.length > 5) {
    checkpasstext.value = "비밀번호가 동일합니다.";
    passCheck.value = true;
  } else {
    checkpasstext.value = "비밀번호는 6자 이상으로, 동일하게 입력해 주세요";
    passCheck.value = false;
  }
});



function idDupCheck(){
  unameDupCheck(id.value,
    (success)=>{
      console.log(success);
      if(window.confirm("사용 가능한 아이디입니다. 사용하시겠습니까?")){
        document.getElementById("floatingId").setAttribute("disabled", "disabled");
        document.getElementById("idDupCheckBtn").setAttribute("disabled", "disabled");
        idCheck.value = true;
      }
    },
    (fail)=>{
      console.log(fail);
      alert("이미 사용중인 아이디입니다.");
    }
  )
}

function mailDupCheck(){
  if(!idCheck.value){
    alert("아이디 중복 확인을 먼저 진행해주시기 바랍니다.");
    return;
  }
  emailDupCheck(email.value,
    (success)=>{
      if(window.confirm("사용 가능한 이메일입니다. 인증 메일을 보내시겠습니까?")){
        document.getElementById("floatingEmail").setAttribute("disabled", "disabled");
        document.getElementById("emailDupCheckBtn").setAttribute("disabled", "disabled");
        getVerifyCode(id.value, email.value,
          (success)=>{
            alert("메일 전송을 완료했습니다. 인증코드를 확인해주세요.");
          },
          (fail)=>{
            alert("메일 전송에 실패했습니다. 잠시 후 시도하거나 다른 메일 주소를 이용해 주세요.");
            document.getElementById("floatingEmail").removeAttribute("disabled");
            document.getElementById("emailDupCheckBtn").removeAttribute("disabled");
          }
        )
      }
    },
    (fail)=>{
      alert("이미 사용중인 이메일입니다.");
    }
  )
}

function submit(){
  if(!idCheck.value){
    alert("아이디 중복 확인을 먼저 진행해주시기 바랍니다.");
    return;
  }
  if(verifyCode.value.length===0){
    alert("인증 코드를 입력해 주시기 바랍니다.");
  }
  if(userName.value.length===0){
    alert("닉네임을 입력해 주시기 바랍니다.");
    return;
  }
  if(!passCheck.value){
    alert("비밀번호와 비밀번호 확인을 동일하게 입력해주시기 바랍니다.");
    return;
  }

  signup(id.value, password.value, userName.value, email.value, verifyCode.value,
    (success)=>{
      if(window.confirm("회원가입을 성공했습니다. 로그인화면으로 이동합니다.")){
        router.push("/");
      }else{
        router.push("/");
      }
    },
    (fail)=>{
      alert("회원가입에 실패했습니다. 인증코드가 올바르지 않을 수 있습니다. 다시 시도해 보세요.");
    }
  )
}

</script>

<template>
  <div>
    <div class="loginForm row justify-content-center">
      <h1>회원가입</h1>
      <div class="col col-6">
        <!-- Email input -->
        <div data-mdb-input-init class="form-floating mb-4" style="display:flex">
          <input
            type="text"
            id="floatingId"
            class="form-control"
            placeholder="아이디"
            required
            v-model="id"
            style="margin-right: 10px"
          />
          <label for="floatingId">아이디</label>
          <button id="idDupCheckBtn" class="btn btn-outline-success" style="white-space: nowrap;" @click="idDupCheck">중복확인</button>
        </div>

        <div data-mdb-input-init class="form-floating mb-4" style="display:flex">
          <input
            type="email"
            id="floatingEmail"
            class="form-control"
            placeholder="이메일"
            required
            v-model="email"
            style="margin-right: 10px"
          />
          <label for="floatingEmail">이메일</label>
          <button id="emailDupCheckBtn" class="btn btn-outline-success" style="white-space: nowrap;" @click="mailDupCheck">인증하기</button>
        </div>
        <div data-mdb-input-init class="form-floating mb-4">
          <input
            type="text"
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

            @click="submit"
          >
            회원가입
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
