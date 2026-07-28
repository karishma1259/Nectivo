import React, { useEffect, useState } from 'react';
import { getDistributions, distributeBottle } from '../services/distributionService';
import { getAvailableBottles } from '../services/inventoryService';
import { getBabies } from '../services/babyService';

export default function Distribution() {
  const [distributions, setDistributions] = useState([]);
  const [bottles, setBottles] = useState([]);
  const [babies, setBabies] = useState([]);
  const [form, setForm] = useState({ bottleId: '', babyId: '', approvedByDoctor: '', remarks: '' });
  const [error, setError] = useState('');

  const load = () => {
    getDistributions().then((res) => setDistributions(res.data)).catch(() => setError('Failed to load distributions'));
    getAvailableBottles().then((res) => setBottles(res.data));
    getBabies().then((res) => setBabies(res.data));
  };

  useEffect(() => { load(); }, []);

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await distributeBottle({ ...form, bottleId: Number(form.bottleId), babyId: Number(form.babyId) });
      setForm({ bottleId: '', babyId: '', approvedByDoctor: '', remarks: '' });
      load();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to distribute bottle');
    }
  };

  return (
    <div className="container mt-4">
      <h3 className="mb-4">Milk Distribution & Feeding</h3>
      {error && <div className="alert alert-danger">{error}</div>}

      <div className="card p-3 mb-4">
        <h6>Issue Bottle to Baby</h6>
        <form onSubmit={handleSubmit} className="row g-2">
          <div className="col-md-3">
            <select className="form-select" name="bottleId" value={form.bottleId} onChange={handleChange} required>
              <option value="">Select Available Bottle</option>
              {bottles.map((b) => <option key={b.id} value={b.id}>{b.bottleCode} ({b.quantityMl}ml)</option>)}
            </select>
          </div>
          <div className="col-md-3">
            <select className="form-select" name="babyId" value={form.babyId} onChange={handleChange} required>
              <option value="">Select Baby</option>
              {babies.map((b) => <option key={b.id} value={b.id}>{b.babyName}</option>)}
            </select>
          </div>
          <div className="col-md-3">
            <input className="form-control" name="approvedByDoctor" placeholder="Approved by Dr." value={form.approvedByDoctor} onChange={handleChange} required />
          </div>
          <div className="col-md-3">
            <input className="form-control" name="remarks" placeholder="Remarks" value={form.remarks} onChange={handleChange} />
          </div>
          <div className="col-md-2 mt-2">
            <button className="btn btn-primary w-100" type="submit">Distribute</button>
          </div>
        </form>
      </div>

      <div className="card p-3">
        <table className="table table-hover align-middle">
          <thead><tr><th>Bottle</th><th>Baby</th><th>Approved By</th><th>Date</th><th>Remarks</th></tr></thead>
          <tbody>
            {distributions.map((d) => (
              <tr key={d.id}>
                <td><code>{d.bottleCode}</code></td>
                <td>{d.babyName}</td>
                <td>{d.approvedByDoctor}</td>
                <td>{new Date(d.distributedAt).toLocaleString()}</td>
                <td>{d.remarks || '-'}</td>
              </tr>
            ))}
            {distributions.length === 0 && <tr><td colSpan="5" className="text-center text-muted">No distributions yet.</td></tr>}
          </tbody>
        </table>
      </div>
    </div>
  );
}
