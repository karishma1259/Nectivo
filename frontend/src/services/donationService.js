import api from './api';

export const getDonations = () => api.get('/donations');
export const createDonation = (data) => api.post('/donations', data);
export const updateDonationStatus = (id, status) =>
  api.patch(`/donations/${id}/status?status=${status}`);
