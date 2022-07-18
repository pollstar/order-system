package academy.softserve.os.service;

import academy.softserve.os.model.Job;
import academy.softserve.os.model.Price;

import java.util.List;

public interface PriceService {
    Price createPrice(Price price);
    List<Price> getAllPrice(Job job);
}
