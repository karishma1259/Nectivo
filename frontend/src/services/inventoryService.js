import api from './api';

export const getBottles = () => api.get('/inventory/bottles');
export const getAvailableBottles = () => api.get('/inventory/bottles/available');
export const createBottle = (data) => api.post('/inventory/bottles', data);
export const markExpired = (id) => api.patch(`/inventory/bottles/${id}/expire`);
