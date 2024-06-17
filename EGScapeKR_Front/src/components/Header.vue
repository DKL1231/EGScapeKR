<script setup>
import { computed, onMounted } from 'vue';
import { RouterLink, useRouter } from 'vue-router';
import { useTokenStore } from '../stores/auth';
import { getUserData } from '@/api/user';

const tokenStore = useTokenStore();
const router = useRouter();

const nickname = computed(() => tokenStore.nickname);

const logout = () => {
  tokenStore.clearTokens();
  tokenStore.clearData();
  router.push('/login');
};

onMounted(() => {
  if (tokenStore.accessToken) {
    getUserData((error)=>{
      console.log(error);
    })
  }
});
</script>

<template>
  <nav class="navbar navbar-expand-lg" style="background-color: white">
    <div class="container-fluid">
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarScroll"
        aria-controls="navbarScroll"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarScroll">
        <ul
          class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll"
          style="--bs-scroll-height: 100px"
        >
          <li class="nav-item">
            <RouterLink to="/" class="nav-link">메인</RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink to="/about" class="nav-link">About</RouterLink>
          </li>
        </ul>

        <ul
          class="navbar-nav ms-auto my-2 my-lg-0 navbar-nav-scroll"
          style="--bs-scroll-height: 100px"
        >
          <template v-if="nickname">
            <li class="nav-item dropdown">
              <a
                class="nav-link dropdown-toggle"
                href="#"
                id="navbarDropdown"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                환영합니다, {{ nickname }}
              </a>
              <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                <li><RouterLink class="dropdown-item" to="/mypage">마이페이지</RouterLink></li>
                <li><a class="dropdown-item" href="#" @click="logout">로그아웃</a></li>
              </ul>
            </li>
          </template>
          <template v-else>
            <li class="nav-item">
              <RouterLink to="/login" class="nav-link">로그인</RouterLink>
            </li>
            <li class="nav-item">
              <RouterLink to="/signup" class="nav-link">회원가입</RouterLink>
            </li>
          </template>
        </ul>
      </div>
    </div>
  </nav>
</template>

<style scoped>
.nav-item a {
  color: black;
  text-decoration: none;
  font-weight: 500;
  font-size: 16.3px;
}

.nav-item a:hover {
  color: rgb(139, 176, 89);
  text-decoration: none;
  font-weight: 600;
  font-size: 16.3px;
}
</style>
