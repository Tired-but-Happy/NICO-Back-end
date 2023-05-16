package ni.co.nico.repository.style;

import ni.co.nico.domain.Style;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StyleRepository extends JpaRepository<Style,Long>,StyleRepositoryCustom{
    List<Style> findByOwnerAddress(String ownerAddress);
}
