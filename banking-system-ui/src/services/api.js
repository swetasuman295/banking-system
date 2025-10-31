import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/v1';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const accountsAPI = {
  getAllBalances: async () => {
    const response = await apiClient.get('/accounts/balances');
    return response.data;
  },
};

export const transactionsAPI = {
  withdraw: async (data) => {
    const response = await apiClient.post('/transactions/withdraw', data);
    return response.data;
  },
  
  transfer: async (data) => {
    const response = await apiClient.post('/transactions/transfer', data);
    return response.data;
  },
};

export default apiClient;