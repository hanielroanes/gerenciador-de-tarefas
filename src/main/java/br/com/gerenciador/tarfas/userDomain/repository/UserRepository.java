package br.com.gerenciador.tarfas.userDomain.repository;

import br.com.gerenciador.tarfas.userDomain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailIgnoreCase(String username);
}
