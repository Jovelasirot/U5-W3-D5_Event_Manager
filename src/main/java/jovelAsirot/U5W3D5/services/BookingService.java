package jovelAsirot.U5W3D5.services;

import jovelAsirot.U5W3D5.entities.Booking;
import jovelAsirot.U5W3D5.repositories.BookingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    BookingDAO bDAO;

    public Page<Booking> getAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return this.bDAO.findAll(pageable);
    }

}
