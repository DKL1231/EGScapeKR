import { defineStore } from 'pinia';
import Cookies from 'js-cookie';

export const useTokenStore = defineStore('auth', {
  state: () => ({
    accessToken: localStorage.getItem('accessToken') || '',
    refreshToken: Cookies.get('refreshToken') || '',
    nickname: '',
  }),
  actions: {
    setTokens(accessToken, refreshToken) {
      this.accessToken = accessToken;
      this.refreshToken = refreshToken;
      localStorage.setItem('accessToken', accessToken);
      Cookies.set('refreshToken', refreshToken);
    },
    clearTokens() {
      this.accessToken = '';
      this.refreshToken = '';
      localStorage.removeItem('accessToken');
      Cookies.remove('refreshToken');
    },
    setNickname(nickname) {
        this.nickname = nickname;
      },
      clearNickname() {
        this.nickname = '';
      },
  },
});