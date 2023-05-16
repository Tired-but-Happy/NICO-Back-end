package ni.co.nico.service.style.impl;

import lombok.RequiredArgsConstructor;
import ni.co.nico.domain.Style;
import ni.co.nico.repository.style.StyleRepository;
import ni.co.nico.service.style.StyleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StyleServiceImpl implements StyleService {
    private final StyleRepository styleRepository;

    @Override
    public void getNewbieStyle(String userAddress) {
        Style style = new Style();
        style.setStyleName("Newbie");
        style.setOwnerAddress(userAddress);

        styleRepository.save(style);
    }

    @Override
    public List<String> getStyleList(String ownerAddress) {
        List<Style> styles = styleRepository.findByOwnerAddress(ownerAddress);
        List<String> styleList = new ArrayList<>();

        for (Style style : styles) {
            styleList.add(style.getStyleName());
        }

        return styleList;
    }


}
