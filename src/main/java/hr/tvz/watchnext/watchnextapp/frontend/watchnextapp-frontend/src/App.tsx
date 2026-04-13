import { useState } from 'react';
import { useSeries } from './hooks/useSeries';
import { SeriesListComponent } from './components/seriesListComponent';
import { SeriesDetailComponent } from './components/seriesDetailComponent';

function App() {
    const { data, loading, error } = useSeries();
    const [selectedSeriesId, setSelectedSeriesId] = useState<string | null>(null);

    if (loading) return <div>Učitavam podatke...</div>;
    if (error) return <div>Greška: {error}</div>;

    return (
        <div style={{ padding: '20px', margin: '0 auto' }}>
            
            <SeriesListComponent 
                seriesList={data} 
                onSelectSeries={(title) => setSelectedSeriesId(title)} 
            />

            {selectedSeriesId && (
                <SeriesDetailComponent 
                    selectedSeriesId={selectedSeriesId} 
                    seriesList={data} 
                />
            )}
        </div>
    );
}

export default App;