import { useState } from 'react';
import { type Series } from '../types/series';
import { useNavigate, useParams } from 'react-router-dom';

interface SeriesDetailProps {
    seriesList: Series[];
    onStatusUpdate: () => void;
}

export const SeriesDetailComponent = ({ seriesList, onStatusUpdate }: SeriesDetailProps) => {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();
    const selectedSeries = seriesList.find(s => s.title === id);
    const [newStatus, setNewStatus] = useState(selectedSeries?.status ?? 'WATCHING');


    if (!selectedSeries) {
        return (
            <div style={{ padding: '20px', textAlign: 'center' }}>
                <h3>Serija nije pronađena!</h3>
                <button onClick={() => navigate('/list')} style={backButtonStyle}>
                    Povratak na listu
                </button>
            </div>
        );
    }

        const handleStatusUpdate = async () => {
            const response = await fetch(
                `http://localhost:8080/api/series/${selectedSeries.id}/status?status=${newStatus}`,
                { method: 'PATCH' }
            );
        if (response.ok) {
            alert('Status uspješno promijenjen!');
            onStatusUpdate();
        }
};


    return (
        <div style={{
            width: '100%',
            marginTop: '30px',
            borderRadius: '8px',
            overflow: 'hidden',
            boxShadow: '0 4px 15px rgba(0,0,0,0.1)',
            backgroundColor: 'white',
            border: '1px solid #ddd'
        }}>
            <div style={{ 
                backgroundColor: '#007bff', 
                color: 'white', 
                padding: '15px 20px',
                fontSize: '1.2rem',
                fontWeight: 'bold',
                display: 'flex',
                alignItems: 'center',
                gap: '12px'
            }}>
                <button onClick={() => navigate('/list')} style={inlineBackButtonStyle}>←</button>
                <span>{selectedSeries.title}</span>
            </div>

            <div style={{ padding: '20px' }}>
                <div style={detailRowStyle}>
                    <strong>Žanr:</strong>
                    <span style={badgeStyle}>{selectedSeries.genre}</span>
                </div>

                <div style={detailRowStyle}>
                    <strong>Broj sezona:</strong>
                    <span>{selectedSeries.totalSeasons}</span>
                </div>

                <div style={detailRowStyle}>
                    <strong>Status:</strong>
                    <span style={{ 
                        color: selectedSeries.status === 'COMPLETED' ? '#28a745' : 
                               selectedSeries.status === 'WATCHING'  ? '#007bff' : '#ffc107',
                        fontWeight: 'bold' 
                    }}>
                        {selectedSeries.status}
                    </span>
                </div>

                <div style={detailRowStyle}>
                    <strong>IMDB Rating:</strong>
                    <span style={{ color: '#007bff', fontWeight: 'bold' }}>
                        {selectedSeries.imdbRating ?? '-'} / 10
                    </span>
                </div>

                <div style={{ ...detailRowStyle, borderBottom: 'none' }}>
                    <strong>IMDB ID:</strong>
                    {selectedSeries.imdbId ? (
                        <a 
                            href={`https://www.imdb.com/title/${selectedSeries.imdbId}`} 
                            target="_blank" 
                            rel="noreferrer"
                            style={{ color: '#f5c518', fontWeight: 'bold' }}
                        >
                            {selectedSeries.imdbId} ↗
                        </a>
                    ) : (
                        <span style={{ color: '#aaa' }}>—</span>
                    )}
                </div>
                <div style={{ marginTop: '20px', display: 'flex', alignItems: 'center', gap: '12px' }}>
                    <strong>Promijeni status:</strong>
                    <select
                        value={newStatus}
                        onChange={e => setNewStatus(e.target.value)}
                        style={{ padding: '8px', borderRadius: '4px', border: '1px solid #ccc' }}
                    >
                        <option value="WATCHING">WATCHING</option>
                        <option value="PLANNED">PLANNED</option>
                        <option value="COMPLETED">COMPLETED</option>
                    </select>
                    <button onClick={handleStatusUpdate} style={{
                        padding: '8px 16px',
                        backgroundColor: '#007bff',
                        color: 'white',
                        border: 'none',
                        borderRadius: '4px',
                        cursor: 'pointer',
                        fontWeight: 'bold'
                    }}>
                        Spremi status
                    </button>
                </div>
            </div>
        </div>
    );
};

const detailRowStyle: React.CSSProperties = {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: '12px 0',
    borderBottom: '1px solid #eee',
    fontSize: '1rem',
    color: '#333'
};

const badgeStyle: React.CSSProperties = {
    backgroundColor: '#e9ecef',
    padding: '2px 8px',
    borderRadius: '4px',
    fontSize: '0.9rem'
};

const inlineBackButtonStyle: React.CSSProperties = {
    backgroundColor: 'rgba(255,255,255,0.2)',
    border: 'none',
    color: 'white',
    padding: '5px 12px',
    borderRadius: '4px',
    cursor: 'pointer',
    fontSize: '1.2rem',
};

const backButtonStyle: React.CSSProperties = {
    padding: '10px 20px',
    backgroundColor: '#007bff',
    color: 'white',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
    fontWeight: 'bold',
    marginTop: '10px'
};