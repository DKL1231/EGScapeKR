<script setup>
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'
import HeaderView from '@/components/Header.vue'
</script>

<template>
  <header>
    <div id="header">
      <HeaderView id="headerView"></HeaderView>
    </div>
  </header>

  <RouterView />
</template>

<style scoped>
#header {
  min-height: 10vh;
  display:grid;
}
</style>
