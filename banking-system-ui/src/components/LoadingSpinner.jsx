import React from 'react';
import { Loader2 } from 'lucide-react';

const LoadingSpinner = () => {
  return (
    <div className="flex flex-col items-center justify-center py-12">
      <Loader2 className="animate-spin text-rabobank-orange mb-4" size={48} />
      <p className="text-gray-600">Loading accounts...</p>
    </div>
  );
};

export default LoadingSpinner;