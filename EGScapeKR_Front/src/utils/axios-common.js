import axios from 'axios';
import { API_BASE_URL } from '../config/apiConfig';
import { useTokenStore } from '../stores/auth';
import { storeToRefs } from 'pinia';
import Cookies from 'js-cookie';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 요청 인터셉터
apiClient.interceptors.request.use(
  config => {
    const tokenStore = useTokenStore();
    const { accessToken } = storeToRefs(tokenStore);
    if (accessToken.value) {
      config.headers['Authorization'] = `${accessToken.value}`;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// 응답 인터셉터
apiClient.interceptors.response.use(
  response => {
    return response;
  },
  async error => {
    const originalRequest = error.config;
    const aToken = useTokenStore().accessToken;
    if(aToken === '') return;
    if (error.response.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
        try {
          const { data } = await axios.post(`${API_BASE_URL}/auth/refresh`, {}, { withCredentials: true });
          tokenStore.setTokens(data.accessToken, Cookies.get('refreshToken'));
          originalRequest.headers['Authorization'] = `${data.accessToken}`;
          return axios(originalRequest);
        } catch (e) {
          console.error('Refresh token failed', e);
          alert("로그인이 만료되었습니다.");
          const tokenStore = useTokenStore();
          //tokenStore.clearTokens();
        }
    }
    return Promise.reject(error);
  }
);

export default apiClient;
