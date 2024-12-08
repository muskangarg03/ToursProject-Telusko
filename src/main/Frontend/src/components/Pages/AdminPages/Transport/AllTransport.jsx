import React, { useState, useEffect } from "react";
import { useDispatch } from "react-redux";
import { adminTransport } from "../../../../Redux/API/API";
import { Train, Bus, Clock, Info } from "lucide-react";

const TransportCard = ({ transport }) => {
  const getTransportIcon = (type) => {
    switch (type) {
      case "Train":
        return <Train className="text-blue-600 w-6 h-6" />;
      case "Bus":
        return <Bus className="text-green-600 w-6 h-6" />;
      default:
        return <Info className="text-gray-500 w-6 h-6" />;
    }
  };

  return (
    <div className="bg-white shadow-md rounded-lg p-4 mb-4 border border-gray-100 hover:shadow-xl transition-all duration-300 ease-in-out">
      <div className="flex items-center mb-3">
        {getTransportIcon(transport.transportType)}
        <h3 className="ml-3 text-lg font-semibold text-gray-800">
          {transport.transportName}
        </h3>
      </div>
      <div className="space-y-2">
        <div className="flex items-center text-gray-600">
          <Clock className="w-4 h-4 mr-2" />
          <span className="text-sm">
            Estimated Travel Time: {transport.estimatedTravelTime}
          </span>
        </div>
        <div className="bg-gray-50 p-2 rounded text-sm text-gray-700">
          <p>
            {transport.transportDescription ||
              "No description available for this transport option."}
          </p>
        </div>
        <div className="flex justify-between items-center mt-3">
          <span className="text-xs text-gray-500 bg-gray-100 px-2 py-1 rounded">
            {transport.transportType}
          </span>
          {/* <span className="text-xs text-gray-500">ID: {transport.id}</span> */}
        </div>
      </div>
    </div>
  );
};

const AllTransport = () => {
  const dispatch = useDispatch();
  const [transports, setTransports] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    dispatch(adminTransport())
      .then((response) => {
        if (response.payload && response.payload.data) {
          setTransports(response.payload.data);
          setLoading(false);
        } else {
          setError("No transport data found");
          setLoading(false);
        }
      })
      .catch((err) => {
        setError("Error fetching transport data");
        setLoading(false);
        console.error(err);
      });
  }, [dispatch]);

  if (loading) {
    return (
      <div className="flex justify-center items-center h-screen">
        <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-blue-500"></div>
      </div>
    );
  }

  if (error) {
    return (
      <div
        className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative"
        role="alert"
      >
        {error}
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold text-gray-800 mb-6 text-center">
        Transport Options
      </h1>
      {transports.length === 0 ? (
        <div className="text-center text-gray-500">
          No transport options available
        </div>
      ) : (
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
          {transports.map((transport) => (
            <TransportCard key={transport.id} transport={transport} />
          ))}
        </div>
      )}
    </div>
  );
};

export default AllTransport;
