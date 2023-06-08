/**
 * @jest-environment jsdom
 */
import { render, screen } from '@testing-library/react';
import App from './App';

test('renders app', () => {
  render(<App />);
  const logo = screen.getByAltText(/dotseven logo/i);
  expect(logo).toBeInTheDocument();
});
