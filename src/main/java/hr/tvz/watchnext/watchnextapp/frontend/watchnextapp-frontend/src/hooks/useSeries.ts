import { useState, useEffect, useCallback } from 'react';
import { type Series } from '../types/series';
import API from '../api/axiosConfig';

export const useSeries = () => {
    const [data, setData] = useState<Series[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    const removeLocally = (title: string) => {
        setData(prevData => prevData.filter(s => s.title !== title));
    };

    const fetchData = useCallback(async () => {
        try {
            setLoading(true);
            
            const response = await API.get<Series[]>('/series');
            
            setData(response.data);
            setError(null);
        } catch (err: any) {
            setError(err.message || 'Došlo je do pogreške.');
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        fetchData();
    }, [fetchData]);

    return { data, loading, error, refreshData: fetchData, removeLocally };
};