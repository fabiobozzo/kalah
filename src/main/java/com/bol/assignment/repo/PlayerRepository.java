package com.bol.assignment.repo;

import com.bol.assignment.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {

  Player findByNameAndEmail(String name, String email);

}
