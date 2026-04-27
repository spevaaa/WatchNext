import { type Series } from '../types/series';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
    
interface Props {
    seriesList: Series[];
    onDelete: (title: string) => void;
    onUpdate: (series: Series) => void;
    onDeleteByStatus: (status: string) => void;
}

export const SeriesListComponent = ({ seriesList, onDelete, onUpdate, onDeleteByStatus }: Props) => {
    const navigate = useNavigate();
    const [statusToDelete, setStatusToDelete] = useState('COMPLETED');

    return (
        <div style={{ width: '100%', padding: '20px' }}>
            <h2 style={{ textAlign: 'center', marginBottom: '20px' }}>Popis serija</h2>
            <div style={{ display: 'flex', alignItems: 'center', gap: '12px', marginBottom: '16px' }}>
                <strong style={{ color: 'white' }}>Obriši po statusu:</strong>
                <select
                    value={statusToDelete}
                    onChange={e => setStatusToDelete(e.target.value)}
                    style={{ padding: '6px 10px', borderRadius: '4px', border: '1px solid #ccc' }}
                >
                    <option value="WATCHING">WATCHING</option>
                    <option value="PLANNED">PLANNED</option>
                    <option value="COMPLETED">COMPLETED</option>
                </select>
                <button
                    onClick={() => {
                        if (window.confirm(`Sigurno želiš obrisati sve ${statusToDelete} serije?`)) {
                            onDeleteByStatus(statusToDelete);
                        }
                    }}
                    style={btnStyle('#ff0000')}
                >
                    Obriši sve
                </button>
            </div>
            <table style={{ 
                width: '100%', 
                borderCollapse: 'collapse', 
                boxShadow: '0 2px 10px rgba(0,0,0,0.1)',
            }}>
                <thead>
                    <tr style={{ backgroundColor: '#007bff' }}>
                        <th style={thStyle}>Naslov</th>
                        <th style={thStyle}>Žanr</th>
                        <th style={thStyle}>Sezone</th>
                        <th style={thStyle}>Status</th>
                        <th style={thStyle}>Akcija</th>
                    </tr>
                </thead>
                <tbody>
                    {seriesList.map((series) => (
                        <tr key={series.title} style={{ borderBottom: '1px solid #eee' }}>
                            <td style={tdStyle}>{series.title}</td>
                            <td style={{ ...tdStyle, textAlign: 'center' }}>{series.genre}</td>
                            <td style={{ ...tdStyle, textAlign: 'center' }}>{series.totalSeasons}</td>
                            <td style={{ ...tdStyle, textAlign: 'center' }}>{series.status}</td>
                            <td style={{ ...tdStyle, textAlign: 'center', whiteSpace: 'nowrap' }}>
                                <button onClick={() => navigate(`/details/${series.title}`)} style={btnStyle('#007bff')}>Detalji</button>
                                <button onClick={() => onUpdate(series)} style={btnStyle('#018921')}>Uredi</button>
                                <button onClick={() => onDelete(series.title)} style={btnStyle('#ff0000')}>Obriši</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

const thStyle: React.CSSProperties = {
    padding: '12px 16px',
    textAlign: 'left',
    color: 'white',
};

const tdStyle: React.CSSProperties = {
    padding: '12px 16px',
    color: 'white',
};

const btnStyle = (color: string): React.CSSProperties => ({
    padding: '5px 10px',
    marginLeft: '6px',
    cursor: 'pointer',
    backgroundColor: color,
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    fontSize: '0.85rem',
});