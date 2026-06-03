import { Routes, Route, Link, Navigate } from 'react-router-dom';
import { useState } from 'react';
import { SeriesListComponent } from './components/seriesListComponent';
import { SeriesDetailComponent } from './components/seriesDetailComponent';
import { SeriesAddComponent } from './components/SeriesAddComponent';
import { useSeries } from './hooks/useSeries';
import { SeriesEditComponent } from './components/SeriesEditComponent';
import { type Series } from './types/series';
import { LoginPage } from './pages/LoginPage';
import { useAuth } from './context/AuthContext';
import { ProtectedRoute } from './components/ProtectedRoute';
import API from './api/axiosConfig';

function App() {
    const { user: username, role, logout, isAuthenticated } = useAuth();
    const { data, loading, error, refreshData, removeLocally } = useSeries();
    const [editingSeries, setEditingSeries] = useState<Series | null>(null);

    if (loading) return <div style={{ padding: '20px', color: 'white' }}>Učitavam...</div>;
    if (error) return <div style={{ padding: '20px', color: 'red' }}>Greška: {error}</div>;

    const handleDelete = async (title: string) => {
        if (!window.confirm(`Sigurno želite obrisati seriju "${title}"?`)) return;

        removeLocally(title);

        try {
            await API.delete(`/series/title/${encodeURIComponent(title)}`);
        } catch (err: any) {
            console.error("Greška pri brisanju:", err);
            refreshData();
        }
    };

    const handleUpdate = async (originalTitle: string, updatedSeries: Series) => {
        try {
            await API.put(`/series/title/${encodeURIComponent(originalTitle)}`, updatedSeries);
            setEditingSeries(null);
            refreshData();
        } catch (err: any) {
            console.error("Update failed", err);
            const message = err.response?.data?.message || "Došlo je do pogreške.";
            alert(`Greška: ${message}`);
        }
    };

    const handleDeleteByStatus = async (status: string) => {
        try {
            await API.delete(`/series/status/${status}`);
            refreshData();
        } catch (err) {
            console.error("Delete by status failed", err);
        }
    };

    return (
        <div style={{ width: '70%', margin: '0 auto' }}>
            {isAuthenticated && (
                <nav style={{ padding: '20px', backgroundColor: '#003363', marginBottom: '20px', borderRadius: '8px', width: '100%' }}>
                    <Link to="/" style={{ color: 'white', marginRight: '20px', textDecoration: 'none' }}>Početna</Link>
                    <Link to="/list" style={{ color: 'white', textDecoration: 'none' }}>Lista serija</Link>
                    <br /><br />
                    <div style={{ display: 'flex', alignItems: 'center', gap: '12px', width: '70%', margin: '0 auto', justifyContent: 'center'}}>
                        <span style={{ color: 'white', fontSize: '0.9rem' }}>
                             {username} — <span style={{ color: '#ffc107' }}>{role}</span>
                        </span>
                        <button 
                            onClick={logout} 
                            style={{
                                padding: '6px 14px',
                                backgroundColor: '#ff0000',
                                color: 'white',
                                border: 'none',
                                borderRadius: '4px',
                                cursor: 'pointer',
                                fontSize: '0.85rem'
                            }}
                        >
                            Odjava
                        </button>
                    </div>
                </nav>
            )}

            <Routes>
                <Route path="/login" element={!isAuthenticated ? <LoginPage /> : <Navigate to="/" replace />} />

                <Route path="/" element={
                    <ProtectedRoute>
                        <div style={{ textAlign: 'center', marginTop: '50px', color: 'white' }}>
                            <h2 style={{ fontSize: '2rem' }}>Dobrodošli u WatchNext!</h2>
                            <Link to="/list" style={{ fontSize: '1.5rem', color: '#007bff', textDecoration: 'none' }}>Pogledaj listu serija</Link>
                        </div>
                    </ProtectedRoute>
                } />

                <Route path="/list" element={
                    <ProtectedRoute>
                        <SeriesListComponent 
                            seriesList={data} 
                            onDelete={handleDelete} 
                            onUpdate={(series) => setEditingSeries(series)} 
                            onDeleteByStatus={handleDeleteByStatus}
                        />
                        {editingSeries && (
                            <SeriesEditComponent 
                                series={editingSeries} 
                                onSave={handleUpdate} 
                                onCancel={() => setEditingSeries(null)} 
                            />
                        )}

                        {role === 'ROLE_ADMIN' && <SeriesAddComponent onSeriesAdded={refreshData} />}
                    </ProtectedRoute>
                } />

                <Route path="/details/:id" element={
                    <ProtectedRoute>
                        <SeriesDetailComponent seriesList={data} onStatusUpdate={refreshData} />
                    </ProtectedRoute>
                } />

                <Route path="*" element={<Navigate to="/" replace />} />
            </Routes>
        </div>
    );
}

export default App;