package academy.softserve.os.service.impl;

import academy.softserve.os.model.Price;
import academy.softserve.os.repository.PriceRepository;
import academy.softserve.os.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;

    @Override
    @Transactional
    public Price createPrice(Price price) {
        return priceRepository.save(price);
    }
}
