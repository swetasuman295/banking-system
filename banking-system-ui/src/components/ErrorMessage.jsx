import React from 'react';
import { AlertCircle } from 'lucide-react';

const ErrorMessage = ({ message }) => {
  return (
    <div className="bg-red-50 border border-red-200 rounded-lg p-6 flex items-start gap-3">
      <AlertCircle className="text-red-600 flex-shrink-0" size={24} />
      <div>
        <h3 className="text-red-800 font-semibold mb-1">Error Loading Accounts</h3>
        <p className="text-red-700">{message}</p>
      </div>
    </div>
  );
};

export default ErrorMessage;