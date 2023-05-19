package ni.co.nico.set.nft;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ni.co.nico.set.ContractDTO;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class NftUtil {
    public static void addContractNamesFromJson(Nft nft, String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<ContractDTO> contracts = objectMapper.readValue(new File(filePath), new TypeReference<List<ContractDTO>>() {});
            for (ContractDTO contract : contracts) {
                nft.addContractName(contract.getContract_name());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
