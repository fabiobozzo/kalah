package com.bol.assignment.repo;

import com.bol.assignment.model.Room;
import com.bol.assignment.model.RoomStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

  List<Room> findByStatusOrderByCreatedAtDesc(RoomStatus status);

  Boolean existsByNameAndStatus(String name, RoomStatus status);
}
