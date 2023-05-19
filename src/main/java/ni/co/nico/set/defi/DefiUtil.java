package ni.co.nico.set.defi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ni.co.nico.set.ContractDTO;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DefiUtil {

    public static void addContractNamesFromJson(Defi defi, String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<ContractDTO> contracts = objectMapper.readValue(new File(filePath), new TypeReference<List<ContractDTO>>() {});
            for (ContractDTO contract : contracts) {
                defi.addContractName(contract.getContract_name());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
