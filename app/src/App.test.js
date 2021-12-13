import {render, screen} from '@testing-library/react';
import App from './App';
import {MockedProvider} from '@apollo/react-testing'

test('renders learn react link', () => {
    render(
        <MockedProvider>
            <App/>
        </MockedProvider>
    );
});
