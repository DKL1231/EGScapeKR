import Cookies from 'js-cookie';

export function getAccessToken() {
  return localStorage.getItem('accessToken');
}

export function getRefreshToken() {
  return Cookies.get('refreshToken');
}

export function setAccessToken(token) {
  localStorage.setItem('accessToken', token);
}

export function clearTokens() {
  localStorage.removeItem('accessToken');
  Cookies.remove('refreshToken');
}

export function setAuthHeaders() {
  const token = getAccessToken();
  return token ? { Authorization: `${token}` } : {};
}
