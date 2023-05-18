package ni.co.nico.service.Blockchain.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ni.co.nico.domain.UsedApp;
import ni.co.nico.repository.transaction.UsedAppRepository;
import ni.co.nico.service.Blockchain.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BlockchainServiceImpl implements BlockchainService {

    @Autowired
    private UsedAppRepository usedAppRepository;

    public void processTransactionData(String json, String userAddress) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode txnsNode = rootNode.get("txns");
            if (txnsNode != null && txnsNode.isArray()) {
                for (JsonNode txnNode : txnsNode) {
                    JsonNode predecessorAccountIdNode = txnNode.get("predecessor_account_id");
                    JsonNode receiverAccountIdNode = txnNode.get("receiver_account_id");

                    if (predecessorAccountIdNode != null && receiverAccountIdNode != null) {
                        String predecessorAccountId = predecessorAccountIdNode.asText();
                        String receiverAccountId = receiverAccountIdNode.asText();

                        UsedApp usedApp = new UsedApp();
                        usedApp.setUserAddress(userAddress);

                        if (predecessorAccountId.equals(userAddress)) {
                            usedApp.setAppName(receiverAccountId);
                        } else {
                            usedApp.setAppName(predecessorAccountId);
                        }
                        usedAppRepository.save(usedApp);
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // 예외 처리 로직 추가
        }
    }

}
