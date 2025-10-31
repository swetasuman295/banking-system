import React from 'react';
import { Wallet } from 'lucide-react';

const Header = () => {
  return (
    <header className="bg-rabobank-blue text-white shadow-lg">
      <div className="container mx-auto px-4 py-6">
        <div className="flex items-center gap-3">
          <Wallet size={32} className="text-rabobank-orange" />
          <div>
            <h1 className="text-2xl font-bold">Rabobank Banking System</h1>
            <p className="text-sm text-gray-300">Account Overview</p>
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;