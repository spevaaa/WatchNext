// src/pages/LoginPage.tsx
import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';

export const LoginPage: React.FC = () => {
  const { login } = useAuth();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setLoading(true);

    try {
      const response = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
      });

      if (!response.ok) {
        throw new Error('Neispravno korisničko ime ili lozinka');
      }

      const data = await response.json();
      login(data.username, data.token, data.role);
    } catch (err: any) {
      setError(err.message || 'Došlo je do pogreške pri prijavi.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ width: '70%', margin: '0 auto', marginTop: '50px' }}>
      {/* Gornji panel/naslov u stilu navigacije iz App.tsx */}
      <div style={{ 
        padding: '20px', 
        backgroundColor: '#003363', 
        color: 'white', 
        borderRadius: '8px 8px 0 0', 
        textAlign: 'center' 
      }}>
        <h2 style={{ margin: 0, fontSize: '2rem' }}>WatchNext</h2>
        <p style={{ margin: '5px 0 0 0', fontSize: '0.9rem', color: '#ffc107' }}>
          Prijava u sustav serija
        </p>
      </div>

      {/* Tijelo forme s okvirima i gumbima u stilu aplikacije */}
      <div style={{ 
        border: '1px solid #003363', 
        borderRadius: '0 0 8px 8px', 
        padding: '30px', 
        backgroundColor: '#f8f9fa' 
      }}>
        
        {error && (
          <div style={{ 
            padding: '12px', 
            backgroundColor: '#f8d7da', 
            color: '#721c24', 
            border: '1px solid #f5c6cb', 
            borderRadius: '4px', 
            marginBottom: '20px',
            fontSize: '0.9rem'
          }}>
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
          <div style={{ display: 'flex', flexDirection: 'column', gap: '6px' }}>
            <label style={{ fontWeight: 'bold', fontSize: '0.9rem', color: '#333' }}>
              Korisničko ime:
            </label>
            <input
              type="text"
              required
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="Npr. admin"
              style={{
                padding: '10px',
                border: '1px solid #ccc',
                borderRadius: '4px',
                fontSize: '1rem'
              }}
            />
          </div>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '6px' }}>
            <label style={{ fontWeight: 'bold', fontSize: '0.9rem', color: '#333' }}>
              Lozinka:
            </label>
            <input
              type="password"
              required
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="••••••••"
              style={{
                padding: '10px',
                border: '1px solid #ccc',
                borderRadius: '4px',
                fontSize: '1rem'
              }}
            />
          </div>

          <button
            type="submit"
            disabled={loading}
            style={{
              padding: '12px',
              backgroundColor: loading ? '#6c757d' : '#007bff',
              color: 'white',
              border: 'none',
              borderRadius: '4px',
              cursor: loading ? 'not-allowed' : 'pointer',
              fontSize: '1rem',
              fontWeight: 'bold',
              transition: 'background-color 0.2s'
            }}
          >
            {loading ? 'Prijava u tijeku...' : 'Prijavi se'}
          </button>
        </form>
      </div>
    </div>
  );
};