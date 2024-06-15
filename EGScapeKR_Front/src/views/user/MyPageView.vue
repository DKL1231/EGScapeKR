<script setup>
import { ref } from "vue";
import { verifyPass } from "@/api/user";

const password = ref('');
const isVerified = ref(false);

function verifyPassword(){
    verifyPass(password.value,
        (data)=>{
            isVerified.value = true;
        },
        (error)=>{
            if(error.response.status === 401){
                alert("비밀번호를 확인 후 다시 입력해 주세요.");
                return;
            }
            if(error.response.status === 409){
                alert("로그인이 만료되었습니다.");
                return;
            }
        }
    )
}

</script>

<template>
  <div>
    <div v-if="!isVerified">
      <h2>비밀번호 확인</h2>
      <form @submit.prevent="verifyPassword">
        <input
          type="password"
          v-model="password"
          placeholder="비밀번호"
          required
        />
        <button type="submit">확인</button>
      </form>
    </div>
    <div v-else>
      <!-- 실제 마이페이지 내용 -->
      <h1>마이페이지</h1>
      <p>여기에 마이페이지 콘텐츠를 표시합니다.</p>
    </div>
  </div>
</template>

<style scoped></style>
