package ni.co.nico.service.Blockchain.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ni.co.nico.controller.user.UserController;
import ni.co.nico.domain.UsedApp;
import ni.co.nico.dto.mypage.UsedAppDTO;
import ni.co.nico.repository.transaction.UsedAppRepository;
import ni.co.nico.service.Blockchain.UsedAppService;
import ni.co.nico.set.defi.Defi;
import ni.co.nico.set.defi.DefiUtil;
import ni.co.nico.set.nft.Nft;
import ni.co.nico.set.nft.NftUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
public class UsedAppServiceImpl implements UsedAppService {
    private final Logger LOGGER = LoggerFactory.getLogger(UsedAppServiceImpl.class);
    @Autowired
    private UsedAppRepository usedAppRepository;

    public void processTransactionData(String json, String userAddress) {
        Defi defi = new Defi();
        DefiUtil.addContractNamesFromJson(defi, "src/main/java/ni/co/nico/set/defi/defiData.json");
        Set<String> defiSet = defi.getContractNames();

        Nft nft = new Nft();
        NftUtil.addContractNamesFromJson(nft, "src/main/java/ni/co/nico/set/nft/nftData.json");
        Set<String> nftSet = nft.getContractNames();
        LOGGER.info("[토큰에서 정보빼오기] 로그인유저 이름 :{}",defiSet);
        LOGGER.info("[토큰에서 정보빼오기] 로그인유저 이름 :{}",nftSet);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            if (rootNode.isArray()) {
                for (JsonNode txnNode : rootNode) {
                    JsonNode firstActionTypeNode = txnNode.get("first_action_type");
                    if (firstActionTypeNode != null && firstActionTypeNode.asText().equals("functionCall")) {
                        JsonNode signerNode = txnNode.get("signer");
                        JsonNode receiverNode = txnNode.get("receiver");

                        if (signerNode != null && receiverNode != null) {
                            String signer = signerNode.asText();
                            String receiver = receiverNode.asText();

                            UsedApp usedApp = new UsedApp();
                            usedApp.setUserAddress(userAddress);

                            if (!signer.equals(userAddress)) {
                                usedApp.setAppName(signer);
                            } else {
                                usedApp.setAppName(receiver);
                            }

                            // appCategory 설정
                            if (defiSet.contains(usedApp.getAppName())) {
                                usedApp.setAppCategory("defi");
                            } else if (nftSet.contains(usedApp.getAppName())) {
                                usedApp.setAppCategory("nft");
                            } else {
                                usedApp.setAppCategory("others");
                            }

                            usedAppRepository.save(usedApp);
                        }
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // 예외 처리 로직 추가
        }

    }

    public List<UsedAppDTO> getTopUsedAppsByUser(String userAddress) {
        Defi defi = new Defi();
        DefiUtil.addContractNamesFromJson(defi, "src/main/java/ni/co/nico/set/defi/defiData.json");
        Set<String> defiSet = defi.getContractNames();

        Nft nft = new Nft();
        NftUtil.addContractNamesFromJson(nft, "src/main/java/ni/co/nico/set/nft/nftData.json");
        Set<String> nftSet = nft.getContractNames();

        List<UsedApp> usedApps = usedAppRepository.findByUserAddress(userAddress);

        // appname을 횟수별로 매핑하는 맵
        Map<String, Integer> appNameCountMap = new HashMap<>();

        // appname 별로 사용 횟수 계산
        for (UsedApp usedApp : usedApps) {
            String appName = usedApp.getAppName();
            appNameCountMap.put(appName, appNameCountMap.getOrDefault(appName, 0) + 1);
        }

        // 횟수별로 내림차순으로 정렬하여 상위 5개만 추출
        List<Map.Entry<String, Integer>> sortedEntries = appNameCountMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toList());

        // 결과를 담을 리스트
        List<UsedAppDTO> usedAppDTOs = new ArrayList<>();

        // 정렬된 엔트리를 순회하며 DTO로 변환하여 리스트에 추가
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            String appName = entry.getKey();
            int count = entry.getValue();

            UsedAppDTO usedAppDTO = new UsedAppDTO();
            usedAppDTO.setUserAddress(userAddress);
            usedAppDTO.setAppName(appName);
            usedAppDTO.setCount(count);
            // appCategory 설정
            if (defiSet.contains(appName)) {
                usedAppDTO.setAppCategory("defi");
            } else if (nftSet.contains(appName)) {
                usedAppDTO.setAppCategory("nft");
            } else {
                usedAppDTO.setAppCategory("others");
            }
            usedAppDTOs.add(usedAppDTO);
        }

        return usedAppDTOs;
    }



}
