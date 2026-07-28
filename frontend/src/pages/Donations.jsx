import React, { useEffect, useState } from 'react';
import { getDonations, createDonation, updateDonationStatus } from '../services/donationService';
import { getDonors } from '../services/donorService';

export default function Donations() {
  const [donations, setDonations] = useState([]);
  const [donors, setDonors] = useState([]);
  const [form, setForm] = useState({ donorId: '', quantityMl: '', notes: '' });
  const [error, setError] = useState('');

  const load = () => {
    getDonations().then((res) => setDonations(res.data)).catch(() => setError('Failed to load donations'));
    getDonors().then((res) => setDonors(res.data));
  };

  useEffect(() => { load(); }, []);

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await createDonation({ ...form, donorId: Number(form.donorId), quantityMl: Number(form.quantityMl) });
      setForm({ donorId: '', quantityMl: '', notes: '' });
      load();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to record donation');
    }
  };

  const handleStatus = async (id, status) => {
    await updateDonationStatus(id, status);
    load();
  };

  return (
    <div className="container mt-4">
      <h3 className="mb-4">Donation Management</h3>
      {error && <div className="alert alert-danger">{error}</div>}

      <div className="card p-3 mb-4">
        <h6>Record New Donation</h6>
        <form onSubmit={handleSubmit} className="row g-2">
          <div className="col-md-4">
            <select className="form-select" name="donorId" value={form.donorId} onChange={handleChange} required>
              <option value="">Select Donor (must be screening-cleared)</option>
              {donors.filter(d => d.healthScreeningCleared).map((d) => (
                <option key={d.id} value={d.id}>{d.fullName}</option>
              ))}
            </select>
          </div>
          <div className="col-md-3">
            <input type="number" className="form-control" name="quantityMl" placeholder="Quantity (ml)"
              value={form.quantityMl} onChange={handleChange} required />
          </div>
          <div className="col-md-3">
            <input className="form-control" name="notes" placeholder="Notes" value={form.notes} onChange={handleChange} />
          </div>
          <div className="col-md-2">
            <button className="btn btn-primary w-100" type="submit">Add Donation</button>
          </div>
        </form>
      </div>

      <div className="card p-3">
        <table className="table table-hover align-middle">
          <thead><tr><th>Donor</th><th>Quantity (ml)</th><th>Date</th><th>Status</th><th>Notes</th><th>Actions</th></tr></thead>
          <tbody>
            {donations.map((d) => (
              <tr key={d.id}>
                <td>{d.donorName}</td>
                <td>{d.quantityMl}</td>
                <td>{new Date(d.donationDate).toLocaleString()}</td>
                <td><span className="badge bg-info text-dark">{d.status}</span></td>
                <td>{d.notes || '-'}</td>
                <td>
                  {d.status === 'PENDING' && (
                    <>
                      <button className="btn btn-sm btn-outline-success me-1" onClick={() => handleStatus(d.id, 'APPROVED')}>Approve</button>
                      <button className="btn btn-sm btn-outline-danger" onClick={() => handleStatus(d.id, 'REJECTED')}>Reject</button>
                    </>
                  )}
                  {d.status === 'APPROVED' && (
                    <button className="btn btn-sm btn-outline-primary" onClick={() => handleStatus(d.id, 'COLLECTED')}>Mark Collected</button>
                  )}
                </td>
              </tr>
            ))}
            {donations.length === 0 && <tr><td colSpan="6" className="text-center text-muted">No donations yet.</td></tr>}
          </tbody>
        </table>
      </div>
    </div>
  );
}
