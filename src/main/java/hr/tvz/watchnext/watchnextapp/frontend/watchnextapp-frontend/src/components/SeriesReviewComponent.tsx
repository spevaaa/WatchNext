import { type Review } from '../types/review';

interface Props {
    reviews: Review[];
}

export const SeriesReviewComponent = ({ reviews }: Props) => {
    return (
        <div style={{ marginTop: '20px' }}>
            <h4 style={{ color: '#007bff', borderBottom: '1px solid #007bff', paddingBottom: '5px' }}>
                Recenzije korisnika
            </h4>
            {reviews.length === 0 ? (
                <p style={{ fontStyle: 'italic', color: '#888' }}>Nema recenzija za ovu seriju.</p>
            ) : (
                reviews.map(rev => (
                    <div key={rev.id} style={{ 
                        backgroundColor: '#f8f9fa', 
                        padding: '10px', 
                        borderRadius: '5px', 
                        marginBottom: '10px',
                        borderLeft: '4px solid #007bff'
                    }}>
                        <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '5px' }}>
                            <span style={{color: 'black', fontWeight: 'bold'}}>{rev.author}</span>
                            <span style={{ fontWeight: 'bold', color: '#007bff' }}>{rev.score}/10</span>
                        </div>
                        <p style={{ margin: 0, fontSize: '0.9rem', color: 'black' }}>"{rev.comment}"</p>
                    </div>
                ))
            )}
        </div>
    );
};