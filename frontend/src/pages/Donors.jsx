import React, { useEffect, useState } from 'react';
import { getDonors, createDonor, clearScreening, deleteDonor } from '../services/donorService';

const emptyForm = { fullName: '', bloodGroup: '', dateOfBirth: '', address: '' };

export default function Donors() {
  const [donors, setDonors] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [error, setError] = useState('');

  const load = () => getDonors().then((res) => setDonors(res.data)).catch(() => setError('Failed to load donors'));

  useEffect(() => { load(); }, []);

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await createDonor(form);
      setForm(emptyForm);
      load();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to create donor');
    }
  };

  const handleClearScreening = async (id) => {
    await clearScreening(id);
    load();
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Deactivate this donor?')) return;
    await deleteDonor(id);
    load();
  };

  return (
    <div className="container mt-4">
      <h3 className="mb-4">Donor Management</h3>
      {error && <div className="alert alert-danger">{error}</div>}

      <div className="card p-3 mb-4">
        <h6>Register New Donor</h6>
        <form onSubmit={handleSubmit} className="row g-2">
          <div className="col-md-3">
            <input className="form-control" name="fullName" placeholder="Full Name"
              value={form.fullName} onChange={handleChange} required />
          </div>
          <div className="col-md-2">
            <input className="form-control" name="bloodGroup" placeholder="Blood Group"
              value={form.bloodGroup} onChange={handleChange} />
          </div>
          <div className="col-md-2">
            <input type="date" className="form-control" name="dateOfBirth"
              value={form.dateOfBirth} onChange={handleChange} />
          </div>
          <div className="col-md-3">
            <input className="form-control" name="address" placeholder="Address"
              value={form.address} onChange={handleChange} />
          </div>
          <div className="col-md-2">
            <button className="btn btn-primary w-100" type="submit">Add Donor</button>
          </div>
        </form>
      </div>

      <div className="card p-3">
        <table className="table table-hover align-middle">
          <thead>
            <tr>
              <th>Name</th><th>Blood Group</th><th>Address</th><th>Screening</th><th>Status</th><th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {donors.map((d) => (
              <tr key={d.id}>
                <td>{d.fullName}</td>
                <td>{d.bloodGroup || '-'}</td>
                <td>{d.address || '-'}</td>
                <td>
                  {d.healthScreeningCleared
                    ? <span className="badge bg-success">Cleared</span>
                    : <span className="badge bg-warning text-dark">Pending</span>}
                </td>
                <td>{d.active ? <span className="badge bg-primary">Active</span> : <span className="badge bg-secondary">Inactive</span>}</td>
                <td>
                  {!d.healthScreeningCleared && (
                    <button className="btn btn-sm btn-outline-success me-2" onClick={() => handleClearScreening(d.id)}>
                      Clear Screening
                    </button>
                  )}
                  <button className="btn btn-sm btn-outline-danger" onClick={() => handleDelete(d.id)}>Deactivate</button>
                </td>
              </tr>
            ))}
            {donors.length === 0 && (
              <tr><td colSpan="6" className="text-center text-muted">No donors yet.</td></tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
