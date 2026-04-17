import { useState } from 'react';

interface SeriesAddProps {
    onSeriesAdded: () => void;
}

export const SeriesAddComponent = ({ onSeriesAdded }: SeriesAddProps) => {
    const [title, setTitle] = useState('');
    const [genre, setGenre] = useState('');
    const [totalSeasons, setTotalSeasons] = useState(1);
    const [status, setStatus] = useState('WATCHING');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        const newSeries = { 
        title: title,
        genre: genre,
        totalSeasons: totalSeasons,
        status: status,
        imdbRating: 0.0,
        internalCode: "S" + Math.floor(Math.random() * 1000).toString().padStart(3, '0'),
        awardCount: 0
    };

        try {
            const response = await fetch('http://localhost:8080/api/series', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newSeries),
            });

            if (response.ok) {
                setTitle('');
                setGenre('');
                setTotalSeasons(1);
                setStatus('WATCHING');
                
                onSeriesAdded();
                alert('Serija uspješno dodana!');
            } else {
                const errorData = await response.json();
                alert(`Greška: ${errorData.message}`);  
            }
        } catch (error) {
            console.error("Error adding series:", error);
        }
    };

   return (
        <div style={containerStyle}>
            <div style={headerStyle}>
                Dodaj novu seriju
            </div>

            <form onSubmit={handleSubmit} style={formStyle}>
                <div style={inputGroupStyle}>
                    <label style={labelStyle}>Naslov serije</label>
                    <input 
                        type="text" placeholder="npr. The Bear" value={title} 
                        onChange={(e) => setTitle(e.target.value)} required 
                        style={inputStyle}
                    />
                </div>

                <div style={inputGroupStyle}>
                    <label style={labelStyle}>Žanr</label>
                    <input 
                        type="text" placeholder="npr. Drama" value={genre} 
                        onChange={(e) => setGenre(e.target.value)} required 
                        style={inputStyle}
                    />
                </div>

                <div style={{ display: 'flex', gap: '15px' }}>
                    <div style={{ ...inputGroupStyle, flex: 1 }}>
                        <label style={labelStyle}>Broj sezona</label>
                        <input 
                            type="number" value={totalSeasons} 
                            onChange={(e) => setTotalSeasons(Number(e.target.value))} min="1" required 
                            style={inputStyle}
                        />
                    </div>

                    <div style={{ ...inputGroupStyle, flex: 1 }}>
                        <label style={labelStyle}>Status gledanja</label>
                        <select value={status} onChange={(e) => setStatus(e.target.value)} style={inputStyle}>
                            <option value="WATCHING">WATCHING</option>
                            <option value="PLANNED">PLANNED</option>
                            <option value="COMPLETED">COMPLETED</option>
                        </select>
                    </div>
                </div>

                <button type="submit" style={buttonStyle}>
                    Spremi u listu
                </button>
            </form>
        </div>
    );
};

const containerStyle: React.CSSProperties = {
    marginTop: '30px',
    borderRadius: '8px',
    overflow: 'hidden',
    boxShadow: '0 4px 15px rgba(0,0,0,0.2)',
    backgroundColor: '#ffffff',
    border: '1px solid #ddd'
};

const headerStyle: React.CSSProperties = {
    backgroundColor: '#007bff',
    color: 'white',
    padding: '12px 20px',
    fontSize: '1.1rem',
    fontWeight: 'bold'
};

const formStyle: React.CSSProperties = {
    padding: '20px',
    display: 'flex',
    flexDirection: 'column',
    gap: '15px'
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
    color: 'black',
    padding: '10px',
    borderRadius: '5px',
    border: '1px solid #ccc',
    fontSize: '1rem',
    outline: 'none',
    backgroundColor: '#f8f9fa'
};

const buttonStyle: React.CSSProperties = {
    marginTop: '10px',
    padding: '12px',
    backgroundColor: '#28a745',
    color: 'white',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
    fontSize: '1rem',
    fontWeight: 'bold',
    transition: 'background-color 0.2s'
};