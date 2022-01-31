package services;

import org.testng.annotations.Test;

public class Main {
    @Test
    public static  void Main_Booking(){

        CreateToken createToken =  new CreateToken();
        String token = createToken.postCrateToken();
        GetBookingAll_Ids getBookingAll_ids = new GetBookingAll_Ids();
        getBookingAll_ids.Get_All_Bookings();
        CreateNewBooking createNewBooking = new CreateNewBooking();
        String booking_id = createNewBooking.Post_Create_Booking();
        ShowBookingWithID showBookingWithID = new ShowBookingWithID();
        showBookingWithID.ShowBooking(booking_id);
        UpdateCreatedBooking updateCreatedBooking = new UpdateCreatedBooking();
        updateCreatedBooking.Put_Update_Created_Booking(token,booking_id);
        Delete_Created_Booking delete_created_booking = new Delete_Created_Booking();
        delete_created_booking.deleteBooking(token,booking_id);
        ShowBookingWithID showBookingWithID2 = new ShowBookingWithID();
        showBookingWithID2.ShowBooking(booking_id);



    }
}
