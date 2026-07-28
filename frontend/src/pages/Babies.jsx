import React, { useEffect, useState } from 'react';
import { getBabies, createBaby, deleteBaby } from '../services/babyService';

const emptyForm = { babyName: '', dateOfBirth: '', birthWeightKg: '', parentName: '', parentContact: '', nicuAdmitted: false, diagnosisNotes: '' };

export default function Babies() {
  const [babies, setBabies] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [error, setError] = useState('');

  const load = () => getBabies().then((res) => setBabies(res.data)).catch(() => setError('Failed to load babies'));

  useEffect(() => { load(); }, []);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setForm({ ...form, [name]: type === 'checkbox' ? checked : value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await createBaby({ ...form, birthWeightKg: form.birthWeightKg ? Number(form.birthWeightKg) : null });
      setForm(emptyForm);
      load();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to register baby');
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Remove this baby record?')) return;
    await deleteBaby(id);
    load();
  };

  return (
    <div className="container mt-4">
      <h3 className="mb-4">Baby Management</h3>
      {error && <div className="alert alert-danger">{error}</div>}

      <div className="card p-3 mb-4">
        <h6>Register Baby</h6>
        <form onSubmit={handleSubmit} className="row g-2">
          <div className="col-md-2"><input className="form-control" name="babyName" placeholder="Baby Name" value={form.babyName} onChange={handleChange} required /></div>
          <div className="col-md-2"><input type="date" className="form-control" name="dateOfBirth" value={form.dateOfBirth} onChange={handleChange} /></div>
          <div className="col-md-1"><input type="number" step="0.1" className="form-control" name="birthWeightKg" placeholder="Kg" value={form.birthWeightKg} onChange={handleChange} /></div>
          <div className="col-md-2"><input className="form-control" name="parentName" placeholder="Parent Name" value={form.parentName} onChange={handleChange} required /></div>
          <div className="col-md-2"><input className="form-control" name="parentContact" placeholder="Contact" value={form.parentContact} onChange={handleChange} required /></div>
          <div className="col-md-1 form-check mt-2">
            <input type="checkbox" className="form-check-input" name="nicuAdmitted" checked={form.nicuAdmitted} onChange={handleChange} />
            <label className="form-check-label">NICU</label>
          </div>
          <div className="col-md-2"><button className="btn btn-primary w-100" type="submit">Register</button></div>
        </form>
      </div>

      <div className="card p-3">
        <table className="table table-hover align-middle">
          <thead>
            <tr><th>Name</th><th>DOB</th><th>Weight (kg)</th><th>Parent</th><th>Contact</th><th>NICU</th><th>Actions</th></tr>
          </thead>
          <tbody>
            {babies.map((b) => (
              <tr key={b.id}>
                <td>{b.babyName}</td>
                <td>{b.dateOfBirth || '-'}</td>
                <td>{b.birthWeightKg ?? '-'}</td>
                <td>{b.parentName}</td>
                <td>{b.parentContact}</td>
                <td>{b.nicuAdmitted ? <span className="badge bg-danger">Yes</span> : <span className="badge bg-secondary">No</span>}</td>
                <td><button className="btn btn-sm btn-outline-danger" onClick={() => handleDelete(b.id)}>Remove</button></td>
              </tr>
            ))}
            {babies.length === 0 && <tr><td colSpan="7" className="text-center text-muted">No babies registered yet.</td></tr>}
          </tbody>
        </table>
      </div>
    </div>
  );
}
