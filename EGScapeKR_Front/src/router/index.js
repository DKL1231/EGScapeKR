import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '@/views/user/LoginView.vue'
import SignupView from '@/views/user/SignupView.vue'
import MypageView from '@/views/user/MyPageView.vue'
import { useTokenStore } from '@/stores/auth'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignupView
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: MypageView,
      beforeEnter: (to, from, next) => {
        const tokenStore = useTokenStore();
        if(tokenStore.accessToken){
          next();
        }else{
          next('/login');
        }
      }
    }
  ]
})

export default router
