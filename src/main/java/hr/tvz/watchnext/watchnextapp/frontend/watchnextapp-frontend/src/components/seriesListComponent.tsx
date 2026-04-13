import { type Series } from '../types/series';

interface SeriesListProps {
    seriesList: Series[];
    onSelectSeries: (title: string) => void;
}

export const SeriesListComponent = ({ seriesList, onSelectSeries }: SeriesListProps) => {
    return (
        <div className="series-list-container" style={{ padding: '20px' }}>
            <h2 style={{ textAlign: 'center', marginBottom: '20px' }}>Popis serija</h2>
            <table style={{ 
                width: '100%', 
                borderCollapse: 'collapse', 
                boxShadow: '0 2px 10px rgba(0,0,0,0.1)',
                borderRadius: '8px'
            }}>
                <thead>
                    <tr style={{ backgroundColor: '#007bff', borderBottom: '2px solid #ddd' }}>
                        <th style={{ padding: '12px', textAlign: 'center', color: 'white' }}>Naslov</th>
                        <th style={{ padding: '12px', textAlign: 'center', color: 'white' }}>Žanr</th>
                        <th style={{ padding: '12px', textAlign: 'center', color: 'white' }}>Broj sezona</th>
                        <th style={{ padding: '12px', textAlign: 'center', color: 'white' }}>Akcija</th>
                    </tr>
                </thead>
                <tbody>
                    {seriesList.map((series) => (
                        <tr key={series.title} style={{ borderBottom: '1px solid #eee', color: 'white' }}>
                            <td style={{ padding: '12px' }}>{series.title}</td>
                            <td style={{ padding: '12px' }}>{series.genre}</td>
                            <td style={{ padding: '12px', textAlign: 'center' }}>
                                {series.totalSeasons}
                            </td>
                            <td style={{ padding: '12px', textAlign: 'center' }}>
                                <button 
                                    onClick={() => onSelectSeries(series.title)}
                                    style={{
                                        padding: '6px 12px',
                                        cursor: 'pointer',
                                        backgroundColor: '#007bff',
                                        color: 'white',
                                        border: 'none',
                                        borderRadius: '4px'
                                    }}
                                >
                                    Detalji
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};