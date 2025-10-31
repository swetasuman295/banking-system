import React from 'react';
import { CreditCard, User, Mail, CheckCircle, XCircle } from 'lucide-react';

const AccountCard = ({ account }) => {
  const formatCurrency = (amount) => {
    return new Intl.NumberFormat('nl-NL', {
      style: 'currency',
      currency: 'EUR'
    }).format(amount);
  };

  return (
    <div className="bg-white rounded-lg shadow-md hover:shadow-xl transition-shadow duration-300 overflow-hidden">
      <div className="bg-gradient-to-r from-rabobank-blue to-blue-900 text-white p-6">
        <div className="flex items-center justify-between mb-4">
          <h3 className="text-xl font-bold">{account.accountId}</h3>
          {account.active ? (
            <CheckCircle className="text-green-400" size={24} />
          ) : (
            <XCircle className="text-red-400" size={24} />
          )}
        </div>
        <div className="space-y-2">
          <p className="text-sm text-gray-300">IBAN</p>
          <p className="font-mono text-lg">{account.accountNumber}</p>
        </div>
      </div>

      <div className="p-6">
        <div className="mb-6">
          <p className="text-3xl font-bold text-rabobank-blue">
            {formatCurrency(account.balance)}
          </p>
          <p className="text-sm text-gray-500 mt-1">Current Balance</p>
        </div>

        <div className="space-y-3 border-t pt-4">
          <div className="flex items-center gap-2 text-gray-700">
            <User size={18} className="text-rabobank-orange" />
            <span className="font-medium">{account.userName}</span>
          </div>
          
          <div className="flex items-center gap-2 text-gray-600 text-sm">
            <Mail size={16} className="text-gray-400" />
            <span>{account.userEmail}</span>
          </div>

          <div className="flex items-center gap-2 pt-2">
            <CreditCard size={18} className="text-rabobank-orange" />
            <div className="flex-1">
              <p className="font-mono text-sm">{account.cardNumber}</p>
              <p className="text-xs text-gray-500">
                <span className={`inline-block px-2 py-1 rounded ${
                  account.cardType === 'CREDIT' 
                    ? 'bg-purple-100 text-purple-800' 
                    : 'bg-green-100 text-green-800'
                }`}>
                  {account.cardType}
                </span>
                {account.cardType === 'CREDIT' && (
                  <span className="ml-2 text-xs">• 1% fee applies</span>
                )}
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AccountCard;