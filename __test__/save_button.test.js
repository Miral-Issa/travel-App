/**
 * @jest-environment jsdom
 */


const fs = require('fs');
const path = require('path');

beforeAll(() => {
    // Load the actual HTML file into Jest's DOM
    const html = fs.readFileSync(path.resolve(__dirname, '../src/client/views/index.html'), 'utf8');
    document.body.innerHTML = html;
});

beforeAll(async () => {
    const module = await import('../src/client/js/save_trip'); // Dynamic import
    save_button = module.save_button; // Assign only the function we need
});


describe('save_button', () => {
    let cityInput, dateInput;

    beforeEach(() => {
        // Mock DOM elements
        cityInput = document.getElementById("cityName");
        cityInput.value = "Paris";

        dateInput = document.getElementById("departingDate");
        dateInput.value = '2025-06-15';

        // Mock fetch function
        global.fetch = jest.fn(() =>
            Promise.resolve({
                json: () => Promise.resolve({ city: 'Paris', date: '2025-06-15', forecast: 'Sunny' })
            })
        );
    });

    afterEach(() => {
        document.body.innerHTML = ''; // Clean up the DOM
        jest.clearAllMocks(); // Clear mocks after each test
    });

    test('sends city and date to the server and returns weather forecast', async () => {
        const result = await save_button();

        // Ensure fetch was called with correct parameters
        expect(global.fetch).toHaveBeenCalledWith(
            'http://localhost:8000/save_trip',
            expect.objectContaining({
                method: 'POST',
                credentials: 'same-origin',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ city: 'Paris', date: '2025-06-15' })
            })
        );

        // Ensure the function returns the expected forecast
        expect(result).toEqual({ city: 'Paris', date: '2025-06-15', forecast: 'Sunny' });
    });
});
