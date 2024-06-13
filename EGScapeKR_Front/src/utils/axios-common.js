import axios from 'axios';
import { API_BASE_URL } from '../config/apiConfig';
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
    const token = localStorage.getItem('accessToken');
    if (token) {
      config.headers['Authorization'] = `${token}`;
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
      const refreshToken = Cookies.get('refreshToken');
      console.log(refreshToken);
      if (refreshToken) {
        try {
          const { data } = await axios.post(`https://${API_BASE_URL}/auth/refresh`, { refreshToken });
          localStorage.setItem('accessToken', data.accessToken);
          Cookies.set('refreshToken', data.refreshToken);
          originalRequest.headers['Authorization'] = `${data.accessToken}`;
          return axios(originalRequest);
        } catch (e) {
          console.error('Refresh token failed', e);
          clearTokens();
        }
      }
    }
    return Promise.reject(error);
  }
);

export default apiClient;


function clearTokens() {
  localStorage.removeItem('accessToken');
  Cookies.remove('refreshToken');
}