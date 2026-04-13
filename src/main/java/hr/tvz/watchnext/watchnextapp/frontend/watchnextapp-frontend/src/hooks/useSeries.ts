import { useState, useEffect } from 'react';
import { type Series } from '../types/series';
import { mockSeriess } from '../data/mockSeries';

export const useSeries = () => {
    const [data, setData] = useState<Series[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                setLoading(true);

                await new Promise((resolve) => setTimeout(resolve, 1500));

                setData(mockSeriess);
                setError(null);
            } catch (err) {
                setError("Došlo je do pogreške prilikom dohvaćanja podataka.");
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, []);

    return { data, loading, error };
};