import { useState } from 'react';

interface LoginProps {
    onLogin: (token: string, username: string, role: string) => void;
}

export const LoginComponent = ({ onLogin }: LoginProps) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError('');

        try {
            const response = await fetch('http://localhost:8080/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                const data = await response.json();
                onLogin(data.token, data.username, data.role);
            } else {
                setError('Pogrešno korisničko ime ili lozinka.');
            }
        } catch (err) {
            setError('Greška pri spajanju na server.');
        }
    };

    return (
        <div style={containerStyle}>
            <div style={headerStyle}>WatchNext — Prijava</div>
            <form onSubmit={handleSubmit} style={formStyle}>
                <div style={inputGroupStyle}>
                    <label style={labelStyle}>Korisničko ime</label>
                    <input
                        type="text"
                        value={username}
                        onChange={e => setUsername(e.target.value)}
                        required
                        style={inputStyle}
                        placeholder="user/admin"
                    />
                </div>
                <div style={inputGroupStyle}>
                    <label style={labelStyle}>Lozinka</label>
                    <input
                        type="password"
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                        required
                        style={inputStyle}
                        placeholder="password"
                    />
                </div>
                {error && <p style={{ color: 'red', fontSize: '0.9rem' }}>{error}</p>}
                <button type="submit" style={buttonStyle}>Prijava</button>
            </form>
        </div>
    );
};

const containerStyle: React.CSSProperties = {
    width: '400px',
    margin: '100px auto',
    borderRadius: '8px',
    overflow: 'hidden',
    boxShadow: '0 4px 15px rgba(0,0,0,0.2)',
    backgroundColor: '#ffffff',
    border: '1px solid #ddd'
};

const headerStyle: React.CSSProperties = {
    backgroundColor: '#007bff',
    color: 'white',
    padding: '16px 20px',
    fontSize: '1.2rem',
    fontWeight: 'bold',
    textAlign: 'center'
};

const formStyle: React.CSSProperties = {
    padding: '24px',
    display: 'flex',
    flexDirection: 'column',
    gap: '14px'
};

const inputGroupStyle: React.CSSProperties = {
    display: 'flex',
    flexDirection: 'column',
    gap: '5px'
};

const labelStyle: React.CSSProperties = {
    fontSize: '0.9rem',
    fontWeight: 'bold',
    color: '#555'
};

const inputStyle: React.CSSProperties = {
    padding: '10px',
    borderRadius: '5px',
    border: '1px solid #ccc',
    fontSize: '1rem',
    outline: 'none',
    backgroundColor: '#f8f9fa',
    color: 'black'
};

const buttonStyle: React.CSSProperties = {
    padding: '12px',
    backgroundColor: '#007bff',
    color: 'white',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
    fontSize: '1rem',
    fontWeight: 'bold'
};