package academy.softserve.os.service.impl;

import academy.softserve.os.model.Job;
import academy.softserve.os.model.Price;
import academy.softserve.os.repository.PriceRepository;
import academy.softserve.os.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;

    @Override
    @Transactional
    public Price createPrice(Price price) {
        return priceRepository.save(price);
    }

    @Override
    @Transactional
    public List<Price> getAllPrice(Job job) {
        return priceRepository.findAllByJobId(job.getId());
    }
}
