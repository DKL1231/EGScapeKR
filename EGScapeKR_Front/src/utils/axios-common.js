import axios from 'axios';
import { API_BASE_URL } from '../config/apiConfig';
import { useTokenStore } from '../stores/auth';
import { storeToRefs } from 'pinia';

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
    if (error.response.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      const tokenStore = useTokenStore();
      const { refreshToken } = storeToRefs(tokenStore);
      console.log(refreshToken.value);
      if (refreshToken.value) {
        try {
          const { data } = await axios.post(`${API_BASE_URL}/auth/refresh`, { refreshToken: refreshToken.value });
          tokenStore.setTokens(data.accessToken, data.refreshToken);
          originalRequest.headers['Authorization'] = `${data.accessToken}`;
          return axios(originalRequest);
        } catch (e) {
          console.error('Refresh token failed', e);
          tokenStore.clearTokens();
        }
      }
    }
    return Promise.reject(error);
  }
);

export default apiClient;
