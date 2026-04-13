import { type Series } from '../types/series';
import { SeriesReviewComponent } from './SeriesReviewComponent';
import { mockReview } from '../data/mockReview';

interface SeriesDetailProps {
    selectedSeriesId: string | null;
    seriesList: Series[];
}

export const SeriesDetailComponent = ({ selectedSeriesId, seriesList }: SeriesDetailProps) => {
    const selectedSeries = seriesList.find(s => s.title === selectedSeriesId);

    if (!selectedSeries) {
        return null; 
    }

    const filteredReviews = mockReview.filter(r => r.seriesTitle === selectedSeries.title);

    return (
        <div style={{ 
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
                fontWeight: 'bold'
            }}>
                {selectedSeries.title}
            </div>

            <div style={{ padding: '20px' }}>
                <div style={detailRowStyle}>
                    <strong>Žanr:</strong> 
                    <span style={badgeStyle}>{selectedSeries.genre}</span>
                </div>
                
                <div style={detailRowStyle}>
                    <strong>Broj sezona:</strong> {selectedSeries.totalSeasons}
                </div>

                <div style={detailRowStyle}>
                    <strong>Status:</strong> 
                    <span style={{ 
                        color: selectedSeries.status === 'FINISHED' ? '#28a745' : '#ffc107',
                        fontWeight: 'bold' 
                    }}>
                        {selectedSeries.status}
                    </span>
                </div>

                <div style={{ ...detailRowStyle, borderBottom: 'none' }}>
                    <strong>Prosječna ocjena:</strong> 
                    <span style={{ fontSize: '1.1rem', color: '#007bff', fontWeight: 'bold' }}>
                        {selectedSeries.averageRating} / 10
                    </span>
                </div>
            </div>

            <div style={{ padding: '20px', borderTop: '1px solid #eee' }}>
                <SeriesReviewComponent reviews={filteredReviews} />
            </div>
        </div>
            
    );
    
};


const detailRowStyle: React.CSSProperties = {
    display: 'flex',
    justifyContent: 'space-between',
    padding: '10px 0',
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