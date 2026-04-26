import { useState } from 'react';
import { type Series } from '../types/series';

interface EditProps {
    series: Series;
    onSave: (originalTitle: string, updatedData: Series) => void;
    onCancel: () => void;
}

export const SeriesEditComponent = ({ series, onSave, onCancel }: EditProps) => {
    const [formData, setFormData] = useState<Series>({ ...series });

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        onSave(series.title, formData);
    };

    return (
        <div style={editContainerStyle}>
            <h3 style={{color: '#007bff'}}>Uredi seriju: {series.title}</h3>
            <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
                <input 
                    value={formData.title} 
                    onChange={e => setFormData({...formData, title: e.target.value})}
                    style={inputStyle} placeholder="Naslov"
                />
                <input 
                    value={formData.genre} 
                    onChange={e => setFormData({...formData, genre: e.target.value})}
                    style={inputStyle} placeholder="Žanr"
                />
                <input 
                    type="number" 
                    value={formData.totalSeasons} 
                    onChange={e => setFormData({...formData, totalSeasons: Number(e.target.value)})}
                    style={inputStyle}
                />
                <select 
                    value={formData.status} 
                    onChange={e => setFormData({...formData, status: e.target.value})}
                    style={inputStyle}
                >
                    <option value="WATCHING">WATCHING</option>
                    <option value="PLANNED">PLANNED</option>
                    <option value="COMPLETED">COMPLETED</option>
                </select>
                
                <div style={{ display: 'flex', gap: '10px' }}>
                    <button type="submit" style={saveButtonStyle}>Spremi promjene</button>
                    <button type="button" onClick={onCancel} style={cancelButtonStyle}>Odustani</button>
                </div>
            </form>
        </div>
    );
};

const editContainerStyle = { padding: '20px', backgroundColor: '#f9f9f9', border: '1px solid #ccc', borderRadius: '8px', marginTop: '20px' };
const inputStyle = { padding: '8px', borderRadius: '4px', border: '1px solid #ccc', backgroundColor: 'white', color: 'black' };
const saveButtonStyle = { padding: '10px', backgroundColor: '#018921', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' };
const cancelButtonStyle = { padding: '10px', backgroundColor: 'red', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' };