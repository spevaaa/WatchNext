import { Routes, Route, Link } from 'react-router-dom';
import { useState } from 'react';
import { SeriesListComponent } from './components/seriesListComponent';
import { SeriesDetailComponent } from './components/seriesDetailComponent';
import { SeriesAddComponent } from './components/SeriesAddComponent';
import { useSeries } from './hooks/useSeries';
import { SeriesEditComponent } from './components/SeriesEditComponent';
import { type Series } from './types/series';

function App() {
    const { data, loading, error, refreshData, removeLocally } = useSeries();
    const [editingSeries, setEditingSeries] = useState<Series | null>(null);

    if (loading) return <div style={{ padding: '20px' }}>Učitavam...</div>;
    if (error) return <div style={{ padding: '20px', color: 'red' }}>Greška: {error}</div>;

    const handleDelete = async (title: string) => {
        if (!window.confirm(`Sigurno želite obrisati seriju "${title}"?`)) return;

        removeLocally(title);

        try {
            const response = await fetch(`http://localhost:8080/api/series/title/${encodeURIComponent(title)}`, {
                method: 'DELETE'
            });

            if (!response.ok) {
                throw new Error('Greška prilikom brisanja serije.');
            }
        } catch (err: any) {
            console.error(err);
            refreshData();
        }
    };

    const handleUpdate = async (originalTitle: string, updatedSeries: Series) => {
        try {
            const response = await fetch(`http://localhost:8080/api/series/title/${encodeURIComponent(originalTitle)}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(updatedSeries),
            });

            if (response.ok) {
                setEditingSeries(null);
                refreshData();
            } else {
                const errorData = await response.json();
                alert(`Greška: ${errorData.message}`);
            }
        } catch (err) {
            console.error("Update failed", err);
        }
    };

    return (
        <div style={{ width: '70%', margin: '0 auto' }}>
            <nav style={{ padding: '20px', backgroundColor: '#003363', marginBottom: '20px', borderRadius: '8px', width: '100%' }}>
                <Link to="/" style={{ color: 'white', marginRight: '20px', textDecoration: 'none' }}>Početna</Link>
                <Link to="/list" style={{ color: 'white', textDecoration: 'none' }}>Lista serija</Link>
            </nav>

            <Routes>
                <Route path="/" element={
                    <div style={{ textAlign: 'center', marginTop: '50px' }}>
                        <h2 style={{fontSize : '2rem'}}>Dobrodošli u WatchNext!</h2>
                        <Link to="/list" style={{ fontSize: '1.5rem', color: '#007bff', textDecoration: 'none' }}>Pogledaj listu serija</Link>
                    </div>
                } />

                <Route path="/list" element={
                    <>                        
                        <SeriesListComponent 
                            seriesList={data} 
                            onDelete={handleDelete} 
                            onUpdate={(series) => setEditingSeries(series)} 
                        />
                        {editingSeries && (
                            <SeriesEditComponent 
                                series={editingSeries} 
                                onSave={handleUpdate} 
                                onCancel={() => setEditingSeries(null)} 
                            />
                        )}

                        <SeriesAddComponent onSeriesAdded={refreshData} />
                    </>
                } />

                <Route path="/details/:id" element={
                    <SeriesDetailComponent seriesList={data} />
                } />
            </Routes>
        </div>
    );
}

export default App;