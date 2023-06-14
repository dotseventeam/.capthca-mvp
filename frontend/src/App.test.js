/**
 * @jest-environment jsdom
 */
import { render, screen } from '@testing-library/react';
import App from './App';
import Footer from './components/Footer'

test('renders app', () => {
  render(<App />);
  const logo = screen.getByAltText(/dotseven logo/i);
  expect(logo).toBeInTheDocument();
});

test('renders footer', () => {
  render(<Footer />);
  const footerText = screen.getByText('.7 (Dot Seven) - © 2023');
  expect(footerText).toBeInTheDocument();
});
