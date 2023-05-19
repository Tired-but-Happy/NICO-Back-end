package ni.co.nico.repository.transaction;

import ni.co.nico.domain.UsedApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsedAppRepository extends JpaRepository<UsedApp, Long>, UsedAppRepositoryCustom{
    List<UsedApp> findByUserAddress(String userAddress);

    UsedApp findByBlockHash(String blockHash);
    UsedApp findByUserAddressAndBlockHash(String userAddress,String blockHash);
}
