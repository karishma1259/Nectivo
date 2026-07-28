import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Navbar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  if (!user) return null;

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
      <div className="container">
        <Link className="navbar-brand" to="/">🍼 Nectivo</Link>
        <div className="collapse navbar-collapse">
          <ul className="navbar-nav me-auto">
            <li className="nav-item"><Link className="nav-link" to="/">Dashboard</Link></li>
            <li className="nav-item"><Link className="nav-link" to="/donors">Donors</Link></li>
            <li className="nav-item"><Link className="nav-link" to="/babies">Babies</Link></li>
            <li className="nav-item"><Link className="nav-link" to="/donations">Donations</Link></li>
            <li className="nav-item"><Link className="nav-link" to="/inventory">Inventory</Link></li>
            <li className="nav-item"><Link className="nav-link" to="/distribution">Distribution</Link></li>
          </ul>
          <span className="navbar-text text-white me-3">
            {user.name} ({user.role})
          </span>
          <button className="btn btn-outline-light btn-sm" onClick={handleLogout}>Logout</button>
        </div>
      </div>
    </nav>
  );
}
