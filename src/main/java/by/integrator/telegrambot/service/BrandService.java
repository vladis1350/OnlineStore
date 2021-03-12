package by.integrator.telegrambot.service;

import by.integrator.telegrambot.bot.state.ClientBotState;
import by.integrator.telegrambot.model.Brand;
import by.integrator.telegrambot.model.Client;
import by.integrator.telegrambot.model.User;
import by.integrator.telegrambot.model.enums.BrandList;
import by.integrator.telegrambot.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandService {

    @Autowired private BrandRepository brandRepository;

    @Transactional
    public void save(Brand brand) {
        brandRepository.save(brand);
    }

    @Transactional
    public void delete(Brand brand) {
        brandRepository.delete(brand);
    }

    @Transactional
    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    public void createBrands() {
        if (getAll().isEmpty()) {
            for (BrandList brandList : BrandList.values()) {
                Brand brand = Brand.builder()
                        .name(brandList.getName())
                        .link(brandList.getLink())
                        .build();
                save(brand);
            }
        }
    }

}
