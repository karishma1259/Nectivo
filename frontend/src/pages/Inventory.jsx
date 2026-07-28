import React, { useEffect, useState } from 'react';
import { getBottles, createBottle, markExpired } from '../services/inventoryService';
import { getDonations } from '../services/donationService';

export default function Inventory() {
  const [bottles, setBottles] = useState([]);
  const [donations, setDonations] = useState([]);
  const [form, setForm] = useState({ donationId: '', quantityMl: '', storageLocation: '', expiryDate: '' });
  const [error, setError] = useState('');

  const load = () => {
    getBottles().then((res) => setBottles(res.data)).catch(() => setError('Failed to load inventory'));
    getDonations().then((res) => setDonations(res.data.filter(d => d.status === 'APPROVED' || d.status === 'COLLECTED')));
  };

  useEffect(() => { load(); }, []);

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await createBottle({
        ...form,
        donationId: Number(form.donationId),
        quantityMl: Number(form.quantityMl),
        expiryDate: form.expiryDate || null,
      });
      setForm({ donationId: '', quantityMl: '', storageLocation: '', expiryDate: '' });
      load();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to create bottle');
    }
  };

  const handleExpire = async (id) => {
    await markExpired(id);
    load();
  };

  const badge = (status) => {
    const map = { AVAILABLE: 'success', RESERVED: 'warning', DISTRIBUTED: 'primary', EXPIRED: 'danger' };
    return <span className={`badge bg-${map[status] || 'secondary'}`}>{status}</span>;
  };

  return (
    <div className="container mt-4">
      <h3 className="mb-4">Inventory / Storage</h3>
      {error && <div className="alert alert-danger">{error}</div>}

      <div className="card p-3 mb-4">
        <h6>Bottle a Pasteurized Donation</h6>
        <form onSubmit={handleSubmit} className="row g-2">
          <div className="col-md-3">
            <select className="form-select" name="donationId" value={form.donationId} onChange={handleChange} required>
              <option value="">Select Approved/Collected Donation</option>
              {donations.map((d) => (
                <option key={d.id} value={d.id}>#{d.id} - {d.donorName} ({d.quantityMl}ml)</option>
              ))}
            </select>
          </div>
          <div className="col-md-2">
            <input type="number" className="form-control" name="quantityMl" placeholder="Quantity (ml)" value={form.quantityMl} onChange={handleChange} required />
          </div>
          <div className="col-md-3">
            <input className="form-control" name="storageLocation" placeholder="Storage Location / Freezer" value={form.storageLocation} onChange={handleChange} />
          </div>
          <div className="col-md-2">
            <input type="date" className="form-control" name="expiryDate" value={form.expiryDate} onChange={handleChange} />
          </div>
          <div className="col-md-2">
            <button className="btn btn-primary w-100" type="submit">Create Bottle</button>
          </div>
        </form>
      </div>

      <div className="card p-3">
        <table className="table table-hover align-middle">
          <thead><tr><th>Bottle Code</th><th>Quantity (ml)</th><th>Storage</th><th>Expiry</th><th>Status</th><th>Actions</th></tr></thead>
          <tbody>
            {bottles.map((b) => (
              <tr key={b.id}>
                <td><code>{b.bottleCode}</code></td>
                <td>{b.quantityMl}</td>
                <td>{b.storageLocation || '-'}</td>
                <td>{b.expiryDate}</td>
                <td>{badge(b.status)}</td>
                <td>
                  {b.status === 'AVAILABLE' && (
                    <button className="btn btn-sm btn-outline-danger" onClick={() => handleExpire(b.id)}>Mark Expired</button>
                  )}
                </td>
              </tr>
            ))}
            {bottles.length === 0 && <tr><td colSpan="6" className="text-center text-muted">No bottles in inventory.</td></tr>}
          </tbody>
        </table>
      </div>
    </div>
  );
}
