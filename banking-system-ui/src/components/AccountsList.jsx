import React, { useState, useEffect } from 'react';
import { accountsAPI } from '../services/api';
import AccountCard from './AccountCard';
import LoadingSpinner from './LoadingSpinner';
import ErrorMessage from './ErrorMessage';
import { RefreshCw } from 'lucide-react';

const AccountsList = () => {
  const [accounts, setAccounts] = useState([]);
  const [totalBalance, setTotalBalance] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [lastUpdated, setLastUpdated] = useState(null);

  const fetchAccounts = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await accountsAPI.getAllBalances();
      setAccounts(data.accounts);
      setTotalBalance(data.totalBalance);
      setLastUpdated(new Date().toLocaleTimeString());
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to fetch accounts. Please try again.');
      console.error('Error fetching accounts:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAccounts();
  }, []);

  const formatCurrency = (amount) => {
    return new Intl.NumberFormat('nl-NL', {
      style: 'currency',
      currency: 'EUR'
    }).format(amount);
  };

  if (loading) {
    return <LoadingSpinner />;
  }

  if (error) {
    return (
      <div>
        <ErrorMessage message={error} />
        <button
          onClick={fetchAccounts}
          className="mt-4 px-6 py-2 bg-rabobank-orange text-white rounded-lg hover:bg-orange-600 transition-colors"
        >
          Retry
        </button>
      </div>
    );
  }

  return (
    <div>
      <div className="mb-8 bg-white rounded-lg shadow-md p-6">
        <div className="flex items-center justify-between">
          <div>
            <h2 className="text-2xl font-bold text-rabobank-blue mb-2">
              Account Overview
            </h2>
            <p className="text-gray-600">
              {accounts.length} account{accounts.length !== 1 ? 's' : ''} • Total Balance: {' '}
              <span className="font-bold text-rabobank-blue">{formatCurrency(totalBalance)}</span>
            </p>
            {lastUpdated && (
              <p className="text-sm text-gray-500 mt-1">Last updated: {lastUpdated}</p>
            )}
          </div>
          <button
            onClick={fetchAccounts}
            className="flex items-center gap-2 px-4 py-2 bg-rabobank-orange text-white rounded-lg hover:bg-orange-600 transition-colors"
          >
            <RefreshCw size={18} />
            Refresh
          </button>
        </div>
      </div>

      {accounts.length === 0 ? (
        <div className="text-center py-12">
          <p className="text-gray-600">No accounts found</p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {accounts.map((account) => (
            <AccountCard key={account.accountId} account={account} />
          ))}
        </div>
      )}
    </div>
  );
};

export default AccountsList;