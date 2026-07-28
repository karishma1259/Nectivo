import api from './api';

export const getDistributions = () => api.get('/distribution');
export const distributeBottle = (data) => api.post('/distribution', data);
