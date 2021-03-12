package by.integrator.telegrambot.service.bot;

import by.integrator.telegrambot.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitializationService {

    @Autowired private BrandService brandService;

    public void initialize() {
        brandService.createBrands();
    }

}
