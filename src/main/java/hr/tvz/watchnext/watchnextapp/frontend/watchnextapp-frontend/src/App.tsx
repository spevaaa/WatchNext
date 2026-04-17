import { Routes, Route, Link } from 'react-router-dom';
import { SeriesListComponent } from './components/seriesListComponent';
import { SeriesDetailComponent } from './components/seriesDetailComponent';
import { SeriesAddComponent } from './components/SeriesAddComponent';
import { useSeries } from './hooks/useSeries';

function App() {
    const { data, loading, error, refreshData, removeLocally } = useSeries();

    if (loading) return <div style={{ padding: '20px' }}>Učitavam...</div>;
    if (error) return <div style={{ padding: '20px', color: 'red' }}>Greška: {error}</div>;

    const handleDelete = async (title: string) => {
        if (!window.confirm(`Sigurno želite obrisati seriju "${title}"?`)) return;

        removeLocally(title);

        try {
            const response = await fetch(`http://localhost:8080/api/series/title/${title}`, {
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


    return (
        <div style={{ width: '55%', margin: '0 auto' }}>
            <nav style={{ padding: '20px', backgroundColor: '#003363', marginBottom: '20px', borderRadius: '8px' }}>
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
                        <SeriesListComponent seriesList={data} onDelete={handleDelete} />
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