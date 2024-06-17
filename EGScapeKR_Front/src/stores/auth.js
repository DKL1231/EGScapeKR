import { defineStore } from 'pinia';
import Cookies from 'js-cookie';

export const useTokenStore = defineStore('auth', {
  state: () => ({
    accessToken: localStorage.getItem('accessToken') || '',
    userid: '',
    nickname: '',
    useremail: '',
  }),
  actions: {
    setTokens(accessToken) {
      this.accessToken = accessToken;
      localStorage.setItem('accessToken', accessToken);
    },
    clearTokens() {
      this.accessToken = '';
      localStorage.removeItem('accessToken');
    },
    setNickname(nickname) {
        this.nickname = nickname;
    },
    setUserId(newId) {
      this.userid = newId;
    },
    setUserEmail(newEmail) {
      this.useremail = newEmail;
    },
    clearData() {
      this.nickname = '';
      this.userid = '';
      this.useremail = '';
    },
  },
});