/**
 * @jest-environment jsdom
 */

import {remove_button} from "../src/client/js/remove_trip";

const fs = require('fs');
const path = require('path');

beforeAll(() => {
    // Load the actual HTML file into Jest's DOM
    const html = fs.readFileSync(path.resolve(__dirname, '../src/client/views/index.html'), 'utf8');
    document.body.innerHTML = html;
});

describe('remove_button', () => {
    let daysAway, temp, desc;

    beforeEach(() => {
        daysAway = document.getElementById("daysAway");

        temp = document.getElementById("temp");

        desc = document.getElementById("desc");
    });

    afterEach(() => {
        document.body.innerHTML = ''; // Clean up the DOM
    });

    test('clears and updates the trip details correctly', () => {
        // Call the function
        remove_button();

        // Assertions
        expect(daysAway.innerHTML).toBe('input a city and date please');
        expect(temp.innerHTML).toBe('high- , low-');
        expect(desc.innerHTML).toBe('what would the weather of the trip be?');
    });
});
