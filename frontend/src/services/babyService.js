import api from './api';

export const getBabies = () => api.get('/babies');
export const getBaby = (id) => api.get(`/babies/${id}`);
export const createBaby = (data) => api.post('/babies', data);
export const updateBaby = (id, data) => api.put(`/babies/${id}`, data);
export const deleteBaby = (id) => api.delete(`/babies/${id}`);
