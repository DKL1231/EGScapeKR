import axios from 'axios';
import { API_BASE_URL } from '../config/apiConfig';
import { setAuthHeaders, getRefreshToken, setAccessToken, clearTokens } from '../utils/auth';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

apiClient.interceptors.request.use(
  config => {
    config.headers = {
      ...config.headers,
      ...setAuthHeaders(),
    };
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

let isRefreshing = false;
let refreshSubscribers = [];

function onRrefreshed(token) {
  refreshSubscribers.map(callback => callback(token));
}

function addRefreshSubscriber(callback) {
  refreshSubscribers.push(callback);
}

apiClient.interceptors.response.use(
  response => response,
  async error => {
    const originalRequest = error.config;
    if (error.response.status === 401 && !originalRequest._retry) {
      if (isRefreshing) {
        return new Promise((resolve, reject) => {
          addRefreshSubscriber(token => {
            originalRequest.headers['Authorization'] = 'Bearer ' + token;
            resolve(apiClient(originalRequest));
          });
        });
      }

      originalRequest._retry = true;
      isRefreshing = true;

      const refreshToken = getRefreshToken();
      if (refreshToken) {
        try {
          const response = await axios.post(`${API_BASE_URL}/auth/refresh`, { token: refreshToken });
          const newAccessToken = response.data.accessToken;
          setAccessToken(newAccessToken);
          axios.defaults.headers.common['Authorization'] = `Bearer ${newAccessToken}`;
          isRefreshing = false;
          onRrefreshed(newAccessToken);
          refreshSubscribers = [];
          return apiClient(originalRequest);
        } catch (refreshError) {
          isRefreshing = false;
          clearTokens();
          return Promise.reject(refreshError);
        }
      } else {
        clearTokens();
        return Promise.reject(error);
      }
    }
    return Promise.reject(error);
  }
);

export default apiClient;
