import React, { useState, useEffect, useRef } from "react";
import {
  BadgePlus,
  Eye,
  Pencil,
  Trash2,
  X,
  Upload,
  Plus,
  Minus,
} from "lucide-react";
import { useNavigate } from "react-router-dom";
import { Header, Footer, Banner } from "../../Reusable/Banner";
import { useDispatch } from "react-redux";
import { adminTours, deleteTour, updateTour } from "../../../Redux/API/API";
import axios from "axios";
import { toast } from "sonner";

const Dashboard = () => {
  const [tours, setTours] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [tourImages, setTourImages] = useState({});
  const [selectedTour, setSelectedTour] = useState(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [editFormData, setEditFormData] = useState({
    meals: [],
    activities: [],
  });

  // New state for image handling
  const [image1Preview, setImage1Preview] = useState(null);
  const [image2Preview, setImage2Preview] = useState(null);
  const [image1File, setImage1File] = useState(null);
  const [image2File, setImage2File] = useState(null);

  const image1Ref = useRef(null);
  const image2Ref = useRef(null);

  const baseUrl = import.meta.env.VITE_BASE_URL;
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleViewDetails = (tourId) => {
    navigate(`/admin/tour/${tourId}`);
  };

  const fetchImage = async (imagePath) => {
    try {
      const token = localStorage.getItem("token");
      const response = await fetch(`${baseUrl}/upload_images/${imagePath}`, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      return response.ok ? URL.createObjectURL(await response.blob()) : null;
    } catch (error) {
      console.error("Image fetch error:", error);
      return null;
    }
  };

  useEffect(() => {
    const fetchTours = async () => {
      try {
        const response = await dispatch(adminTours());
        const data = response.payload.data;
        console.log(data, "data");
        setTours(data);

        const imageMap = {};
        data.forEach((tour) => {
          // Assuming tourImages is an array of image URLs in the tour object
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

  const handleImageUpload = (e, setPreview, setFile) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setPreview(reader.result);
        setFile(file);
      };
      reader.readAsDataURL(file);
    }
  };

  const handleEditTour = (tour) => {
    setSelectedTour(tour);
    setEditFormData({
      tourName: tour.tourName,
      tourDescription: tour.tourDescription,
      tourGuide: tour.tourGuide,
      startDate: tour.startDate,
      endDate: tour.endDate,
      meals: tour.meals || [],
      activities: tour.activities || [],
      price: tour.price,
      ticketsAvailable: tour.ticketsAvailable,
      tourImages: tour.tourImages || [],
    });

    const fetchImagePreviews = async () => {
      if (tour.tourImages && tour.tourImages.length > 0) {
        setImage1Preview(tour.tourImages[0]);
        setImage2Preview(tour.tourImages[1]);
      } else {
        // No images available
        setImage1Preview(null);
        setImage2Preview(null);
      }
    };

    // Call the async function
    fetchImagePreviews();

    // Reset image files
    setImage1File(null);
    setImage2File(null);

    setIsEditModalOpen(true);
  };

  const handleUpdateTour = async () => {
    try {
      const formData = new FormData();

      // Append tour data
      formData.append("tour", JSON.stringify(editFormData));

      // Append images if they exist
      if (image1File) formData.append("image1", image1File);
      if (image2File) formData.append("image2", image2File);

      // Dispatch update tour action
      const updateResponse = await dispatch(
        updateTour({
          tourId: selectedTour.id,
          formData,
        })
      ).unwrap(); // Using unwrap() to throw an error if the action fails

      // Refresh tours after successful update
      const response = await dispatch(adminTours());
      setTours(response.payload.data);

      setIsEditModalOpen(false);
      toast.success("Tour Updated");
      setSelectedTour(null);
    } catch (error) {
      console.error("Error updating tour:", error);
      toast.error("Failed to update tour");
    }
  };

  const handleDelete = async (tour) => {
    // try {
    //   const token = localStorage.getItem("token");
    //   const response = await axios.delete(`${baseUrl}/admin/tours/${tour.id}`, {
    //     headers: {
    //       Authorization: `Bearer ${token}`,
    //     },
    //   });

    //   if (response.status === 204) {
    //     // Refresh tours after successful deletion
    //     const updatedResponse = await dispatch(adminTours());
    //     setTours(updatedResponse.payload.data);

    //     toast.success("Tour deleted successfully");
    //   }
    // } catch (error) {
    //   console.error("Error deleting tour:", error);
    //   toast.error("Failed to delete tour");
    // }
    try {
      const tourId = tour.id;
      const response = await dispatch(deleteTour(tourId));
      if (response.payload.status === 204) {
        const updatedResponse = dispatch(adminTours());
        setTours(updatedResponse.payload.data);

        toast.success("Tour deleted successfully");
      }
    } catch (error) {
      console.error("Error deleting tour:", error);
      toast.error("Failed to delete tour");
    }
  };

  const addMeal = () => {
    setEditFormData((prev) => ({
      ...prev,
      meals: [...(prev.meals || []), ""],
    }));
  };

  const removeMeal = (index) => {
    setEditFormData((prev) => ({
      ...prev,
      meals: prev.meals.filter((_, i) => i !== index),
    }));
  };

  const updateMeal = (index, value) => {
    setEditFormData((prev) => ({
      ...prev,
      meals: prev.meals.map((meal, i) => (i === index ? value : meal)),
    }));
  };

  const addActivity = () => {
    setEditFormData((prev) => ({
      ...prev,
      activities: [...(prev.activities || []), ""],
    }));
  };

  const removeActivity = (index) => {
    setEditFormData((prev) => ({
      ...prev,
      activities: prev.activities.filter((_, i) => i !== index),
    }));
  };

  const updateActivity = (index, value) => {
    setEditFormData((prev) => ({
      ...prev,
      activities: prev.activities.map((activity, i) =>
        i === index ? value : activity
      ),
    }));
  };

  const EditModal = () => (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white rounded-xl p-6 w-full max-w-lg relative max-h-[90vh] overflow-y-auto">
        <button
          onClick={() => setIsEditModalOpen(false)}
          className="absolute top-4 right-4"
        >
          <X className="text-gray-500 hover:text-gray-700" />
        </button>
        <h2 className="text-2xl font-bold mb-4">Edit Tour</h2>

        <div className="space-y-4">
          {/* Image Upload Section */}
          <div className="flex space-x-4">
            <div className="w-1/2">
              <label className="block mb-2 text-sm font-medium">Image 1</label>
              <div
                className="w-full h-40 border-2 border-dashed rounded-lg flex items-center justify-center relative cursor-pointer"
                onClick={() => image1Ref.current.click()}
              >
                <input
                  type="file"
                  ref={image1Ref}
                  className="hidden"
                  accept="image/*"
                  onChange={(e) =>
                    handleImageUpload(e, setImage1Preview, setImage1File)
                  }
                />
                {image1Preview ? (
                  <img
                    src={image1Preview}
                    alt="Preview"
                    className="w-full h-full object-cover rounded-lg"
                  />
                ) : (
                  <div className="flex flex-col items-center text-gray-500">
                    <Upload className="w-10 h-10 mb-2" />
                    <span>Upload Image 1</span>
                  </div>
                )}
              </div>
            </div>

            <div className="w-1/2">
              <label className="block mb-2 text-sm font-medium">Image 2</label>
              <div
                className="w-full h-40 border-2 border-dashed rounded-lg flex items-center justify-center relative cursor-pointer"
                onClick={() => image2Ref.current.click()}
              >
                <input
                  type="file"
                  ref={image2Ref}
                  className="hidden"
                  accept="image/*"
                  onChange={(e) =>
                    handleImageUpload(e, setImage2Preview, setImage2File)
                  }
                />
                {image2Preview ? (
                  <img
                    src={image2Preview}
                    alt="Preview"
                    className="w-full h-full object-cover rounded-lg"
                  />
                ) : (
                  <div className="flex flex-col items-center text-gray-500">
                    <Upload className="w-10 h-10 mb-2" />
                    <span>Upload Image 2</span>
                  </div>
                )}
              </div>
            </div>
          </div>

          {/* Rest of the form remains the same */}
          <input
            type="text"
            placeholder="Tour Name"
            value={editFormData.tourName}
            onChange={(e) =>
              setEditFormData({ ...editFormData, tourName: e.target.value })
            }
            className="w-full p-2 border rounded-md"
          />
          <textarea
            placeholder="Tour Description"
            value={editFormData.tourDescription}
            onChange={(e) =>
              setEditFormData({
                ...editFormData,
                tourDescription: e.target.value,
              })
            }
            className="w-full p-2 border rounded-md h-24"
          />

          <div>
            <div className="flex justify-between items-center mb-2">
              <label className="text-lg font-semibold">Meals</label>
              <button
                onClick={addMeal}
                className="flex items-center text-blue-600 hover:text-blue-800"
              >
                <Plus className="mr-1" size={16} /> Add Meal
              </button>
            </div>
            {editFormData.meals &&
              editFormData.meals.map((meal, index) => (
                <div key={index} className="flex items-center space-x-2 mb-2">
                  <input
                    type="text"
                    placeholder={`Meal ${index + 1}`}
                    value={meal}
                    onChange={(e) => updateMeal(index, e.target.value)}
                    className="flex-grow p-2 border rounded-md"
                  />
                  <button
                    onClick={() => removeMeal(index)}
                    className="text-red-500 hover:text-red-700"
                  >
                    <Minus />
                  </button>
                </div>
              ))}
          </div>

          {/* Activities Section */}
          <div>
            <div className="flex justify-between items-center mb-2">
              <label className="text-lg font-semibold">Activities</label>
              <button
                onClick={addActivity}
                className="flex items-center text-blue-600 hover:text-blue-800"
              >
                <Plus className="mr-1" size={16} /> Add Activity
              </button>
            </div>
            {editFormData.activities &&
              editFormData.activities.map((activity, index) => (
                <div key={index} className="flex items-center space-x-2 mb-2">
                  <input
                    type="text"
                    placeholder={`Activity ${index + 1}`}
                    value={activity}
                    onChange={(e) => updateActivity(index, e.target.value)}
                    className="flex-grow p-2 border rounded-md"
                  />
                  <button
                    onClick={() => removeActivity(index)}
                    className="text-red-500 hover:text-red-700"
                  >
                    <Minus />
                  </button>
                </div>
              ))}
          </div>

          <div className="grid grid-cols-2 gap-4">
            <input
              type="text"
              placeholder="Tour Guide"
              value={editFormData.tourGuide}
              onChange={(e) =>
                setEditFormData({ ...editFormData, tourGuide: e.target.value })
              }
              className="w-full p-2 border rounded-md"
            />
            <input
              type="number"
              placeholder="Price"
              value={editFormData.price}
              onChange={(e) =>
                setEditFormData({ ...editFormData, price: e.target.value })
              }
              className="w-full p-2 border rounded-md"
            />
          </div>
          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm mb-1">Start Date</label>
              <input
                type="date"
                value={editFormData.startDate}
                onChange={(e) =>
                  setEditFormData({
                    ...editFormData,
                    startDate: e.target.value,
                  })
                }
                className="w-full p-2 border rounded-md"
              />
            </div>
            <div>
              <label className="block text-sm mb-1">End Date</label>
              <input
                type="date"
                value={editFormData.endDate}
                onChange={(e) =>
                  setEditFormData({ ...editFormData, endDate: e.target.value })
                }
                className="w-full p-2 border rounded-md"
              />
            </div>
          </div>
          <input
            type="number"
            placeholder="Tickets Available"
            value={editFormData.ticketsAvailable}
            onChange={(e) =>
              setEditFormData({
                ...editFormData,
                ticketsAvailable: e.target.value,
              })
            }
            className="w-full p-2 border rounded-md"
          />
        </div>

        <div className="mt-6 flex justify-end space-x-4">
          <button
            onClick={() => setIsEditModalOpen(false)}
            className="px-4 py-2 bg-gray-200 text-gray-800 rounded-md"
          >
            Cancel
          </button>
          <button
            onClick={handleUpdateTour}
            className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600"
          >
            Update Tour
          </button>
        </div>
      </div>
    </div>
  );

  if (loading) {
    return (
      <div className="flex justify-center items-center min-h-screen bg-gray-50">
        <div className="w-16 h-16 border-4 border-t-4 border-blue-500 rounded-full animate-spin"></div>
      </div>
    );
  }

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
      {isEditModalOpen && <EditModal />}
      <div className="container mx-auto px-4 py-6 relative">
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-3xl font-bold text-gray-800">Tour Dashboard</h1>
          <button
            onClick={() => navigate("/admin/addtour")}
            className="flex items-center space-x-2 px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition-colors shadow-md"
          >
            <BadgePlus className="w-5 h-5" />
            <span>Add New Tour</span>
          </button>
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
                    <div className="flex space-x-3">
                      <Pencil
                        className="text-blue-500 size-5 cursor-pointer hover:text-blue-700"
                        onClick={() => handleEditTour(tour)}
                      />
                      <Trash2
                        className="text-red-500 size-5 cursor-pointer hover:text-red-700"
                        onClick={() => handleDelete(tour)}
                      />
                    </div>
                  </h2>

                  <p className="text-gray-600 mb-4 line-clamp-2">
                    {tour.tourDescription}
                  </p>

                  <div className="flex justify-between items-center">
                    <div>
                      <p className="text-blue-600 font-semibold text-lg">
                        ${tour.price}
                      </p>
                      <p className="text-sm text-gray-500">
                        {tour.ticketsAvailable} tickets available
                      </p>
                    </div>

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
            ))}
          </div>
        )}
      </div>

      <Footer />
    </div>
  );
};

export default Dashboard;
