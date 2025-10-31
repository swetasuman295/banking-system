import React from 'react';
import AccountsList from './components/AccountsList';
import Header from './components/Header';

function App() {
  return (
    <div className="min-h-screen bg-gray-100">
      <Header />
      <main className="container mx-auto px-4 py-8">
        <AccountsList />
      </main>
    </div>
  );
}

export default App;