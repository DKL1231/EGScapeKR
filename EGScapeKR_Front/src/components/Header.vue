<script setup>
import { RouterLink } from "vue-router";
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
            <RouterLink to="/" class="nav-link">asdf</RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink to="/" class="nav-link">asdf</RouterLink>
          </li>
        </ul>

        <ul
          class="navbar-nav ms-auto my-2 my-lg-0 navbar-nav-scroll"
          style="--bs-scroll-height: 100px"
        >
          <li class="nav-item">
            <RouterLink to="/login" class="nav-link">로그인</RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink to="/signup" class="nav-link">회원가입</RouterLink>
          </li>
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
