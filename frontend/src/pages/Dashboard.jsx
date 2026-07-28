import React, { useEffect, useState } from 'react';
import { PieChart, Pie, Cell, Tooltip, ResponsiveContainer, BarChart, Bar, XAxis, YAxis, CartesianGrid, Legend } from 'recharts';
import { getDashboardStats } from '../services/dashboardService';

const COLORS = ['#0d6efd', '#dc3545', '#198754'];

export default function Dashboard() {
  const [stats, setStats] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    getDashboardStats()
      .then((res) => setStats(res.data))
      .catch(() => setError('Could not load dashboard stats.'));
  }, []);

  if (error) return <div className="container mt-4"><div className="alert alert-danger">{error}</div></div>;
  if (!stats) return <div className="container mt-4">Loading dashboard...</div>;

  const bottleData = [
    { name: 'Available', value: stats.availableBottles },
    { name: 'Expired', value: stats.expiredBottles },
    { name: 'Distributed', value: stats.distributedBottles },
  ];

  const milkData = [
    { name: 'Collected (ml)', value: stats.totalMilkCollectedMl },
    { name: 'Distributed (ml)', value: stats.totalMilkDistributedMl },
  ];

  const cards = [
    { label: 'Total Donors', value: stats.totalDonors, color: '#0d6efd' },
    { label: 'Total Babies', value: stats.totalBabies, color: '#20c997' },
    { label: 'Total Donations', value: stats.totalDonations, color: '#fd7e14' },
    { label: 'Available Bottles', value: stats.availableBottles, color: '#198754' },
  ];

  return (
    <div className="container mt-4">
      <h3 className="mb-4">Dashboard Overview</h3>
      <div className="row g-3 mb-4">
        {cards.map((c) => (
          <div className="col-md-3" key={c.label}>
            <div className="stat-card" style={{ backgroundColor: c.color }}>
              <div className="fs-2 fw-bold">{c.value}</div>
              <div>{c.label}</div>
            </div>
          </div>
        ))}
      </div>

      <div className="row g-3">
        <div className="col-md-6">
          <div className="card p-3">
            <h6>Bottle Status</h6>
            <ResponsiveContainer width="100%" height={260}>
              <PieChart>
                <Pie data={bottleData} dataKey="value" nameKey="name" outerRadius={90} label>
                  {bottleData.map((entry, index) => (
                    <Cell key={index} fill={COLORS[index % COLORS.length]} />
                  ))}
                </Pie>
                <Tooltip />
                <Legend />
              </PieChart>
            </ResponsiveContainer>
          </div>
        </div>
        <div className="col-md-6">
          <div className="card p-3">
            <h6>Milk Collected vs Distributed (ml)</h6>
            <ResponsiveContainer width="100%" height={260}>
              <BarChart data={milkData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Bar dataKey="value" fill="#0d6efd" radius={[6, 6, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </div>
        </div>
      </div>
    </div>
  );
}
