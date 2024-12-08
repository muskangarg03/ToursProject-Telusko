import React, { useState, useEffect, useRef } from "react";
import { Eye } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { Header, Footer, Banner } from "../../Reusable/Banner";
import { useDispatch } from "react-redux";
import { userTours } from "../../../Redux/API/API";
import whatsapp from "../../../assets/Images/whatsapp.png";

const UserDashboard = () => {
  const [tours, setTours] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [tourImages, setTourImages] = useState({});

  // New state for image handling

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleViewDetails = (tourId) => {
    navigate(`/user/tour/${tourId}`);
  };

  const handleBookTour = (tourId) => {
    navigate(`/user/book/${tourId}`);
  };

  const handleContactSupport = () => {
    window.open(
      "https://api.whatsapp.com/send?phone=916232412554&text=Hello, Welcome to tours chat support!",
      "_blank"
    );
  };

  useEffect(() => {
    const fetchTours = async () => {
      try {
        const response = await dispatch(userTours());
        const data = response.payload.data.availableTours;
        console.log(data, "data");
        setTours(data);

        const imageMap = {};
        data.forEach((tour) => {
          if (tour.tourImages && tour.tourImages.length > 0) {
            // Use the first image as the primary image
            imageMap[tour.id] = tour.tourImages[0];
          }
        });

        // Set the tour images
        setTourImages(imageMap);

        setLoading(false);
      } catch (error) {
        console.error("Error fetching tours:", error);
        setError("Failed to load tours");
        setLoading(false);
      }
    };

    fetchTours();
  }, [dispatch]);

  // if (loading) {
  //   return (
  //     <div className="flex justify-center items-center min-h-screen bg-gray-50">
  //       <div className="w-16 h-16 border-4 border-t-4 border-blue-500 rounded-full animate-spin"></div>
  //     </div>
  //   );
  // }

  if (error) {
    return (
      <div className="flex justify-center items-center min-h-screen bg-gray-50">
        <div className="bg-white p-8 rounded-xl shadow-lg text-center space-y-4 max-w-md w-full">
          <h2 className="text-2xl font-bold text-red-600">Error</h2>
          <p className="text-gray-700">{error}</p>
          <button
            onClick={() => window.location.reload()}
            className="w-full py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition-colors"
          >
            Retry
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col">
      <Header />
      <Banner />
      {/* {isEditModalOpen && <EditModal />} */}
      <div className="container mx-auto px-4 py-6 relative">
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-3xl font-bold text-gray-800">Tour Dashboard</h1>
        </div>

        {tours.length === 0 ? (
          <div className="text-center text-gray-500 py-10">
            No tours available. Click "Add New Tour" to get started.
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {tours.map((tour) => (
              <div
                key={tour.id}
                className="bg-white rounded-xl shadow-lg overflow-hidden transform transition hover:scale-105 hover:shadow-xl"
              >
                <div className="h-48 overflow-hidden relative">
                  {tourImages[tour.id] ? (
                    <img
                      src={tourImages[tour.id]}
                      alt={tour.tourName}
                      className="w-full h-full object-cover"
                    />
                  ) : (
                    <div className="w-full h-full bg-gray-200 flex items-center justify-center text-gray-500">
                      No Image
                    </div>
                  )}
                </div>

                <div className="p-4">
                  <h2 className="text-xl font-bold mb-2 text-gray-800 flex items-center justify-between">
                    <span className="truncate">{tour.tourName}</span>
                  </h2>

                  <p className="text-gray-600 mb-4 line-clamp-2">
                    {tour.tourDescription}
                  </p>

                  <div className="flex justify-between items-center">
                    <div>
                      <p className="text-green-600 font-semibold text-lg">
                        ${tour.price}
                      </p>
                      <p className="text-sm text-gray-500">
                        {tour.ticketsAvailable} tickets available
                      </p>
                    </div>
                    <div className="flex space-x-2">
                      <button
                        onClick={() => handleBookTour(tour.id)}
                        className="flex items-center px-3 py-2 bg-green-500 text-white rounded-md hover:bg-green-600 transition-colors"
                      >
                        Book
                      </button>
                      <button
                        onClick={() => handleViewDetails(tour.id)}
                        className="flex items-center px-3 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition-colors"
                      >
                        <Eye className="mr-2 w-4 h-4" />
                        Details
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}

        <button
          onClick={handleContactSupport}
          className="fixed bottom-6 right-6 bg-[#25D366] text-white rounded-full w-16 h-16 shadow-2xl hover:bg-[#128C7E] transition-colors z-50 flex items-center justify-center"
        >
          <div className="relative">
            {/* <MessageCircle className="w-8 h-8 fill-white" />
            <Phone className="absolute bottom-[-1px]  top-2.5 left-2 right-[-1px] w-4 h-4 bg-white text-[#25D366] rounded-full p-0.5 shadow-lg group-hover:text-[#128C7E]" /> */}
            <img
              src={whatsapp}
              alt="whatsapp"
              className="transform transition hover:scale-105 hover:shadow-xl"
            />
          </div>
        </button>
      </div>
      <Footer />
    </div>
  );
};

export default UserDashboard;
