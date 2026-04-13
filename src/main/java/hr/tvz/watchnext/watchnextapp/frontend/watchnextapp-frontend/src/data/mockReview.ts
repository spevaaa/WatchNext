import { type Review } from '../types/review';

export const mockReview: Review[] = [
    { id: 1, seriesTitle: "Breaking Bad", author: "Ivan", comment: "Najbolja serija ikad!", score: 10 },
    { id: 2, seriesTitle: "Breaking Bad", author: "Ana", comment: "Malo spora na početku, ali isplati se.", score: 9 },
    { id: 3, seriesTitle: "The Bear", author: "Marko", comment: "Stresno za gledati, ali vrhunska gluma.", score: 9 },
    { id: 4, seriesTitle: "The Bear", author: "Maja", comment: "Ne mogu prestati gledati!", score: 10 },
    { id: 5, seriesTitle: "Stranger Things", author: "Lucija", comment: "Super nostalgija za 80-ima.", score: 8 },
    { id: 6, seriesTitle: "Stranger Things", author: "Petar", comment: "Previše klišeja, ali zabavno.", score: 7 },
    
];