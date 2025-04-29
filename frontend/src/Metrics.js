import axios from 'axios';

export const trackRequest = (method, path) => {
    try {
        axios.post('/track-metric', { method, path });
    } catch (error) {
        console.error('Failed to track metric', error);
    }
};

