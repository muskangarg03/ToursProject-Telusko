import React, { useState, useEffect, useRef } from "react";
import { Eye } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { Header, Footer, Banner } from "../../Reusable/Banner";
import { useDispatch } from "react-redux";
import { userTours } from "../../../Redux/API/API";
import whatsapp from "../../../assets/Images/whatsapp.png";
import BookTour from "./BookTour";

const UserDashboard = () => {
  const [tours, setTours] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [tourImages, setTourImages] = useState({});
  const [selectedTour, setSelectedTour] = useState(null);


  // New state for image handling

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleViewDetails = (tourId) => {
    navigate(`/user/tour/${tourId}`);
  };

  const handleBookTour = (tour) => {
    setSelectedTour(tour);
  };

  const handleCloseModal = () => {
    setSelectedTour(null);
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
  //     <div className="flex items-center justify-center min-h-screen bg-gray-50">
  //       <div className="w-16 h-16 border-4 border-t-4 border-blue-500 rounded-full animate-spin"></div>
  //     </div>
  //   );
  // }

  if (error) {
    return (
      <div className="flex items-center justify-center min-h-screen bg-gray-50">
        <div className="w-full max-w-md p-8 space-y-4 text-center bg-white shadow-lg rounded-xl">
          <h2 className="text-2xl font-bold text-red-600">Error</h2>
          <p className="text-gray-700">{error}</p>
          <button
            onClick={() => window.location.reload()}
            className="w-full py-2 text-white transition-colors bg-blue-500 rounded-md hover:bg-blue-600"
          >
            Retry
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="flex flex-col min-h-screen bg-gray-50">
      <Header />
      <Banner />
      {/* {isEditModalOpen && <EditModal />} */}
      <div className="container relative px-4 py-6 mx-auto">
        <div className="flex items-center justify-between mb-6">
          <h1 className="text-3xl font-bold text-gray-800">Tour Dashboard</h1>
        </div>

        {tours.length === 0 ? (
          <div className="py-10 text-center text-gray-500">
            No tours available. Click "Add New Tour" to get started.
          </div>
        ) : (
          <div className="grid grid-cols-1 gap-6 md:grid-cols-2 lg:grid-cols-3">
            {tours.map((tour) => (
              <div
                key={tour.id}
                className="overflow-hidden transition transform bg-white shadow-lg rounded-xl hover:scale-105 hover:shadow-xl"
              >
                <div className="relative h-48 overflow-hidden">
                  {tourImages[tour.id] ? (
                    <img
                      src={tourImages[tour.id]}
                      alt={tour.tourName}
                      className="object-cover w-full h-full"
                    />
                  ) : (
                    <div className="flex items-center justify-center w-full h-full text-gray-500 bg-gray-200">
                      No Image
                    </div>
                  )}
                </div>

                <div className="p-4">
                  <h2 className="flex items-center justify-between mb-2 text-xl font-bold text-gray-800">
                    <span className="truncate">{tour.tourName}</span>
                  </h2>

                  <p className="mb-4 text-gray-600 line-clamp-2">
                    {tour.tourDescription}
                  </p>

                  <div className="flex items-center justify-between">
                    <div>
                      <p className="text-lg font-semibold text-green-600">
                        ${tour.price}
                      </p>
                      <p className="text-sm text-gray-500">
                        {tour.ticketsAvailable} tickets available
                      </p>
                    </div>
                    <div className="flex space-x-2">
                      <button
                        onClick={() => handleBookTour(tour)}
                        className="flex items-center px-3 py-2 text-white transition-colors bg-green-500 rounded-md hover:bg-green-600"
                      >
                        Book
                      </button>
                      <button
                        onClick={() => handleViewDetails(tour.id)}
                        className="flex items-center px-3 py-2 text-white transition-colors bg-blue-500 rounded-md hover:bg-blue-600"
                      >
                        <Eye className="w-4 h-4 mr-2" />
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
              className="transition transform hover:scale-105 hover:shadow-xl"
            />
          </div>
        </button>
      </div>
      <Footer />

      {selectedTour && (
        <BookTour
          tourId={selectedTour.id}
          isOpen={!!selectedTour}
          onClose={handleCloseModal}
          ticketsAvailable={selectedTour.ticketsAvailable}
        />
      )}

    </div>
  );
};

export default UserDashboard;
