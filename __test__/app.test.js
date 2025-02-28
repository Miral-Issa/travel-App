/**
 * @jest-environment jsdom
 */

//import { getImage } from '../src/client/js/app';

const fs = require('fs');
const path = require('path');

beforeAll(() => {
    // Load the actual HTML file into Jest's DOM
    const html = fs.readFileSync(path.resolve(__dirname, '../src/client/views/index.html'), 'utf8');
    document.body.innerHTML = html;
});

beforeAll(async () => {
    const module = await import('../src/client/js/app'); // Dynamic import
    getImage = module.getImage; // Assign only the function we need
});

global.fetch = jest.fn(); // Mock fetch

describe('getImage', () => {
    let cityInput, imageElement, saveButton;

    beforeEach(() => {
        // Mock DOM elements
        cityInput = document.getElementById("cityName");
        cityInput.value = "London";

        imageElement = document.getElementById("tripImage");

        saveButton = document.getElementById("save");
    });

    afterEach(() => {
        document.body.innerHTML = ''; // Clean up DOM
        jest.clearAllMocks(); // Reset mocks
    });

    test('updates image source on successful API response', async () => {
        // Mock API response
        fetch.mockResolvedValue({
            json: jest.fn().mockResolvedValue({
                hits: [{ largeImageURL: 'https://example.com/london.jpg' }]
            })
        });

        await getImage('https://api.example.com/', 'testKey');

        expect(fetch).toHaveBeenCalledWith('https://api.example.com/testKey&q=London');
        expect(imageElement.src).toBe('https://example.com/london.jpg');
    });
});
