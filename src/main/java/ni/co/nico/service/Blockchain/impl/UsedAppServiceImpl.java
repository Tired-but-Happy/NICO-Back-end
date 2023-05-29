package ni.co.nico.service.Blockchain.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ni.co.nico.Util.TimestampConverter;
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

import java.time.LocalDate;
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
        LOGGER.info("[토큰에서 정보빼오기] 로그인유저 이름 :{}", defiSet);
        LOGGER.info("[토큰에서 정보빼오기] 로그인유저 이름 :{}", nftSet);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            if (rootNode.isArray()) {
                for (JsonNode txnNode : rootNode) {
                    JsonNode firstActionTypeNode = txnNode.get("first_action_type");
                    JsonNode timestampNode = txnNode.get("transaction_timestamp");

                    if (timestampNode != null && firstActionTypeNode != null && firstActionTypeNode.asText().equals("functionCall")) {
                        String timestampStr = timestampNode.asText();
                        List<String> yearMonth = TimestampConverter.convertTimestamp(timestampStr);
                        JsonNode signerNode = txnNode.get("signer");
                        JsonNode receiverNode = txnNode.get("receiver");
                        JsonNode blockHashNode = txnNode.get("block_hash");
                        if (signerNode != null && receiverNode != null) {
                            String signer = signerNode.asText();
                            String receiver = receiverNode.asText();
                            String blockHash = blockHashNode.asText();

                            UsedApp usedApp = new UsedApp();
                            usedApp.setUserAddress(userAddress);
                            usedApp.setBlockHash(blockHash);
                            usedApp.setCreatedYear(yearMonth.get(0));
                            usedApp.setCreatedMonth(yearMonth.get(1));

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

                            UsedApp existingUsedApp = usedAppRepository.findByUserAddressAndBlockHash(userAddress, blockHash);
                            if (existingUsedApp == null) {
                                // "block_hash" 값이 데이터베이스에 존재하지 않는 경우에 대한 추가 로직을 여기에 작성합니다.
                                // 예: 로그 출력, 이메일 알림 등
                                LOGGER.info("Block hash does not exist in the database: {}", usedApp.getBlockHash());

                                usedAppRepository.save(usedApp);
                            }
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


        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentQuarter = (currentDate.getMonthValue() - 1) / 3 + 1;
        LocalDate quarterStart = LocalDate.of(currentYear, (currentQuarter - 1) * 3 + 1, 1);
        LocalDate quarterEnd = quarterStart.plusMonths(3).minusDays(1);


        List<UsedApp> usedApps = usedAppRepository.findByUserAddress(userAddress);
        List<UsedApp> usedAppsInQuarter = usedApps.stream()
                .filter(usedApp -> {
                    LocalDate appDate = LocalDate.parse(usedApp.getCreatedDate()); // Assuming createdDate is the field representing the date
                    return !appDate.isBefore(quarterStart) && !appDate.isAfter(quarterEnd);
                })
                .collect(Collectors.toList());

        // 현재 분기 내 개수로 매핑 앱 이름 매핑
        Map<String, Integer> appNameCountMapInQuarter = new HashMap<>();

        // 현재 분기 내에서 앱 이름당 사용 횟수 계산
        for (UsedApp usedApp : usedAppsInQuarter) {
            String appName = usedApp.getAppName();
            appNameCountMapInQuarter.put(appName, appNameCountMapInQuarter.getOrDefault(appName, 0) + 1);
        }

        // 개수별로 내림차순으로 정렬하고 상위 5개만 추출
        List<Map.Entry<String, Integer>> sortedEntries = appNameCountMapInQuarter.entrySet()
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
            usedAppDTO.setYear(currentYear);
            usedAppDTO.setQuarter(currentQuarter);
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
