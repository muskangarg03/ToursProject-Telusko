import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { useParams } from "react-router-dom";
import { userBook, confirmBooking } from "../../../Redux/API/API";

const BookTour = () => {
  const [numberOfTickets, setNumberOfTickets] = useState(0);
  const [bookingResponse, setBookingResponse] = useState(null);
  const dispatch = useDispatch();
  const { tourId } = useParams();

  const handleSubmit = (e) => {
    e.preventDefault();

    dispatch(userBook({ tourId, numberOfTickets }))
      .then((res) => {
        console.log(res, "booking response");
        if (!res.ok) {
          console.log("error occured");
        }
        setBookingResponse(res.payload.data);
      })
      .catch((error) => {
        console.error("Booking failed", error);
      });
  };

  const handleConfirmBooking = () => {
    if (!bookingResponse) return;

    console.log(bookingResponse.booking.bookingId, "booking ID");
    const bookingId = bookingResponse.booking.bookingId;
    // const paymentTransactionId = aaaaa;

    dispatch(
      confirmBooking({
        bookingId,
        // paymentTransactionId,
      })
    )
      .then((res) => {
        console.log(res, "confirmation response");
        if (res.ok) {
          alert("Booking confirmed successfully!");
        }
      })
      .catch((error) => {
        console.error("Booking confirmation failed", error);
        alert("Booking confirmation failed");
      });
  };

  return (
    <div className="max-w-md mx-auto p-4">
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label
            htmlFor="booktour"
            className="block text-sm font-medium text-gray-700"
          >
            Book Tour
          </label>
          <input
            type="number"
            id="booktour"
            value={numberOfTickets}
            onChange={(e) => setNumberOfTickets(Number(e.target.value))}
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50"
            min="0"
          />
        </div>
        <button
          type="submit"
          className="w-full bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600"
        >
          Book
        </button>
      </form>

      {bookingResponse && (
        <div className="mt-4 p-4 bg-gray-100 rounded">
          <h2 className="text-lg font-semibold">Booking Details</h2>
          <p>Booking ID: {bookingResponse.booking.bookingId}</p>
          <p>Total Price: ${bookingResponse.booking.totalPrice}</p>
          <p>Number of Tickets: {bookingResponse.booking.numberOfTickets}</p>

          <button
            onClick={handleConfirmBooking}
            className="mt-4 w-full bg-green-500 text-white py-2 px-4 rounded hover:bg-green-600"
          >
            Confirm Booking
          </button>
        </div>
      )}
    </div>
  );
};

export default BookTour;
